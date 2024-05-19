package Task1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


import java.util.Scanner;

public class Server {

    static ServerSocket servSock = null;

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter server port:");
        int serverPort = input.nextInt();
        FileInputStream fis;
        FileOutputStream fos = null;
        try {


        servSock = new ServerSocket(serverPort);
        Socket clientSocket = servSock.accept();

        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

        String classFile = (String) in.readObject();
        classFile = classFile.replaceFirst("client", "server");
        byte[] b = (byte[]) in.readObject();
        fos = new FileOutputStream(classFile);
        fos.write(b);

        Executable ex = (Executable) in.readObject();

        double startTime = System.nanoTime();
        Object output = ex.execute();
        double endTime = System.nanoTime();
        double completionTime = endTime - startTime;

        ResultImpl r = new ResultImpl(output, completionTime);
        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
        classFile = "out/production/NetWorkL2/Task1/ResultImpl.class";
        out.writeObject(classFile);
         fis = new FileInputStream(classFile);
        byte[] bo = new byte[fis.available()];
        if(fis.read(bo)!=-1) {
            out.writeObject(bo);
            out.writeObject(r);
        }
        }catch (IOException | ClassNotFoundException e){
            System.err.println(e.getMessage());

        } finally {
            assert fos != null;
            fos.close();
        }
    }
}