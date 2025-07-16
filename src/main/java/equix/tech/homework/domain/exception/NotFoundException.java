package equix.tech.homework.domain.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Object key) {
        super("Not found with item key: " + key);
    }
}
