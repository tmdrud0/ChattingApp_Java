package message;
import java.io.Serializable;
import java.lang.String;

public class Message implements Serializable{
    GoalStream goal_stream;
    CommandType command_type;
    String message;

    public Message(GoalStream gs, CommandType ct, String s){
        goal_stream = gs;
        command_type = ct;
        message = s;
    }
    public Message(CommandType ct, String s){
        goal_stream = MessageParser.getStream(ct);
        command_type = ct;
        message = s;
    }
    public GoalStream getStream(){
        return goal_stream;
    }
    public String getMessage(){
        return message;
    }
}