package com.drivingschool.schedule.dto;

import com.drivingschool.schedule.entity.ScheduleType;
import lombok.Data;

@Data
public class ScheduleDTO {
    private String id;
    private String studentId;
    private String studentName;
    private String instructorId;
    private String instructorName;
    private String vehicleId;
    private String vehicleRegistration;
    private String scheduleDate;
    private String startTime;
    private String endTime;
    private ScheduleType scheduleType;
    private String status;
    private String notes;
}
