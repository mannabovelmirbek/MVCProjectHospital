package peaksoft.repository;

import org.springframework.stereotype.Repository;
import peaksoft.entity.Department;

import java.util.List;

@Repository
public interface DepartmentRepo {
    void saveDepartment(Department department);
    List<Department> getAllDepartment();
    Department getByIdDepartment(Long id);
    void updateDepartment(Long id,Department newDepartment);
    void deleteDepartment(Long id);

    boolean existsByName(String name);
}
