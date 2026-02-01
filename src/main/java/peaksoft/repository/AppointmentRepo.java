package peaksoft.repository;

import org.springframework.stereotype.Repository;
import peaksoft.entity.Appointment;

import java.util.List;

@Repository
public interface AppointmentRepo {
    void saveAppointment(Appointment appointment);
    List<Appointment> getAllAppointment();
    Appointment getByIdAppointment(Long id);
    void updateAppointment(Long id,Appointment newAppointment);
    void deleteAppointment(Long id);

    List<Appointment> findAllOrderByDateDesc();

}
