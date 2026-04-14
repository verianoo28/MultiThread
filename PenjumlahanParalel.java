import java.util.Scanner;

class PenjumlahanThread extends Thread {
    private int start, end;
    private long PenjumlahanParsial = 0;

    public PenjumlahanThread(int start, int end, String nama) {
        super(nama); // Set nama thread
        this.start = start;
        this.end = end;
    }

    public void run() {
        // Output: Tugas yang dikerjakan
        System.out.println(getName() + " menghitung rentang: " + start + " - " + end);
        for (int i = start; i <= end; i++) {
            PenjumlahanParsial += i;
        }
        // Output: Hasil parsial
        System.out.println("Hasil " + getName() + ": " + PenjumlahanParsial);
    }

    public long getPenjumlahanParsial() {
        return PenjumlahanParsial;
    }
}

public class PenjumlahanParalel {
    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Jumlah Thread: ");
        int nThread = sc.nextInt();
        System.out.print("Angka Akhir: ");
        int angkaAkhir = sc.nextInt();

        PenjumlahanThread[] threads = new PenjumlahanThread[nThread];
        int range = angkaAkhir / nThread;
        int start = 1;

        // 1. Divide: Membagi tugas ke masing-masing thread
        for (int i = 0; i < nThread; i++) {
            int end = (i == nThread - 1) ? angkaAkhir : (start + range - 1);
            threads[i] = new PenjumlahanThread(start, end, "Thread-" + (i + 1));
            threads[i].start();
            start = end + 1;
        }

        long totalAkhir = 0;
        // 2. Synchronization & Conquer: Menunggu dan menjumlahkan hasil
        for (PenjumlahanThread t : threads) {
            t.join(); 
            totalAkhir += t.getPenjumlahanParsial();
        }

        // Output: Hasil akhir
        System.out.println("\n--- TOTAL AKHIR: " + totalAkhir + " ---");
    }
}