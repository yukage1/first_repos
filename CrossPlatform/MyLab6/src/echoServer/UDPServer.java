package echoServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public abstract class UDPServer implements Runnable {
    private final int bufferSize;
    private final int port;
    private volatile boolean isShutDown = false;

    public UDPServer(int bufferSize, int port) {
        this.bufferSize = bufferSize;
        this.port = port;
    }

    public UDPServer(int port) {
        this(8192, port);
    }

    public UDPServer() {
        this(12345, 8192);
    }

    @Override
    public void run() {
        byte[] buffer = new byte[bufferSize];
        try (DatagramSocket socket = new DatagramSocket(port)) {
            socket.setSoTimeout(10000);
            while (true) {
                if (isShutDown)
                    return;
                DatagramPacket incoming = new DatagramPacket(buffer,
                        buffer.length);
                try {
                    socket.receive(incoming);
                    this.respond(socket, incoming);
                } catch (SocketTimeoutException ex) {
                    if (isShutDown)
                        return;
                } catch (IOException ex) {
                    System.err.println(ex.getMessage() + "\n" + ex);
                }
            } // end while
        } catch (SocketException ex) {
            System.err.println("Could not bind to port: " + port + "\n" + ex);
        }
    }

    public void shutDown() {
        this.isShutDown = true;
    }

    public abstract void respond(DatagramSocket socket, DatagramPacket request) throws IOException;

}
