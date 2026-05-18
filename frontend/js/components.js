window.API_URL = 'http://localhost:8080/api';

function initSidebar(activePage) {
    // Check authentication
    const token = localStorage.getItem('token');
    const role = localStorage.getItem('userRole');
    const userName = localStorage.getItem('userName') || 'User';

    if (!token && !window.location.href.includes('login.html') && !window.location.href.includes('signup.html')) {
        window.location.href = 'login.html';
        return;
    }

    const isAdmin = role === 'ROLE_ADMIN';
    const isStudent = role === 'ROLE_STUDENT';
    const isInstructor = role === 'ROLE_INSTRUCTOR';

    let displayRole = 'User';
    if (isAdmin) displayRole = 'System Admin';
    if (isStudent) displayRole = 'Student';
    if (isInstructor) displayRole = 'Instructor';

    let navItems = `
        <a href="index.html" class="sidebar-item ${activePage === 'dashboard' ? 'active' : ''}">
            <i data-lucide="layout-dashboard" size="18"></i>
            <span>Dashboard</span>
        </a>
    `;

    if (isAdmin) {
        navItems += `
            <a href="instructors.html" class="sidebar-item ${activePage === 'instructors' ? 'active' : ''}">
                <i data-lucide="users-2" size="18"></i>
                <span>Instructor</span>
            </a>
            <a href="students.html" class="sidebar-item ${activePage === 'students' ? 'active' : ''}">
                <i data-lucide="graduation-cap" size="18"></i>
                <span>Student</span>
            </a>
            <a href="vehicles.html" class="sidebar-item ${activePage === 'vehicles' ? 'active' : ''}">
                <i data-lucide="car" size="18"></i>
                <span>Vehicle</span>
            </a>
            <a href="schedules.html" class="sidebar-item ${activePage === 'schedules' ? 'active' : ''}">
                <i data-lucide="calendar-range" size="18"></i>
                <span>Schedule</span>
            </a>
            <a href="packages.html" class="sidebar-item ${activePage === 'packages' ? 'active' : ''}">
                <i data-lucide="package" size="18"></i>
                <span>Course Packages</span>
            </a>
            <a href="payments.html" class="sidebar-item ${activePage === 'payments' ? 'active' : ''}">
                <i data-lucide="banknote" size="18"></i>
                <span>Payments</span>
            </a>
            <a href="admins.html" class="sidebar-item ${activePage === 'admins' ? 'active' : ''}">
                <i data-lucide="shield-check" size="18"></i>
                <span>Admin</span>
            </a>
        `;
    } else if (isInstructor) {
        navItems += `
            <a href="schedules.html" class="sidebar-item ${activePage === 'schedules' ? 'active' : ''}">
                <i data-lucide="calendar-range" size="18"></i>
                <span>My Schedule</span>
            </a>
            <a href="students.html" class="sidebar-item ${activePage === 'students' ? 'active' : ''}">
                <i data-lucide="graduation-cap" size="18"></i>
                <span>My Students</span>
            </a>
            <a href="vehicles.html" class="sidebar-item ${activePage === 'vehicles' ? 'active' : ''}">
                <i data-lucide="car" size="18"></i>
                <span>Vehicles</span>
            </a>
        `;
    } else if (isStudent) {
        navItems += `
            <a href="schedules.html" class="sidebar-item ${activePage === 'schedules' ? 'active' : ''}">
                <i data-lucide="calendar-range" size="18"></i>
                <span>My Schedule</span>
            </a>
            <a href="instructors.html" class="sidebar-item ${activePage === 'instructors' ? 'active' : ''}">
                <i data-lucide="users-2" size="18"></i>
                <span>My Instructors</span>
            </a>
            <a href="packages.html" class="sidebar-item ${activePage === 'packages' ? 'active' : ''}">
                <i data-lucide="package" size="18"></i>
                <span>Course Packages</span>
            </a>
            <a href="payments.html" class="sidebar-item ${activePage === 'payments' ? 'active' : ''}">
                <i data-lucide="banknote" size="18"></i>
                <span>My Payments</span>
            </a>
            <a href="#" class="sidebar-item" id="myProfileLink">
                <i data-lucide="user-circle" size="18"></i>
                <span>My Profile</span>
            </a>
        `;
    }

    const sidebarHtml = `
    <div class="sidebar">
        <a href="index.html" class="sidebar-brand">
            <div class="brand-logo">
                <i data-lucide="car-front" class="text-white" size="24"></i>
            </div>
            <div>
                <h1 class="brand-name">DriveHUB</h1>
                <p class="text-xs text-slate-500 m-0 fw-semibold uppercase tracking-widest" style="font-size: 0.6rem;">Management System</p>
            </div>
        </a>

        <nav class="flex-1">
            ${navItems}
        </nav>

        <div class="sidebar-footer">
            <a href="#" class="sidebar-item text-logout-red" id="logoutBtn">
                <i data-lucide="log-out" size="18"></i>
                <span>Logout</span>
            </a>
        </div>
    </div>
    `;
    document.body.insertAdjacentHTML('afterbegin', sidebarHtml);

    // Global Profile Preview Modal Container
    const modalContainer = `
    <div class="modal fade" id="profilePreviewModal" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered modal-lg">
            <div class="modal-content border-0 glass-card p-0 shadow-2xl overflow-hidden" id="profilePreviewContent">
                <!-- Content will be injected here -->
            </div>
        </div>
    </div>
    `;
    document.body.insertAdjacentHTML('beforeend', modalContainer);

    // Modern Header Component
    const headerHtml = `
    <header class="top-header">
        <div class="search-wrapper">
            <i data-lucide="search" class="search-icon" size="18"></i>
            <input type="text" id="globalSearch" class="search-input" placeholder="Search system data...">
            <div id="globalSearchResults" class="search-results-dropdown shadow-2xl"></div>
        </div>
        <div class="header-actions">
            <button class="icon-btn position-relative" id="notificationBtn">
                <i data-lucide="bell" size="20"></i>
                <span id="notificationDot" class="position-absolute top-1 end-1 p-1 bg-rose-500 border border-dark rounded-circle d-none" style="width: 8px; height: 8px;"></span>
            </button>
            <div class="profile-section">
                <div class="profile-info">
                    <p class="profile-name">${userName}</p>
                    <p class="profile-role">${displayRole}</p>
                </div>
                <img src="https://ui-avatars.com/api/?name=${encodeURIComponent(userName)}&background=2563eb&color=fff&bold=true" class="profile-avatar" alt="User Profile">
            </div>
        </div>
    </header>
    `;

    const mainContent = document.querySelector('.main-content');
    if (mainContent) {
        mainContent.insertAdjacentHTML('afterbegin', headerHtml);
    }

    lucide.createIcons();
    setupGlobalSearch();
    checkPendingPayments();

    document.getElementById('logoutBtn').addEventListener('click', (e) => {
        e.preventDefault();
        localStorage.removeItem('token');
        localStorage.removeItem('userRole');
        localStorage.removeItem('userName');
        window.location.href = 'login.html';
    });

    const myProfileLink = document.getElementById('myProfileLink');
    if (myProfileLink) {
        myProfileLink.addEventListener('click', async (e) => {
            e.preventDefault();
            try {
                const students = await api.getStudents();
                // Find by name matching the logged in user
                const me = students.find(s => s.fullName === userName);
                if (me) {
                    showProfilePreview('student', me);
                } else {
                    alert('Profile information not found.');
                }
            } catch (err) {
                console.error('Failed to load profile', err);
            }
        });
    }

    // Check page access
    const path = window.location.pathname;
    if (!isAdmin && path.includes('admins.html')) {
        window.location.href = 'index.html';
    }
}

async function checkPendingPayments() {
    const role = localStorage.getItem('userRole');
    if (role === 'ROLE_ADMIN' || role === 'ROLE_INSTRUCTOR') {
        try {
            const payments = await api.getPayments();
            const pending = payments.filter(p => p.status === 'PENDING');
            if (pending.length > 0) {
                const dot = document.getElementById('notificationDot');
                if (dot) dot.classList.remove('d-none');

                // If on dashboard, maybe show a toast or alert
                if (window.location.pathname.includes('index.html')) {
                    showPendingAlert(pending.length);
                }
            }
        } catch (err) {
            console.error('Failed to check pending payments', err);
        }
    }
}

function showPendingAlert(count) {
    const alertId = 'pendingPaymentsAlert';
    if (document.getElementById(alertId)) return;

    const alertHtml = `
        <div id="${alertId}" class="glass-card border-rose-500/20 bg-rose-500/5 p-4 mb-4 d-flex align-items-center gap-3 animate-in fade-in slide-in-from-top-4 duration-500">
            <div class="icon-btn bg-rose-500/10 text-rose-500"><i data-lucide="alert-circle" size="20"></i></div>
            <div class="flex-1">
                <p class="m-0 fw-bold text-navy" style="font-size: 0.9rem;">Attention Needed</p>
                <p class="m-0 text-secondary text-xs">There are ${count} pending bank transfers that require your verification.</p>
            </div>
            <a href="payments.html" class="btn btn-sm btn-rose-500 text-white px-3 py-1 text-xs rounded-pill text-decoration-none">Review Now</a>
            <button class="icon-btn text-secondary" onclick="this.parentElement.remove()"><i data-lucide="x" size="16"></i></button>
        </div>
    `;

    const container = document.querySelector('.main-content .container-fluid') || document.querySelector('.main-content');
    if (container) {
        container.insertAdjacentHTML('afterbegin', alertHtml);
        lucide.createIcons();
    }
}

async function setupGlobalSearch() {
    const searchInput = document.getElementById('globalSearch');
    const resultsContainer = document.getElementById('globalSearchResults');
    if (!searchInput || !resultsContainer) return;

    let data = { students: [], instructors: [], vehicles: [] };

    searchInput.addEventListener('input', async (e) => {
        const query = e.target.value.toLowerCase().trim();
        if (query.length < 2) {
            resultsContainer.classList.remove('active');
            return;
        }

        // Lazy load data if needed
        if (data.students.length === 0 && data.instructors.length === 0 && data.vehicles.length === 0) {
            try {
                const [s, i, v] = await Promise.all([
                    api.getStudents().catch(() => []),
                    api.getInstructors().catch(() => []),
                    api.getVehicles().catch(() => [])
                ]);
                data.students = s || [];
                data.instructors = i || [];
                data.vehicles = v || [];
            } catch (err) { console.error('Search data fetch failed', err); }
        }

        const filteredStudents = data.students.filter(s => s.fullName && s.fullName.toLowerCase().includes(query)).slice(0, 3);
        const filteredInstructors = data.instructors.filter(i => i.fullName && i.fullName.toLowerCase().includes(query)).slice(0, 3);
        const filteredVehicles = data.vehicles.filter(v =>
            (v.registrationNumber && v.registrationNumber.toLowerCase().includes(query)) ||
            (v.make && v.make.toLowerCase().includes(query)) ||
            (v.model && v.model.toLowerCase().includes(query))
        ).slice(0, 3);

        if (filteredStudents.length === 0 && filteredInstructors.length === 0 && filteredVehicles.length === 0) {
            resultsContainer.innerHTML = `
                <div class="p-5 text-center">
                    <div class="w-12 h-12 rounded-full bg-slate-50 d-flex align-items-center justify-center mx-auto mb-3">
                        <i data-lucide="search-x" class="text-slate-300" size="24"></i>
                    </div>
                    <p class="text-secondary fw-bold m-0" style="font-size: 0.85rem;">No matches found</p>
                    <p class="text-xs text-slate-400 mt-1">Try searching for something else</p>
                </div>
            `;
            lucide.createIcons();
        } else {
            let html = '';
            if (filteredStudents.length > 0) {
                html += '<div class="search-result-category">Students</div>';
                filteredStudents.forEach(s => {
                    html += `<a href="students.html" class="search-result-item"><i data-lucide="user" size="14"></i> ${s.fullName}</a>`;
                });
            }
            if (filteredInstructors.length > 0) {
                html += '<div class="search-result-category">Instructors</div>';
                filteredInstructors.forEach(i => {
                    html += `<a href="instructors.html" class="search-result-item"><i data-lucide="users-2" size="14"></i> ${i.fullName}</a>`;
                });
            }
            if (filteredVehicles.length > 0) {
                html += '<div class="search-result-category">Vehicles</div>';
                filteredVehicles.forEach(v => {
                    html += `<a href="vehicles.html" class="search-result-item"><i data-lucide="car" size="14"></i> ${v.registrationNumber} (${v.make})</a>`;
                });
            }
            resultsContainer.innerHTML = html;
            lucide.createIcons();
        }
        resultsContainer.classList.add('active');
    });

    // Close on click outside
    document.addEventListener('click', (e) => {
        if (!searchInput.contains(e.target) && !resultsContainer.contains(e.target)) {
            resultsContainer.classList.remove('active');
        }
    });
}

// Global Profile Preview Function
function formatLabel(str) {
    if (!str) return '';
    return str.split('_').map(word =>
        word.charAt(0).toUpperCase() + word.slice(1).toLowerCase()
    ).join(' ');
}

function showProfilePreview(type, data) {
    const content = document.getElementById('profilePreviewContent');
    if (!content) return;

    let profileHtml = '';
    const initials = (data.fullName || data.registrationNumber || '??').split(' ').map(n => n[0]).join('').substring(0, 2).toUpperCase();
    const avatarUrl = data.fullName ? `https://ui-avatars.com/api/?name=${encodeURIComponent(data.fullName)}&background=2563eb&color=fff&bold=true&size=128` : null;

    if (type === 'student') {
        profileHtml = `
            <div class="resume-header p-5 d-flex align-items-center gap-4 bg-indigo-500/5 border-bottom border-black/5">
                ${avatarUrl ? `<img src="${avatarUrl}" class="rounded-circle shadow-lg border-4 border-black/10" style="width: 120px; height: 120px;">` : `<div class="rounded-circle bg-indigo-500/10 text-indigo-400 d-flex align-items-center justify-content-center display-4 fw-bold shadow-lg" style="width: 120px; height: 120px;">${initials}</div>`}
                <div>
                    <h2 class="display-6 fw-bold m-0 text-navy">${data.fullName}</h2>
                    <p class="text-indigo-400 fw-bold tracking-widest uppercase mt-1">Student Profile</p>
                    <span class="badge-custom ${data.status === 'ACTIVE' ? 'badge-success' : 'badge-info'} px-4 py-1 mt-2">${data.status}</span>
                </div>
                <div class="ms-auto text-end">
                    <button type="button" class="icon-btn text-secondary position-absolute top-0 end-0 m-4" data-bs-dismiss="modal"><i data-lucide="x" size="32"></i></button>
                    ${localStorage.getItem('userRole') === 'ROLE_STUDENT' ? `
                        <button class="btn-modern btn-primary-modern py-2 px-4 mt-3" onclick="showEditMyProfile(${JSON.stringify(data).replace(/"/g, '&quot;')})">
                            <i data-lucide="edit-2" size="16" class="me-2"></i> Edit Profile
                        </button>
                    ` : ''}
                </div>
            </div>
            <div class="resume-body p-5">
                <div class="row g-5">
                    <div class="col-md-6">
                        <h6 class="text-xs fw-bold text-secondary text-uppercase tracking-widest mb-4">Personal Details</h6>
                        <div class="d-flex flex-column gap-3">
                            <div class="detail-item">
                                <p class="text-xs text-secondary mb-1">NIC Number</p>
                                <p class="fw-bold m-0 text-navy">${data.nic || 'Not provided'}</p>
                            </div>
                            <div class="detail-item">
                                <p class="text-xs text-secondary mb-1">Date of Birth</p>
                                <p class="fw-bold m-0 text-navy">${data.dateOfBirth || 'Not provided'}</p>
                            </div>
                            <div class="detail-item">
                                <p class="text-xs text-secondary mb-1">Address</p>
                                <p class="fw-bold m-0 text-navy">${data.address || 'Not provided'}</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <h6 class="text-xs fw-bold text-secondary text-uppercase tracking-widest mb-4">Training Information</h6>
                        <div class="d-flex flex-column gap-3">
                            <div class="detail-item">
                                <p class="text-xs text-secondary mb-1">License Category</p>
                                <p class="fw-bold m-0 text-navy">${formatLabel(data.licenseCategory) || 'Not assigned'}</p>
                            </div>
                            <div class="detail-item">
                                <p class="text-xs text-secondary mb-1">Current Phase</p>
                                <p class="fw-bold m-0 text-navy">${(data.trainingPhase || 'REGISTRATION').replace('_', ' ')}</p>
                            </div>
                            <div class="detail-item">
                                <p class="text-xs text-secondary mb-1">Enrollment Date</p>
                                <p class="fw-bold m-0 text-navy">${data.enrolledDate ? new Date(data.enrolledDate).toLocaleDateString() : new Date().toLocaleDateString()}</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-12 mt-5">
                        <h6 class="text-xs fw-bold text-secondary text-uppercase tracking-widest mb-4">Contact Details</h6>
                        <div class="row g-4">
                            <div class="col-md-6">
                                <div class="glass-card p-3 d-flex align-items-center gap-3 border-white/5">
                                    <div class="icon-btn text-indigo-400 bg-indigo-500/10"><i data-lucide="mail" size="18"></i></div>
                                    <span class="fw-medium">${data.email || 'N/A'}</span>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="glass-card p-3 d-flex align-items-center gap-3 border-white/5">
                                    <div class="icon-btn text-emerald-400 bg-emerald-500/10"><i data-lucide="phone" size="18"></i></div>
                                    <span class="fw-medium">${data.phone || 'N/A'}</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        `;
    } else if (type === 'instructor') {
        profileHtml = `
            <div class="resume-header p-5 d-flex align-items-center gap-4 bg-indigo-500/5 border-bottom border-black/5">
                ${avatarUrl ? `<img src="${avatarUrl}" class="rounded-circle shadow-lg border-4 border-black/10" style="width: 120px; height: 120px;">` : `<div class="rounded-circle bg-indigo-500/10 text-indigo-400 d-flex align-items-center justify-content-center display-4 fw-bold shadow-lg" style="width: 120px; height: 120px;">${initials}</div>`}
                <div>
                    <h2 class="display-6 fw-bold m-0 text-navy">${data.fullName}</h2>
                    <p class="text-indigo-400 fw-bold tracking-widest uppercase mt-1">Professional Instructor</p>
                    <span class="badge-custom ${data.status === 'AVAILABLE' ? 'badge-success' : 'badge-info'} px-4 py-1 mt-2">${data.status}</span>
                </div>
                <button type="button" class="icon-btn ms-auto text-secondary" data-bs-dismiss="modal"><i data-lucide="x" size="32"></i></button>
            </div>
            <div class="resume-body p-5">
                <div class="row g-5">
                    <div class="col-md-6">
                        <h6 class="text-xs fw-bold text-secondary text-uppercase tracking-widest mb-4">Professional Bio</h6>
                        <div class="d-flex flex-column gap-3">
                            <div class="detail-item">
                                <p class="text-xs text-secondary mb-1">Specialization</p>
                                <p class="fw-bold m-0 text-navy">${formatLabel(data.specialization)}</p>
                            </div>
                            <div class="detail-item">
                                <p class="text-xs text-secondary mb-1">Experience</p>
                                <p class="fw-bold m-0 text-navy">${data.yearsOfExperience} Years of Professional Training</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <h6 class="text-xs fw-bold text-secondary text-uppercase tracking-widest mb-4">Contact Information</h6>
                        <div class="d-flex flex-column gap-3">
                            <div class="detail-item">
                                <p class="text-xs text-secondary mb-1">Work Email</p>
                                <p class="fw-bold m-0 text-navy">${data.email}</p>
                            </div>
                            <div class="detail-item">
                                <p class="text-xs text-secondary mb-1">Phone Number</p>
                                <p class="fw-bold m-0 text-navy">${data.phone}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        `;
    } else if (type === 'vehicle') {
        profileHtml = `
            <div class="resume-header p-5 d-flex align-items-center gap-4 bg-indigo-500/5 border-bottom border-black/5">
                <div class="rounded-circle bg-indigo-500/10 text-indigo-400 d-flex align-items-center justify-content-center shadow-lg" style="width: 120px; height: 120px;"><i data-lucide="car" size="64" stroke-width="1.5" style="width: 64px; height: 64px;"></i></div>
                <div>
                    <h2 class="display-6 fw-bold m-0 text-navy">${data.make} ${data.model}</h2>
                    <p class="text-indigo-400 fw-bold tracking-widest uppercase mt-1">Fleet Vehicle Profile</p>
                    <span class="badge-custom ${data.status === 'AVAILABLE' || data.status === 'OPERATIONAL' ? 'badge-success' : 'badge-warning'} px-4 py-1 mt-2">${data.status}</span>
                </div>
                <button type="button" class="icon-btn ms-auto text-secondary" data-bs-dismiss="modal"><i data-lucide="x" size="32"></i></button>
            </div>
            <div class="resume-body p-5">
                <div class="row g-5">
                    <div class="col-md-6">
                        <h6 class="text-xs fw-bold text-secondary text-uppercase tracking-widest mb-4">Technical Specs</h6>
                        <div class="d-flex flex-column gap-3">
                            <div class="detail-item">
                                <p class="text-xs text-secondary mb-1">Registration Number</p>
                                <p class="fw-bold m-0 text-navy">${data.registrationNumber}</p>
                            </div>
                            <div class="detail-item">
                                <p class="text-xs text-secondary mb-1">Manufacturing Year</p>
                                <p class="fw-bold m-0 text-navy">${data.year}</p>
                            </div>
                            <div class="detail-item">
                                <p class="text-xs text-secondary mb-1">Vehicle Type</p>
                                <p class="fw-bold m-0 text-navy">${formatLabel(data.vehicleType)}</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <h6 class="text-xs fw-bold text-secondary text-uppercase tracking-widest mb-4">Fleet Status</h6>
                        <div class="d-flex flex-column gap-3">
                            <div class="detail-item">
                                <p class="text-xs text-secondary mb-1">Assigned Category</p>
                                <p class="fw-bold m-0 text-navy">${formatLabel(data.assignedCategory)}</p>
                            </div>
                            <div class="detail-item">
                                <p class="text-xs text-secondary mb-1">Last Maintenance</p>
                                <p class="fw-bold m-0 text-navy">${data.lastServiceDate ? new Date(data.lastServiceDate).toLocaleDateString() : new Date().toLocaleDateString()}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        `;
    } else if (type === 'admin') {
        profileHtml = `
            <div class="resume-header p-5 d-flex align-items-center gap-4 bg-indigo-500/5 border-bottom border-black/5">
                ${avatarUrl ? `<img src="${avatarUrl}" class="rounded-circle shadow-lg border-4 border-black/10" style="width: 120px; height: 120px;">` : `<div class="rounded-circle bg-indigo-500/10 text-indigo-400 d-flex align-items-center justify-content-center display-4 fw-bold shadow-lg" style="width: 120px; height: 120px;">${initials}</div>`}
                <div>
                    <h2 class="display-6 fw-bold m-0 text-navy">${data.fullName}</h2>
                    <p class="text-indigo-400 fw-bold tracking-widest uppercase mt-1">System Administrator</p>
                    <span class="badge-custom badge-info px-4 py-1 mt-2">${data.role}</span>
                </div>
                <button type="button" class="icon-btn ms-auto text-secondary" data-bs-dismiss="modal"><i data-lucide="x" size="32"></i></button>
            </div>
            <div class="resume-body p-5">
                <div class="row g-4">
                    <div class="col-md-12">
                        <h6 class="text-xs fw-bold text-secondary text-uppercase tracking-widest mb-4">Access Details</h6>
                        <div class="detail-item mb-4">
                            <p class="text-xs text-secondary mb-1">Username</p>
                            <p class="fw-bold m-0 text-navy text-lg">@${data.username}</p>
                        </div>
                        <div class="glass-card p-4 border-indigo-500/20 bg-indigo-500/5">
                            <div class="d-flex align-items-center gap-3 text-indigo-400">
                                <i data-lucide="shield-check" size="24"></i>
                                <p class="m-0 fw-bold">Full administrative access granted for this account.</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        `;
    }

    content.innerHTML = profileHtml;
    lucide.createIcons();
    const modal = new bootstrap.Modal(document.getElementById('profilePreviewModal'));
    modal.show();
}

window.showProfilePreview = showProfilePreview;

async function showEditMyProfile(data) {
    const modalId = 'editMyProfileModal';
    let modalEl = document.getElementById(modalId);
    if (!modalEl) {
        const modalHtml = `
            <div class="modal fade" id="${modalId}" tabindex="-1">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content border-0 glass-card p-0 shadow-2xl">
                        <div class="modal-header border-black/10 p-5 pb-4">
                            <div>
                                <h4 class="modal-title fw-bold">Update My Profile</h4>
                                <p class="text-secondary text-sm m-0 mt-1">Modify your personal details.</p>
                            </div>
                            <button type="button" class="icon-btn" data-bs-dismiss="modal"><i data-lucide="x" size="24"></i></button>
                        </div>
                        <form id="editMyProfileForm">
                            <div class="modal-body p-5 pt-4">
                                <div class="row g-4 mb-5">
                                    <div class="col-12">
                                        <label class="form-label text-xs fw-bold text-secondary text-uppercase tracking-widest mb-3">Full Name</label>
                                        <input type="text" class="search-input" id="editMyName" value="${data.fullName}" required>
                                    </div>
                                    <div class="col-12">
                                        <label class="form-label text-xs fw-bold text-secondary text-uppercase tracking-widest mb-3">Phone Number</label>
                                        <input type="text" class="search-input" id="editMyPhone" value="${data.phone}" required>
                                    </div>
                                    <div class="col-md-12">
                                        <label class="form-label text-xs fw-bold text-secondary text-uppercase tracking-widest mb-3">Date of Birth</label>
                                        <input type="date" class="search-input" id="editMyDob" value="${data.dateOfBirth}" required>
                                    </div>
                                    <div class="col-12">
                                        <label class="form-label text-xs fw-bold text-secondary text-uppercase tracking-widest mb-3">Address</label>
                                        <input type="text" class="search-input" id="editMyAddress" value="${data.address}" required>
                                    </div>
                                </div>
                                <button type="submit" class="btn-modern btn-primary-modern w-100 py-3">Save Changes</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        `;
        document.body.insertAdjacentHTML('beforeend', modalHtml);
        modalEl = document.getElementById(modalId);
        lucide.createIcons();
    }

    // Close preview modal if open
    const previewModalEl = document.getElementById('profilePreviewModal');
    if (previewModalEl) {
        const previewModal = bootstrap.Modal.getInstance(previewModalEl);
        if (previewModal) previewModal.hide();
    }

    const editModal = new bootstrap.Modal(modalEl);
    editModal.show();

    document.getElementById('editMyProfileForm').onsubmit = async (e) => {
        e.preventDefault();
        try {
            const updatedData = {
                ...data,
                fullName: document.getElementById('editMyName').value,
                phone: document.getElementById('editMyPhone').value,
                dateOfBirth: document.getElementById('editMyDob').value,
                address: document.getElementById('editMyAddress').value
            };

            await api.updateStudent(data.id, updatedData);

            // Update localStorage if name changed
            localStorage.setItem('userName', updatedData.fullName);

            editModal.hide();
            // Show success alert and reload
            alert('Profile updated successfully!');
            location.reload();
        } catch (err) {
            alert('Failed to update profile: ' + err.message);
        }
    };
}

window.showEditMyProfile = showEditMyProfile;
