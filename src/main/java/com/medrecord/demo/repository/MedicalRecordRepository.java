package com.medrecord.demo.repository;

import com.medrecord.demo.entity.MedicalRecord;
import com.medrecord.demo.entity.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicalRecordRepository extends CrudRepository<MedicalRecord, Integer> {
    Optional<MedicalRecord> findMedicalRecordByPatient(Patient patient);
}
