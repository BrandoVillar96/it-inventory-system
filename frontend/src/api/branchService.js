import api from "./axios";

export const branchService = {
    getAll: () => api.get("/branches"),
    getById: (id) => api.get(`/branches/${id}`),
    create: (data) => api.post("/branches", data),
    update: (id, data) => api.put(`/branches/${id}`, data),
    delete: (id) => api.delete(`/branches/${id}`),
};