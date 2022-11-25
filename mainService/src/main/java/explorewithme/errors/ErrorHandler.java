package explorewithme.errors;

import explorewithme.exceptions.InsufficientRightsException;
import explorewithme.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.xml.bind.ValidationException;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(InsufficientRightsException.class)
    public ApiError handleException(InsufficientRightsException e) {
        return new ApiError(e.getMessage(), "Not enough rights",
                HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NotFoundException.class)
    public ApiError handleException(NotFoundException e) {
        return new ApiError(e.getMessage(), "Not found",
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ApiError handleException(ValidationException e) {
        return new ApiError(e.getMessage(), "Validate exception, wrong fields",
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ApiError handleException(RuntimeException e) {
        return new ApiError(e.getMessage(), "Unknown Error",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
