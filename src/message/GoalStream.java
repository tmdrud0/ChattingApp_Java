package message;
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
    public boolean is_same(String s){
        return Objects.equals(com, s);
    }
}
enum ChannelCommand implements CommandType{
    MESSAGE("ch_mes");

    final private String com;
    ChannelCommand(String com){
        this.com = com;
    }
    public boolean is_same(String s){
        return Objects.equals(com, s);
    }
}
enum UserCommand implements CommandType{
    MESSAGE("user_mes"),
    ERROR("");

    final private String com;
    UserCommand(String com){
        this.com = com;
    }
    public boolean is_same(String s){
        return Objects.equals(com, s);
    }
}
public enum GoalStream{
    SEVER(Arrays.asList(SeverCommand.values())),
    CHANNEL(Arrays.asList(ChannelCommand.values())),
    USER(Arrays.asList(UserCommand.values()));
    private final List<? extends CommandType> com_list;
    GoalStream(List<? extends CommandType> com_list){
        this.com_list = com_list;
    }
    public List<? extends CommandType> getList(){
        return com_list;
    }
}