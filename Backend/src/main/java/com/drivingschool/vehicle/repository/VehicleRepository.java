package com.drivingschool.vehicle.repository;

import com.drivingschool.vehicle.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {
    @org.springframework.data.jpa.repository.Query(value = "SELECT * FROM vehicle WHERE id LIKE 'V%' ORDER BY CAST(SUBSTRING(id, 2) AS UNSIGNED) DESC LIMIT 1", nativeQuery = true)
    Vehicle findLastManualId();
}

