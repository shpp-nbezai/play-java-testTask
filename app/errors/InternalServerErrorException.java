package errors;

public class InternalServerErrorException extends Throwable {
    public InternalServerErrorException() {
    }

    public InternalServerErrorException(String message) {
        super(message);
    }

}
