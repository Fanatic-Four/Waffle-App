package fanaticfour.waffle;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by khue on 3/26/16.
 */
public class Event implements Serializable{
    String creator_username;
    String eventName;
    ArrayList<String> attendees;
    String description;
    String type;

    public Event(String typ, String cname, String ename, String desc){
        type = typ;
        creator_username = cname;
        eventName= ename;
        description = desc;
        attendees = new ArrayList<String>();
    }

    public void addAttendee(String uname){
        attendees.add(uname);
    }

    public String toString(){
        String result = "Event Name: " + eventName + "\nEvent creator: " + creator_username;

        if(description != null && description.length() != 0){
            result = result + "\n" + description+"\n";
        }

        return result;
    }
}
