package peaksoft.service;

import peaksoft.entity.Department;

import java.util.List;

public interface DepartmentService {
    void saveDepartmentWithHospital(Long id,Department department);
    List<Department> getAllDepartmentByHospital(Long id);
    Department getByIdDepartment(Long id);
    void updateDepartment(Long id,Department newDepartment);
    void deleteDepartment(Long id);

    boolean existsByName(String name);
}
