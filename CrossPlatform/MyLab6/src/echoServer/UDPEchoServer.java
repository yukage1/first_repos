package echoServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPEchoServer extends UDPServer {
    public final static int DEFAULT_PORT = 7;

    public UDPEchoServer() {
        super(DEFAULT_PORT);
    }

    @Override
    public void respond(DatagramSocket socket, DatagramPacket request) {
        DatagramPacket reply = new DatagramPacket(request.getData(), request.getLength(), request.getAddress(), request.getPort());
        try {
            socket.send(reply);
        } catch (IOException e) {
            System.err.println(e.getMessage() + "\n" + e);
        }
    }

    public static void main(String[] args) {
        UDPServer udpServer = new UDPEchoServer();
        Thread thread = new Thread(udpServer);
        thread.start();
        System.out.println("Start echo-server...");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage() + "\n" + e);
        }
        udpServer.shutDown();
        System.out.println("Finish echo-server...");
    }
}
