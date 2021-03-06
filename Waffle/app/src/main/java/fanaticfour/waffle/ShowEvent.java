package fanaticfour.waffle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowEvent extends Activity {

    // List view
    private ListView lv;

    // Listview Adapter
    ArrayAdapter<String> adapter;

    // Search EditText
    EditText inputSearch;

    // ArrayList for Listview
    ArrayList<HashMap<String, String>> productList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event);

        String allEvents[] = {"Supper Bowl Raffle", "Chocolate Raffle", "Ice Cream Raffle", "Board Games Raffle", "Whiteboard Raffle", "Oculus Rift Raffle"};
        Button createEvent = (Button) findViewById(R.id.create_event_button);
        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createEvent(view);
            }
        });
//
//        ArrayList<Event> eventList = null;
//        try{
//            Bundle evtBundle = getIntent().getExtras();
//            eventList = (ArrayList<Event>) evtBundle.getSerializable("eventList");
//        }
//        catch (Exception e){
//        }
//
//        ArrayList<Event> hosting_events = getEventsOfType(eventList, "HOST");
//        ArrayList<Event> attending_events = getEventsOfType(eventList, "ATTENDING");
//        ArrayList<Event> other_events = getEventsOfType(eventList, "OTHER");


        lv = (ListView) findViewById(R.id.list_view);
        inputSearch = (EditText) findViewById(R.id.inputSearch);

        // Adding items to listview
        adapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.attending_events, allEvents);
//        adapter = new CustomAdapter(this);

        // Listview Data
//        for(Event e : hosting_events){
//            adapter.addHostEvent(e);
//        }
//        for(Event e : attending_events){
//            adapter.addAttendEvent(e);
//        }
        //Add other events later

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

                Intent i = new Intent(ShowEvent.this, JoinEvent.class);
                //If you wanna send any data to nextActicity.class you can use
                //i.putExtra(String key, value.get(position));

                startActivity(i);
            }
        });


        /**
         * Enabling Search Filter
         * */
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                ShowEvent.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

    }

    private ArrayList<Event> getEventsOfType(ArrayList<Event> eventList, String type) {
        ArrayList<Event> result = new ArrayList<Event>();
        for(Event e : eventList){
            if(e.type.equals(type)){
                result.add(e);
            }
        }
        return result;
    }

    private void createEvent(View view) {
        Intent intent = new Intent(this, CreateEvent.class);
        startActivity(intent);
    }

}
