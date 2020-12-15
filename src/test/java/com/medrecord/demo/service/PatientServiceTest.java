package com.medrecord.demo.service;

import com.medrecord.demo.entity.MedicalRecord;
import com.medrecord.demo.entity.Patient;
import com.medrecord.demo.repository.MedicalRecordRepository;
import com.medrecord.demo.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @InjectMocks
    private PatientService patientService;

    @Test
    void save() {
        LocalDate date = LocalDate.parse("1809-02-12");
        Patient patientReq = new Patient("Abraham", "Lincoln", "the.greatest@gmail.com", date, "male");
        Patient patientResp = new Patient(1,"Abraham", "Lincoln", "the.greatest@gmail.com", date, "male");

        when(patientRepository.save(patientReq)).thenReturn(patientResp);

        Patient patientResult = patientService.save(patientReq);

        assertEquals(patientResp, patientResult);
        verify(patientRepository, times(1)).save(patientReq);
    }

    @Test
    void findById() {
        LocalDate date = LocalDate.parse("1809-02-12");
        Patient patientResp = new Patient(1,"Abraham", "Lincoln", "the.greatest@gmail.com", date, "male");

        when(patientRepository.findById(1)).thenReturn(java.util.Optional.of(patientResp));

        Patient patientResult = patientService.findById(1);

        assertEquals(patientResp, patientResult);
        verify(patientRepository, times(1)).findById(1);
    }

    @Test
    void deletePatientById() {
        patientService.deletePatientById(1);
        verify(patientRepository, times(1)).deleteById(1);
    }

    @Test
    void updatePatientById() {
        LocalDate date = LocalDate.parse("1809-02-12");
        Patient patientResp = new Patient(1,"Abraham", "Lincoln", "the.greatest@gmail.com", date, "male");

        Patient newPatient = new Patient("Bob", "Marley", "the.cool@gmail.com", date, "male");
        Patient newPatientResp = new Patient(1,"Bob", "Marley", "the.cool@gmail.com", date, "male");

        when(patientRepository.findById(1)).thenReturn(java.util.Optional.of(patientResp));
        when(patientRepository.save(newPatient)).thenReturn(newPatientResp);

        ResponseEntity<Patient> actualResult = patientService.updatePatientById(newPatient, 1);
        ResponseEntity<Patient> expectedResult = new ResponseEntity<>(newPatientResp, HttpStatus.OK);

        assertEquals(expectedResult, actualResult);
        verify(patientRepository, times(1)).findById(1);
        verify(patientRepository, times(1)).save(newPatient);
    }

    @Test
    void findMedicalRecordByPatientId() {
        LocalDate date = LocalDate.parse("1809-02-12");
        Patient patientResp = new Patient(1,"Abraham", "Lincoln", "the.greatest@gmail.com", date, "male");
        MedicalRecord medicalRecordResp = new MedicalRecord(2, "Greg", "test info", date, patientResp);

        when(patientRepository.findById(1)).thenReturn(java.util.Optional.of(patientResp));
        when(medicalRecordRepository.findMedicalRecordByPatient(patientResp)).thenReturn(java.util.Optional.of(medicalRecordResp));

        MedicalRecord medicalRecordActualResult = patientService.findMedicalRecordByPatientId(1);

        assertEquals(medicalRecordResp, medicalRecordActualResult);
        verify(patientRepository, times(1)).findById(1);
        verify(medicalRecordRepository, times(1)).findMedicalRecordByPatient(patientResp);
    }

    @Test
    void saveMedicalRecord() {
        LocalDate date = LocalDate.parse("1809-02-12");
        Patient patient = new Patient(1,"Abraham", "Lincoln", "the.greatest@gmail.com", date, "male");
        MedicalRecord medicalRecordReq = new MedicalRecord("Greg", "test info", date, patient);
        MedicalRecord medicalRecordResp = new MedicalRecord(2, "Greg", "test info", date, patient);

        when(patientRepository.findById(1)).thenReturn(java.util.Optional.of(patient));
        when(medicalRecordRepository.save(medicalRecordReq)).thenReturn(medicalRecordResp);

        MedicalRecord medicalRecordActualResult = patientService.saveMedicalRecord(1, medicalRecordReq);

        assertEquals(medicalRecordResp, medicalRecordActualResult);
        verify(patientRepository, times(1)).findById(1);
        verify(medicalRecordRepository, times(1)).save(medicalRecordReq);
    }

    @Test
    void deleteMedicalRecordByPatientId() {
        patientService.deleteMedicalRecordByPatientId(1);
        verify(medicalRecordRepository, times(1)).deleteById(1);
    }
}
