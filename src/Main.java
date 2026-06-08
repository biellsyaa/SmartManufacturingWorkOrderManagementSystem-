import exception.DuplicateWorkOrderException;
import exception.InvalidQuantityException;
import exception.MachineUnavailableException;
import model.Machine;
import model.Operator;
import service.ProductionManager;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * =================================================================
 *   SMART MANUFACTURING WORK ORDER MANAGEMENT SYSTEM
 *   Proyek Akhir - Pemrograman Berorientasi Objek Lanjut (Java)
 * =================================================================
 *
 * Main.java adalah entry point aplikasi.
 * Mengelola interaksi pengguna melalui menu berbasis console.
 *
 * Konsep OOP yang diimplementasikan:
 * - Encapsulation      : semua atribut class bersifat private
 * - Abstraction        : enum Status, interface melalui method publik
 * - Inheritance        : ProductionThread extends Thread
 * - Polymorphism       : override method run() pada ProductionThread
 * - Exception Handling : 3 custom exception + try-catch-finally
 * - Multithreading     : setiap work order berjalan di thread terpisah
 * - Synchronization    : synchronized(machine) mencegah konflik mesin
 * - Modular            : kode dibagi ke package model, exception, service, thread
 */
public class Main {

    // ===================== SHARED OBJECTS =====================
    private static ProductionManager manager = new ProductionManager();
    private static Scanner scanner = new Scanner(System.in);

    // ===================== ENTRY POINT =====================

    public static void main(String[] args) {
        printBanner();

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readIntSafe("  Pilihan Anda: ");

            switch (choice) {
                case 1:  menuMachine();     break;
                case 2:  menuOperator();    break;
                case 3:  menuWorkOrder();   break;
                case 4:  menuProduction();  break;
                case 0:
                    System.out.println("\n  Terima kasih. Sistem ditutup.\n");
                    running = false;
                    break;
                default:
                    System.out.println("  [!] Pilihan tidak valid. Coba lagi.\n");
            }
        }
        scanner.close();
    }

    // ============================================================
    //  SUB-MENU: MACHINE
    // ============================================================

    private static void menuMachine() {
        boolean back = false;
        while (!back) {
            System.out.println("\n  ===== MANAJEMEN MESIN =====");
            System.out.println("  1. Tambah Mesin");
            System.out.println("  2. Lihat Daftar Mesin");
            System.out.println("  0. Kembali");
            int choice = readIntSafe("  Pilihan: ");

            switch (choice) {
                case 1:
                    addMachine();
                    break;
                case 2:
                    manager.viewMachines();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("  [!] Pilihan tidak valid.");
            }
        }
    }

    /**
     * Proses input dan penambahan mesin baru.
     */
    private static void addMachine() {
        System.out.println("\n  --- Tambah Mesin Baru ---");
        System.out.print("  Machine ID   : ");
        String id = scanner.nextLine().trim();
        System.out.print("  Machine Name : ");
        String name = scanner.nextLine().trim();

        if (id.isEmpty() || name.isEmpty()) {
            System.out.println("  [!] ID dan Nama mesin tidak boleh kosong.");
            return;
        }

        manager.addMachine(id, name);
    }

    // ============================================================
    //  SUB-MENU: OPERATOR
    // ============================================================

    private static void menuOperator() {
        boolean back = false;
        while (!back) {
            System.out.println("\n  ===== MANAJEMEN OPERATOR =====");
            System.out.println("  1. Tambah Operator");
            System.out.println("  2. Lihat Daftar Operator");
            System.out.println("  0. Kembali");
            int choice = readIntSafe("  Pilihan: ");

            switch (choice) {
                case 1:
                    addOperator();
                    break;
                case 2:
                    manager.viewOperators();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("  [!] Pilihan tidak valid.");
            }
        }
    }

    /**
     * Proses input dan penambahan operator baru.
     */
    private static void addOperator() {
        System.out.println("\n  --- Tambah Operator Baru ---");
        System.out.print("  Operator ID   : ");
        String id = scanner.nextLine().trim();
        System.out.print("  Operator Name : ");
        String name = scanner.nextLine().trim();

        if (id.isEmpty() || name.isEmpty()) {
            System.out.println("  [!] ID dan Nama operator tidak boleh kosong.");
            return;
        }

        manager.addOperator(id, name);
    }

    // ============================================================
    //  SUB-MENU: WORK ORDER
    // ============================================================

    private static void menuWorkOrder() {
        boolean back = false;
        while (!back) {
            System.out.println("\n  ===== MANAJEMEN WORK ORDER =====");
            System.out.println("  1. Buat Work Order Baru");
            System.out.println("  2. Lihat Seluruh Work Order");
            System.out.println("  0. Kembali");
            int choice = readIntSafe("  Pilihan: ");

            switch (choice) {
                case 1:
                    createWorkOrder();
                    break;
                case 2:
                    manager.viewWorkOrders();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("  [!] Pilihan tidak valid.");
            }
        }
    }

    /**
     * Proses input dan pembuatan work order baru.
     * Mendemonstrasikan catch untuk tiga custom exception.
     */
    private static void createWorkOrder() {
        System.out.println("\n  --- Buat Work Order Baru ---");

        // Cek apakah mesin dan operator tersedia
        if (manager.getMachineList().isEmpty()) {
            System.out.println("  [!] Belum ada mesin. Silakan tambah mesin terlebih dahulu.");
            return;
        }
        if (manager.getOperatorList().isEmpty()) {
            System.out.println("  [!] Belum ada operator. Silakan tambah operator terlebih dahulu.");
            return;
        }

        // --- Input Work Order ID ---
        System.out.print("  Work Order ID : ");
        String woId = scanner.nextLine().trim();

        // --- Input Product Name ---
        System.out.print("  Product Name  : ");
        String productName = scanner.nextLine().trim();

        // --- Input Quantity ---
        int quantity = readIntSafe("  Quantity      : ");

        // --- Tampilkan daftar mesin & pilih ---
        System.out.println("\n  Daftar Mesin:");
        manager.viewMachines();
        System.out.print("  Masukkan Machine ID : ");
        String machineId = scanner.nextLine().trim();
        Machine selectedMachine = manager.findMachineById(machineId);
        if (selectedMachine == null) {
            System.out.println("  [!] Machine ID tidak ditemukan.");
            return;
        }

        // --- Tampilkan daftar operator & pilih ---
        System.out.println("\n  Daftar Operator:");
        manager.viewOperators();
        System.out.print("  Masukkan Operator ID : ");
        String operatorId = scanner.nextLine().trim();
        Operator selectedOperator = manager.findOperatorById(operatorId);
        if (selectedOperator == null) {
            System.out.println("  [!] Operator ID tidak ditemukan.");
            return;
        }

        // ============================================================
        // EXCEPTION HANDLING: try-catch untuk 3 custom exception
        // ============================================================
        try {
            manager.createWorkOrder(woId, productName, quantity, selectedMachine, selectedOperator);

        } catch (DuplicateWorkOrderException e) {
            // CATCH: Work Order ID sudah ada
            System.out.println("\n  [ERROR] DUPLIKASI: " + e.getMessage());

        } catch (InvalidQuantityException e) {
            // CATCH: Quantity tidak valid (<= 0)
            System.out.println("\n  [ERROR] QUANTITY TIDAK VALID: " + e.getMessage());

        } catch (MachineUnavailableException e) {
            // CATCH: Mesin sedang digunakan
            System.out.println("\n  [ERROR] MESIN TIDAK TERSEDIA: " + e.getMessage());
        }
    }

    // ============================================================
    //  SUB-MENU: PRODUCTION
    // ============================================================

    private static void menuProduction() {
        boolean back = false;
        while (!back) {
            System.out.println("\n  ===== MANAJEMEN PRODUKSI =====");
            System.out.println("  1. Jalankan Semua Work Order (Paralel)");
            System.out.println("  2. Lihat Status Work Order");
            System.out.println("  0. Kembali");
            int choice = readIntSafe("  Pilihan: ");

            switch (choice) {
                case 1:
                    manager.startProduction();
                    break;
                case 2:
                    manager.viewWorkOrders();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("  [!] Pilihan tidak valid.");
            }
        }
    }

    // ============================================================
    //  UTILITY METHODS
    // ============================================================

    /**
     * Membaca input integer dengan aman (menangani InputMismatchException).
     * @param prompt Pesan yang ditampilkan kepada pengguna
     * @return Integer yang dimasukkan pengguna, atau -1 jika input tidak valid
     */
    private static int readIntSafe(String prompt) {
        System.out.print(prompt);
        try {
            int value = scanner.nextInt();
            scanner.nextLine(); // Konsumsi newline
            return value;
        } catch (InputMismatchException e) {
            scanner.nextLine(); // Bersihkan buffer
            return -1;
        }
    }

    /**
     * Menampilkan banner aplikasi saat pertama kali dijalankan.
     */
    private static void printBanner() {
        System.out.println("\n  ╔══════════════════════════════════════════════════════╗");
        System.out.println("  ║    SMART MANUFACTURING WORK ORDER MANAGEMENT SYSTEM  ║");
        System.out.println("  ║           Proyek Akhir - OOP Lanjut (Java)           ║");
        System.out.println("  ╚══════════════════════════════════════════════════════╝\n");
    }

    /**
     * Menampilkan menu utama aplikasi.
     */
    private static void printMainMenu() {
        System.out.println("  ┌─────────────────────────────────┐");
        System.out.println("  │          MENU UTAMA             │");
        System.out.println("  ├─────────────────────────────────┤");
        System.out.println("  │  1. Manajemen Mesin             │");
        System.out.println("  │  2. Manajemen Operator          │");
        System.out.println("  │  3. Manajemen Work Order        │");
        System.out.println("  │  4. Produksi                    │");
        System.out.println("  │  0. Keluar                      │");
        System.out.println("  └─────────────────────────────────┘");
    }
}
