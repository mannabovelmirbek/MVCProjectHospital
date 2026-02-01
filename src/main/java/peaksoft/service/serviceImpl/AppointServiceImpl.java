package peaksoft.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.entity.Appointment;
import peaksoft.service.AppointmentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointServiceImpl implements AppointmentService {
    @Override
    public void saveAppointment(Appointment appointment) {

    }

    @Override
    public List<Appointment> getAllAppointment() {
        return List.of();
    }

    @Override
    public Appointment getByIdAppointment(Long id) {
        return null;
    }

    @Override
    public void updateAppointment(Long id, Appointment newAppointment) {

    }

    @Override
    public void deleteAppointment(Long id) {

    }

    @Override
    public List<Appointment> findAllOrderByDateDesc() {
        return List.of();
    }
}
