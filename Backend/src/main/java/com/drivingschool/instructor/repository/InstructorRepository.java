package com.drivingschool.instructor.repository;

import com.drivingschool.instructor.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, String> {
    Instructor findByEmail(String email);
    @org.springframework.data.jpa.repository.Query(value = "SELECT * FROM instructor WHERE id LIKE 'I%' ORDER BY CAST(SUBSTRING(id, 2) AS UNSIGNED) DESC LIMIT 1", nativeQuery = true)
    Instructor findLastManualId();
}
