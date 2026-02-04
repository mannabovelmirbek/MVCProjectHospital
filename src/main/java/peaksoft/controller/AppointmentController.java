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

        System.out.println("Hospital ID: " + hospitalId);
        System.out.println("Department ID: " + departmentId);
        System.out.println("Doctor ID: " + doctorId);
        System.out.println("Patient ID: " + patientId);
        System.out.println("Date: " + dateStr);

        try {
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
        } catch (Exception e) {
            System.err.println("ERROR SAVING APPOINTMENT: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }

        return "redirect:/appointments";
    }

    @GetMapping("/{id}")
    public String getAppointmentById(@PathVariable("id") Long id, Model model) {
        try {
            Appointment appointment = appointmentService.getByIdAppointment(id);
            model.addAttribute("appointment", appointment);
            return "appointmentById";
        } catch (Exception e) {
            System.err.println("ERROR GETTING APPOINTMENT: " + e.getMessage());
            e.printStackTrace();
            return "redirect:/appointments";
        }
    }

    @GetMapping("/edit/{id}")
    public String editAppointmentForm(@PathVariable("id") Long id, Model model) {
        try {
            Appointment appointment = appointmentService.getByIdAppointment(id);
            List<Hospital> hospitals = hospitalService.getAllHospital();
            List<Patient> patients = patientService.getAllPatient();
            List<Department> departments = departmentService.getAllDepartmentByHospital(appointment.getHospital().getId());
            List<Doctor> doctors = doctorService.getDoctorsByDepartment(appointment.getDepartment().getId());

            model.addAttribute("editAppointment", appointment);
            model.addAttribute("hospitals", hospitals);
            model.addAttribute("patients", patients);
            model.addAttribute("departments", departments);
            model.addAttribute("doctors", doctors);
            model.addAttribute("currentHospitalId", appointment.getHospital().getId());
            model.addAttribute("currentDepartmentId", appointment.getDepartment().getId());

            return "editAppointment";
        } catch (Exception e) {
            System.err.println("ERROR LOADING EDIT FORM: " + e.getMessage());
            e.printStackTrace();
            return "redirect:/appointments";
        }
    }

    @PostMapping("/update/{id}")
    public String updateAppointment(@PathVariable("id") Long id,
                                    @RequestParam("hospitalId") Long hospitalId,
                                    @RequestParam("departmentId") Long departmentId,
                                    @RequestParam("doctorId") Long doctorId,
                                    @RequestParam("patientId") Long patientId,
                                    @RequestParam("date") String dateStr) {
        System.out.println("ID: " + id);
        System.out.println("Hospital ID: " + hospitalId);
        System.out.println("Department ID: " + departmentId);
        System.out.println("Doctor ID: " + doctorId);
        System.out.println("Patient ID: " + patientId);
        System.out.println("Date: " + dateStr);

        try {
            Hospital hospital = hospitalService.getByIdHospital(hospitalId);
            Department department = departmentService.getByIdDepartment(departmentId);
            Doctor doctor = doctorService.getByIdDoctor(doctorId);
            Patient patient = patientService.getByIdPatient(patientId);
            LocalDate date = LocalDate.parse(dateStr);

            System.out.println("Hospital: " + hospital.getName());
            System.out.println("Department: " + department.getName());
            System.out.println("Doctor: " + doctor.getFirstName() + " " + doctor.getLastName());
            System.out.println("Patient: " + patient.getFirstName() + " " + patient.getLastName());
            System.out.println("Date: " + date);

            Appointment updatedAppointment = new Appointment();
            updatedAppointment.setId(id);
            updatedAppointment.setDate(date);
            updatedAppointment.setHospital(hospital);
            updatedAppointment.setDepartment(department);
            updatedAppointment.setDoctor(doctor);
            updatedAppointment.setPatient(patient);

            appointmentService.updateAppointment(id, updatedAppointment);


        } catch (Exception e) {
            System.err.println("ERROR UPDATING APPOINTMENT: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }

        return "redirect:/appointments";
    }

    @PostMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable("id") Long id) {
        System.out.println("=== DELETING APPOINTMENT ID: " + id + " ===");
        try {
            appointmentService.deleteAppointment(id);
            System.out.println("=== APPOINTMENT DELETED SUCCESSFULLY ===");
        } catch (Exception e) {
            System.err.println("ERROR DELETING APPOINTMENT: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return "redirect:/appointments";
    }

    @GetMapping("/api/departments/{hospitalId}")
    @ResponseBody
    public List<Map<String, Object>> getDepartmentsByHospital(@PathVariable("hospitalId") Long hospitalId) {
        try {
            List<Department> departments = departmentService.getAllDepartmentByHospital(hospitalId);
            return departments.stream()
                    .map(dept -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("id", dept.getId());
                        map.put("name", dept.getName());
                        return map;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("ERROR GETTING DEPARTMENTS: " + e.getMessage());
            return List.of();
        }
    }

    @GetMapping("/api/doctors/{departmentId}")
    @ResponseBody
    public List<Map<String, Object>> getDoctorsByDepartment(@PathVariable("departmentId") Long departmentId) {
        try {
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
        } catch (Exception e) {
            System.err.println("ERROR GETTING DOCTORS: " + e.getMessage());
            return List.of();
        }
    }

    @GetMapping("/api/doctors")
    @ResponseBody
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctor();
    }
}