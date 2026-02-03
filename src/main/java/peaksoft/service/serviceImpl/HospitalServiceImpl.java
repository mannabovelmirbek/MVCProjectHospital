package peaksoft.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.entity.Hospital;
import peaksoft.repository.HospitalRepo;
import peaksoft.service.HospitalService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {
    private final HospitalRepo hospitalRepo;

    @Override
    public void saveHospital(Hospital hospital) {
        hospitalRepo.saveHospital(hospital);
    }

    @Override
    public List<Hospital> getAllHospital() {
        return hospitalRepo.getAllHospital();
    }

    @Override
    public Hospital getByIdHospital(Long id) {
        return hospitalRepo.getByIdHospital(id);
    }

    @Override
    public void updateHospital(Long id, Hospital newHospital) {
        hospitalRepo.updateHospital(id,newHospital);
    }

    @Override
    public void deleteHospital(Long id) {
        hospitalRepo.deleteHospital(id);
    }
}