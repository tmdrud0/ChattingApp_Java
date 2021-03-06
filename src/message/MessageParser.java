package message;
import java.lang.String;

public class MessageParser {
    static final String ORDER_COM = "/";
    static final GoalStream BASIC_GS = GoalStream.CHANNEL;
    static final CommandType BASIC_CT = ChannelCommand.MESSAGE;
    static final GoalStream ERROR_GS = GoalStream.USER;
    static final CommandType ERROR_CT = UserCommand.ERROR;
    static final String INVALID_COM = "The command is invalid";
    private MessageParser(){}

    static public GoalStream getStream(CommandType t){
        for(GoalStream gs : GoalStream.values())
        for(CommandType ct : gs.getList())
        if(t==ct)   return gs;
        return ERROR_GS;
    }
    static private CommandType getCommandType(String s){
        for(GoalStream gs : GoalStream.values())
        for(CommandType ct : gs.getList())
        if(ct.is_same(s))   return ct;
        return ERROR_CT;
    }
    static public Message parse(String s)
    {
        if(!s.startsWith(ORDER_COM))    return new Message(BASIC_GS,BASIC_CT,s);
        
        String[] tmp = s.split(" ",2);
        if(tmp.length==1)   return new Message(ERROR_GS, ERROR_CT, INVALID_COM);
        String com = tmp[0].substring(1);
        CommandType ct = getCommandType(com);
        GoalStream gs = getStream(ct);
        String mes = tmp[1];
        if(ct==UserCommand.ERROR)   mes = INVALID_COM;
        return new Message(gs,ct,mes);
    }
}
