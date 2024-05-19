package Task2;


import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class User implements Serializable {
    private final long serialVersionUID = 1;
    private InetAddress address ;
    private int port;

    public User(InetAddress address, int port)  {
        this.address = address;
        this.port = port;
    }

    public void setAddress(String address) throws UnknownHostException {
        this.address = InetAddress.getByName(address);
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "User{" +
                "serialVersionUID=" + serialVersionUID +
                ", address=" + address +
                ", port=" + port +
                '}';
    }
}