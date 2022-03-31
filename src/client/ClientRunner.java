package client;

import java.io.*;
import java.net.Socket;
import message.GoalStream;

public class ClientRunner {
    
    static final String HOST_NAME = "127.0.0.1";
    static final int PORT_NUM = 7777;
    static final Client client = Client.getClient();    
    static MyPrinter printer;
    static MyScanner scanner;
    
    static void initUser(){
        System.out.println("init user inputoutput");
        try{
            PipedInputStream pis = new PipedInputStream();
            PipedOutputStream pos = new PipedOutputStream(pis);
            PipedInputStream sis = new PipedInputStream();
            PipedOutputStream sos = new PipedOutputStream(sis);

            printer = new MyPrinter(pis);
            scanner = new MyScanner(sos);
            client.addReceiver(GoalStream.USER, sis);
            client.addSender(GoalStream.USER, pos);

            Thread piThread = new Thread(printer);
            Thread soThread = new Thread(scanner);
            piThread.start();
            soThread.start();
        } catch (Exception e) {
            System.out.println("Fail to init User");
            e.printStackTrace();
            System.exit(1);
        }
    }
    static void initSever(){
        try {
            Socket socket = new Socket(HOST_NAME,PORT_NUM);
            client.addSocket(GoalStream.SEVER, socket);
        } catch (Exception e) {
            System.out.println("Fail to init Sever");
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("succes connecting to sever");
    }
    static void login(){
        System.out.println("");
        try {
            
        } catch (Exception e) {
            System.out.println("Failed to login");
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static void main(String[] args) {
        initUser();
      //initSever();
        //login();
    }
}
