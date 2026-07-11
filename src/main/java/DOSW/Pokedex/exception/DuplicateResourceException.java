package DOSW.Pokedex.exception;

public class DuplicateResourceException extends BusinessException {

    public DuplicateResourceException(String resource, String field, Object value) {
        super(resource + " con " + field + "=" + value + " ya existe", "DUPLICATE");
    }
}
