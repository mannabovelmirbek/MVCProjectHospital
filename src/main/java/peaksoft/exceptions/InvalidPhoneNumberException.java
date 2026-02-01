package peaksoft.exceptions;

public class InvalidPhoneNumberException extends HospitalException {
    public InvalidPhoneNumberException(String message) {
        super(message);
    }
}
// Исключение, выбрасываемое при недопустимом формате номера телефона
