package fanaticfour.waffle;

import android.os.Parcel;
import android.os.Parcelable;

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

    public Event(String cname, String ename){
        creator_username = cname;
        eventName= ename;
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
