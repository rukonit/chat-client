package com.rukon.clientServer;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;


public class Main {

    // = fields == //
    private String userName;
    private Socket socket;
    private BufferedReader input;
    private PrintWriter printWriter;


    // = methods == //
    public Main() {

        try {
            this.socket = new Socket("192.168.1.245", 5000);
            this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.printWriter = new PrintWriter(socket.getOutputStream(), true);

            this.printWriter.println("User " + (new Random(10)).toString());
            new ReceiveMessage().start();
        }
        catch (IOException e){
            System.out.println("Data send/receive failed: " + e.getMessage());

        }
    }

    public void sendMessage(String message){
            printWriter.println(message);
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        Main m = new Main();

        while (true) {
            String message = scanner.nextLine();
            m.sendMessage(message);
        if(message.equals("logout")){
            System.out.println("Logged Out");
            m.socket.close();
            break;
        }
    }
 }

    //= sub classes = //
    class ReceiveMessage extends Thread {
        @Override
        public void run() {
        try {
            while (true) {
                String inMessage = input.readLine();
                System.out.println("New Message: " + inMessage);
            }
        }
        catch (IOException e){

        }

        }
    }


}
