package peaksoft.service;

import peaksoft.entity.Doctor;

import java.util.List;

public interface DoctorService {
    void saveDoctor(Doctor doctor);
    void assignDoctorToDepartments(Long doctorId, List<Long> departmentIds);
    List<Doctor> getAllDoctor();
    Doctor getByIdDoctor(Long id);
    void updateDoctor(Long id,Doctor newDoctor);
    void deleteDoctor(Long id);

    long countDoctorsByHospitalId(Long hospitalId);

    boolean existsByEmail(String email);

    List<Doctor> getDoctorsByDepartment(Long departmentId);
}