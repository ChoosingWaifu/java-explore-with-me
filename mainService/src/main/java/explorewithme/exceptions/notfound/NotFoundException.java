package explorewithme.exceptions.notfound;

public class NotFoundException extends RuntimeException {
    private static final String ERROR_MESSAGE = "Not found";

    public NotFoundException() {
        super(ERROR_MESSAGE);
    }
}
