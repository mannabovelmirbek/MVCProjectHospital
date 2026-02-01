package peaksoft.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.entity.Department;
import peaksoft.service.DepartmentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
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
