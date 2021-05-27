package net.transolyfer.transolyfergo.app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import net.transolyfer.transolyfergo.R;
import net.transolyfer.transolyfergo.app.ui.activity.IPhotoFlag;
import net.transolyfer.transolyfergo.app.ui.components.CustomTextView;
import net.transolyfer.transolyfergo.domain.model.Event;

import java.util.List;

/**
 * Created by garymontengro on 22/06/17.
 */

public class EventsAdapter extends BaseAdapter {

    private IPhotoFlag photoFlag;
    private List<Event> eventList;
    private LayoutInflater inflater = null;

    public EventsAdapter(List<Event> eventList, Context context,IPhotoFlag photoFlag) {
        this.eventList = eventList;
        inflater = LayoutInflater.from(context);
        this.photoFlag = photoFlag;
    }

    @Override
    public int getCount() {
        return eventList.size();
    }

    @Override
    public Object getItem(int position) {
        return eventList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.row_single_view, null);
            holder = new ViewHolder();
            holder.tviNationality = (CustomTextView) convertView
                    .findViewById(R.id.tvTextView);

            convertView.setTag(holder);
        } else{
            holder = (ViewHolder) convertView.getTag();
        }

        Event event = (Event) getItem(position);
        holder.tviNationality.setText(event.getName());

        if(event.getFlagFotoApp()!=null){
            photoFlag.getPhotoFlag(event.getFlagFotoApp());
        }
        if(event.getFlagObsApp()!=null){
            photoFlag.getObgFlag(event.getFlagObsApp());
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.row_drop_down, null);
            holder = new ViewHolder();
            holder.tviNationality = (CustomTextView) convertView
                    .findViewById(R.id.tvTextView);

            convertView.setTag(holder);
        } else{
            holder = (ViewHolder) convertView.getTag();
        }

        Event event = (Event) getItem(position);
        holder.tviNationality.setText(event.getName());

        return convertView;
    }

    class ViewHolder {
        CustomTextView tviNationality;
    }
}
