package explorewithme.errors;

import explorewithme.exceptions.DbConflictException;
import explorewithme.exceptions.InsufficientRightsException;
import explorewithme.exceptions.notfound.NotFoundException;
import explorewithme.utility.DateTimeMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.xml.bind.ValidationException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(InsufficientRightsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError handleException(InsufficientRightsException e) {
        return ApiError.builder()
                .errors(List.of(Arrays.toString(e.getStackTrace())))
                .message(e.getMessage())
                .reason("Not enough rights")
                .status(HttpStatus.FORBIDDEN.toString())
                .timestamp(LocalDateTime.now().format(DateTimeMapper.format()))
                .build();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleException(NotFoundException e) {
        return ApiError.builder()
                .errors(List.of(Arrays.toString(e.getStackTrace())))
                .message(e.getMessage())
                .reason("Not found")
                .status(HttpStatus.NOT_FOUND.toString())
                .timestamp(LocalDateTime.now().format(DateTimeMapper.format()))
                .build();
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleException(ValidationException e) {
        return ApiError.builder()
                .errors(List.of(Arrays.toString(e.getStackTrace())))
                .message(e.getMessage())
                .reason("Validate exception, wrong fields")
                .status(HttpStatus.BAD_REQUEST.toString())
                .timestamp(LocalDateTime.now().format(DateTimeMapper.format()))
                .build();
    }

    @ExceptionHandler(DbConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleException(DbConflictException e) {
        return ApiError.builder()
                .errors(List.of(Arrays.toString(e.getStackTrace())))
                .message(e.getMessage())
                .reason("Db conflict, constraints")
                .status(HttpStatus.CONFLICT.toString())
                .timestamp(LocalDateTime.now().format(DateTimeMapper.format()))
                .build();
    }
}
