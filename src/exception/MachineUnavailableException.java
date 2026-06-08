package exception;

/**
 * MachineUnavailableException dilempar ketika mesin sedang digunakan
 * oleh work order lain dan tidak dapat dialokasikan.
 */
public class MachineUnavailableException extends Exception {

    private String machineId;

    /**
     * Constructor dengan pesan detail.
     * @param machineId ID mesin yang tidak tersedia
     */
    public MachineUnavailableException(String machineId) {
        super("Mesin [" + machineId + "] sedang digunakan. Silakan pilih mesin lain.");
        this.machineId = machineId;
    }

    public String getMachineId() {
        return machineId;
    }
}
