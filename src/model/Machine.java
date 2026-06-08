package model;

/**
 * Class Machine merepresentasikan mesin produksi dalam sistem manufaktur.
 * Mengimplementasikan konsep Encapsulation dengan private attributes dan public getters/setters.
 */
public class Machine {

    // ===================== ATTRIBUTES =====================
    private String machineId;
    private String machineName;
    private boolean available;

    // ===================== CONSTRUCTOR =====================

    /**
     * Constructor untuk membuat objek Machine baru.
     * @param machineId   ID unik mesin
     * @param machineName Nama mesin
     */
    public Machine(String machineId, String machineName) {
        this.machineId   = machineId;
        this.machineName = machineName;
        this.available   = true; // Default: mesin tersedia
    }

    // ===================== GETTERS =====================

    public String getMachineId() {
        return machineId;
    }

    public String getMachineName() {
        return machineName;
    }

    public boolean isAvailable() {
        return available;
    }

    // ===================== SETTERS =====================

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    // ===================== METHODS =====================

    /**
     * Mengembalikan representasi string dari objek Machine.
     */
    @Override
    public String toString() {
        return String.format("| %-10s | %-20s | %-12s |",
                machineId,
                machineName,
                available ? "AVAILABLE" : "IN USE");
    }
}
