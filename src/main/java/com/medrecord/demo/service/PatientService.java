package com.medrecord.demo.service;

import com.medrecord.demo.entity.MedicalRecord;
import com.medrecord.demo.entity.Patient;
import com.medrecord.demo.repository.MedicalRecordRepository;
import com.medrecord.demo.repository.PatientRepository;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final MedicalRecordRepository medicalRecordRepository;

    public PatientService(PatientRepository patientRepository, MedicalRecordRepository medicalRecordRepository) {
        this.patientRepository = patientRepository;
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public Patient save(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient findById(Integer id) {
        return  patientRepository.findById(id).get();
    }

    public void deletePatientById(Integer id) {
        patientRepository.deleteById(id);
    }

    public MedicalRecord findMedicalRecordByPatientId(Integer patientId) {
        Patient patient = patientRepository.findById(patientId).get();
        return medicalRecordRepository.findMedicalRecordByPatient(patient).get();
    }

    public MedicalRecord saveMedicalRecord(Integer patientId, MedicalRecord medicalRecord) {
        Patient patient = patientRepository.findById(patientId).get();
        medicalRecord.setPatient(patient);
        return medicalRecordRepository.save(medicalRecord);
    }

    public void deleteMedicalRecordByPatientId(Integer id) {
        medicalRecordRepository.deleteById(id);
    }
}
