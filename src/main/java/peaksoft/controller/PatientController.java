package peaksoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.entity.Hospital;
import peaksoft.entity.Patient;
import peaksoft.service.HospitalService;
import peaksoft.service.PatientService;

import java.util.List;

@Controller
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;
    private final HospitalService hospitalService;

    @GetMapping
    public String getAllPatients(Model model) {
        List<Patient> patients = patientService.getAllPatient();
        model.addAttribute("patients", patients);
        return "patientsList";
    }

    @GetMapping("/add")
    public String addPatientForm(Model model) {
        List<Hospital> hospitals = hospitalService.getAllHospital();
        model.addAttribute("newPatient", new Patient());
        model.addAttribute("hospitals", hospitals);
        return "newPatient";
    }

    @PostMapping("/save")
    public String savePatient(@ModelAttribute("newPatient") Patient patient,
                              @RequestParam("hospitalId") Long hospitalId) {
        Hospital hospital = hospitalService.getByIdHospital(hospitalId);
        patient.setHospital(hospital);
        patientService.savePatient(patient);
        return "redirect:/patients";
    }

    @GetMapping("/{id}")
    public String getPatientById(@PathVariable("id") Long id, Model model) {
        Patient patient = patientService.getByIdPatient(id);
        model.addAttribute("patient", patient);
        return "patientById";
    }

    @GetMapping("/edit/{id}")
    public String editPatientForm(@PathVariable("id") Long id, Model model) {
        Patient patient = patientService.getByIdPatient(id);
        List<Hospital> hospitals = hospitalService.getAllHospital();
        model.addAttribute("editPatient", patient);
        model.addAttribute("hospitals", hospitals);
        return "editPatient";
    }

    @PostMapping("/update/{id}")
    public String updatePatient(@PathVariable("id") Long id,
                                @ModelAttribute("editPatient") Patient patient) {
        patientService.updatePatient(id, patient);
        return "redirect:/patients";
    }

    @PostMapping("/delete/{id}")
    public String deletePatient(@PathVariable("id") Long id) {
        patientService.deletePatient(id);
        return "redirect:/patients";
    }
}