import { useState } from "react";
import { Layout, Menu } from "antd";
import {
    DashboardOutlined,
    ShopOutlined,
    TeamOutlined,
    DesktopOutlined,
    InboxOutlined,
} from "@ant-design/icons";
import { useNavigate, useLocation } from "react-router-dom";

const { Header, Sider, Content } = Layout;

const MainLayout = ({ children }) => {
    const [collapsed, setCollapsed] = useState(false);
    const navigate = useNavigate();
    const location = useLocation();

    const menuItems = [
        { key: "/", icon: <DashboardOutlined />, label: "Dashboard" },
        { key: "/branches", icon: <ShopOutlined />, label: "Sucursales" },
        { key: "/persons", icon: <TeamOutlined />, label: "Personal" },
        { key: "/equipment", icon: <DesktopOutlined />, label: "Equipos" },
        { key: "/supplies", icon: <InboxOutlined />, label: "Consumibles" },
    ];

    return (
        <Layout style={{ minHeight: "100vh" }}>
            <Sider collapsible collapsed={collapsed} onCollapse={setCollapsed}>
                <div
                    style={{
                        height: 64,
                        display: "flex",
                        alignItems: "center",
                        justifyContent: "center",
                        color: "white",
                        fontWeight: "bold",
                        fontSize: collapsed ? 14 : 16,
                    }}
                >
                    {collapsed ? "IT" : "IT Inventory"}
                </div>
                <Menu
                    theme="dark"
                    mode="inline"
                    selectedKeys={[location.pathname]}
                    items={menuItems}
                    onClick={({ key }) => navigate(key)}
                />
            </Sider>
            <Layout>
                <Header
                    style={{
                        background: "#fff",
                        padding: "0 24px",
                        display: "flex",
                        alignItems: "center",
                        fontSize: 18,
                        fontWeight: 600,
                        boxShadow: "0 2px 8px rgba(0,0,0,0.06)",
                    }}
                >
                    Sistema de Gestión de Inventario IT
                </Header>
                <Content style={{ margin: 24, padding: 24, background: "#fff", borderRadius: 8 }}>
                    {children}
                </Content>
            </Layout>
        </Layout>
    );
};

export default MainLayout;