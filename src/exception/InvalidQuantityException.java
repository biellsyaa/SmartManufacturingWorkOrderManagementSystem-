package exception;

/**
 * InvalidQuantityException dilempar ketika quantity yang dimasukkan
 * adalah 0 atau negatif (tidak valid).
 */
public class InvalidQuantityException extends Exception {

    private int invalidValue;

    /**
     * Constructor dengan nilai quantity yang tidak valid.
     * @param invalidValue Nilai quantity yang menyebabkan exception
     */
    public InvalidQuantityException(int invalidValue) {
        super("Quantity tidak valid: " + invalidValue + ". Quantity harus lebih dari 0.");
        this.invalidValue = invalidValue;
    }

    public int getInvalidValue() {
        return invalidValue;
    }
}
