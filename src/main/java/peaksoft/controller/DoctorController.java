package peaksoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.entity.Doctor;
import peaksoft.entity.Hospital;
import peaksoft.service.DepartmentService;
import peaksoft.service.DoctorService;
import peaksoft.service.HospitalService;

import java.util.List;

@Controller
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;
    private final HospitalService hospitalService;
    private final DepartmentService departmentService;


    @GetMapping
    public String getAllDoctors(Model model) {
        List<Doctor> doctors = doctorService.getAllDoctor();
        model.addAttribute("doctors", doctors);
        return "doctorsList";
    }

    @GetMapping("/add")
    public String addDoctorForm(Model model) {
        List<Hospital> hospitals = hospitalService.getAllHospital();
        model.addAttribute("newDoctor", new Doctor());
        model.addAttribute("hospitals", hospitals);
        return "newDoctor";
    }

    @PostMapping("/save")
    public String saveDoctor(@ModelAttribute("newDoctor") Doctor doctor,
                             @RequestParam("hospitalId") Long hospitalId,
                             @RequestParam(value = "departmentIds", required = false) List<Long> departmentIds) {

        // 1. Устанавливаем hospital для доктора
        Hospital hospital = hospitalService.getByIdHospital(hospitalId);
        doctor.setHospital(hospital);

        // 2. Сохраняем доктора
        doctorService.saveDoctor(doctor);

        // 3. Назначаем доктора в департаменты (если выбраны)
        if (departmentIds != null && !departmentIds.isEmpty()) {
            doctorService.assignDoctorToDepartments(doctor.getId(), departmentIds);
        }

        return "redirect:/doctors";
    }

    @GetMapping("/{id}")
    public String getDoctorById(@PathVariable("id") Long id, Model model) {
        Doctor doctor = doctorService.getByIdDoctor(id);
        model.addAttribute("doctor", doctor);
        return "doctorById";
    }

    @GetMapping("/edit/{id}")
    public String editDoctorForm(@PathVariable("id") Long id, Model model) {
        Doctor doctor = doctorService.getByIdDoctor(id);
        List<Hospital> hospitals = hospitalService.getAllHospital();
        model.addAttribute("editDoctor", doctor);
        model.addAttribute("hospitals", hospitals);
        return "editDoctor";
    }

    @PostMapping("/update/{id}")
    public String updateDoctor(@PathVariable("id") Long id,
                               @ModelAttribute("editDoctor") Doctor doctor) {
        doctorService.updateDoctor(id, doctor);
        return "redirect:/doctors";
    }

    @PostMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable("id") Long id) {
        doctorService.deleteDoctor(id);
        return "redirect:/doctors";
    }
}