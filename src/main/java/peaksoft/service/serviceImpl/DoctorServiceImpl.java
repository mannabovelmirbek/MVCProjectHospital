package peaksoft.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.entity.Doctor;
import peaksoft.repository.DoctorRepo;
import peaksoft.service.DoctorService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepo doctorRepo;

    @Override
    public void saveDoctor(Doctor doctor) {
        doctorRepo.saveDoctor(doctor);
    }

    @Override
    public void assignDoctorToDepartments(Long doctorId, List<Long> departmentIds) {
        doctorRepo.assignDoctorToDepartments(doctorId, departmentIds);
    }

    @Override
    public List<Doctor> getAllDoctor() {
        return doctorRepo.getAllDoctor();
    }

    @Override
    public Doctor getByIdDoctor(Long id) {
        return doctorRepo.getByIdDoctor(id);
    }

    @Override
    public void updateDoctor(Long id, Doctor newDoctor) {
        doctorRepo.updateDoctor(id, newDoctor);
    }

    @Override
    public void deleteDoctor(Long id) {
        doctorRepo.deleteDoctor(id);
    }

    @Override
    public long countDoctorsByHospitalId(Long hospitalId) {
        return doctorRepo.countDoctorsByHospitalId(hospitalId);
    }

    @Override
    public boolean existsByEmail(String email) {
        return doctorRepo.existsByEmail(email);
    }

    @Override
    public List<Doctor> getDoctorsByDepartment(Long departmentId) {
        return doctorRepo.getDoctorsByDepartment(departmentId);
    }
}