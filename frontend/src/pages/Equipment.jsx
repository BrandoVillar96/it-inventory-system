import { useEffect, useState, useMemo } from "react";
import { Table, Button, Modal, Form, Input, Select, DatePicker, Space, message, Popconfirm, Tag } from "antd";
import { PlusOutlined, EditOutlined, DeleteOutlined, ClearOutlined } from "@ant-design/icons";
import dayjs from "dayjs";
import { equipmentService } from "../api/equipmentService";
import { branchService } from "../api/branchService";
import { personService } from "../api/personService";

const TYPE_LABELS = {
    PRINTER: "Impresora",
    COMPUTER: "Computadora",
    MONITOR: "Monitor",
    KEYBOARD: "Teclado",
    MOUSE: "Mouse",
    CAMERA: "Cámara",
    PERIPHERAL: "Periférico",
    OTHER: "Otro",
};

const STATUS_LABELS = {
    ACTIVE: { text: "Activo", color: "green" },
    MAINTENANCE: { text: "En Mantenimiento", color: "orange" },
    NEEDS_MAINTENANCE: { text: "Requiere Mantenimiento", color: "gold" },
    NEEDS_REPLACEMENT: { text: "Requiere Reemplazo", color: "red" },
    RETIRED: { text: "Retirado", color: "default" },
};

const Equipment = () => {
    const [equipment, setEquipment] = useState([]);
    const [branches, setBranches] = useState([]);
    const [persons, setPersons] = useState([]);
    const [loading, setLoading] = useState(false);
    const [modalOpen, setModalOpen] = useState(false);
    const [editing, setEditing] = useState(null);
    const [form] = Form.useForm();

    // Estados de los filtros
    const [filterType, setFilterType] = useState(null);
    const [filterBranch, setFilterBranch] = useState(null);
    const [filterAssigned, setFilterAssigned] = useState(null);
    const [filterStatus, setFilterStatus] = useState(null);

    const loadData = async () => {
        setLoading(true);
        try {
            const [equipRes, branchesRes, personsRes] = await Promise.all([
                equipmentService.getAll(),
                branchService.getAll(),
                personService.getAll(),
            ]);
            setEquipment(equipRes.data);
            setBranches(branchesRes.data);
            setPersons(personsRes.data);
        } catch {
            message.error("Error al cargar datos");
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        loadData();
    }, []);

    // Aplica los filtros sobre la lista de equipos
    const filteredEquipment = useMemo(() => {
        return equipment.filter((e) => {
            if (filterType && e.type !== filterType) return false;
            if (filterBranch && e.branchId !== filterBranch) return false;
            if (filterAssigned && e.assignedToId !== filterAssigned) return false;
            if (filterStatus && e.status !== filterStatus) return false;
            return true;
        });
    }, [equipment, filterType, filterBranch, filterAssigned, filterStatus]);

    const clearFilters = () => {
        setFilterType(null);
        setFilterBranch(null);
        setFilterAssigned(null);
        setFilterStatus(null);
    };

    const hasActiveFilters = filterType || filterBranch || filterAssigned || filterStatus;

    const openCreate = () => {
        setEditing(null);
        form.resetFields();
        setModalOpen(true);
    };

    const openEdit = (record) => {
        setEditing(record);
        form.setFieldsValue({
            type: record.type,
            brand: record.brand,
            model: record.model,
            serialNumber: record.serialNumber,
            status: record.status,
            branchId: record.branchId,
            assignedToId: record.assignedToId,
            deliveryDate: record.deliveryDate ? dayjs(record.deliveryDate) : null,
        });
        setModalOpen(true);
    };

    const handleSubmit = async () => {
        try {
            const values = await form.validateFields();
            const payload = {
                ...values,
                deliveryDate: values.deliveryDate ? values.deliveryDate.format("YYYY-MM-DD") : null,
            };
            if (editing) {
                await equipmentService.update(editing.id, payload);
                message.success("Equipo actualizado");
            } else {
                await equipmentService.create(payload);
                message.success("Equipo creado");
            }
            setModalOpen(false);
            loadData();
        } catch (err) {
            if (err.response) message.error(err.response.data.message || "Error al guardar");
        }
    };

    const handleDelete = async (id) => {
        try {
            await equipmentService.delete(id);
            message.success("Equipo retirado");
            loadData();
        } catch {
            message.error("Error al eliminar");
        }
    };

    const columns = [
        {
            title: "Tipo",
            dataIndex: "type",
            key: "type",
            render: (type) => TYPE_LABELS[type] || type,
        },
        { title: "Marca", dataIndex: "brand", key: "brand" },
        { title: "Modelo", dataIndex: "model", key: "model" },
        { title: "No. Serie", dataIndex: "serialNumber", key: "serialNumber" },
        {
            title: "Estado",
            dataIndex: "status",
            key: "status",
            render: (status) => {
                const s = STATUS_LABELS[status] || { text: status, color: "default" };
                return <Tag color={s.color}>{s.text}</Tag>;
            },
        },
        { title: "Sucursal", dataIndex: "branchName", key: "branchName" },
        { title: "Asignado a", dataIndex: "assignedToName", key: "assignedToName" },
        {
            title: "Acciones",
            key: "actions",
            render: (_, record) => (
                <Space>
                    <Button icon={<EditOutlined />} onClick={() => openEdit(record)} size="small">
                        Editar
                    </Button>
                    <Popconfirm
                        title="¿Retirar este equipo?"
                        onConfirm={() => handleDelete(record.id)}
                        okText="Sí"
                        cancelText="No"
                    >
                        <Button icon={<DeleteOutlined />} danger size="small">
                            Retirar
                        </Button>
                    </Popconfirm>
                </Space>
            ),
        },
    ];

    return (
        <div>
            <div style={{ display: "flex", justifyContent: "space-between", marginBottom: 16 }}>
                <h2>Equipos</h2>
                <Button type="primary" icon={<PlusOutlined />} onClick={openCreate}>
                    Nuevo Equipo
                </Button>
            </div>

            {/* Barra de filtros */}
            <Space wrap style={{ marginBottom: 16 }}>
                <Select
                    placeholder="Filtrar por tipo"
                    allowClear
                    style={{ width: 200 }}
                    value={filterType}
                    onChange={setFilterType}
                    options={Object.entries(TYPE_LABELS).map(([value, label]) => ({ value, label }))}
                />
                <Select
                    placeholder="Filtrar por sucursal"
                    allowClear
                    style={{ width: 200 }}
                    value={filterBranch}
                    onChange={setFilterBranch}
                    options={branches.map((b) => ({ value: b.id, label: b.name }))}
                />
                <Select
                    placeholder="Filtrar por asignado"
                    allowClear
                    style={{ width: 200 }}
                    value={filterAssigned}
                    onChange={setFilterAssigned}
                    options={persons.map((p) => ({ value: p.id, label: p.fullName }))}
                />
                <Select
                    placeholder="Filtrar por estado"
                    allowClear
                    style={{ width: 200 }}
                    value={filterStatus}
                    onChange={setFilterStatus}
                    options={Object.entries(STATUS_LABELS).map(([value, { text }]) => ({ value, label: text }))}
                />
                {hasActiveFilters && (
                    <Button icon={<ClearOutlined />} onClick={clearFilters}>
                        Limpiar filtros
                    </Button>
                )}
            </Space>

            <Table
                columns={columns}
                dataSource={filteredEquipment}
                rowKey="id"
                loading={loading}
                pagination={{ pageSize: 10 }}
                scroll={{ x: 1000 }}
            />

            <Modal
                title={editing ? "Editar Equipo" : "Nuevo Equipo"}
                open={modalOpen}
                onOk={handleSubmit}
                onCancel={() => setModalOpen(false)}
                okText="Guardar"
                cancelText="Cancelar"
                width={600}
            >
                <Form form={form} layout="vertical">
                    <Form.Item
                        name="type"
                        label="Tipo de Equipo"
                        rules={[{ required: true, message: "Selecciona el tipo" }]}
                    >
                        <Select
                            placeholder="Selecciona tipo"
                            options={Object.entries(TYPE_LABELS).map(([value, label]) => ({ value, label }))}
                        />
                    </Form.Item>
                    <Form.Item name="brand" label="Marca">
                        <Input placeholder="HP, Dell, Lenovo..." />
                    </Form.Item>
                    <Form.Item name="model" label="Modelo">
                        <Input placeholder="LaserJet Pro M404n" />
                    </Form.Item>
                    <Form.Item name="serialNumber" label="Número de Serie">
                        <Input placeholder="SN-12345" />
                    </Form.Item>
                    <Form.Item name="status" label="Estado">
                        <Select
                            placeholder="Selecciona estado"
                            options={Object.entries(STATUS_LABELS).map(([value, { text }]) => ({
                                value,
                                label: text,
                            }))}
                        />
                    </Form.Item>
                    <Form.Item
                        name="branchId"
                        label="Sucursal"
                        rules={[{ required: true, message: "Selecciona una sucursal" }]}
                    >
                        <Select
                            placeholder="Selecciona sucursal"
                            options={branches.map((b) => ({ value: b.id, label: b.name }))}
                        />
                    </Form.Item>
                    <Form.Item name="assignedToId" label="Asignado a">
                        <Select
                            placeholder="Selecciona persona (opcional)"
                            allowClear
                            options={persons.map((p) => ({ value: p.id, label: p.fullName }))}
                        />
                    </Form.Item>
                    <Form.Item name="deliveryDate" label="Fecha de Entrega">
                        <DatePicker style={{ width: "100%" }} format="YYYY-MM-DD" />
                    </Form.Item>
                </Form>
            </Modal>
        </div>
    );
};

export default Equipment;