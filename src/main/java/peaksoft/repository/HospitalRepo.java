package peaksoft.repository;

import org.springframework.stereotype.Repository;
import peaksoft.entity.Hospital;

import java.util.List;

@Repository
public interface HospitalRepo {
    void saveHospital(Hospital hospital);
    List<Hospital> getAllHospital();
    Hospital getByIdHospital(Long id);
    void updateHospital(Long id,Hospital newHospital);
    void deleteHospital(Long id);
}
