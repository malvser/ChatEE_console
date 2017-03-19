package malov.serg.ChatEE_console_Client;

import java.io.IOException;

public class Main {
    public static void main(String[] args)throws IOException{
	System.out.println("Description of conditions of communication");
        System.out.println("message from all - all:hello all or  :hello all");
        System.out.println("message for the room - red or blue or green or default");
        System.out.println("private message to someone - user:text");
        System.out.println("List of all users - users");
        System.out.println("Leaving chat - exit or logout");
        System.out.println("login: Sergey, password: ser");
        System.out.println("login: Tanya, password: tan");
        System.out.println("login: Artem, password: art");
        System.out.println("----------------------------------------------------------");
        Algorithm.startChat();
    }
}
