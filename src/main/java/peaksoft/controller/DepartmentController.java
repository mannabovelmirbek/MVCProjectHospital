package peaksoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.entity.Department;
import peaksoft.service.DepartmentService;

import java.util.List;

@Controller
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping("/hospital/{hospitalId}")
    public String getAllDepartmentsByHospital(@PathVariable("hospitalId") Long hospitalId, Model model) {
        List<Department> allDepartmentByHospital = departmentService.getAllDepartmentByHospital(hospitalId);
        model.addAttribute("departments", allDepartmentByHospital);
        model.addAttribute("hospitalId", hospitalId);
        return "departmentsList";
    }

    @GetMapping("/add/{hospitalId}")
    public String addDepartmentForm(@PathVariable("hospitalId") Long hospitalId, Model model) {
        model.addAttribute("newDepartment", new Department());
        model.addAttribute("hospitalId", hospitalId);
        return "newDepartment";
    }

    @PostMapping("/save/{hospitalId}")
    public String saveDepartment(@PathVariable("hospitalId") Long hospitalId,
                                 @ModelAttribute("newDepartment") Department department) {
        departmentService.saveDepartmentWithHospital(hospitalId, department);
        // ✅ ИЗМЕНЕНО: Теперь перенаправляет на страницу деталей больницы
        return "redirect:/hospitals/" + hospitalId;
    }
}