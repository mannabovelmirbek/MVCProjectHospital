package peaksoft.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.entity.Doctor;
import peaksoft.service.DoctorService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    @Override
    public void saveDoctor(Doctor doctor) {

    }

    @Override
    public List<Doctor> getAllDoctor() {
        return List.of();
    }

    @Override
    public Doctor getByIdDoctor(Long id) {
        return null;
    }

    @Override
    public void updateDoctor(Long id, Doctor newDoctor) {

    }

    @Override
    public void deleteDoctor(Long id) {

    }

    @Override
    public long countDoctorsByHospitalId(Long hospitalId) {
        return 0;
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }
}
