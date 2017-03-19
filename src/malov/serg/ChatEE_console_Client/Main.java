package malov.serg.ChatEE_console_Client;

import java.io.IOException;

public class Main {
    public static void main(String[] args)throws IOException{
	System.out.println("welcome to JSON chat");
        System.out.println("message from all example-   all:hello people  or  :hello people");
        System.out.println("for exit type word- exit or logout");
        System.out.println("for rooms type word - red or blue or green or default");
        System.out.println("private message type- user:text ");
        System.out.println("for user info type- users");
        System.out.println("login: Sergey, password: ser");
        System.out.println("login: Tanya, password: tan");
        System.out.println("login: Artem, password: art");
        System.out.println("----------------------------------------------------------");
        Logik.startChat();
    }
}
