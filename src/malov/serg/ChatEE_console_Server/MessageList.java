package malov.serg.ChatEE_console_Server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;


public class MessageList { //singleton обьект создается при инициализации класса
    private static final MessageList msgList=new MessageList();

    private final ArrayList<Message> list=new ArrayList<Message>();
    private final ArrayList<Message> listRed=new ArrayList<Message>();
    private final ArrayList<Message> listBlue=new ArrayList<Message>();
    private final ArrayList<Message> listGreen=new ArrayList<Message>();

    private MessageList(){};
    public static MessageList getInstance(){
        return msgList;
    }

    public synchronized void add(Message m){
        ArrayList<Message> tList=getTargetList(m.getRoom());
        tList.add(m);
    }

    public synchronized String toJSON(int i,String to,String room){
        ArrayList<Message> tList=getTargetList(room);
        ArrayList<Message> resList=new ArrayList<Message>();
        for(int n=i;n<tList.size();++n){
            if(equal(tList.get(n), to))
            resList.add(tList.get(n));
        }
        Message wm=new Message();
        wm.setMessage(Integer.toString(tList.size()));
        resList.add(wm);
        // to JSON
        if(resList.size()>1){
            Gson gson=new GsonBuilder().create();
            return gson.toJson(resList.toArray());
        }else{
            return null;
        }
    }

    private ArrayList<Message> getTargetList(String room){
        if(room.equalsIgnoreCase("red"))
            return listRed;
        else if(room.equalsIgnoreCase("blue"))
            return listBlue;
        else if(room.equalsIgnoreCase("green"))
            return listGreen;
        else
            return list;
    }

    private boolean equal(Message m, String to){
        boolean all=m.getTo().equalsIgnoreCase("all");
        boolean me=m.getTo().equalsIgnoreCase(to);
        return all||me;
    }
}