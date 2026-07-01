import api from "./axios";

export const equipmentService = {
    getAll: () => api.get("/equipment"),
    getById: (id) => api.get(`/equipment/${id}`),
    getByBranch: (branchId) => api.get(`/equipment/branch/${branchId}`),
    getByStatus: (status) => api.get(`/equipment/status/${status}`),
    getByType: (type) => api.get(`/equipment/type/${type}`),
    create: (data) => api.post("/equipment", data),
    update: (id, data) => api.put(`/equipment/${id}`, data),
    updateStatus: (id, status) => api.patch(`/equipment/${id}/status?status=${status}`),
    delete: (id) => api.delete(`/equipment/${id}`),
};