package peaksoft.exceptions;

public class DoctorEmailAlreadyExistsException extends HospitalException {
    public DoctorEmailAlreadyExistsException(String message) {
        super(message);
    }
}
//Exception для дубликата Email