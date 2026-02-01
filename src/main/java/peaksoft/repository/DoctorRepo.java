package peaksoft.repository;

import org.springframework.stereotype.Repository;
import peaksoft.entity.Doctor;

import java.util.List;

@Repository
public interface DoctorRepo {
    void saveDoctor(Doctor doctor);
    List<Doctor> getAllDoctor();
    Doctor getByIdDoctor(Long id);
    void updateDoctor(Long id,Doctor newDoctor);
    void deleteDoctor(Long id);

    long countDoctorsByHospitalId(Long hospitalId);

    boolean existsByEmail(String email);

}
