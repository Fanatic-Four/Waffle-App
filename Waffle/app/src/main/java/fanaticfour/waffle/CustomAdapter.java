package fanaticfour.waffle;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Created by calvinmei on 3/26/16.
 */
public class CustomAdapter extends BaseAdapter{
    private static final int TYPE_HOST = 0;
    private static final int TYPE_ATTENDING= 1;
    private static final int TYPE_OTHER = 2;

    private static final int TYPE_MAX_COUNT = 3;

    private Context context;

    private ArrayList<String> mData = new ArrayList<String>();
    private LayoutInflater mInflater;

    private TreeSet mSeparatorsSet = new TreeSet();

    public CustomAdapter(Context ctxt) {
        mInflater = (LayoutInflater)ctxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        context = ctxt;
    }

    public void addAttendEvent(Event e) {
        mData.add(e.toString());
        notifyDataSetChanged();
    }

    public void addHostEvent(Event e) {
        mData.add(e.toString());
        // save separator position
        mSeparatorsSet.add(mData.size() - 1);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return mSeparatorsSet.contains(position) ? TYPE_HOST : TYPE_ATTENDING;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_MAX_COUNT;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public String getItem(int position) {
        return (String)mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        int type = getItemViewType(position);
        System.out.println("getView " + position + " " + convertView + " type = " + type);
        if (convertView == null) {
            holder = new ViewHolder();
            System.out.println("IN HERHEHEHREHRHEHR");
            switch (type) {
                case TYPE_ATTENDING:
                    convertView = mInflater.inflate(R.layout.list_item, null);
                    convertView.setBackgroundColor(Color.parseColor("#0000FF"));
                    holder.textView = (TextView)convertView.findViewById(R.id.attending_events);
                    convertView.findViewById(R.id.attending_events).setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            context.startActivity(new Intent(context, JoinEvent.class));
                        }
                    });
                    break;
                case TYPE_HOST:
                    convertView = mInflater.inflate(R.layout.host_event_item, null);
                    convertView.setBackgroundColor(Color.parseColor("#FF0000"));
                    holder.textView = (TextView)convertView.findViewById(R.id.host_event);
                    convertView.findViewById(R.id.attending_events).setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            //i.putExtra(String key, value.get(position));
                            context.startActivity(new Intent(context, HostingEvent.class));
                        }
                    });
                    break;
                case TYPE_OTHER: // create an "other" view
                    convertView = mInflater.inflate(R.layout.host_event_item, null);
                    holder.textView = (TextView)convertView.findViewById(R.id.host_event);
                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.textView.setText((String)mData.get(position));
        return convertView;
    }



    public static class ViewHolder {
        public TextView textView;
    }
}
