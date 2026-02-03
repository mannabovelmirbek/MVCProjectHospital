package peaksoft.repository.repositoryImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import peaksoft.entity.Appointment;
import peaksoft.exceptions.NotFoundException;
import peaksoft.exceptions.RequiredFieldException;
import peaksoft.repository.AppointmentRepo;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class AppointmentRepoImpl implements AppointmentRepo {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public void saveAppointment(Appointment appointment) {
        // Проверка на null полей (VII)
        if (appointment.getDate() == null) {
            throw new RequiredFieldException("Appointment date cannot be null");
        }
        if (appointment.getHospital() == null) {
            throw new RequiredFieldException("Appointment hospital cannot be null");
        }
        if (appointment.getDepartment() == null) {
            throw new RequiredFieldException("Appointment department cannot be null");
        }
        if (appointment.getDoctor() == null) {
            throw new RequiredFieldException("Appointment doctor cannot be null");
        }
        if (appointment.getPatient() == null) {
            throw new RequiredFieldException("Appointment patient cannot be null");
        }

        entityManager.persist(appointment);
    }

    @Override
    public List<Appointment> getAllAppointment() {
        return entityManager.createQuery(
                        "select a from Appointment a " +
                                "join fetch a.hospital " +
                                "join fetch a.department " +
                                "join fetch a.doctor " +
                                "join fetch a.patient",
                        Appointment.class)
                .getResultList();
    }

    @Override
    public Appointment getByIdAppointment(Long id) {
        Appointment appointment = entityManager.find(Appointment.class, id);
        if (appointment == null) {
            throw new NotFoundException("Appointment with id " + id + " not found");
        }
        return appointment;
    }

    @Override
    public void updateAppointment(Long id, Appointment newAppointment) {
        Appointment appointment = entityManager.find(Appointment.class, id);
        if (appointment == null) {
            throw new NotFoundException("Appointment with id " + id + " not found");
        }

        // Проверка на null полей
        if (newAppointment.getDate() == null) {
            throw new RequiredFieldException("Appointment date cannot be null");
        }

        appointment.setDate(newAppointment.getDate());
        if (newAppointment.getHospital() != null) {
            appointment.setHospital(newAppointment.getHospital());
        }
        if (newAppointment.getDepartment() != null) {
            appointment.setDepartment(newAppointment.getDepartment());
        }
        if (newAppointment.getDoctor() != null) {
            appointment.setDoctor(newAppointment.getDoctor());
        }
        if (newAppointment.getPatient() != null) {
            appointment.setPatient(newAppointment.getPatient());
        }
    }

    @Override
    public void deleteAppointment(Long id) {
        Appointment appointment = entityManager.find(Appointment.class, id);
        if (appointment == null) {
            throw new NotFoundException("Appointment with id " + id + " not found");
        }
        entityManager.remove(appointment);
    }

    @Override
    public List<Appointment> findAllOrderByDateDesc() {
        // Последние записи первыми (II)
        return entityManager.createQuery(
                        "select a from Appointment a " +
                                "join fetch a.hospital " +
                                "join fetch a.department " +
                                "join fetch a.doctor " +
                                "join fetch a.patient " +
                                "order by a.date desc",
                        Appointment.class)
                .getResultList();
    }
}