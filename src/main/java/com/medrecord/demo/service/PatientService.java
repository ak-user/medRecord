package com.medrecord.demo.service;

import com.medrecord.demo.controller.InvalidRequestException;
import com.medrecord.demo.dto.PatientSearchParams;
import com.medrecord.demo.entity.MedicalRecord;
import com.medrecord.demo.entity.Patient;
import com.medrecord.demo.repository.MedicalRecordRepository;
import com.medrecord.demo.repository.PatientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    private void validatePatientByFullNameAndEmail(Patient patient) {
        if (patient.getFirstName().length() < 3) {
            throw new InvalidRequestException("First name must be more than 3 characters");
        }

        if (patient.getLastName().length() < 3) {
            throw new InvalidRequestException("Last name must be more than 3 characters");
        }

        if (patient.getEmail().length() < 3) {
            throw new InvalidRequestException("Email must be more than 3 characters");
        }
    }

    public Patient save(Patient patient) {

        validatePatientByFullNameAndEmail(patient);

        Optional<List<Patient>> patientList = patientRepository.findByFirstNameContainingAndLastNameContainingAndDob(
                patient.getFirstName(),
                patient.getLastName(),
                patient.getDob()
        );

        if (patientList.isPresent() && patientList.get().size() != 0) {
            throw new InvalidRequestException("Invalid patient for create, patient with FN, LN, DOB already exist");
        }


        Optional<List<Patient>> foundedPatientsWithEmail = patientRepository.findByEmail(patient.getEmail());

        if (foundedPatientsWithEmail.isPresent() && foundedPatientsWithEmail.get().size() != 0) {
            throw new InvalidRequestException("You can't use this email. Current email '" + patient.getEmail() + "' exist");
        }

        return patientRepository.save(patient);
    }

    public Patient findById(Integer id) {
        return  patientRepository.findById(id).get();
    }

    public void deletePatientById(Integer id) {
        patientRepository.deleteById(id);
    }

    public ResponseEntity<Patient> updatePatientById(Patient newPatient, Integer id) {

        validatePatientByFullNameAndEmail(newPatient);

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
