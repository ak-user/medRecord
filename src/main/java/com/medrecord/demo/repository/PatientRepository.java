package com.medrecord.demo.repository;

import com.medrecord.demo.entity.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Integer> {
    Optional<List<Patient>> findByFirstNameContainingOrLastNameContainingOrEmailContainingAndDobBetween(
            String phraseForFirstName,
            String phraseForLastname,
            String phraseForEmail,
            LocalDate dateFrom,
            LocalDate dateTo
    );

    Optional<List<Patient>> findByFirstNameContainingAndLastNameContainingAndDob(
            String phraseForFirstName,
            String phraseForLastname,
            LocalDate dob
    );

    Optional<List<Patient>> findByEmail(String email);

}
