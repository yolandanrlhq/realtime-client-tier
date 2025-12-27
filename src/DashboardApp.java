import view.FrameUtama; // WAJIB
import javax.swing.SwingUtilities;
import socket.RealtimeSocket;

import java.net.URI;

public class DashboardApp {

    // simpan socket sebagai global (opsional, tapi rapi)
    public static RealtimeSocket socket;

    public static void main(String[] args) {

        // 0️⃣ CONNECT WEBSOCKET (AMAN, BUKAN DI EDT)
        try {
            socket = new RealtimeSocket(
                new URI("ws://localhost:3000")
            );
            socket.connect(); // async, tidak blocking
        } catch (Exception e) {
            System.err.println("Gagal koneksi WebSocket");
            e.printStackTrace();
        }

        // 1️⃣ Setup LookAndFeel (TETAP)
        try {
            com.formdev.flatlaf.FlatLightLaf.setup();
        } catch (Exception e) {
            System.err.println("Gagal memuat tema modern, menggunakan tema standar.");
        }

        // 2️⃣ Jalankan UI di EDT (TETAP, TIDAK DIUBAH)
        SwingUtilities.invokeLater(() -> {
            try {
                FrameUtama frame = new FrameUtama();
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                System.out.println("Aplikasi Client-Tier Berhasil Dijalankan!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
