package course.java.sdm.engine.exceptions;

public class DuplicateIdsException extends RuntimeException {
    public DuplicateIdsException (String duplicatedEntity, String entitysCollection, Throwable cause) {
        super(String.format("There are 2 %ss  with the same id in %s collection", duplicatedEntity, entitysCollection), cause);
    }
}
