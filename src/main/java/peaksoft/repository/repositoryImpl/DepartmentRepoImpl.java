package peaksoft.repository.repositoryImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import peaksoft.entity.Department;
import peaksoft.repository.DepartmentRepo;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class DepartmentRepoImpl implements DepartmentRepo {
    @PersistenceContext
    private final EntityManager entityManager;
    @Override
    public void saveDepartment(Department department) {

    }

    @Override
    public List<Department> getAllDepartment() {
        return List.of();
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
