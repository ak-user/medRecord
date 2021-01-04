package com.medrecord.demo.controller;

import com.medrecord.demo.dto.PatientSearchParams;
import com.medrecord.demo.entity.MedicalRecord;
import com.medrecord.demo.entity.Patient;
import com.medrecord.demo.service.PatientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientControllerTest {

    @Mock
    PatientService patientService;

    @InjectMocks
    PatientController patientController;

    @Test
    void getPatientById() {
        LocalDate date = LocalDate.parse("1809-02-12");
        Patient patient = new Patient(
                1,
                "Abraham",
                "Lincoln",
                "the.greatest@gmail.com",
                date,
                "male"
        );

        when(patientService.findById(1)).thenReturn(patient);

        Patient result = patientController.getPatientById(1);

        verify(patientService, times(1)).findById(1);
        assertThat(result).isEqualTo(patient);
    }

    @Test
    void createPatient() {
        LocalDate date = LocalDate.parse("1809-02-12");
        Patient patientReq = new Patient(
                "Abraham",
                "Lincoln",
                "the.greatest@gmail.com",
                date,
                "male"
        );
        Patient patientResp = new Patient(
                1,
                "Abraham",
                "Lincoln",
                "the.greatest@gmail.com",
                date,
                "male"
        );
        when(patientService.save(patientReq)).thenReturn(patientResp);

        Patient result = patientController.createPatient(patientReq);

        verify(patientService, times(1)).save(patientReq);
        assertThat(result).isEqualTo(patientResp);
    }

    @Test
    void deletePatientById() {
        patientController.deletePatientById(1);
        verify(patientService, times(1)).deletePatientById(1);
    }

    @Test
    void updatePatientById() {

        LocalDate date = LocalDate.parse("1992-02-14");
        Patient patientReq = new Patient(
                "Anton",
                "Boss",
                "the.greatest@gmail.com",
                date,
                "male"
        );

        Patient patientResp = new Patient(
                1,
                "Anton",
                "Boss",
                "the.greatest@gmail.com",
                date,
                "male"
        );

        ResponseEntity<Patient> responseEntity = new ResponseEntity<>(patientResp, HttpStatus.OK);

        when(patientService.updatePatientById(patientReq, 1)).thenReturn(responseEntity);

        ResponseEntity<Patient> result = patientController.updatePatientById(patientReq, 1);

        verify(patientService, times(1)).updatePatientById(patientReq, 1);
        assertThat(result.getBody()).isEqualTo(patientResp);
    }

    @Test
    void searchPatients() {
        LocalDate date = LocalDate.parse("1992-02-14");


        Patient patientRespOne = new Patient(
                1,
                "Anton",
                "Boss",
                "the.greatest@gmail.com",
                date,
                "male"
        );

        Patient patientRespSecond = new Patient(
                1,
                "Matviy",
                "Boss",
                "the.greatest2@gmail.com",
                date,
                "male"
        );

        List<Patient> patients = new ArrayList<>();
        patients.add(patientRespOne);
        patients.add(patientRespSecond);

        ResponseEntity<List<Patient>> responseEntity = new ResponseEntity<>(patients, HttpStatus.OK);

        LocalDate dobFrom = LocalDate.parse("1992-02-13");
        LocalDate dobTo = LocalDate.parse("2000-02-15");
        PatientSearchParams patientSearchParams = new PatientSearchParams("Boss", dobFrom, dobTo);
        when(patientService.searchPatients(patientSearchParams)).thenReturn(responseEntity);

        ResponseEntity<List<Patient>> result = patientController.searchPatients(patientSearchParams);

        verify(patientService, times(1)).searchPatients(patientSearchParams);
        assertThat(Objects.requireNonNull(result.getBody()).size()).isEqualTo(2);
    }

    @Test
    void findMedicalRecordByPatientId() {
        LocalDate date = LocalDate.parse("1809-02-12");
        Patient patient = new Patient(1,"Abraham", "Lincoln", "the.greatest@gmail.com", date, "male");
        MedicalRecord medicalRecord = new MedicalRecord(2, "Greg", "test info", date, patient);

        when(patientService.findMedicalRecordByPatientId(2)).thenReturn(medicalRecord);

        MedicalRecord result = patientController.findMedicalRecordByPatientId(2);

        verify(patientService, times(1)).findMedicalRecordByPatientId(2);
        assertThat(result).isEqualTo(medicalRecord);
    }

    @Test
    void createMedicalRecord() {
        LocalDate date = LocalDate.parse("1809-02-12");
        Patient patient = new Patient(1,"Abraham", "Lincoln", "the.greatest@gmail.com", date, "male");
        MedicalRecord medicalRecordReq = new MedicalRecord("Greg", "test info", date, patient);
        MedicalRecord medicalRecordResp = new MedicalRecord(2, "Greg", "test info", date, patient);

        when(patientService.saveMedicalRecord(1, medicalRecordReq)).thenReturn(medicalRecordResp);

        MedicalRecord result = patientController.createMedicalRecord(1, medicalRecordReq);

        verify(patientService, times(1)).saveMedicalRecord(1, medicalRecordReq);
        assertThat(result).isEqualTo(medicalRecordResp);
    }

    @Test
    void deleteMedicalRecordByPatientId() {
        patientController.deleteMedicalRecordByPatientId(2);
        verify(patientService, times(1)).deleteMedicalRecordByPatientId(2);
    }
}
