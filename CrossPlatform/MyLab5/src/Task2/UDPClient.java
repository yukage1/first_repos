package Task2;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;

public class UDPClient {
    private final Scanner scanner = new Scanner(System.in);
    private final ActiveUsers activeUsersList;
    private DatagramSocket datagramSocket;
    private DatagramPacket datagramPacket;
    private InetAddress serverAddress;
    private final int serverPort;
    private static final int answerWaitingTime = 1000;
    private int bufferSize = 256;

    public UDPClient(InetAddress serverAddress, int serverPort){
        activeUsersList = new ActiveUsers();
        this.serverPort = serverPort;
        try{
            this.serverAddress = serverAddress;
            datagramSocket = new DatagramSocket(64344);
            datagramSocket.setSoTimeout(answerWaitingTime);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void checkServerTurningOff(int normalBufferSize){
        System.out.print("Should I stop this server after this data input?\n1.Stop\n2.Continue\n->");
        int choice = scanner.nextInt();

        if (choice == 1)
            bufferSize = 0;
        else
            bufferSize = normalBufferSize;
    }

    public void work(int normalBufferSize){
        byte[] buffer = new byte[normalBufferSize];
        try{
            checkServerTurningOff(normalBufferSize);
            tryingConnectToTheServer(buffer);

            while(true){
                boolean isAccepted = false;
                while (!isAccepted)
                    try {
                        datagramPacket = new DatagramPacket(buffer, buffer.length);
                        datagramSocket.receive(datagramPacket);
                        isAccepted = true;
                    } catch (SocketTimeoutException e){
                        System.out.println("Error.The waiting time for reception has expired.");
                        tryingConnectToTheServer(buffer);
                    }

                if (datagramPacket.getLength() == 0) {
                    System.out.println("User list taken");
                    break;
                }

                ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(datagramPacket.getData(), 0, datagramPacket.getLength()));
                User user = (User) in.readObject();
                activeUsersList.registerUser(user);
                clearBuffer(buffer);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            datagramSocket.close();
        }

        System.out.println("\nOnline users: " + activeUsersList.getUsersCount());
        System.out.println(activeUsersList);
        if (bufferSize == 0){
            System.out.println("Server stopped");
        }

    }

    private void tryingConnectToTheServer(byte[] buffer) throws IOException {
        System.out.print("Sending a request...\n");
        datagramPacket = new DatagramPacket(buffer, bufferSize, serverAddress, serverPort);
        datagramSocket.send(datagramPacket);
        System.out.println("Request sent\n");
    }

    private void clearBuffer(byte[] buffer){
        Arrays.fill(buffer, (byte) 0);
    }

    public static void main(String[] args) throws Exception {
        UDPClient udpClient = new UDPClient(InetAddress.getLocalHost(), 1501);
        udpClient.work(256);
    }
}
