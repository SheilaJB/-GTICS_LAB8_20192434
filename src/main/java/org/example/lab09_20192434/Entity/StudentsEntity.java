package org.example.lab09_20192434.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "students")
@Data
public class StudentsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "identificacion")
    private String identificacion;
    @Column(name = "gpa")
    private double gpa;
    @Column(name = "facultad")
    private String facultad;
    @Column(name = "numeroCredito")
    private int numeroCredito;

}
