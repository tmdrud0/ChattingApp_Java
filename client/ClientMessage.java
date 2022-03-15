import java.lang.String;
import java.util.*;

interface CommandType{
    public boolean is_same(String s);
} 

enum SeverCommand implements CommandType{
    LOGIN("login"),
    CHANGE_CHANNEL("ch_chan");
    
    final private String com;
    SeverCommand(String com){
        this.com = com;
    }
    public boolean is_same(String s)
    {
        return s==com;
    }
}
enum ChannelCommand implements CommandType{
    MESSAGE("ch_mes");

    final private String com;
    ChannelCommand(String com){
        this.com = com;
    }
    public boolean is_same(String s)
    {
        return s==com;
    }
}
enum ErrorCommand implements CommandType{
    ERROR("");

    final private String com;
    ErrorCommand(String com){
        this.com = com;
    }
    public boolean is_same(String s)
    {
        return s==com;
    }
}
enum GoalStream{
    SEVER(Arrays.asList(SeverCommand.values())),
    CHANNEL(Arrays.asList(ChannelCommand.values())),
    ERROR(new ArrayList<CommandType>());

    private final List<? extends CommandType> com_list;
    GoalStream(List<? extends CommandType> com_list)
    {
        this.com_list = com_list;
    }
    static public GoalStream getStream(String s)
    {
        for(GoalStream gs : GoalStream.values())
        for(CommandType ct : gs.com_list)
        if(ct.is_same(s))   return gs;
        return GoalStream.ERROR;
    }
    static public CommandType getCommandType(String s)
    {
        for(GoalStream gs : GoalStream.values())
        for(CommandType ct : gs.com_list)
        if(ct.is_same(s))   return ct;
        return ErrorCommand.ERROR;
    }
}



public class ClientMessage {
    GoalStream goal_stream;
    CommandType command_type;
    String message;

    static final String ORDER_COM = "/";
    static final GoalStream BASIC_GS = GoalStream.CHANNEL;
    static final CommandType BASIC_CT = ChannelCommand.MESSAGE;
    static final String INVALID_COM = "The command is invalid";
    void setValue(GoalStream gs, CommandType ct, String s)
    {
        goal_stream = gs;
        command_type = ct;
        message = s;
    }
    ClientMessage(GoalStream gs, CommandType ct, String s)
    {
        setValue(gs,ct,s);
    }
    ClientMessage(String s)
    {
        if(!s.startsWith(ORDER_COM))    setValue(BASIC_GS,BASIC_CT,s);

        String[] tmp = s.split(" ",2);
        String com = tmp[0].substring(1);
        goal_stream = GoalStream.getStream(com);
        command_type = GoalStream.getCommandType(com);
        message = tmp[1];
        if(goal_stream==GoalStream.ERROR)   message = INVALID_COM;
    }
}
