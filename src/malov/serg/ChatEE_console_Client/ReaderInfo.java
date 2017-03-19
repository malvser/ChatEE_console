package malov.serg.ChatEE_console_Client;

import java.util.Scanner;


public class ReaderInfo {
    private Scanner scn;
    private String login;
    public ReaderInfo(){

        this.scn = new Scanner(System.in);
    }
    public String readLogin(){
        System.out.print("enter Login: ");
        login = scn.nextLine();
        return login;
    }
    public String readPass(){
        System.out.print("enter password: ");
        return scn.nextLine();
    }
     public String text(){
        String text=scn.nextLine();
        if(text.equalsIgnoreCase(""))
            text();
        else
            return text;
        return null;
    }

    public String getLogin() {
        return login;
    }
}
