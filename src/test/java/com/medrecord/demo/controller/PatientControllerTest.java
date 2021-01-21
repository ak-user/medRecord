package com.medrecord.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medrecord.demo.dto.PatientSearchParams;
import com.medrecord.demo.entity.MedicalRecord;
import com.medrecord.demo.entity.Patient;
import com.medrecord.demo.service.PatientService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PatientController.class)
class PatientControllerTest {


    @Autowired
    private MockMvc mvc;

    @MockBean
    PatientService patientService;

    @InjectMocks
    PatientController patientController;

    @Test
    void getPatientById() throws Exception {
        LocalDate date = LocalDate.parse("1809-02-12");
        Patient patient = Patient.builder()
                .id(1)
                .firstName("Abraham")
                .lastName("Lincoln")
                .email("the.greatest@gmail.com")
                .dob(date)
                .gender("male")
                .build();

        when(patientService.findById(1)).thenReturn(patient);

        mvc.perform(get("/patient?id=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(patient.getFirstName())));
    }

    @Test
    void createPatient() throws Exception {
        LocalDate date = LocalDate.parse("1809-02-12");

        Patient patientReq = Patient.builder()
                .firstName("Abraham")
                .lastName("Lincoln")
                .email("the.greatest@gmail.com")
                .dob(date)
                .gender("male")
                .build();

        Patient patientResp = Patient.builder()
                .id(1)
                .firstName("Abraham")
                .lastName("Lincoln")
                .email("the.greatest@gmail.com")
                .dob(date)
                .gender("male")
                .build();

        when(patientService.save(patientReq)).thenReturn(patientResp);

        mvc.perform(post("/patient")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(patientReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(patientResp.getFirstName())));
    }

    @Test
    void createPatientWithoutBody() throws Exception {
        mvc.perform(post("/patient")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createPatientWithLessThanTreeLetters() throws Exception {
        when(patientService.save(any())).thenThrow( new InvalidRequestException("First name must be more than 3 characters"));

        mvc.perform(post("/patient")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deletePatientById() throws Exception {
        mvc.perform(delete("/patient/1"))
                .andExpect(status().isOk());
    }

    @Test
    void updatePatientById() throws Exception {

        LocalDate date = LocalDate.parse("1992-02-14");
        Patient patientReq = Patient.builder()
                .firstName("Anton")
                .lastName("Boss")
                .email("the.greatest@gmail.com")
                .dob(date)
                .gender("male")
                .build();

        Patient patientResp = Patient.builder()
                .id(1)
                .firstName("Anton")
                .lastName("Boss")
                .email("the.greatest@gmail.com")
                .dob(date)
                .gender("male")
                .build();

        ResponseEntity<Patient> responseEntity = new ResponseEntity<>(patientResp, HttpStatus.OK);

        when(patientService.updatePatientById(patientReq, 1)).thenReturn(responseEntity);

        mvc.perform(put("/patient?id=1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(patientReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(patientResp.getFirstName())));
    }

    @Test
    void searchPatients() throws Exception {
        LocalDate date = LocalDate.parse("1992-02-14");

        Patient patientRespOne = Patient.builder()
                .id(1)
                .firstName("Anton")
                .lastName("Boss")
                .email("the.greatest@gmail.com")
                .dob(date)
                .gender("male")
                .build();

        Patient patientRespSecond = Patient.builder()
                .id(2)
                .firstName("Matviy")
                .lastName("Boss")
                .email("the.greatest2@gmail.com")
                .dob(date)
                .gender("male")
                .build();

        List<Patient> patients = new ArrayList<>();
        patients.add(patientRespOne);
        patients.add(patientRespSecond);

        ResponseEntity<List<Patient>> responseEntity = new ResponseEntity<>(patients, HttpStatus.OK);

        LocalDate dobFrom = LocalDate.parse("1992-02-13");
        LocalDate dobTo = LocalDate.parse("2000-02-15");
        PatientSearchParams patientSearchParams = new PatientSearchParams("Boss", dobFrom, dobTo);
        when(patientService.searchPatients(patientSearchParams)).thenReturn(responseEntity);

        mvc.perform(post("/patient/_search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(patientSearchParams)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is(patientRespOne.getFirstName())));
    }

    @Test
    void findMedicalRecordByPatientId() throws Exception {
        LocalDate date = LocalDate.parse("1809-02-12");
        Patient patient = Patient.builder()
                .id(1)
                .firstName("Abraham")
                .lastName("Lincoln")
                .email("the.greatest@gmail.com")
                .dob(date)
                .gender("male")
                .build();

        MedicalRecord medicalRecord = MedicalRecord.builder()
                .id(2)
                .doctorName("Greg")
                .info("test info")
                .date(date)
                .patient(patient)
                .build();

        when(patientService.findMedicalRecordByPatientId(1)).thenReturn(medicalRecord);

        mvc.perform(get("/patient/1/medrecord")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.doctorName", is(medicalRecord.getDoctorName())));

    }

    @Test
    void createMedicalRecord() throws Exception {
        LocalDate date = LocalDate.parse("1809-02-12");
        Patient patient = Patient.builder()
                .id(1)
                .firstName("Abraham")
                .lastName("Lincoln")
                .email("the.greatest@gmail.com")
                .dob(date)
                .gender("male")
                .build();


        MedicalRecord medicalRecordReq = MedicalRecord.builder()
                .doctorName("Greg")
                .info("test info")
                .date(date)
                .patient(patient)
                .build();

        MedicalRecord medicalRecordResp = MedicalRecord.builder()
                .id(2)
                .doctorName("Greg")
                .info("test info")
                .date(date)
                .patient(patient)
                .build();

        when(patientService.saveMedicalRecord(1, medicalRecordReq)).thenReturn(medicalRecordResp);

        mvc.perform(post("/patient/1/medrecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(medicalRecordReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.doctorName", is(medicalRecordResp.getDoctorName())));

    }

    @Test
    void deleteMedicalRecordByPatientId() throws Exception {

        mvc.perform(delete("/patient/1/medrecord/1"))
                .andExpect(status().isOk());

    }
}
