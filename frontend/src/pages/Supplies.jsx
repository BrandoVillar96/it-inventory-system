import { useEffect, useState } from "react";
import { Table, Button, Modal, Form, Input, InputNumber, Select, Space, message, Popconfirm, Tag } from "antd";
import { PlusOutlined, EditOutlined, DeleteOutlined } from "@ant-design/icons";
import { supplyService } from "../api/supplyService";
import { branchService } from "../api/branchService";

const TYPE_LABELS = {
    TONER: "Tóner",
    DRUM: "Tambor",
    BARCODE_LABEL: "Etiqueta Código Barras",
    INK_CARTRIDGE: "Cartucho de Tinta",
    PAPER: "Papel",
    OTHER: "Otro",
};

const Supplies = () => {
    const [supplies, setSupplies] = useState([]);
    const [branches, setBranches] = useState([]);
    const [loading, setLoading] = useState(false);
    const [modalOpen, setModalOpen] = useState(false);
    const [editing, setEditing] = useState(null);
    const [form] = Form.useForm();

    const loadData = async () => {
        setLoading(true);
        try {
            const [suppliesRes, branchesRes] = await Promise.all([
                supplyService.getAll(),
                branchService.getAll(),
            ]);
            setSupplies(suppliesRes.data);
            setBranches(branchesRes.data);
        } catch {
            message.error("Error al cargar datos");
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        loadData();
    }, []);

    const openCreate = () => {
        setEditing(null);
        form.resetFields();
        form.setFieldsValue({ quantity: 0, minStock: 5 });
        setModalOpen(true);
    };

    const openEdit = (record) => {
        setEditing(record);
        form.setFieldsValue({
            type: record.type,
            brand: record.brand,
            quantity: record.quantity,
            minStock: record.minStock,
            branchId: record.branchId,
        });
        setModalOpen(true);
    };

    const handleSubmit = async () => {
        try {
            const values = await form.validateFields();
            if (editing) {
                await supplyService.update(editing.id, values);
                message.success("Consumible actualizado");
            } else {
                await supplyService.create(values);
                message.success("Consumible creado");
            }
            setModalOpen(false);
            loadData();
        } catch (err) {
            if (err.response) message.error(err.response.data.message || "Error al guardar");
        }
    };

    const handleDelete = async (id) => {
        try {
            await supplyService.delete(id);
            message.success("Consumible eliminado");
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
        { title: "Cantidad", dataIndex: "quantity", key: "quantity" },
        { title: "Stock Mínimo", dataIndex: "minStock", key: "minStock" },
        {
            title: "Estado Stock",
            dataIndex: "lowStock",
            key: "lowStock",
            render: (lowStock) => (
                <Tag color={lowStock ? "red" : "green"}>{lowStock ? "Stock Bajo" : "Suficiente"}</Tag>
            ),
        },
        { title: "Sucursal", dataIndex: "branchName", key: "branchName" },
        {
            title: "Acciones",
            key: "actions",
            render: (_, record) => (
                <Space>
                    <Button icon={<EditOutlined />} onClick={() => openEdit(record)} size="small">
                        Editar
                    </Button>
                    <Popconfirm
                        title="¿Eliminar este consumible?"
                        onConfirm={() => handleDelete(record.id)}
                        okText="Sí"
                        cancelText="No"
                    >
                        <Button icon={<DeleteOutlined />} danger size="small">
                            Eliminar
                        </Button>
                    </Popconfirm>
                </Space>
            ),
        },
    ];

    return (
        <div>
            <div style={{ display: "flex", justifyContent: "space-between", marginBottom: 16 }}>
                <h2>Consumibles</h2>
                <Button type="primary" icon={<PlusOutlined />} onClick={openCreate}>
                    Nuevo Consumible
                </Button>
            </div>

            <Table
                columns={columns}
                dataSource={supplies}
                rowKey="id"
                loading={loading}
                pagination={{ pageSize: 10 }}
            />

            <Modal
                title={editing ? "Editar Consumible" : "Nuevo Consumible"}
                open={modalOpen}
                onOk={handleSubmit}
                onCancel={() => setModalOpen(false)}
                okText="Guardar"
                cancelText="Cancelar"
            >
                <Form form={form} layout="vertical">
                    <Form.Item
                        name="type"
                        label="Tipo de Consumible"
                        rules={[{ required: true, message: "Selecciona el tipo" }]}
                    >
                        <Select
                            placeholder="Selecciona tipo"
                            options={Object.entries(TYPE_LABELS).map(([value, label]) => ({ value, label }))}
                        />
                    </Form.Item>
                    <Form.Item name="brand" label="Marca">
                        <Input placeholder="HP, Canon, Epson..." />
                    </Form.Item>
                    <Form.Item name="quantity" label="Cantidad">
                        <InputNumber min={0} style={{ width: "100%" }} />
                    </Form.Item>
                    <Form.Item name="minStock" label="Stock Mínimo">
                        <InputNumber min={0} style={{ width: "100%" }} />
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
                </Form>
            </Modal>
        </div>
    );
};

export default Supplies;