package peaksoft.repository.repositoryImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import peaksoft.entity.Doctor;
import peaksoft.repository.DoctorRepo;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class DoctorRepoImpl implements DoctorRepo {
    @PersistenceContext
    private final EntityManager entityManager;
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
