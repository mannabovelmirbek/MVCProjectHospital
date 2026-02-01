package peaksoft.repository;

import org.springframework.stereotype.Repository;
import peaksoft.entity.Patient;

import java.util.List;

@Repository
public interface PatientRepo {
    void savePatient(Patient patient);
    List<Patient> getAllPatient();
    Patient getByIdPatient(Long id);
    void updatePatient(Long id,Patient newPatient);
    void deletePatient(Long id);

    long countPatientsByHospitalId(Long hospitalId);
}
