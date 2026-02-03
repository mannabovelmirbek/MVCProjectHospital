package peaksoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.entity.Hospital;
import peaksoft.service.DoctorService;
import peaksoft.service.HospitalService;
import peaksoft.service.PatientService;

import java.util.List;

@Controller
@RequestMapping("/hospitals")
@RequiredArgsConstructor
public class HospitalController {

    private final HospitalService hospitalService;
    private final DoctorService doctorService;
    private final PatientService patientService;

    @GetMapping
    public String getAllHospitals(Model model) {
        List<Hospital> allHospital = hospitalService.getAllHospital();
        model.addAttribute("hospitals", allHospital);
        return "mainPage";
    }

    @GetMapping("/newHospital")
    public String createHospital(Model model) {
        model.addAttribute("newHospital", new Hospital());
        return "newHospital";
    }

    @PostMapping("/saveHospital")
    public String saveHospital(@ModelAttribute("newHospital") Hospital hospital) {
        hospitalService.saveHospital(hospital);
        return "redirect:/hospitals";
    }

    @GetMapping("/{id}")
    public String getByIdHospital(@PathVariable("id") Long id, Model model) {
        Hospital hospital = hospitalService.getByIdHospital(id);

        // Количество пациентов и докторов в hospital (I)
        long doctorsCount = doctorService.countDoctorsByHospitalId(id);
        long patientsCount = patientService.countPatientsByHospitalId(id);

        model.addAttribute("hospital", hospital);
        model.addAttribute("doctorsCount", doctorsCount);
        model.addAttribute("patientsCount", patientsCount);
        return "hospitalById";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Long id, Model model) {
        Hospital hospital = hospitalService.getByIdHospital(id);
        model.addAttribute("editHospital", hospital);
        return "editHospital";
    }

    @PostMapping("/update/{id}")
    public String updateHospital(@PathVariable("id") Long id,
                                 @ModelAttribute("editHospital") Hospital hospital) {
        hospitalService.updateHospital(id, hospital);
        return "redirect:/hospitals";
    }

    @PostMapping("/delete/{id}")
    public String deleteHospital(@PathVariable("id") Long id) {
        hospitalService.deleteHospital(id);
        return "redirect:/hospitals";
    }
}