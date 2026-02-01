package peaksoft.exceptions;

public class NotFoundException extends HospitalException {
    public NotFoundException(String message) {
        super(message);
    }
}
//Исключение, которое выбрасывается, когда сущность не найдена