package com.medrecord.demo.controller;

import com.medrecord.demo.entity.MedicalRecord;
import com.medrecord.demo.entity.Patient;
import com.medrecord.demo.service.PatientService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public Patient getPatientByID(@RequestParam Integer id) {
        return patientService.findById(id);
    }

    @GetMapping("/{patientId}/medrecord")
    public MedicalRecord findMedicalRecordByPatientId(@PathVariable Integer patientId) {
        return patientService.findMedicalRecordByPatientId(patientId);
    }

    @DeleteMapping("/{patientId}")
    public void deletePatientById(@PathVariable Integer patientId) {
        patientService.deletePatientById(patientId);
    }

    @PostMapping
    public Patient createPatient(@RequestBody Patient patient) {
        return patientService.save(patient);
    }

    @PostMapping("/{patientId}/medrecord")
    public MedicalRecord createMedicalRecord(@PathVariable Integer patientId, @RequestBody MedicalRecord medicalRecord ) {
        return patientService.saveMedicalRecord(patientId, medicalRecord);
    }

    @DeleteMapping("/{patientId}/medrecord/{recordId}")
    public void deleteMedicalRecordByPatientId(@PathVariable Integer recordId) {
        patientService.deleteMedicalRecordByPatientId(recordId);
    }
}
