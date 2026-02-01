package peaksoft.repository.repositoryImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import peaksoft.entity.Patient;
import peaksoft.repository.PatientRepo;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class PatientRepoImpl implements PatientRepo {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public void savePatient(Patient patient) {

    }

    @Override
    public List<Patient> getAllPatient() {
        return List.of();
    }

    @Override
    public Patient getByIdPatient(Long id) {
        return null;
    }

    @Override
    public void updatePatient(Long id, Patient newPatient) {

    }

    @Override
    public void deletePatient(Long id) {

    }

    @Override
    public long countPatientsByHospitalId(Long hospitalId) {
        return 0;
    }
}
