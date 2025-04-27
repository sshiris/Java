package org.openjfx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Java_Client {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args){
        try{
            // Create a socket to connect to the server
            Socket socket = new Socket (SERVER_IP, SERVER_PORT);
            System.out.println("Connected to server: "+ SERVER_IP +":"+ SERVER_PORT);

            // Setting up input and output streams
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //Start a thread to handle incoming messages
            new Thread(() -> {
                try {
                    String serverResponse;
                    while ((serverResponse = in. readLine()) != null){
                        System.out.println(serverResponse);
                    }
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }).start();

            //read user input from console and send to server
            Scanner scanner = new Scanner(System.in);
            String userInput;
            while(true){
                userInput = scanner.nextLine();
                out.println(userInput);// the println writes the user input to the stream, and the flushes it to the server.
                if(userInput.equalsIgnoreCase("bye")){
                    System.out.println("Exiting client...");
                    break;
                }
            }

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
