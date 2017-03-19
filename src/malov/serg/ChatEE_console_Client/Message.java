package malov.serg.ChatEE_console_Client;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message implements Serializable{
    private static final long serialVersionUID=1L;
    private Date date=new Date();
    private String from;
    private String to;
    private String message;
    private String room;

    public Message(){};
    public Message(String text,String from,String to,String room){
        this.message = text;
        this.from = from;
        this.to = to;
        this.room = room;
    }

    private String toJSON(){
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }

    public int send(String url)throws IOException{
        URL u = new URL(url);
        HttpURLConnection conn = (HttpURLConnection)u.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        OutputStream os=conn.getOutputStream();
        try{
                String json = toJSON();
                os.write(json.getBytes());
            return conn.getResponseCode();
        }finally {
            os.close();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append("| "+ message + " | room:["+room+"] from:[" + from + "]" + " to:[" + Who() + "] " + getStringDate());
        return sb.toString();
    }

    private String Who(){
        return (to!=null)? to : "all";
    }

    private String getStringDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(date);
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public String getFrom() {
        return from;
    }
    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }
    public void setTo(String to) {
        this.to = to;
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
    public void setRoom(String room) {
        this.room = room;
    }
}
