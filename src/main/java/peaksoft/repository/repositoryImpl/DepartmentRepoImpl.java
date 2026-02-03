package peaksoft.repository.repositoryImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import peaksoft.entity.Department;
import peaksoft.entity.Hospital;
import peaksoft.repository.DepartmentRepo;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class DepartmentRepoImpl implements DepartmentRepo {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public void saveDepartmentWithHospital(Long id, Department department) {
        Hospital hospital = entityManager.find(Hospital.class, id);
        department.setHospital(hospital);
        entityManager.persist(department);
        hospital.getDepartmentList().add(department);
    }

    @Override
    public List<Department> getAllDepartmentByHospital(Long id) {
        return entityManager.createQuery("select d from Department d join fetch d.hospital h where d.hospital.id = :id",Department.class)
                .setParameter("id",id).getResultList();
    }

    @Override
    public Department getByIdDepartment(Long id) {
        return null;
    }

    @Override
    public void updateDepartment(Long id, Department newDepartment) {

    }

    @Override
    public void deleteDepartment(Long id) {

    }

    @Override
    public boolean existsByName(String name) {
        return false;
    }
}
