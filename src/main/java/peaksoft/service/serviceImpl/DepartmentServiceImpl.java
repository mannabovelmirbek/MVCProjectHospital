package peaksoft.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.entity.Department;
import peaksoft.repository.DepartmentRepo;
import peaksoft.service.DepartmentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepo departmentRepo;

    @Override
    public void saveDepartmentWithHospital(Long id, Department department) {
        departmentRepo.saveDepartmentWithHospital(id, department);
    }

    @Override
    public List<Department> getAllDepartmentByHospital(Long id) {
        return departmentRepo.getAllDepartmentByHospital(id);
    }

    @Override
    public Department getByIdDepartment(Long id) {
        return departmentRepo.getByIdDepartment(id);
    }

    @Override
    public void updateDepartment(Long id, Department newDepartment) {
        departmentRepo.updateDepartment(id, newDepartment);
    }

    @Override
    public void deleteDepartment(Long id) {
        departmentRepo.deleteDepartment(id);
    }

    @Override
    public boolean existsByName(String name) {
        return departmentRepo.existsByName(name);
    }
}