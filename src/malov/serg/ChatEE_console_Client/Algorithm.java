package malov.serg.ChatEE_console_Client;

import java.io.IOException;


public class Algorithm {
    private static ReaderInfo reader = new ReaderInfo();
    private static User user;
    private static int chatCounter = 0;
    private static AllMessages allMessages;
    private static AllUsers allUsers;

    private static boolean autentify(String type) {
        try {
            if (type.equalsIgnoreCase("login")) {
                user = new User(reader.readLogin(), reader.readPass());
                boolean login = user.sendLoginLogout("http://localhost:8080/user?param="+type);
                System.out.println((login) ? "Welcome to chat " + reader.getLogin()+", you are in a: "+ user.getRoom()+" room": "wrong user repeat enter");
                return login;
            } else if (type.equalsIgnoreCase("logout")||type.equalsIgnoreCase("exit")) {
                boolean logout = user.sendLoginLogout("http://localhost:8080/user?param="+type);
                System.out.println("good buy " + user.getLogin());
                System.exit(1);
            } else if (type.equalsIgnoreCase("users")) {
                allUsers.printUsers();
                ++chatCounter;
                startChat();
            } else{
                user.setRoom(type);
                boolean room = user.sendLoginLogout("http://localhost:8080/user?param="+type);
                if(room){
                    System.out.println(user.getLogin()+" you are in a: "+ user.getRoom()+" room");
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
        if(chatCounter == 0){
            firstTime();
        }else{
            secondTime();
        }
        while (true){
            System.out.print("<"+ user.getRoom()+" room> ");
            String text = reader.text();
            boolean flag = textAnalyzer(text);
            messageAnalyzer(text,flag);
        }
    }
    private static void firstTime(){
        boolean status = false;
        //-------------------------
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //-------------------------
        while (!status){
            status = autentify("login");
        }
        allUsers = new AllUsers();
        allUsers.setDaemon(true);
        allUsers.start();
        //-------------------------
        allMessagesGoDeamonThread();
    }
    private static void secondTime(){
        allMessages.setRoom(user.getRoom());
    }

    private static void allMessagesGoDeamonThread(){
        allMessages = new AllMessages(user.getLogin());
        allMessages.setRoom(user.getRoom());
        allMessages.setDaemon(true);
        allMessages.start();
    }

    private static boolean textAnalyzer(String text){
        String[] keyWords = new String[]{"exit","logout","red","blue","green","default","users"};
        for(String s:keyWords){
            if(s.equalsIgnoreCase(text))
                if(!user.getRoom().equalsIgnoreCase(text))
                autentify(text);
                else
                return false;
        }
        return true;
    }
    private static void messageAnalyzer(String text, boolean flag)throws IOException{
        if(flag) {
            Message m;
            String[] texts = text.split(":");
            if (allUsers.userExist(texts[0])) {
                m = new Message(texts[1], user.getLogin(), texts[0], user.getRoom());
                m.send("http://localhost:8080/add");
            }else if(texts[0].equalsIgnoreCase("")) {
                m = new Message(texts[1], user.getLogin(), "all", user.getRoom());
                m.send("http://localhost:8080/add");
            }else
                System.out.println("user: " + texts[0] + " doesn't exist");
        }
    }
}
