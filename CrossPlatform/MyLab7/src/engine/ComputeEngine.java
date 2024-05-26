package engine;

import compute.Compute;
import compute.Task;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ComputeEngine implements Compute {
    private boolean running;

    public ComputeEngine() {
        super();
        this.running = true;
    }

    public <T> T executeTask(Task<T> t) throws RemoteException {
        return t.execute();
    }

    public static void main(String[] args) {
        ComputeEngine engine = new ComputeEngine();
        try {
            Compute stub = (Compute) UnicastRemoteObject.exportObject(engine, 0);
            Registry registry = LocateRegistry.createRegistry(8080);
            String name = "Compute";
            registry.rebind(name, stub);
            System.out.println("ComputeEngine is ready to work");
            Scanner scanner = new Scanner(System.in);
            System.out.println("Type 'stop' to shut down the server.");
            while (engine.isRunning()) {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("stop")) {
                    engine.stop();
                    break;
                }
            }
            scanner.close();
            UnicastRemoteObject.unexportObject(engine, true);
            System.out.println("ComputeEngine has been shut down.");
        } catch (RemoteException e) {
            System.err.println("ComputeEngine exception:");
            e.printStackTrace();
        }
    }

    public synchronized boolean isRunning() {
        return running;
    }

    public synchronized void stop() {
        running = false;
    }
}