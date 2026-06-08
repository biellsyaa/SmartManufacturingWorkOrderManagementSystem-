package thread;

import model.Machine;
import model.WorkOrder;

/**
 * ProductionThread menjalankan simulasi produksi untuk satu work order
 * secara asinkron menggunakan Thread.
 *
 * Konsep OOP yang diimplementasikan:
 * - Inheritance: extends Thread
 * - Encapsulation: private workOrder attribute
 * - Polymorphism: override method run()
 */
public class ProductionThread extends Thread {

    // ===================== ATTRIBUTES =====================
    private WorkOrder workOrder;

    // Objek lock (monitor) berdasarkan mesin — digunakan untuk synchronization
    // Key: menggunakan objek Machine sebagai lock agar dua WO dengan mesin
    // yang sama tidak bisa berjalan bersamaan.

    // ===================== CONSTRUCTOR =====================

    /**
     * Constructor ProductionThread.
     * @param workOrder Work order yang akan diproses oleh thread ini
     */
    public ProductionThread(WorkOrder workOrder) {
        this.workOrder = workOrder;
        // Memberi nama thread sesuai Work Order ID untuk kemudahan debug
        this.setName("Thread-" + workOrder.getWorkOrderId());
    }

    // ===================== CORE METHOD =====================

    /**
     * Method run() adalah titik masuk eksekusi thread.
     * Override dari class Thread — implementasi Polymorphism.
     *
     * Skenario synchronization:
     * - Thread mengunci (lock) objek Machine sebelum memulai produksi
     * - Thread lain yang ingin menggunakan mesin yang sama harus menunggu
     * - Setelah produksi selesai, lock dilepas dan mesin tersedia kembali
     */
    @Override
    public void run() {
        Machine machine = workOrder.getMachine();

        // ============================================================
        // SYNCHRONIZATION: Mengunci objek Machine agar mesin tidak
        // digunakan oleh dua work order secara bersamaan.
        // ============================================================
        synchronized (machine) {
            try {

                // --- Ubah status WO menjadi RUNNING ---
                workOrder.setStatus(WorkOrder.Status.RUNNING);

                System.out.println("\n  [" + workOrder.getWorkOrderId() + "] Produksi DIMULAI"
                        + " | Mesin: " + machine.getMachineName()
                        + " | Operator: " + workOrder.getOperator().getOperatorName());

                // --- Simulasi progress produksi (20% - 100%) ---
                for (int progress = 20; progress <= 100; progress += 20) {
                    Thread.sleep(800); // Simulasi waktu proses tiap tahap
                    System.out.println("  " + workOrder.getWorkOrderId()
                            + " : " + progress + "%");
                }

                // --- Ubah status WO menjadi COMPLETED ---
                workOrder.setStatus(WorkOrder.Status.COMPLETED);
                System.out.println("  [" + workOrder.getWorkOrderId() + "] STATUS --> COMPLETED\n");

            } catch (InterruptedException e) {
                System.out.println("  [" + workOrder.getWorkOrderId() + "] Thread diinterupsi: " + e.getMessage());
                Thread.currentThread().interrupt();
            } finally {
                // --- Pastikan mesin selalu dibebaskan meski terjadi error ---
                machine.setAvailable(true);
            }
        }
    }

    // ===================== GETTER =====================

    public WorkOrder getWorkOrder() {
        return workOrder;
    }
}
