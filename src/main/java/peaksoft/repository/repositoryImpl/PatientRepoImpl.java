package peaksoft.repository.repositoryImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import peaksoft.entity.Patient;
import peaksoft.exceptions.InvalidPhoneNumberException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.exceptions.RequiredFieldException;
import peaksoft.repository.PatientRepo;

import java.util.List;
import java.util.regex.Pattern;

@Repository
@Transactional
@RequiredArgsConstructor
public class PatientRepoImpl implements PatientRepo {
    @PersistenceContext
    private final EntityManager entityManager;

    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+996\\d{9}$");

    @Override
    public void savePatient(Patient patient) {
        // Проверка на null полей (VII)
        if (patient.getFirstName() == null || patient.getFirstName().isBlank()) {
            throw new RequiredFieldException("Patient first name cannot be null or empty");
        }
        if (patient.getLastName() == null || patient.getLastName().isBlank()) {
            throw new RequiredFieldException("Patient last name cannot be null or empty");
        }
        if (patient.getPhoneNumber() == null || patient.getPhoneNumber().isBlank()) {
            throw new RequiredFieldException("Patient phone number cannot be null or empty");
        }
        if (patient.getEmail() == null || patient.getEmail().isBlank()) {
            throw new RequiredFieldException("Patient email cannot be null or empty");
        }
        if (patient.getGender() == null) {
            throw new RequiredFieldException("Patient gender cannot be null");
        }

        // Валидация номера телефона (III)
        validatePhoneNumber(patient.getPhoneNumber());

        entityManager.persist(patient);
    }

    @Override
    public List<Patient> getAllPatient() {
        return entityManager.createQuery(
                        "select p from Patient p join fetch p.hospital", Patient.class)
                .getResultList();
    }

    @Override
    public Patient getByIdPatient(Long id) {
        Patient patient = entityManager.find(Patient.class, id);
        if (patient == null) {
            throw new NotFoundException("Patient with id " + id + " not found");
        }
        return patient;
    }

    @Override
    public void updatePatient(Long id, Patient newPatient) {
        Patient patient = entityManager.find(Patient.class, id);
        if (patient == null) {
            throw new NotFoundException("Patient with id " + id + " not found");
        }

        // Проверка на null полей
        if (newPatient.getFirstName() == null || newPatient.getFirstName().isBlank()) {
            throw new RequiredFieldException("Patient first name cannot be null or empty");
        }
        if (newPatient.getLastName() == null || newPatient.getLastName().isBlank()) {
            throw new RequiredFieldException("Patient last name cannot be null or empty");
        }
        if (newPatient.getPhoneNumber() == null || newPatient.getPhoneNumber().isBlank()) {
            throw new RequiredFieldException("Patient phone number cannot be null or empty");
        }
        if (newPatient.getEmail() == null || newPatient.getEmail().isBlank()) {
            throw new RequiredFieldException("Patient email cannot be null or empty");
        }
        if (newPatient.getGender() == null) {
            throw new RequiredFieldException("Patient gender cannot be null");
        }

        // Валидация номера телефона
        validatePhoneNumber(newPatient.getPhoneNumber());

        patient.setFirstName(newPatient.getFirstName());
        patient.setLastName(newPatient.getLastName());
        patient.setPhoneNumber(newPatient.getPhoneNumber());
        patient.setEmail(newPatient.getEmail());
        patient.setGender(newPatient.getGender());
    }

    @Override
    public void deletePatient(Long id) {
        Patient patient = entityManager.find(Patient.class, id);
        if (patient == null) {
            throw new NotFoundException("Patient with id " + id + " not found");
        }
        entityManager.remove(patient);
    }

    @Override
    public long countPatientsByHospitalId(Long hospitalId) {
        return entityManager.createQuery(
                        "select count(p) from Patient p where p.hospital.id = :hospitalId", Long.class)
                .setParameter("hospitalId", hospitalId)
                .getSingleResult();
    }

    private void validatePhoneNumber(String phoneNumber) {
        if (!PHONE_PATTERN.matcher(phoneNumber).matches()) {
            throw new InvalidPhoneNumberException("Phone number must start with +996 and contain 12 digits total (format: +996XXXXXXXXX)");
        }
    }
}