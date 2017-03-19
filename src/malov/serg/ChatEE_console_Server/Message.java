package malov.serg.ChatEE_console_Server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Message implements Serializable {
    private static final long serialVersionUID=1L;
    private Date date=new Date();
    private String from;
    private String to;
    private String message;
    private String room;

    public Message(){};
    public Message(String text,String from,String to,String room){
        this.message=text;
        this.from=from;
        this.to=to;
        this.room=room;
    }

    public String toJSON(){
        Gson gson=new GsonBuilder().create();
        return gson.toJson(this);
    }
    public static Message fromJSON(String s){
        Gson gson=new GsonBuilder().create();
        return gson.fromJson(s,Message.class);
    }

    public int send(String url)throws IOException{
        URL u = new URL(url);
        HttpURLConnection conn =(HttpURLConnection)u.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        OutputStream os=conn.getOutputStream();
        try{
            String json=toJSON();
            os.write(json.getBytes());
            return conn.getResponseCode();
        }finally {
            os.close();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append("["+getStringDate()+"]"+" from: ["+from+"]"+" to: ["+Who()+"]");
        sb.append("message: ["+message+"]");
        return sb.toString();
    }

    private String Who(){
        return (to!=null)?to:"all";
    }

    private String getStringDate(){
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
        return sdf.format(date);
    }
    public Date getDate() {
        return date;
    }
    public String getFrom() {
        return from;
    }
    public String getTo() {
        return to;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getRoom() {
        return room;
    }
}