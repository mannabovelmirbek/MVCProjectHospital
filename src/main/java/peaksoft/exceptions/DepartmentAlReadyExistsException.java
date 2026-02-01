package peaksoft.exceptions;

public class DepartmentAlReadyExistsException extends HospitalException {
    public DepartmentAlReadyExistsException(String message) {
        super(message);
    }
}
//Exception для дубликата Department