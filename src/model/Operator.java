package model;

/**
 * Class Operator merepresentasikan operator yang bekerja dalam sistem manufaktur.
 * Mengimplementasikan konsep Encapsulation.
 */
public class Operator {

    // ===================== ATTRIBUTES =====================
    private String operatorId;
    private String operatorName;

    // ===================== CONSTRUCTOR =====================

    /**
     * Constructor untuk membuat objek Operator baru.
     * @param operatorId   ID unik operator
     * @param operatorName Nama operator
     */
    public Operator(String operatorId, String operatorName) {
        this.operatorId   = operatorId;
        this.operatorName = operatorName;
    }

    // ===================== GETTERS =====================

    public String getOperatorId() {
        return operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    // ===================== SETTERS =====================

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    // ===================== METHODS =====================

    /**
     * Mengembalikan representasi string dari objek Operator.
     */
    @Override
    public String toString() {
        return String.format("| %-12s | %-25s |", operatorId, operatorName);
    }
}
