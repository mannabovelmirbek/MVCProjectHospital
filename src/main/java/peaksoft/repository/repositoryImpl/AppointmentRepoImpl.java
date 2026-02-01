package peaksoft.repository.repositoryImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import peaksoft.entity.Appointment;
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
