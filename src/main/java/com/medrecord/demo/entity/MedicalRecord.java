package com.medrecord.demo.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "medicalRecords")
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String doctorName;

    @Column
    private String info;

    @Column
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public MedicalRecord() {
    }

    public MedicalRecord(Integer id, String doctorName, String info, LocalDate date, Patient patient) {
        this.id = id;
        this.doctorName = doctorName;
        this.info = info;
        this.date = date;
        this.patient = patient;
    }

    public MedicalRecord(String doctorName, String info, LocalDate date, Patient patient) {
        this.doctorName = doctorName;
        this.info = info;
        this.date = date;
        this.patient = patient;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
