package peaksoft.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.entity.Appointment;
import peaksoft.repository.AppointmentRepo;
import peaksoft.service.AppointmentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointServiceImpl implements AppointmentService {
    private final AppointmentRepo appointmentRepo;

    @Override
    public void saveAppointment(Appointment appointment) {
        appointmentRepo.saveAppointment(appointment);
    }

    @Override
    public List<Appointment> getAllAppointment() {
        return appointmentRepo.getAllAppointment();
    }

    @Override
    public Appointment getByIdAppointment(Long id) {
        return appointmentRepo.getByIdAppointment(id);
    }

    @Override
    public void updateAppointment(Long id, Appointment newAppointment) {
        appointmentRepo.updateAppointment(id, newAppointment);
    }

    @Override
    public void deleteAppointment(Long id) {
        appointmentRepo.deleteAppointment(id);
    }

    @Override
    public List<Appointment> findAllOrderByDateDesc() {
        return appointmentRepo.findAllOrderByDateDesc();
    }
}