package errors;
import javax.inject.*;

@Singleton
public class BadRequestException extends Throwable{

    public BadRequestException() {
    }

    public BadRequestException(String message) {
        super(message);
    }

}
