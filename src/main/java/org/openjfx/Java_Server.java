package org.openjfx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class Java_Server {
    private static final int PORT = 12346;
    private static final CopyOnWriteArrayList<ClientHandler> clients = new CopyOnWriteArrayList<>();

    public static void main(String[] args){
        try{
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port: " + PORT);

            new Thread(() ->{
                Scanner scanner = new Scanner(System.in);
                while(true) {
                    String serverMessage = scanner.nextLine();
                    broadcast("[Server]: " + serverMessage, null);
                }
            }).start();

            //accept incoming connections
            while(true){
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());
                    // create a new client handler for the connected client
                    ClientHandler clientHandler =  new ClientHandler(clientSocket);
                    clients.add(clientHandler);
                    new Thread(clientHandler).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    private static class ClientHandler implements Runnable{
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        private String username;

        public ClientHandler(Socket socket){
            this.clientSocket = socket;
            try{
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        @Override
        public void run(){
            try {
                //get username from client
                out.println("Enter your username:");
                username = in.readLine();
                System.out.println(("User " + username + " connected"));
                out.println("Type Your Message");

                String inputLine;
                while ((inputLine = in.readLine())!= null){
                    System.out.println("[" + username + "]: " + inputLine);
                    broadcast("[" + username + "]: " + inputLine, this);
                }

                //Remove the client handler from the list when the client disconnects
                clients.remove(this);
                System.out.println("User " + username + " disconnected");
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    in.close();
                    out.close();
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void sendMessage(String message){
            out.println(message);
        }


    }
    public static void broadcast(String message, ClientHandler sender){
        for(ClientHandler client : clients){
            if(client != sender){
                client.sendMessage(message);
            }
        }
    }
}
