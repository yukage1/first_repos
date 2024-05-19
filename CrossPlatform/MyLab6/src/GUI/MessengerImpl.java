package GUI;

import javax.swing.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.charset.StandardCharsets;

public class MessengerImpl implements Messenger {
    private UITasks ui;
    private MulticastSocket group;
    private InetAddress addr;
    private int port;
    private String name;
    private boolean canceled;

    public MessengerImpl(UITasks ui, InetAddress addr, int port, String name) {
        this.name = name;
        this.ui = ui;
        this.addr = addr;
        this.port = port;
        this.canceled = false;
        try {
            group = new MulticastSocket(port);
            group.setTimeToLive(2);
            group.joinGroup(addr);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    @Override
    public void start() {
        new Receiver().start();
    }

    @Override
    public void stop() {
        cancel();
        try {
            group.leaveGroup(addr);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Помилка з'єднання");
        } finally {
            group.close();
        }
    }

    @Override
    public void send() {
        new Sender().start();
    }

    private class Receiver extends Thread {

        @Override
        public void run() {
            byte[] in = new byte[512];
            DatagramPacket pkt = new DatagramPacket(in, in.length);
            try {
                while (!isCancelled()) {
                    group.receive(pkt);
                    ui.setText(new String(pkt.getData(), 0, pkt.getLength(), StandardCharsets.UTF_8));
                }
            } catch (IOException e) {
                if (isCancelled()) {
                    JOptionPane.showMessageDialog(null, "З'єднання завершенно");
                } else {
                    JOptionPane.showMessageDialog(null, "Помилка прийому\n" + e.getMessage());
                }
            }
        }

    }

    private class Sender extends Thread {
        @Override
        public void run() {
            String msg = name + ": " + ui.getMessage();
            byte[] out = msg.getBytes(StandardCharsets.UTF_8);
            DatagramPacket pkt = new DatagramPacket(out, out.length, addr, port);
            try {
                group.send(pkt);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Помилка відправлення\n"
                        + e.getMessage());
            }
        }

    }

    private synchronized boolean isCancelled() {
        return canceled;
    }

    private synchronized void cancel() {
        canceled = true;
    }
}
