package com.medrecord.demo.service;

import com.medrecord.demo.dto.PatientSearchParams;
import com.medrecord.demo.entity.MedicalRecord;
import com.medrecord.demo.entity.Patient;
import com.medrecord.demo.repository.MedicalRecordRepository;
import com.medrecord.demo.repository.PatientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public ResponseEntity<Patient> updatePatientById(Patient newPatient, Integer id) {

        Optional<Patient> patientOptional = patientRepository.findById(id);
        if (patientOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        newPatient.setId(id);
        return new ResponseEntity<>(patientRepository.save(newPatient), HttpStatus.OK);
    }

    public ResponseEntity<List<Patient>> searchPatients(PatientSearchParams searchParams) {

        Optional<List<Patient>> patientListOptional = patientRepository
                .findByFirstNameContainingOrLastNameContainingOrEmailContainingAndDobBetween(
                        searchParams.getSearchPhrase(),
                        searchParams.getSearchPhrase(),
                        searchParams.getSearchPhrase(),
                        searchParams.getDobFrom(),
                        searchParams.getDobTo()
                );
        if (patientListOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(patientListOptional.get(), HttpStatus.OK);
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
