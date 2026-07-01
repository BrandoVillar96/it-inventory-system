import api from "./axios";

export const supplyService = {
    getAll: () => api.get("/supplies"),
    getById: (id) => api.get(`/supplies/${id}`),
    getByBranch: (branchId) => api.get(`/supplies/branch/${branchId}`),
    getLowStock: () => api.get("/supplies/low-stock"),
    create: (data) => api.post("/supplies", data),
    update: (id, data) => api.put(`/supplies/${id}`, data),
    updateQuantity: (id, quantity) => api.patch(`/supplies/${id}/quantity?quantity=${quantity}`),
    delete: (id) => api.delete(`/supplies/${id}`),
};