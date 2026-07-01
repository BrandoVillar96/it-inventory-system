import { useEffect, useState } from "react";
import { Card, Col, Row, Statistic, Spin, Alert } from "antd";
import {
    ShopOutlined,
    TeamOutlined,
    DesktopOutlined,
    WarningOutlined,
} from "@ant-design/icons";
import { branchService } from "../api/branchService";
import { personService } from "../api/personService";
import { equipmentService } from "../api/equipmentService";
import { supplyService } from "../api/supplyService";

const Dashboard = () => {
    const [stats, setStats] = useState({
        branches: 0,
        persons: 0,
        equipment: 0,
        lowStock: 0,
    });
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const loadStats = async () => {
            try {
                const [branches, persons, equipment, lowStock] = await Promise.all([
                    branchService.getAll(),
                    personService.getAll(),
                    equipmentService.getAll(),
                    supplyService.getLowStock(),
                ]);
                setStats({
                    branches: branches.data.length,
                    persons: persons.data.length,
                    equipment: equipment.data.length,
                    lowStock: lowStock.data.length,
                });
            } catch (err) {
                setError("No se pudo conectar con el servidor. Verifica que el backend esté corriendo.");
            } finally {
                setLoading(false);
            }
        };
        loadStats();
    }, []);

    if (loading) {
        return (
            <div style={{ textAlign: "center", padding: 80 }}>
                <Spin size="large" />
            </div>
        );
    }

    if (error) {
        return <Alert type="error" message={error} showIcon />;
    }

    return (
        <div>
            <h2 style={{ marginBottom: 24 }}>Resumen General</h2>
            <Row gutter={[16, 16]}>
                <Col xs={24} sm={12} lg={6}>
                    <Card>
                        <Statistic
                            title="Sucursales"
                            value={stats.branches}
                            prefix={<ShopOutlined style={{ color: "#1677ff" }} />}
                        />
                    </Card>
                </Col>
                <Col xs={24} sm={12} lg={6}>
                    <Card>
                        <Statistic
                            title="Personal Registrado"
                            value={stats.persons}
                            prefix={<TeamOutlined style={{ color: "#52c41a" }} />}
                        />
                    </Card>
                </Col>
                <Col xs={24} sm={12} lg={6}>
                    <Card>
                        <Statistic
                            title="Equipos"
                            value={stats.equipment}
                            prefix={<DesktopOutlined style={{ color: "#722ed1" }} />}
                        />
                    </Card>
                </Col>
                <Col xs={24} sm={12} lg={6}>
                    <Card>
                        <Statistic
                            title="Stock Bajo"
                            value={stats.lowStock}
                            valueStyle={{ color: stats.lowStock > 0 ? "#cf1322" : "#3f8600" }}
                            prefix={<WarningOutlined />}
                        />
                    </Card>
                </Col>
            </Row>
        </div>
    );
};

export default Dashboard;