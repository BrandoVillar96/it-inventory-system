import api from "./axios";

export const personService = {
    getAll: () => api.get("/persons"),
    getById: (id) => api.get(`/persons/${id}`),
    getByBranch: (branchId) => api.get(`/persons/branch/${branchId}`),
    create: (data) => api.post("/persons", data),
    update: (id, data) => api.put(`/persons/${id}`, data),
    delete: (id) => api.delete(`/persons/${id}`),
};