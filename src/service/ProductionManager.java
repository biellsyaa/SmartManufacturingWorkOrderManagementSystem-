package service;

import exception.DuplicateWorkOrderException;
import exception.InvalidQuantityException;
import exception.MachineUnavailableException;
import model.Machine;
import model.Operator;
import model.WorkOrder;
import thread.ProductionThread;

import java.util.ArrayList;
import java.util.List;

/**
 * ProductionManager adalah class utama yang mengelola seluruh entitas dalam sistem.
 * Bertindak sebagai "Service Layer" yang menjembatani UI (Main) dengan data (model).
 *
 * Konsep OOP:
 * - Encapsulation: semua list bersifat private, diakses melalui method
 * - Aggregation: ProductionManager memiliki koleksi Machine, Operator, dan WorkOrder
 */
public class ProductionManager {

    // ===================== ATTRIBUTES =====================

    /** Daftar mesin dalam sistem */
    private ArrayList<Machine> machineList;

    /** Daftar operator dalam sistem */
    private ArrayList<Operator> operatorList;

    /** Daftar work order dalam sistem */
    private ArrayList<WorkOrder> workOrderList;

    // ===================== CONSTRUCTOR =====================

    /**
     * Constructor ProductionManager — menginisialisasi semua ArrayList.
     */
    public ProductionManager() {
        this.machineList   = new ArrayList<>();
        this.operatorList  = new ArrayList<>();
        this.workOrderList = new ArrayList<>();
    }

    // ============================================================
    //  MACHINE MANAGEMENT
    // ============================================================

    /**
     * Menambahkan mesin baru ke dalam sistem.
     * @param machineId   ID unik mesin
     * @param machineName Nama mesin
     */
    public void addMachine(String machineId, String machineName) {
        Machine machine = new Machine(machineId, machineName);
        machineList.add(machine);
        System.out.println("  [OK] Mesin [" + machineName + "] berhasil ditambahkan.");
    }

    /**
     * Menampilkan seluruh daftar mesin yang terdaftar.
     */
    public void viewMachines() {
        if (machineList.isEmpty()) {
            System.out.println("  [!] Belum ada mesin yang terdaftar.");
            return;
        }
        System.out.println("\n  +------------+----------------------+--------------+");
        System.out.println("  | Machine ID | Machine Name         | Status       |");
        System.out.println("  +------------+----------------------+--------------+");
        for (Machine m : machineList) {
            System.out.println("  " + m.toString());
        }
        System.out.println("  +------------+----------------------+--------------+");
    }

    /**
     * Mencari mesin berdasarkan ID.
     * @param machineId ID mesin yang dicari
     * @return Objek Machine jika ditemukan, null jika tidak
     */
    public Machine findMachineById(String machineId) {
        for (Machine m : machineList) {
            if (m.getMachineId().equalsIgnoreCase(machineId)) {
                return m;
            }
        }
        return null;
    }

    /**
     * Getter untuk machineList (digunakan oleh Main untuk validasi input).
     */
    public ArrayList<Machine> getMachineList() {
        return machineList;
    }

    // ============================================================
    //  OPERATOR MANAGEMENT
    // ============================================================

    /**
     * Menambahkan operator baru ke dalam sistem.
     * @param operatorId   ID unik operator
     * @param operatorName Nama operator
     */
    public void addOperator(String operatorId, String operatorName) {
        Operator operator = new Operator(operatorId, operatorName);
        operatorList.add(operator);
        System.out.println("  [OK] Operator [" + operatorName + "] berhasil ditambahkan.");
    }

    /**
     * Menampilkan seluruh daftar operator yang terdaftar.
     */
    public void viewOperators() {
        if (operatorList.isEmpty()) {
            System.out.println("  [!] Belum ada operator yang terdaftar.");
            return;
        }
        System.out.println("\n  +--------------+---------------------------+");
        System.out.println("  | Operator ID  | Operator Name             |");
        System.out.println("  +--------------+---------------------------+");
        for (Operator o : operatorList) {
            System.out.println("  " + o.toString());
        }
        System.out.println("  +--------------+---------------------------+");
    }

    /**
     * Mencari operator berdasarkan ID.
     * @param operatorId ID operator yang dicari
     * @return Objek Operator jika ditemukan, null jika tidak
     */
    public Operator findOperatorById(String operatorId) {
        for (Operator o : operatorList) {
            if (o.getOperatorId().equalsIgnoreCase(operatorId)) {
                return o;
            }
        }
        return null;
    }

    /**
     * Getter untuk operatorList (digunakan oleh Main untuk validasi input).
     */
    public ArrayList<Operator> getOperatorList() {
        return operatorList;
    }

    // ============================================================
    //  WORK ORDER MANAGEMENT
    // ============================================================

    /**
     * Membuat dan menambahkan work order baru ke dalam sistem.
     *
     * Method ini mendemonstrasikan Exception Handling:
     * - throw DuplicateWorkOrderException jika ID sudah ada
     * - throw InvalidQuantityException    jika quantity <= 0
     * - throw MachineUnavailableException jika mesin sedang digunakan
     *
     * @param workOrderId ID work order
     * @param productName Nama produk
     * @param quantity    Jumlah produksi
     * @param machine     Mesin yang digunakan
     * @param operator    Operator yang ditugaskan
     * @throws DuplicateWorkOrderException jika ID work order sudah ada
     * @throws InvalidQuantityException    jika quantity tidak valid
     * @throws MachineUnavailableException jika mesin tidak tersedia
     */
    public void createWorkOrder(String workOrderId, String productName, int quantity,
                                Machine machine, Operator operator)
            throws DuplicateWorkOrderException, InvalidQuantityException, MachineUnavailableException {

        // --- Validasi 1: Cek duplikasi Work Order ID ---
        for (WorkOrder wo : workOrderList) {
            if (wo.getWorkOrderId().equalsIgnoreCase(workOrderId)) {
                throw new DuplicateWorkOrderException(workOrderId); // THROW
            }
        }

        // --- Validasi 2: Cek quantity valid ---
        if (quantity <= 0) {
            throw new InvalidQuantityException(quantity); // THROW
        }

        // --- Validasi 3: Cek ketersediaan mesin ---
        if (!machine.isAvailable()) {
            throw new MachineUnavailableException(machine.getMachineId()); // THROW
        }

        // --- Semua validasi lolos: Simpan work order ---
        WorkOrder workOrder = new WorkOrder(workOrderId, productName, quantity, machine, operator);
        workOrderList.add(workOrder);
        System.out.println("  [OK] Work Order [" + workOrderId + "] berhasil dibuat dengan status PENDING.");
    }

    /**
     * Menampilkan seluruh daftar work order.
     */
    public void viewWorkOrders() {
        if (workOrderList.isEmpty()) {
            System.out.println("  [!] Belum ada work order yang terdaftar.");
            return;
        }
        System.out.println("\n  +----------+--------------------+--------+------------+--------------+-----------------+");
        System.out.println("  | WO ID    | Product Name       | Qty    | Status     | Machine ID   | Operator        |");
        System.out.println("  +----------+--------------------+--------+------------+--------------+-----------------+");
        for (WorkOrder wo : workOrderList) {
            System.out.println("  " + wo.toString());
        }
        System.out.println("  +----------+--------------------+--------+------------+--------------+-----------------+");
    }

    /**
     * Getter untuk workOrderList.
     */
    public ArrayList<WorkOrder> getWorkOrderList() {
        return workOrderList;
    }

    // ============================================================
    //  PRODUCTION MANAGEMENT
    // ============================================================

    /**
     * Menjalankan seluruh work order yang berstatus PENDING secara paralel
     * menggunakan thread terpisah untuk setiap work order.
     *
     * Konsep Multithreading:
     * - Setiap WorkOrder dijalankan dalam ProductionThread yang berbeda
     * - Thread berjalan secara paralel (concurrent)
     * - Synchronization ditangani di dalam ProductionThread (synchronized block pada Machine)
     */
    public void startProduction() {
        // Kumpulkan hanya WO yang statusnya PENDING
        List<WorkOrder> pendingOrders = new ArrayList<>();
        for (WorkOrder wo : workOrderList) {
            if (wo.getStatus() == WorkOrder.Status.PENDING) {
                pendingOrders.add(wo);
            }
        }

        if (pendingOrders.isEmpty()) {
            System.out.println("  [!] Tidak ada work order dengan status PENDING untuk dijalankan.");
            return;
        }

        System.out.println("\n  ============================================================");
        System.out.println("  === MEMULAI PRODUKSI: " + pendingOrders.size() + " Work Order(s) ===");
        System.out.println("  ============================================================");

        // Buat dan simpan semua thread
        List<ProductionThread> threads = new ArrayList<>();
        for (WorkOrder wo : pendingOrders) {
            ProductionThread thread = new ProductionThread(wo);
            threads.add(thread);
        }

        // Start semua thread (paralel)
        for (ProductionThread thread : threads) {
            thread.start();
        }

        // Main thread menunggu semua ProductionThread selesai (join)
        for (ProductionThread thread : threads) {
            try {
                thread.join(); // Tunggu thread ini selesai sebelum lanjut
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("  [!] Main thread diinterupsi: " + e.getMessage());
            }
        }

        System.out.println("\n  ============================================================");
        System.out.println("  === SELURUH PRODUKSI TELAH SELESAI ===");
        System.out.println("  ============================================================\n");
    }
}
