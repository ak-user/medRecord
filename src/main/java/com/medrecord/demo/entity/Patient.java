package com.medrecord.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", length = 36 , updatable = false, nullable = false)
    private Integer id;
    private String firstName;
    private String lastName;
    private String dob;
    private String gender;
}
