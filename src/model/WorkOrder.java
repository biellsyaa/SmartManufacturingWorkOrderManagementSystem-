package model;

/**
 * Class WorkOrder merepresentasikan perintah kerja (work order) dalam sistem manufaktur.
 * Mengimplementasikan konsep Encapsulation dan menggunakan enum Status untuk Abstraction.
 */
public class WorkOrder {

    // ===================== ENUM STATUS =====================

    /**
     * Enum untuk status work order.
     * Mengimplementasikan konsep Abstraction dengan mendefinisikan state yang valid.
     */
    public enum Status {
        PENDING, RUNNING, COMPLETED
    }

    // ===================== ATTRIBUTES =====================
    private String   workOrderId;
    private String   productName;
    private int      quantity;
    private Status   status;
    private Machine  machine;
    private Operator operator;

    // ===================== CONSTRUCTOR =====================

    /**
     * Constructor untuk membuat objek WorkOrder baru.
     * @param workOrderId ID unik work order
     * @param productName Nama produk yang akan diproduksi
     * @param quantity    Jumlah unit yang akan diproduksi
     * @param machine     Mesin yang digunakan
     * @param operator    Operator yang ditugaskan
     */
    public WorkOrder(String workOrderId, String productName, int quantity,
                     Machine machine, Operator operator) {
        this.workOrderId = workOrderId;
        this.productName = productName;
        this.quantity    = quantity;
        this.status      = Status.PENDING; // Default status: PENDING
        this.machine     = machine;
        this.operator    = operator;
    }

    // ===================== GETTERS =====================

    public String getWorkOrderId() {
        return workOrderId;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public Status getStatus() {
        return status;
    }

    public Machine getMachine() {
        return machine;
    }

    public Operator getOperator() {
        return operator;
    }

    // ===================== SETTERS =====================

    public void setWorkOrderId(String workOrderId) {
        this.workOrderId = workOrderId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    // ===================== METHODS =====================

    /**
     * Mengembalikan representasi string dari objek WorkOrder.
     */
    @Override
    public String toString() {
        return String.format("| %-8s | %-18s | %-6d | %-10s | %-12s | %-15s |",
                workOrderId,
                productName,
                quantity,
                status.name(),
                machine.getMachineId(),
                operator.getOperatorName());
    }
}
