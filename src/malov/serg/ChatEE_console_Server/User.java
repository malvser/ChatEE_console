package malov.serg.ChatEE_console_Server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;

public class User implements Serializable,Cloneable {
    private static final long serialVersionUID = 1L;
    private String login;
    private String pass;
    private String status = "offline";
    private String room;

    public User(){}
    public User(String login,String pass){
        this.login=login;
        this.pass=pass;
    }

    public String getRoom() {
        return room;
    }
    public void setRoom(String room) {
        this.room = room;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLogin() {
        return login;
    }
    public String getPass() {
        return pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }

    private String toJSON(){
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }

    public static User fromJSON(String u){
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(u,User.class);
    }


    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public int hashCode() {
        byte[] bytes=(login + status + room + (this.getClass().getSimpleName())).getBytes();
        int rez = 0;
        for(Byte b : bytes)
            rez += b.intValue();
        return rez;
    }
}

