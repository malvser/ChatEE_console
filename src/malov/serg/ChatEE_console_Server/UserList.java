package malov.serg.ChatEE_console_Server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class UserList { //singleton
    private static UserList ul=new UserList();
    private ArrayList<User> list=new ArrayList<User>();

    public static UserList getInstance(){return ul;}
    private UserList(){
        list.add(new User("Sergey","ser"));
        list.add(new User("Tanya","tan "));
        list.add(new User("Artem","art"));
    }
    public String toJson(){
        Gson gson=new GsonBuilder().create();
        return gson.toJson(list.toArray());
    }
    public String toJsonNoPass(){
        User[] listRez=new User[list.size()];
        for(int n=0;n<list.size();++n) {
            User u=cloneUser(list.get(n));
            u.setPass(null);
            listRez[n] =u;
        }
        Gson gson=new GsonBuilder().create();
        return gson.toJson(listRez);
    }

    private User cloneUser(User u){
        try {
            return (User)u.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean findUserSetStatus(User u,String type){
        for(User cU:ul.list){
            if(cU.getLogin().equalsIgnoreCase(u.getLogin())&&cU.getPass().equalsIgnoreCase(u.getPass())) {
                if (type.equalsIgnoreCase("login")) {
                    cU.setStatus("online");
                    cU.setRoom("default");
                } else if (type.equalsIgnoreCase("logout")||type.equalsIgnoreCase("exit")) {
                    cU.setStatus("offline");
                    cU.setRoom(null);
                } else {
                    cU.setRoom(type);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
      byte[] bytes=this.getClass().getSimpleName().getBytes();
      int rez=0;
        for(Byte b:bytes){
            rez+=b.intValue();
        }
        for(User u:list){
            rez+=u.hashCode();
        }
        return rez;
    }
}
