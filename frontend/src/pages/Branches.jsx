import { useEffect, useState } from "react";
import { Table, Button, Modal, Form, Input, Space, message, Popconfirm, Tag } from "antd";
import { PlusOutlined, EditOutlined, DeleteOutlined } from "@ant-design/icons";
import { branchService } from "../api/branchService";

const Branches = () => {
    const [branches, setBranches] = useState([]);
    const [loading, setLoading] = useState(false);
    const [modalOpen, setModalOpen] = useState(false);
    const [editing, setEditing] = useState(null);
    const [form] = Form.useForm();

    const loadBranches = async () => {
        setLoading(true);
        try {
            const res = await branchService.getAll();
            setBranches(res.data);
        } catch {
            message.error("Error al cargar sucursales");
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        loadBranches();
    }, []);

    const openCreate = () => {
        setEditing(null);
        form.resetFields();
        setModalOpen(true);
    };

    const openEdit = (record) => {
        setEditing(record);
        form.setFieldsValue(record);
        setModalOpen(true);
    };

    const handleSubmit = async () => {
        try {
            const values = await form.validateFields();
            if (editing) {
                await branchService.update(editing.id, values);
                message.success("Sucursal actualizada");
            } else {
                await branchService.create(values);
                message.success("Sucursal creada");
            }
            setModalOpen(false);
            loadBranches();
        } catch (err) {
            if (err.response) message.error(err.response.data.message || "Error al guardar");
        }
    };

    const handleDelete = async (id) => {
        try {
            await branchService.delete(id);
            message.success("Sucursal eliminada");
            loadBranches();
        } catch {
            message.error("Error al eliminar");
        }
    };

    const columns = [
        { title: "Nombre", dataIndex: "name", key: "name" },
        { title: "Ubicación", dataIndex: "location", key: "location" },
        {
            title: "Estado",
            dataIndex: "active",
            key: "active",
            render: (active) => (
                <Tag color={active ? "green" : "red"}>{active ? "Activa" : "Inactiva"}</Tag>
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
                        title="¿Eliminar esta sucursal?"
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
                <h2>Sucursales</h2>
                <Button type="primary" icon={<PlusOutlined />} onClick={openCreate}>
                    Nueva Sucursal
                </Button>
            </div>

            <Table
                columns={columns}
                dataSource={branches}
                rowKey="id"
                loading={loading}
                pagination={{ pageSize: 10 }}
            />

            <Modal
                title={editing ? "Editar Sucursal" : "Nueva Sucursal"}
                open={modalOpen}
                onOk={handleSubmit}
                onCancel={() => setModalOpen(false)}
                okText="Guardar"
                cancelText="Cancelar"
            >
                <Form form={form} layout="vertical">
                    <Form.Item
                        name="name"
                        label="Nombre"
                        rules={[{ required: true, message: "El nombre es obligatorio" }]}
                    >
                        <Input placeholder="Sucursal Centro" />
                    </Form.Item>
                    <Form.Item name="location" label="Ubicación">
                        <Input placeholder="Veracruz, México" />
                    </Form.Item>
                </Form>
            </Modal>
        </div>
    );
};

export default Branches;