package net.transolyfer.transolyfergo.data.mapper;

import android.util.Log;

import net.transolyfer.transolyfergo.data.entity.response.EventResponse;
import net.transolyfer.transolyfergo.domain.model.EventIntranet;

import java.util.ArrayList;
import java.util.List;

public class EventIntranetDataMapper {

    private static EventIntranetDataMapper mInstance;

    private EventIntranet transform(EventResponse eventEntity) {
        EventIntranet event = null;
        if(eventEntity !=null){
            event = new EventIntranet();

            if(eventEntity.getIdOrder() != 0)
                event.setIdOrder(eventEntity.getIdOrder());
            else event.setIdOrder(0);

            if(!eventEntity.getEventOrder().equals(""))
                event.setEventOrder(eventEntity.getEventOrder());
            else event.setEventOrder("");

            if(!eventEntity.getDateEventOrder().equals(""))
                event.setDateEventOrder(eventEntity.getDateEventOrder());
            else event.setDateEventOrder("");

            if(!eventEntity.getImageUrl().equals(""))
                event.setImageUrl(eventEntity.getImageUrl());
            else event.setImageUrl("");

            if(!eventEntity.getImageUrl2().equals(""))
                event.setImageUrl2(eventEntity.getImageUrl2());
            else event.setImageUrl2("");

            if(!eventEntity.getIconImage().equals(""))
                event.setIconImage(eventEntity.getIconImage());
            else event.setIconImage("");

            if(!eventEntity.getColorImage().equals(""))
                event.setColorImage(eventEntity.getColorImage());
            else event.setColorImage("");
        }

        return event;
    }

    public List<EventIntranet> transformEventIntranetList(List<EventResponse> eventEntityList) {
        List<EventIntranet> eventList = new ArrayList<>();
        if(eventEntityList != null){
            if(eventEntityList.size() > 0){
                try {
                    for(EventResponse eventEntity : eventEntityList){
                        eventList.add(transform(eventEntity));
                    }
                } catch (Exception e){
                    Log.e("Exception: ",e.getMessage());
                }
            }
        }
        return eventList;
    }
    public static synchronized EventIntranetDataMapper getInstance() {
        if (mInstance == null) {
            mInstance = new EventIntranetDataMapper();
        }
        return mInstance;
    }
}
