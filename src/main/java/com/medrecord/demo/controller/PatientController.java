package com.medrecord.demo.controller;

import com.medrecord.demo.entity.Patient;
import com.medrecord.demo.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

//    @GetMapping
//    public Iterable<Patient> list() {
//        return patientRepository.findAll();
//    }
}
