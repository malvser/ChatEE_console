package malov.serg.ChatEE_console_Client;

import java.io.IOException;
import java.util.ArrayList;


public class Logik {
    private static Reader r=new Reader();
    private static User cUser;
    private static int chatCounter=0;
    private static AllMessages am;
    private static AllUsers au;

    private static boolean autentify(String type) {
        try {
            if (type.equalsIgnoreCase("login")) {
                cUser = new User(r.readLogin(), r.readPass());
                boolean login = cUser.sendLoginLogout("http://localhost:8080/user?param="+type);
                System.out.println((login) ? "Welcome to chat " + r.getLogin()+", you are in a: "+cUser.getRoom()+" room": "wrong user repeat enter");
                return login;
            } else if (type.equalsIgnoreCase("logout")||type.equalsIgnoreCase("exit")) {
                boolean logout = cUser.sendLoginLogout("http://localhost:8080/user?param="+type);
                System.out.println("good buy " + cUser.getLogin());
                System.exit(1);
            } else if (type.equalsIgnoreCase("users")) {
                au.printUsers();
                ++chatCounter;
                startChat();
            } else{
                cUser.setRoom(type);
                boolean room = cUser.sendLoginLogout("http://localhost:8080/user?param="+type);
                if(room){
                    System.out.println(cUser.getLogin()+" you are in a: "+cUser.getRoom()+" room");
                    ++chatCounter;
                    startChat();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void startChat()throws IOException{
        if(chatCounter==0){
            firstTime();
        }else{
            secondTime();
        }
        while (true){
            System.out.print("<"+cUser.getRoom()+" room> ");
            String text=r.text();
            boolean flag=textAnalyzer(text);
            messageAnalyzer(text,flag);
        }
    }
    private static void firstTime(){
        boolean status=false;
        //-------------------------
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //-------------------------
        while (!status){
            status=autentify("login");
        }
        au=new AllUsers();
        au.setDaemon(true);
        au.start();
        //-------------------------
        allMessagesGoDeamonThread();
    }
    private static void secondTime(){
        am.setRoom(cUser.getRoom());
    }

    private static void allMessagesGoDeamonThread(){
        am=new AllMessages(cUser.getLogin());
        am.setRoom(cUser.getRoom());
        am.setDaemon(true);
        am.start();
    }

    private static boolean textAnalyzer(String text){
        String[] keyWords=new String[]{"exit","logout","red","blue","green","default","users"};
        for(String s:keyWords){
            if(s.equalsIgnoreCase(text))
                if(!cUser.getRoom().equalsIgnoreCase(text))
                autentify(text);
                else
                return false;
        }
        return true;
    }
    private static void messageAnalyzer(String text,boolean flag)throws IOException{
        if(flag) {
            Message m;
            String[] texts = text.split(":");
            if (au.userExist(texts[0])) {
                m = new Message(texts[1], cUser.getLogin(), texts[0], cUser.getRoom());
                m.send("http://localhost:8080/add");
            }else if(texts[0].equalsIgnoreCase("")) {
                m = new Message(texts[1], cUser.getLogin(), "all", cUser.getRoom());
                m.send("http://localhost:8080/add");
            }else
                System.out.println("user: " + texts[0] + " doesn't exist");
        }
    }
}
