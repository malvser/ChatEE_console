package malov.serg.ChatEE_console_Server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class UserList { //singleton
    private static UserList userList = new UserList();
    private ArrayList<User> list = new ArrayList<User>();

    public static UserList getInstance(){return userList;}
    private UserList(){
        list.add(new User("Sergey","ser"));
        list.add(new User("Tanya","tan"));
        list.add(new User("Artem","art"));
    }
    public String toJson(){
        Gson gson = new GsonBuilder().create();
        return gson.toJson(list.toArray());
    }
    public String toJsonNoPass(){
        User[] listRez = new User[list.size()];
        for(int n = 0; n < list.size(); ++n) {
            User user = cloneUser(list.get(n));
            user.setPass(null);
            listRez[n] = user;
        }
        Gson gson = new GsonBuilder().create();
        return gson.toJson(listRez);
    }

    private User cloneUser(User user){
        try {
            return (User)user.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean findUserSetStatus(User u, String type){
        for(User user : userList.list){
            if(user.getLogin().equalsIgnoreCase(u.getLogin()) && user.getPass().equalsIgnoreCase(u.getPass())) {
                if (type.equalsIgnoreCase("login")) {
                    user.setStatus("online");
                    user.setRoom("default");
                } else if (type.equalsIgnoreCase("logout")||type.equalsIgnoreCase("exit")) {
                    user.setStatus("offline");
                    user.setRoom(null);
                } else {
                    user.setRoom(type);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
      byte[] bytes = this.getClass().getSimpleName().getBytes();
      int rez = 0;
        for(Byte b:bytes){
            rez += b.intValue();
        }
        for(User user:list){
            rez += user.hashCode();
        }
        return rez;
    }
}
