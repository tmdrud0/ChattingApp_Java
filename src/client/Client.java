package client;
import message.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.io.*;
import java.net.Socket;

public class Client {
    static final Client client = new Client();
    static ArrayList<Socket> sockets = new ArrayList<>();
    static Map<GoalStream,ClientSender> sMap = new ConcurrentHashMap<>();
    static Map<GoalStream,ClientReceiver> rMap = new HashMap<>();
    
    private Client(){}
    public static void sendMessage(Message mes){
        ClientSender tmp = sMap.getOrDefault(mes.getStream(),null);
        if(tmp!=null)   tmp.sendMessage(mes);
        else            {
            System.out.println(mes.getStream());
            sMap.get(GoalStream.USER).sendMessage(MessageParser.parse("/error"));
        }
    }
    public void addSender(GoalStream gs, OutputStream output){
        try{
            ClientSender cs = new ClientSender(output);
            sMap.put(gs,cs);
            Thread csThread = new Thread(cs);
            csThread.start();
        }catch(Exception e){    e.printStackTrace();System.exit(1);}
    }
    public void addReceiver(GoalStream gs, InputStream input){
        try{
            ClientReceiver cr = new ClientReceiver(input);
            rMap.put(gs,cr);
            Thread crThread = new Thread(cr);
            crThread.start();
        }catch(Exception e){    e.printStackTrace();System.exit(1);}
    }
    public void addSocket(GoalStream gs,Socket socket){
        sockets.add(socket);
        try{
            addReceiver(gs, socket.getInputStream());
            addSender(gs, socket.getOutputStream());
        }
        catch(Exception e)  {e.printStackTrace();System.exit(1);}
    }
    public void exit(){
        for(ClientSender cs : sMap.values()){
            cs.exit();
        }
        for(ClientReceiver cr : rMap.values()){
            cr.exit();
        }
        try {
            for(Socket socket : sockets){
                if(socket!=null)    socket.close();
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Client getClient(){
        return client;
    }
}
