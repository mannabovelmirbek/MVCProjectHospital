package peaksoft.exceptions;

public class HospitalException extends RuntimeException{
    public HospitalException(String message){
        super(message);
    }
    public HospitalException(String message,Throwable cause){
        super(message,cause);
    }
}
//Базовый Exception класс