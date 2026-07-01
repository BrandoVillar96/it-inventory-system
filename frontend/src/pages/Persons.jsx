import { useEffect, useState } from "react";
import { Table, Button, Modal, Form, Input, Select, Space, message, Popconfirm, Tag } from "antd";
import { PlusOutlined, EditOutlined, DeleteOutlined } from "@ant-design/icons";
import { personService } from "../api/personService";
import { branchService } from "../api/branchService";

const Persons = () => {
    const [persons, setPersons] = useState([]);
    const [branches, setBranches] = useState([]);
    const [loading, setLoading] = useState(false);
    const [modalOpen, setModalOpen] = useState(false);
    const [editing, setEditing] = useState(null);
    const [form] = Form.useForm();

    const loadData = async () => {
        setLoading(true);
        try {
            const [personsRes, branchesRes] = await Promise.all([
                personService.getAll(),
                branchService.getAll(),
            ]);
            setPersons(personsRes.data);
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
        setModalOpen(true);
    };

    const openEdit = (record) => {
        setEditing(record);
        form.setFieldsValue({
            fullName: record.fullName,
            position: record.position,
            email: record.email,
            branchId: record.branchId,
        });
        setModalOpen(true);
    };

    const handleSubmit = async () => {
        try {
            const values = await form.validateFields();
            if (editing) {
                await personService.update(editing.id, values);
                message.success("Personal actualizado");
            } else {
                await personService.create(values);
                message.success("Personal creado");
            }
            setModalOpen(false);
            loadData();
        } catch (err) {
            if (err.response) message.error(err.response.data.message || "Error al guardar");
        }
    };

    const handleDelete = async (id) => {
        try {
            await personService.delete(id);
            message.success("Personal eliminado");
            loadData();
        } catch {
            message.error("Error al eliminar");
        }
    };

    const columns = [
        { title: "Nombre Completo", dataIndex: "fullName", key: "fullName" },
        { title: "Puesto", dataIndex: "position", key: "position" },
        { title: "Email", dataIndex: "email", key: "email" },
        { title: "Sucursal", dataIndex: "branchName", key: "branchName" },
        {
            title: "Estado",
            dataIndex: "active",
            key: "active",
            render: (active) => (
                <Tag color={active ? "green" : "red"}>{active ? "Activo" : "Inactivo"}</Tag>
            ),
        },
        {
            title: "Acciones",
            key: "actions",
            render: (_, record) => (
                <Space>
                    <Button icon={<EditOutlined />} onClick={() => openEdit(record)} size="small">
                        Editar
                    </Button>
                    <Popconfirm
                        title="¿Eliminar este registro?"
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
                <h2>Personal</h2>
                <Button type="primary" icon={<PlusOutlined />} onClick={openCreate}>
                    Nuevo Personal
                </Button>
            </div>

            <Table
                columns={columns}
                dataSource={persons}
                rowKey="id"
                loading={loading}
                pagination={{ pageSize: 10 }}
            />

            <Modal
                title={editing ? "Editar Personal" : "Nuevo Personal"}
                open={modalOpen}
                onOk={handleSubmit}
                onCancel={() => setModalOpen(false)}
                okText="Guardar"
                cancelText="Cancelar"
            >
                <Form form={form} layout="vertical">
                    <Form.Item
                        name="fullName"
                        label="Nombre Completo"
                        rules={[{ required: true, message: "El nombre es obligatorio" }]}
                    >
                        <Input placeholder="Juan Pérez García" />
                    </Form.Item>
                    <Form.Item name="position" label="Puesto">
                        <Input placeholder="Analista de Sistemas" />
                    </Form.Item>
                    <Form.Item
                        name="email"
                        label="Email"
                        rules={[
                            { required: true, message: "El email es obligatorio" },
                            { type: "email", message: "Email inválido" },
                        ]}
                    >
                        <Input placeholder="juan@empresa.com" />
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

export default Persons;