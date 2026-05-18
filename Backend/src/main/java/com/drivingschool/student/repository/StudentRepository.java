package com.drivingschool.student.repository;

import com.drivingschool.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    Student findByEmail(String email);
    @org.springframework.data.jpa.repository.Query(value = "SELECT * FROM student WHERE id LIKE 'S%' ORDER BY CAST(SUBSTRING(id, 2) AS UNSIGNED) DESC LIMIT 1", nativeQuery = true)
    Student findLastManualId();
}
