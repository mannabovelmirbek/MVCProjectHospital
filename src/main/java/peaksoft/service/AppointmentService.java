package peaksoft.service;

import peaksoft.entity.Appointment;

import java.util.List;

public interface AppointmentService {
    void saveAppointment(Appointment appointment);
    List<Appointment> getAllAppointment();
    Appointment getByIdAppointment(Long id);
    void updateAppointment(Long id,Appointment newAppointment);
    void deleteAppointment(Long id);

    List<Appointment> findAllOrderByDateDesc();

}
