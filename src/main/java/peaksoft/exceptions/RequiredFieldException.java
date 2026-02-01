package peaksoft.exceptions;

public class RequiredFieldException extends HospitalException {
    public RequiredFieldException(String message) {
        super(message);
    }
}
//Exception для null полей
