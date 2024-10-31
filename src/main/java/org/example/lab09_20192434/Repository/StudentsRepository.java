package org.example.lab09_20192434.Repository;

import org.example.lab09_20192434.Entity.StudentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentsRepository extends JpaRepository<StudentsEntity, Integer> {

    @Query(value =
            "select * from lab09.students order by gpa desc;", nativeQuery = true)
    List<StudentsEntity> findAllStudents();
}
