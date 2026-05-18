const API_BASE = 'http://localhost:8080/api';

const getAuthHeaders = () => {
    const token = localStorage.getItem('token');
    const headers = { 'Content-Type': 'application/json' };
    if (token) {
        headers['Authorization'] = `Bearer ${token}`;
    }
    return headers;
};

const handleResponse = async (res) => {
    if (res.status === 401) {
        localStorage.removeItem('token');
        localStorage.removeItem('userRole');
        localStorage.removeItem('userName');
        if (!window.location.href.includes('login.html') && !window.location.href.includes('signup.html')) {
            window.location.href = 'login.html';
        }
        throw new Error('Unauthorized');
    }
    if (!res.ok) {
        let errorMsg = `HTTP error! status: ${res.status}`;
        try {
            const errorData = await res.json();
            errorMsg = errorData.message || errorData.error || errorMsg;
        } catch (e) {
            // Not a JSON error
        }
        throw new Error(errorMsg);
    }
    
    // Check if the response is empty
    const text = await res.text();
    return text ? JSON.parse(text) : null;
};

const extractData = (data) => Array.isArray(data) ? data : (data && data.value) || [];

const apiFetch = (url, options = {}) => {
    options.headers = getAuthHeaders();
    return fetch(url, options).then(handleResponse);
};

const api = {
    // Dashboard
    getDashboardStats: () => apiFetch(`${API_BASE}/dashboard/stats`).catch(() => ({})),
    getRevenueTrends: () => apiFetch(`${API_BASE}/dashboard/revenue-trends`).catch(() => ({ monthly: {}, weekly: {} })),
    
    // Students
    getStudents: () => apiFetch(`${API_BASE}/students`).then(extractData).catch(() => []),
    getStudentById: (id) => apiFetch(`${API_BASE}/students/${id}`).catch(() => null),
    createStudent: (data) => apiFetch(`${API_BASE}/students`, { method: 'POST', body: JSON.stringify(data) }),
    updateStudent: (id, data) => apiFetch(`${API_BASE}/students/${id}`, { method: 'PUT', body: JSON.stringify(data) }),
    deleteStudent: (id) => apiFetch(`${API_BASE}/students/${id}`, { method: 'DELETE' }),

    // Instructors
    getInstructors: () => apiFetch(`${API_BASE}/instructors`).then(extractData).catch(() => []),
    getInstructorById: (id) => apiFetch(`${API_BASE}/instructors/${id}`).catch(() => null),
    createInstructor: (data) => apiFetch(`${API_BASE}/instructors`, { method: 'POST', body: JSON.stringify(data) }),
    updateInstructor: (id, data) => apiFetch(`${API_BASE}/instructors/${id}`, { method: 'PUT', body: JSON.stringify(data) }),
    deleteInstructor: (id) => apiFetch(`${API_BASE}/instructors/${id}`, { method: 'DELETE' }),

    // Vehicles
    getVehicles: () => apiFetch(`${API_BASE}/vehicles`).then(extractData).catch(() => []),
    getVehicleById: (id) => apiFetch(`${API_BASE}/vehicles/${id}`).catch(() => null),
    createVehicle: (data) => apiFetch(`${API_BASE}/vehicles`, { method: 'POST', body: JSON.stringify(data) }),
    updateVehicle: (id, data) => apiFetch(`${API_BASE}/vehicles/${id}`, { method: 'PUT', body: JSON.stringify(data) }),
    deleteVehicle: (id) => apiFetch(`${API_BASE}/vehicles/${id}`, { method: 'DELETE' }),

    // Payments
    getPayments: () => apiFetch(`${API_BASE}/payments`).then(extractData).catch(() => []),
    getPaymentById: (id) => apiFetch(`${API_BASE}/payments/${id}`).catch(() => null),
    createPayment: (data) => apiFetch(`${API_BASE}/payments`, { method: 'POST', body: JSON.stringify(data) }),
    updatePayment: (id, data) => apiFetch(`${API_BASE}/payments/${id}`, { method: 'PUT', body: JSON.stringify(data) }),
    approvePayment: (id) => apiFetch(`${API_BASE}/payments/approve/${id}`, { method: 'POST' }),
    rejectPayment: (id) => apiFetch(`${API_BASE}/payments/reject/${id}`, { method: 'POST' }),
    refundPayment: (id) => apiFetch(`${API_BASE}/payments/refund/${id}`, { method: 'POST' }).catch(() => null),
    deletePayment: (id) => apiFetch(`${API_BASE}/payments/${id}`, { method: 'DELETE' }),

    // Schedules
    getSchedules: () => apiFetch(`${API_BASE}/schedules`).then(extractData).catch(() => []),
    getScheduleById: (id) => apiFetch(`${API_BASE}/schedules/${id}`).catch(() => null),
    getTodaySchedules: () => apiFetch(`${API_BASE}/schedules/today`).then(extractData).catch(() => []),
    createSchedule: (data) => apiFetch(`${API_BASE}/schedules`, { method: 'POST', body: JSON.stringify(data) }),
    updateSchedule: (id, data) => apiFetch(`${API_BASE}/schedules/${id}`, { method: 'PUT', body: JSON.stringify(data) }),
    deleteSchedule: (id) => apiFetch(`${API_BASE}/schedules/${id}`, { method: 'DELETE' }),

    // Admins
    getAdmins: () => apiFetch(`${API_BASE}/admins`).then(extractData).catch(() => []),
    getAdminById: (id) => apiFetch(`${API_BASE}/admins/${id}`).catch(() => null),
    createAdmin: (data) => apiFetch(`${API_BASE}/admins`, { method: 'POST', body: JSON.stringify(data) }),
    updateAdmin: (id, data) => apiFetch(`${API_BASE}/admins/${id}`, { method: 'PUT', body: JSON.stringify(data) }),
    deleteAdmin: (id) => apiFetch(`${API_BASE}/admins/${id}`, { method: 'DELETE' }),

    // Packages
    getPackages: () => apiFetch(`${API_BASE}/packages`).then(extractData).catch(() => []),
    getPackageById: (id) => apiFetch(`${API_BASE}/packages/${id}`).catch(() => null),
    createPackage: (data) => apiFetch(`${API_BASE}/packages`, { method: 'POST', body: JSON.stringify(data) }),
    updatePackage: (id, data) => apiFetch(`${API_BASE}/packages/${id}`, { method: 'PUT', body: JSON.stringify(data) }),
    deletePackage: (id) => apiFetch(`${API_BASE}/packages/${id}`, { method: 'DELETE' })
};

window.api = api;
