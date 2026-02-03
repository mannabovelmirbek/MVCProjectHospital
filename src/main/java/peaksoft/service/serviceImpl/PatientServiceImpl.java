package peaksoft.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.entity.Patient;
import peaksoft.repository.PatientRepo;
import peaksoft.service.PatientService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientRepo patientRepo;

    @Override
    public void savePatient(Patient patient) {
        patientRepo.savePatient(patient);
    }

    @Override
    public List<Patient> getAllPatient() {
        return patientRepo.getAllPatient();
    }

    @Override
    public Patient getByIdPatient(Long id) {
        return patientRepo.getByIdPatient(id);
    }

    @Override
    public void updatePatient(Long id, Patient newPatient) {
        patientRepo.updatePatient(id, newPatient);
    }

    @Override
    public void deletePatient(Long id) {
        patientRepo.deletePatient(id);
    }

    @Override
    public long countPatientsByHospitalId(Long hospitalId) {
        return patientRepo.countPatientsByHospitalId(hospitalId);
    }
}