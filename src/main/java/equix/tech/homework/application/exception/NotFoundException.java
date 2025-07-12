package equix.tech.homework.application.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Object key) {
        super("Not found with item key: " + key);
    }
}
