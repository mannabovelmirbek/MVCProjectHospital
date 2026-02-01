package peaksoft.service;

import peaksoft.entity.Hospital;

import java.util.List;

public interface HospitalService {
    void saveHospital(Hospital hospital);
    List<Hospital> getAllHospital();
    Hospital getByIdHospital(Long id);
    void updateHospital(Long id,Hospital newHospital);
    void deleteHospital(Long id);
}
