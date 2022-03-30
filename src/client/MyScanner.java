package client;
import java.io.*;
import message.*;
import java.util.*;

public class MyScanner implements Runnable{
    OutputStream output;
    Scanner input;
    MyScanner(OutputStream output){
        input = new Scanner(System.in);
        this.output = output;
    }
    Message work(){
        String tmp = input.nextLine();
        Message ret = MessageParser.parse(tmp);
        return ret;
    }
    public void run(){
        System.out.println("Scanner is running");
        try (ObjectOutputStream ooStream = new ObjectOutputStream(output)){ 
            while(true) {
                Message cur = work();
                ooStream.writeObject(cur);
            }
        } 
        catch(Exception e){   System.out.println("MyScanner run Error");e.printStackTrace();System.exit(0);}
    }
    public void exit(){
        try {
            if(input!=null)     input.close();
            if(output!=null)    output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Scanner is over");
    }
}
