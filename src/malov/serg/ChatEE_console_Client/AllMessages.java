package malov.serg.ChatEE_console_Client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class AllMessages extends Thread{
    private int n; // в оригинале int n;
    private Map<String,Integer> rCounter=new HashMap<String, Integer>();
    private String login;
    private String room;

    public AllMessages(String login){
        this.login=login;
        feelMap();
    }

    private void feelMap(){
        rCounter.put("default",0);
        rCounter.put("red",0);
        rCounter.put("blue",0);
        rCounter.put("green",0);
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                this.sleep(100);
                int n=rCounter.get(room);
                URL url = new URL("http://localhost:8080/get?n="+n+"&to="+login+"&room="+room);
                HttpURLConnection http=(HttpURLConnection)url.openConnection();
                InputStream is=http.getInputStream();
                try{
                    int sz=is.available();
                    if(sz>0){
                        byte[] buf=new byte[sz];
                        is.read(buf);
                        Gson gson = new GsonBuilder().create();
                        Message[] messages = gson.fromJson(new String(buf), Message[].class);
                        //--------------- какоето-окошко прикрутить
                        for(int i=0;i<messages.length-1;++i)
                            System.out.println(messages[i].toString());
                        //---------------
                        rCounter.put(room, Integer.parseInt(messages[messages.length - 1].getMessage()));
                    }
                }finally {
                    is.close();
                    http.disconnect();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
