package client;
import java.io.*;
import message.*;

public class MyPrinter implements Runnable{
    InputStream input;
    MyPrinter(PipedInputStream input){
        this.input = input;
    }
    void work(Message mes){
        System.out.println(mes.getMessage());
    }
    public void run(){
        System.out.println("printer is running");
        try (ObjectInputStream oiStream = new ObjectInputStream(input)){ 
            while(true) {
                Message cur = (Message)oiStream.readObject();
                work(cur);
            }
        } 
        catch(Exception e){  System.out.println("MyPrinter run Error"); e.printStackTrace();System.exit(0);}
    }
    public void exit(){
        try {
            if(input!=null)     input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Printer is over");
    }
}