package Uv.DeliMgmt.backend.Exception;

public class ProductCannotBeDeletedException extends RuntimeException {
    public ProductCannotBeDeletedException(String message) {
        super(message);
    }
}

