package peaksoft.repository.repositoryImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import peaksoft.entity.Department;
import peaksoft.entity.Hospital;
import peaksoft.exceptions.DepartmentAlReadyExistsException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.exceptions.RequiredFieldException;
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
        if (hospital == null) {
            throw new NotFoundException("Hospital with id " + id + " not found");
        }

        // Проверка на null (VII)
        if (department.getName() == null || department.getName().isBlank()) {
            throw new RequiredFieldException("Department name cannot be null or empty");
        }

        // Проверка на дубликат имени (VI)
        if (existsByName(department.getName())) {
            throw new DepartmentAlReadyExistsException("Department with name '" + department.getName() + "' already exists");
        }

        department.setHospital(hospital);
        entityManager.persist(department);
        hospital.getDepartmentList().add(department);
    }

    @Override
    public List<Department> getAllDepartmentByHospital(Long id) {
        return entityManager.createQuery(
                        "select d from Department d " +
                                "left join fetch d.doctorsList " +  // ← Загружаем doctorsList сразу
                                "where d.hospital.id = :id",
                        Department.class)
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public Department getByIdDepartment(Long id) {
        Department department = entityManager.find(Department.class, id);
        if (department == null) {
            throw new NotFoundException("Department with id " + id + " not found");
        }
        return department;
    }

    @Override
    public void updateDepartment(Long id, Department newDepartment) {
        Department department = entityManager.find(Department.class, id);
        if (department == null) {
            throw new NotFoundException("Department with id " + id + " not found");
        }

        if (newDepartment.getName() == null || newDepartment.getName().isBlank()) {
            throw new RequiredFieldException("Department name cannot be null or empty");
        }

        // Проверка на дубликат имени при обновлении
        if (!department.getName().equals(newDepartment.getName()) && existsByName(newDepartment.getName())) {
            throw new DepartmentAlReadyExistsException("Department with name '" + newDepartment.getName() + "' already exists");
        }

        department.setName(newDepartment.getName());
    }

    @Override
    public void deleteDepartment(Long id) {
        Department department = entityManager.find(Department.class, id);
        if (department == null) {
            throw new NotFoundException("Department with id " + id + " not found");
        }
        entityManager.remove(department);
    }
    @Override
    public List<Department> getAllDepartments() {
        return entityManager.createQuery(
                "select d from Department d", Department.class
        ).getResultList();
    }
    @Override
    public boolean existsByName(String name) {
        try {
            Long count = entityManager.createQuery(
                            "select count(d) from Department d where d.name = :name", Long.class)
                    .setParameter("name", name)
                    .getSingleResult();
            return count > 0;
        } catch (NoResultException e) {
            return false;
        }
    }
}