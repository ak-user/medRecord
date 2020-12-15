package com.medrecord.demo.controller;

import com.medrecord.demo.dto.PatientSearchParams;
import com.medrecord.demo.entity.MedicalRecord;
import com.medrecord.demo.entity.Patient;
import com.medrecord.demo.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public Patient getPatientById(@RequestParam Integer id) {
        return patientService.findById(id);
    }

    @PostMapping
    public Patient createPatient(@RequestBody Patient patient) {
        return patientService.save(patient);
    }

    @DeleteMapping("/{patientId}")
    public void deletePatientById(@PathVariable Integer patientId) {
        patientService.deletePatientById(patientId);
    }

    @PutMapping
    public ResponseEntity<Patient> updatePatientById(@RequestBody Patient patient, @RequestParam Integer id) {
        return patientService.updatePatientById(patient, id);
    }

    @PostMapping("/_search")
    public ResponseEntity<List<Patient>> searchPatients(@RequestBody PatientSearchParams searchParams) {
        return patientService.searchPatients(searchParams);
    }



    @GetMapping("/{patientId}/medrecord")
    public MedicalRecord findMedicalRecordByPatientId(@PathVariable Integer patientId) {
        return patientService.findMedicalRecordByPatientId(patientId);
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
