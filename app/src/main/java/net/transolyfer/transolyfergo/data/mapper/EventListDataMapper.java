package net.transolyfer.transolyfergo.data.mapper;


import net.transolyfer.transolyfergo.data.entity.EventEntity;
import net.transolyfer.transolyfergo.domain.model.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 21/03/2017.
 */

public class EventListDataMapper {

    private static EventListDataMapper mInstance;

    private Event transformNationality(EventEntity eventEntity) {
        Event event = null;
        if(eventEntity !=null){
            event = new Event();

            if(eventEntity.getIdEvent() != 0)
                event.setIdNationality(eventEntity.getIdEvent());
            else event.setIdNationality(0);

            if(eventEntity.getDescription() != "")
                event.setName(eventEntity.getDescription());
            else event.setName("");

            if(eventEntity.getFlagFotoApp() != "")
                event.setFlagFotoApp(eventEntity.getFlagFotoApp());
            else event.setFlagFotoApp("");

            if(eventEntity.getFlagObsApp() != "")
                event.setFlagObsApp(eventEntity.getFlagObsApp());
            else event.setFlagObsApp("");
        }

        return event;
    }

    public List<Event> transformNationalityList(List<EventEntity> eventEntityList) {
        List<Event> eventList = new ArrayList<>();
        if(eventEntityList != null){
            if(eventEntityList.size() > 0){
                try {
                    eventList.add(0,setDefaultElement("Seleccione"));
                    for(EventEntity eventEntity : eventEntityList){
                        eventList.add(transformNationality(eventEntity));
                    }
                } catch (Exception e){

                }
            }
        }
        return eventList;
    }

    public Event setDefaultElement(String defaultElement){
        Event event = new Event();
        event.setIdNationality(0);
        event.setName(defaultElement);
        return event;
    }

    public static synchronized EventListDataMapper getInstance() {
        if (mInstance == null) {
            mInstance = new EventListDataMapper();
        }
        return mInstance;
    }
}
