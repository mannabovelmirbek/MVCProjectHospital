package peaksoft.repository.repositoryImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import peaksoft.entity.Hospital;
import peaksoft.exceptions.NotFoundException;
import peaksoft.exceptions.RequiredFieldException;
import peaksoft.repository.HospitalRepo;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class HospitalRepoImpl implements HospitalRepo {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public void saveHospital(Hospital hospital) {

        if (hospital.getName() == null || hospital.getName().isBlank()) {
            throw new RequiredFieldException("Hospital name cannot be null or empty");
        }
        if (hospital.getAddress() == null || hospital.getAddress().isBlank()) {
            throw new RequiredFieldException("Hospital address cannot be null or empty");
        }
        entityManager.persist(hospital);
    }

    @Override
    public List<Hospital> getAllHospital() {
        return entityManager.createQuery(
                        "select h from Hospital h", Hospital.class)
                .getResultList();
    }

    @Override
    public Hospital getByIdHospital(Long id) {
        Hospital hospital = entityManager.createQuery(
                        "select h from Hospital h left join fetch h.departmentList where h.id = :id",
                        Hospital.class)
                .setParameter("id", id)
                .getSingleResult();

        if (hospital == null) {
            throw new NotFoundException("Hospital with id " + id + " not found");
        }
        return hospital;
    }

    @Override
    public void updateHospital(Long id, Hospital newHospital) {
        Hospital hospital = entityManager.find(Hospital.class, id);
        if (hospital == null) {
            throw new NotFoundException("Hospital with id " + id + " not found");
        }

        if (newHospital.getName() == null || newHospital.getName().isBlank()) {
            throw new RequiredFieldException("Hospital name cannot be null or empty");
        }
        if (newHospital.getAddress() == null || newHospital.getAddress().isBlank()) {
            throw new RequiredFieldException("Hospital address cannot be null or empty");
        }

        hospital.setName(newHospital.getName());
        hospital.setAddress(newHospital.getAddress());
    }

    @Override
    public void deleteHospital(Long id) {
        Hospital hospital = entityManager.find(Hospital.class, id);
        if (hospital == null) {
            throw new NotFoundException("Hospital with id " + id + " not found");
        }
        entityManager.remove(hospital);
    }
}