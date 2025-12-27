package socket;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class RealtimeSocket extends WebSocketClient {

    // Callback untuk trigger refresh UI
    private Runnable onProdukUpdate;

    /**
     * Constructor UTAMA (dipakai oleh PanelProduk)
     */
    public RealtimeSocket(URI serverUri, Runnable onProdukUpdate) {
        super(serverUri);
        this.onProdukUpdate = onProdukUpdate;
    }

    /**
     * Constructor TAMBAHAN (opsional, supaya TIDAK ERROR
     * kalau ada kode lama yang masih pakai new RealtimeSocket(URI))
     */
    public RealtimeSocket(URI serverUri) {
        super(serverUri);
        this.onProdukUpdate = null;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("WebSocket CONNECTED");
    }

    @Override
    public void onMessage(String message) {
        System.out.println("Realtime message: " + message);

        // EVENT PRODUK UPDATE
        if (message.contains("produk_updated")) {
            if (onProdukUpdate != null) {
                onProdukUpdate.run(); // trigger refresh di UI
            }
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("WebSocket DISCONNECTED");
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }
}
