package Task2;


import java.io.Serializable;
import java.util.ArrayList;

public class ActiveUsers implements Serializable {
    private ArrayList<User> users = null;
    private final long serialVersionUID = 1;

    public ActiveUsers(){
        users = new ArrayList<>();
    }

    public void registerUser(User user){
        users.add(user);
    }

    public boolean isEmpty(){
        return users.isEmpty();
    }

    public int getUsersCount(){
        return users.size();
    }

    public boolean isUserRegistered(User u){
        boolean isRegistered = false;

        for (User userFromList: users)
            if (userFromList.equals(u)){
                isRegistered = true;
                break;
            }

        return isRegistered;
    }

    public User getUser(int pos){
        return users.get(pos);
    }


    public String toString() {
        StringBuilder buf = new StringBuilder();
        for (User u : users)
            buf.append("\t").append(u.toString()).append("\n");
        return buf.toString();
    }
}