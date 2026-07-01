import { BrowserRouter, Routes, Route } from "react-router-dom";
import { ConfigProvider } from "antd";
import esES from "antd/locale/es_ES";
import MainLayout from "./layouts/MainLayout";
import Dashboard from "./pages/Dashboard";
import Branches from "./pages/Branches";
import Persons from "./pages/Persons";
import Equipment from "./pages/Equipment";
import Supplies from "./pages/Supplies";

function App() {
    return (
        <ConfigProvider locale={esES}>
            <BrowserRouter>
                <MainLayout>
                    <Routes>
                        <Route path="/" element={<Dashboard />} />
                        <Route path="/branches" element={<Branches />} />
                        <Route path="/persons" element={<Persons />} />
                        <Route path="/equipment" element={<Equipment />} />
                        <Route path="/supplies" element={<Supplies />} />
                    </Routes>
                </MainLayout>
            </BrowserRouter>
        </ConfigProvider>
    );
}

export default App;