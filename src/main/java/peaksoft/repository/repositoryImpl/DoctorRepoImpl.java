package peaksoft.repository.repositoryImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import peaksoft.entity.Department;
import peaksoft.entity.Doctor;
import peaksoft.entity.Hospital;
import peaksoft.exceptions.DoctorEmailAlreadyExistsException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.exceptions.RequiredFieldException;
import peaksoft.repository.DoctorRepo;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class DoctorRepoImpl implements DoctorRepo {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public void saveDoctor(Doctor doctor) {
        // Проверка на null полей (VII)
        if (doctor.getFirstName() == null || doctor.getFirstName().isBlank()) {
            throw new RequiredFieldException("Doctor first name cannot be null or empty");
        }
        if (doctor.getLastName() == null || doctor.getLastName().isBlank()) {
            throw new RequiredFieldException("Doctor last name cannot be null or empty");
        }
        if (doctor.getPosition() == null || doctor.getPosition().isBlank()) {
            throw new RequiredFieldException("Doctor position cannot be null or empty");
        }
        if (doctor.getEmail() == null || doctor.getEmail().isBlank()) {
            throw new RequiredFieldException("Doctor email cannot be null or empty");
        }

        // Проверка на уникальность email (V)
        if (existsByEmail(doctor.getEmail())) {
            throw new DoctorEmailAlreadyExistsException("Doctor with email '" + doctor.getEmail() + "' already exists");
        }

        entityManager.persist(doctor);
    }

    @Override
    public void assignDoctorToDepartments(Long doctorId, List<Long> departmentIds) {
        Doctor doctor = entityManager.find(Doctor.class, doctorId);
        if (doctor == null) {
            throw new NotFoundException("Doctor with id " + doctorId + " not found");
        }

        if (departmentIds != null && !departmentIds.isEmpty()) {
            for (Long deptId : departmentIds) {
                Department department = entityManager.find(Department.class, deptId);
                if (department != null) {
                    // Добавляем связь с обеих сторон
                    if (!doctor.getDepartmentsList().contains(department)) {
                        doctor.getDepartmentsList().add(department);
                    }
                    if (!department.getDoctorsList().contains(doctor)) {
                        department.getDoctorsList().add(doctor);
                    }
                }
            }
        }
    }

    @Override
    public List<Doctor> getAllDoctor() {
        return entityManager.createQuery(
                        "select d from Doctor d join fetch d.hospital", Doctor.class)
                .getResultList();
    }

    @Override
    public Doctor getByIdDoctor(Long id) {
        Doctor doctor = entityManager.find(Doctor.class, id);
        if (doctor == null) {
            throw new NotFoundException("Doctor with id " + id + " not found");
        }
        return doctor;
    }

    @Override
    public void updateDoctor(Long id, Doctor newDoctor) {
        Doctor doctor = entityManager.find(Doctor.class, id);
        if (doctor == null) {
            throw new NotFoundException("Doctor with id " + id + " not found");
        }

        // Проверка на null полей
        if (newDoctor.getFirstName() == null || newDoctor.getFirstName().isBlank()) {
            throw new RequiredFieldException("Doctor first name cannot be null or empty");
        }
        if (newDoctor.getLastName() == null || newDoctor.getLastName().isBlank()) {
            throw new RequiredFieldException("Doctor last name cannot be null or empty");
        }
        if (newDoctor.getPosition() == null || newDoctor.getPosition().isBlank()) {
            throw new RequiredFieldException("Doctor position cannot be null or empty");
        }
        if (newDoctor.getEmail() == null || newDoctor.getEmail().isBlank()) {
            throw new RequiredFieldException("Doctor email cannot be null or empty");
        }

        // Проверка на уникальность email при обновлении
        if (!doctor.getEmail().equals(newDoctor.getEmail()) && existsByEmail(newDoctor.getEmail())) {
            throw new DoctorEmailAlreadyExistsException("Doctor with email '" + newDoctor.getEmail() + "' already exists");
        }

        doctor.setFirstName(newDoctor.getFirstName());
        doctor.setLastName(newDoctor.getLastName());
        doctor.setPosition(newDoctor.getPosition());
        doctor.setEmail(newDoctor.getEmail());
    }

    @Override
    public void deleteDoctor(Long id) {
        Doctor doctor = entityManager.find(Doctor.class, id);
        if (doctor == null) {
            throw new NotFoundException("Doctor with id " + id + " not found");
        }

        // Удаляем связи с департаментами перед удалением доктора
        for (Department dept : doctor.getDepartmentsList()) {
            dept.getDoctorsList().remove(doctor);
        }

        entityManager.remove(doctor);
    }

    @Override
    public long countDoctorsByHospitalId(Long hospitalId) {
        return entityManager.createQuery(
                        "select count(d) from Doctor d where d.hospital.id = :hospitalId", Long.class)
                .setParameter("hospitalId", hospitalId)
                .getSingleResult();
    }

    @Override
    public boolean existsByEmail(String email) {
        try {
            Long count = entityManager.createQuery(
                            "select count(d) from Doctor d where d.email = :email", Long.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return count > 0;
        } catch (NoResultException e) {
            return false;
        }
    }
}