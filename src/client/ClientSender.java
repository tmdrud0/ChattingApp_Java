package client;
import java.io.*;
import message.*;
import java.util.concurrent.*;

public class ClientSender implements Runnable{
    OutputStream output;
    LinkedBlockingQueue<Message> messageQueue;
    ClientSender(OutputStream output){
        messageQueue = new LinkedBlockingQueue<Message>();
        this.output = output;
    }
    public void sendMessage(Message mes){
        try{
            messageQueue.put(mes);
        }
        catch(InterruptedException e){   e.printStackTrace();System.exit(0);}
    }
    public void run(){
        try(ObjectOutputStream ooStream = new ObjectOutputStream(output)){
            while(true){
                Message mes = messageQueue.take();
                ooStream.writeObject(mes);
            }
        }
        catch(InterruptedException e){   e.printStackTrace();}
        catch(IOException e){            e.printStackTrace();}
    }
    public void exit(){
        try{
            if(output!=null)
            output.close();
        }
        catch(IOException e)    {e.printStackTrace();}
    }
}