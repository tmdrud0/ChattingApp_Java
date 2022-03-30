package client;
import java.io.*;
import message.Message;

public class ClientReceiver implements Runnable{
    InputStream input;
    ClientReceiver(InputStream input){
        this.input = input;
        System.out.println("create client receiver");
    }
    public void run(){
        try(ObjectInputStream oiStream = new ObjectInputStream(input)){
            while(true){
                Client.sendMessage((Message)oiStream.readObject());
            }
        }
        catch(IOException e){   e.printStackTrace();}
        catch(Exception ex) {   ex.printStackTrace();}
    }
    public void exit(){
        try{
            if(input!=null)
            input.close();
        }
        catch(IOException e)    {e.printStackTrace();}
    }
}
