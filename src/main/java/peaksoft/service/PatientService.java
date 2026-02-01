package peaksoft.service;

import peaksoft.entity.Patient;

import java.util.List;

public interface PatientService {
    void savePatient(Patient patient);
    List<Patient> getAllPatient();
    Patient getByIdPatient(Long id);
    void updatePatient(Long id,Patient newPatient);
    void deletePatient(Long id);

    long countPatientsByHospitalId(Long hospitalId);
}
