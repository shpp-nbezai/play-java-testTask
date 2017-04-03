package errors;

public class InputDataException extends Throwable{

    public InputDataException() {
    }

    public InputDataException(String message) {
        super("Incorrect input data Exception: " + message);
    }



}
