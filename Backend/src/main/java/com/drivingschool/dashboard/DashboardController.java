package com.drivingschool.dashboard;

import com.drivingschool.instructor.service.InstructorService;
import com.drivingschool.payment.service.PaymentService;
import com.drivingschool.schedule.service.ScheduleService;
import com.drivingschool.student.service.StudentService;
import com.drivingschool.vehicle.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/stats")
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();

        try {
            stats.put("totalStudents", studentService.getAllStudents().size());
            stats.put("totalInstructors", instructorService.getAllInstructors().size());
            stats.put("totalVehicles", vehicleService.getAllVehicles().size());
            stats.put("totalRevenue", paymentService.getAllPayments().stream()
                    .filter(p -> "PAID".equals(p.getStatus() != null ? p.getStatus().name() : null))
                    .mapToDouble(p -> p.getAmount())
                    .sum());
            stats.put("todaySchedulesCount", scheduleService.getTodaySchedules().size());
        } catch (Exception e) {
            System.err.println("Dashboard Stats Error: " + e.getMessage());
            // Provide defaults if something fails
            stats.putIfAbsent("totalStudents", 0);
            stats.putIfAbsent("totalInstructors", 0);
            stats.putIfAbsent("totalVehicles", 0);
            stats.putIfAbsent("totalRevenue", 0.0);
            stats.putIfAbsent("todaySchedulesCount", 0);
        }

        return stats;
    }

    @GetMapping("/revenue-trends")
    public Map<String, Object> getRevenueTrends() {
        Map<String, Object> trends = new HashMap<>();
        trends.put("monthly", paymentService.getMonthlyRevenue());
        trends.put("weekly", paymentService.getWeeklyRevenue());
        return trends;
    }
}
