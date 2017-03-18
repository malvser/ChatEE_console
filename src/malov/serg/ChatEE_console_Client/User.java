package malov.serg.ChatEE_console_Client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

public class User implements Serializable,Cloneable {
    private static final long serialVersionUID=1L;
    private String login;
    private String pass;
    private String status="offline";
    private String room;



    public User(){};
    public User(String login,String pass){
        this.login=login;
        this.pass=pass;
        this.room="default";
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

    private String toJSON(){
        Gson gson=new GsonBuilder().create();
        return gson.toJson(this);
    }

    public static User fromJSON(String u){
        Gson gson=new GsonBuilder().create();
        return gson.fromJson(u,User.class);
    }

    public boolean sendLoginLogout(String url)throws IOException{
        HttpURLConnection conn=(HttpURLConnection)new URL(url).openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        OutputStream os=conn.getOutputStream();
        try {
            os.write(toJSON().getBytes());
            InputStream is=conn.getInputStream();
            try {
                int sz=is.available();
                if(sz>0){
                    byte[] buf=new byte[sz];
                    is.read(buf);
                    Gson gson=new GsonBuilder().create();
                    return gson.fromJson(new String(buf),Boolean.class);
                }
            }finally {
                is.close();
            }
        }finally {
            os.close();
            conn.disconnect();
        }
        return false;
    }

    @Override
    public int hashCode() {
        byte[] bytes=(login+status+room+(this.getClass().getSimpleName())).getBytes();
        int rez=0;
        for(Byte b:bytes)
            rez+=b.intValue();
        return rez;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "login: "+login+", status: "+status+", room: "+room;
    }
}
