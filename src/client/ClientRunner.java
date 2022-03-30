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
            ClientReceiver uReceiver = client.addReceiver(GoalStream.USER, sis);
            ClientSender uSender = client.addSender(GoalStream.USER, pos);

            Thread piThread = new Thread(printer);
            Thread poThread = new Thread(uSender);
            Thread soThread = new Thread(scanner);
            Thread siThread = new Thread(uReceiver);

            piThread.start();
            poThread.start();
            siThread.start();
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
    }
    static void login(){
        try {
            
        } catch (Exception e) {
            System.out.println("Fail to login");
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static void main(String[] args) {
        System.out.println("start");
        initUser();
      //initSever();
        //login();
    }
}
