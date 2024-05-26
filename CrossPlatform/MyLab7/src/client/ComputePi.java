package client;

import compute.Compute;
import compute.Task;

import java.math.BigDecimal;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ComputePi {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the number of digits for e: ");
            int eDigits = validateInput(scanner);
            Task<BigDecimal> eTask = new E(eDigits);
            System.out.print("Enter the number of digits for pi: ");
            int piDigits = validateInput(scanner);
            Task<BigDecimal> piTask = new Pi(piDigits);
            Registry registry = LocateRegistry.getRegistry("localhost", 8080);
            String name = "Compute";
            Compute comp = (Compute) registry.lookup(name);
            BigDecimal pi = comp.executeTask(piTask);
            BigDecimal e = comp.executeTask(eTask);
            System.out.println("Pi: " + pi);
            System.out.println("E: " + e);
            scanner.close();
        } catch (Exception e) {
            System.err.println("ComputePi exception:");
            e.printStackTrace();
        }
    }

    private static int validateInput(Scanner scanner) {
        int digits = 0;
        boolean validInput = false;
        while (!validInput) {
            try {
                digits = scanner.nextInt();
                if (digits <= 0) {
                    System.out.print("Invalid input. Please enter a positive integer: ");
                } else {
                    validInput = true;
                }
            } catch (Exception e) {
                System.out.print("Invalid input. Please enter a positive integer: ");
                scanner.next();
            }
        }
        return digits;
    }
}