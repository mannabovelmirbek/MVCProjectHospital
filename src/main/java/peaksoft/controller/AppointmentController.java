package peaksoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.entity.*;
import peaksoft.service.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final HospitalService hospitalService;
    private final DepartmentService departmentService;
    private final DoctorService doctorService;
    private final PatientService patientService;

    @GetMapping
    public String getAllAppointments(Model model) {
        // Последние записи первыми (II)
        List<Appointment> appointments = appointmentService.findAllOrderByDateDesc();
        model.addAttribute("appointments", appointments);
        return "appointmentsList";
    }

    @GetMapping("/book")
    public String bookAppointmentForm(Model model) {
        List<Hospital> hospitals = hospitalService.getAllHospital();
        List<Patient> patients = patientService.getAllPatient();

        model.addAttribute("newAppointment", new Appointment());
        model.addAttribute("hospitals", hospitals);
        model.addAttribute("patients", patients);
        return "bookAppointment";
    }

    @PostMapping("/save")
    public String saveAppointment(@ModelAttribute("newAppointment") Appointment appointment,
                                  @RequestParam("hospitalId") Long hospitalId,
                                  @RequestParam("departmentId") Long departmentId,
                                  @RequestParam("doctorId") Long doctorId,
                                  @RequestParam("patientId") Long patientId,
                                  @RequestParam("date") String dateStr) {

        Hospital hospital = hospitalService.getByIdHospital(hospitalId);
        Department department = departmentService.getByIdDepartment(departmentId);
        Doctor doctor = doctorService.getByIdDoctor(doctorId);
        Patient patient = patientService.getByIdPatient(patientId);

        appointment.setHospital(hospital);
        appointment.setDepartment(department);
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setDate(LocalDate.parse(dateStr));

        appointmentService.saveAppointment(appointment);
        return "redirect:/appointments";
    }

    @GetMapping("/{id}")
    public String getAppointmentById(@PathVariable("id") Long id, Model model) {
        Appointment appointment = appointmentService.getByIdAppointment(id);
        model.addAttribute("appointment", appointment);
        return "appointmentById";
    }

    @PostMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable("id") Long id) {
        appointmentService.deleteAppointment(id);
        return "redirect:/appointments";
    }

    // ✅ API endpoints - теперь работают благодаря Jackson аннотациям
    @GetMapping("/api/departments/{hospitalId}")
    @ResponseBody
    public List<Map<String, Object>> getDepartmentsByHospital(@PathVariable("hospitalId") Long hospitalId) {
        List<Department> departments = departmentService.getAllDepartmentByHospital(hospitalId);

        return departments.stream()
                .map(dept -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", dept.getId());
                    map.put("name", dept.getName());
                    return map;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/api/doctors/{departmentId}")
    @ResponseBody
    public List<Map<String, Object>> getDoctorsByDepartment(@PathVariable("departmentId") Long departmentId) {
        List<Doctor> doctors = doctorService.getDoctorsByDepartment(departmentId);

        return doctors.stream()
                .map(doctor -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", doctor.getId());
                    map.put("firstName", doctor.getFirstName());
                    map.put("lastName", doctor.getLastName());
                    map.put("position", doctor.getPosition());
                    map.put("email", doctor.getEmail());
                    return map;
                })
                .collect(Collectors.toList());
    }
    @GetMapping("/api/doctors")
    @ResponseBody
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctor();
    }

}