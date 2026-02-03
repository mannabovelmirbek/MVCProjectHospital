package peaksoft.repository;

import org.springframework.stereotype.Repository;
import peaksoft.entity.Department;

import java.util.List;

@Repository
public interface DepartmentRepo {
    void saveDepartmentWithHospital(Long id, Department department);
    List<Department> getAllDepartmentByHospital(Long id);
    List<Department> getAllDepartments();
    Department getByIdDepartment(Long id);
    void updateDepartment(Long id, Department newDepartment);
    void deleteDepartment(Long id);
    boolean existsByName(String name);
}