package com.drivingschool.schedule.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String studentId;
    private String instructorId;
    private String vehicleId;
    private String sheduleDate;
    private String startTime;
    private String endTime;
    @Enumerated(EnumType.STRING)
    private ScheduleType scheduleType;
    private String status;
    private String notes;
}
