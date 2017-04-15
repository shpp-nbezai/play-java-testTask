package errors;
import javax.inject.*;

@Singleton
public class InternalServerErrorException extends Throwable {
    public InternalServerErrorException() {
    }

    public InternalServerErrorException(String message) {
        super(message);
    }
}
