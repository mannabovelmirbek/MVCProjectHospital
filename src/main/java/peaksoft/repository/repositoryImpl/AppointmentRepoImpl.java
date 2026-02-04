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
        System.out.println("✅ Appointment saved successfully");
    }

    @Override
    public List<Appointment> getAllAppointment() {
        // ✅ ДОБАВЛЕН DISTINCT
        List<Appointment> appointments = entityManager.createQuery(
                        "select distinct a from Appointment a " +
                                "left join fetch a.hospital " +
                                "left join fetch a.department " +
                                "left join fetch a.doctor " +
                                "left join fetch a.patient",
                        Appointment.class)
                .getResultList();

        System.out.println("✅ Retrieved " + appointments.size() + " appointments");
        return appointments;
    }

    @Override
    public Appointment getByIdAppointment(Long id) {
        try {
            Appointment appointment = entityManager.createQuery(
                            "select a from Appointment a " +
                                    "left join fetch a.hospital " +
                                    "left join fetch a.department " +
                                    "left join fetch a.doctor " +
                                    "left join fetch a.patient " +
                                    "where a.id = :id",
                            Appointment.class)
                    .setParameter("id", id)
                    .getSingleResult();

            return appointment;
        } catch (Exception e) {
            throw new NotFoundException("Appointment with id " + id + " not found");
        }
    }

    @Override
    public void updateAppointment(Long id, Appointment newAppointment) {
        System.out.println("=== REPOSITORY UPDATE ===");
        System.out.println("ID: " + id);

        Appointment appointment = entityManager.find(Appointment.class, id);
        if (appointment == null) {
            throw new NotFoundException("Appointment with id " + id + " not found");
        }

        if (newAppointment.getDate() == null) {
            throw new RequiredFieldException("Appointment date cannot be null");
        }
        if (newAppointment.getHospital() == null) {
            throw new RequiredFieldException("Appointment hospital cannot be null");
        }
        if (newAppointment.getDepartment() == null) {
            throw new RequiredFieldException("Appointment department cannot be null");
        }
        if (newAppointment.getDoctor() == null) {
            throw new RequiredFieldException("Appointment doctor cannot be null");
        }
        if (newAppointment.getPatient() == null) {
            throw new RequiredFieldException("Appointment patient cannot be null");
        }

        appointment.setDate(newAppointment.getDate());
        appointment.setHospital(newAppointment.getHospital());
        appointment.setDepartment(newAppointment.getDepartment());
        appointment.setDoctor(newAppointment.getDoctor());
        appointment.setPatient(newAppointment.getPatient());

        System.out.println("✅ Appointment updated successfully");
    }

    @Override
    public void deleteAppointment(Long id) {
        Appointment appointment = entityManager.find(Appointment.class, id);
        if (appointment == null) {
            throw new NotFoundException("Appointment with id " + id + " not found");
        }
        entityManager.remove(appointment);
        System.out.println("✅ Appointment deleted successfully");
    }

    @Override
    public List<Appointment> findAllOrderByDateDesc() {
        // ✅ ДОБАВЛЕН DISTINCT
        List<Appointment> appointments = entityManager.createQuery(
                        "select distinct a from Appointment a " +
                                "left join fetch a.hospital " +
                                "left join fetch a.department " +
                                "left join fetch a.doctor " +
                                "left join fetch a.patient " +
                                "order by a.date desc",
                        Appointment.class)
                .getResultList();

        System.out.println("✅ Found " + appointments.size() + " appointments in database");
        return appointments;
    }
}