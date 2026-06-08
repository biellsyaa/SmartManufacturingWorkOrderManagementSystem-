package exception;

/**
 * DuplicateWorkOrderException dilempar ketika Work Order ID yang
 * dimasukkan sudah ada dalam sistem.
 */
public class DuplicateWorkOrderException extends Exception {

    private String duplicateId;

    /**
     * Constructor dengan ID yang duplikat.
     * @param duplicateId Work Order ID yang sudah ada
     */
    public DuplicateWorkOrderException(String duplicateId) {
        super("Work Order ID [" + duplicateId + "] sudah ada dalam sistem. Gunakan ID yang berbeda.");
        this.duplicateId = duplicateId;
    }

    public String getDuplicateId() {
        return duplicateId;
    }
}
