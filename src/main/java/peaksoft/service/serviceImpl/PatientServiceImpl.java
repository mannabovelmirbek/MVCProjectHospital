package peaksoft.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.entity.Patient;
import peaksoft.service.PatientService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {


    @Override
    public void savePatient(Patient patient) {

    }

    @Override
    public List<Patient> getAllPatient() {
        return List.of();
    }

    @Override
    public Patient getByIdPatient(Long id) {
        return null;
    }

    @Override
    public void updatePatient(Long id, Patient newPatient) {

    }

    @Override
    public void deletePatient(Long id) {

    }

    @Override
    public long countPatientsByHospitalId(Long hospitalId) {
        return 0;
    }
}
