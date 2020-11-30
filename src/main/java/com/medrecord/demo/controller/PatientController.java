package com.medrecord.demo.controller;

import com.medrecord.demo.entity.Patient;
import com.medrecord.demo.service.PatientService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }


//    @GetMapping
//    @ResponseBody
//    public Optional<Patient> getPatientByID(@RequestParam Integer id) {
//        return patientRepository.findById(id);
//    }

    @PostMapping
    public Patient createPatient(@RequestBody Patient patient) {
        System.out.println(patient.toString());
        return patientService.savePatient(patient);
    }



}
