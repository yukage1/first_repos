package Task1;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static int port = -1;
    private static String server = null;

    public Client(String server, int port) throws IOException {
        Client.port = port;
        Client.server = server;
        Socket socket = null;
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(server, port), 1000);

        } catch (IOException e) {
            System.out.println("Error: " + e);
        }finally {
            assert socket != null;
            socket.close();
        }
    }
    public static void main(String[] args) throws IOException {
        ObjectOutputStream out = null;
        FileOutputStream fos = null;
        FileInputStream fis = null;
        Socket client;
        try {


            Scanner input = new Scanner(System.in);
            System.out.println("Enter server name:");
            server = input.nextLine();

            System.out.println("Enter port:");
            port = Integer.parseInt(input.nextLine());
            client = new Socket(server, port);

            out = new ObjectOutputStream(client.getOutputStream());

            String classFile = "out/production/NetWorkL2/Task1/JobOne.class";
            out.writeObject(classFile);
            fis = new FileInputStream(classFile);
            byte[] b = new byte[fis.available()];
            if(fis.read(b)!=-1){
            out.writeObject(b);}

            System.out.println("Enter number:");
            int num = Integer.parseInt(input.nextLine());
            JobOne aJob = new JobOne(num);

            out.writeObject(aJob);

            ObjectInputStream in = new ObjectInputStream(client.getInputStream());

            classFile = (String) in.readObject();
            b = (byte[]) in.readObject();
            fos = new FileOutputStream(classFile);
            fos.write(b);

            Result r = (Result) in.readObject();

            System.out.println("result = " + r.output() + ", time taken = " + r.scoreTime() / Math.pow(10, 6) + "ms");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        } finally {
            assert fis != null;
            assert fos != null;
            fis.close();
            fos.close();
            out.close();
        }

    }

}