package malov.serg.ChatEE_console_Client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AllUsers extends Thread {
    private User[] users1;
    private User[] users2;

    public AllUsers(){
        this.users1 = getUsersArray();
    }



    private int hashUsers(User[] users){
        int rez=0;
        for(User u:users)
            rez+=u.hashCode();
        return rez;
    }

    public static User[] getUsersArray(){
        try {
            URL url=new URL("http://localhost:8080/user?userList=all");
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            InputStream is=conn.getInputStream();
            int sz=is.available();
            if(sz>0){
                byte[] buf=new byte[sz];
                is.read(buf);
                Gson gson=new GsonBuilder().create();
                User[] users=gson.fromJson(new String(buf),User[].class);
                return users;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean userExist(String to){
        for(User user:users1){
            if(user.getLogin().equalsIgnoreCase(to)||"all".equalsIgnoreCase(to))
                return true;
        }
        return false;
    }

    private User findUser(){
        for(int n=0;n<users1.length;++n){
            if(users1[n].hashCode()!=users2[n].hashCode())
                return users2[n];
        }
        return null;
    }

    private void cloneArray(){
        users1=new User[users2.length];
        for(int n=0;n<users1.length;++n){
            try {
                users1[n]= (User) users2[n].clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
    }

     void printUsers(){
        for(User u:users1){
            System.out.println(u.toString());
        }
    }

    @Override
    public void run() {
        printUsers();
        try{
            while (!isInterrupted()){
                this.sleep(100);
                do {
                    users2=getUsersArray();
                }while (hashUsers(users1)==hashUsers(users2));
                System.out.println(findUser().toString());
                cloneArray();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
