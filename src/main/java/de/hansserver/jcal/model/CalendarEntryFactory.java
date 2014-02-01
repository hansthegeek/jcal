/*
 * created: 01.02.2014
 */
package de.hansserver.jcal.model;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.parameter.Cn;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Organizer;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Uid;

/**
 *
 * @author Raphael Esterle
 */
public class CalendarEntryFactory {
    
    private final UIDGenerator uidgenerator;
    
    public CalendarEntryFactory(UIDGenerator uidgenerator){
        this.uidgenerator=uidgenerator;
    }
    
    public CalendarEntry create(
                Date start, 
                Date end, 
                Attendee organizer, 
                String discription,
                List<Attendee> attendees) throws URISyntaxException{
        
        // Craete new event witch contains startdate enddate and a discription.
        VEvent vEvent = new VEvent(
                new net.fortuna.ical4j.model.Date(start),
                new net.fortuna.ical4j.model.Date(end), 
                discription);
        
        // Add a unique id.
        vEvent.getProperties().add(new Uid(uidgenerator.generate()));
        
        // Create the organizer.
        Organizer vOrganizer = new Organizer(organizer.getMail());
        vOrganizer.getParameters().add(new Cn(organizer.getName()));
        vEvent.getProperties().add(new Organizer());
        
        // Add all attendees.
        Attendee ca;
        net.fortuna.ical4j.model.property.Attendee vAttendee;
        for(Iterator<Attendee> i = attendees.iterator(); i.hasNext();){
            ca = i.next();
            vAttendee = new net.fortuna.ical4j.model.property.Attendee(
                    URI.create("mailto:"+ca.getMail()));
            vEvent.getProperties().add(new Cn(ca.getName()));
            
        }
        
        // Create the mail calendar object.
        CalendarEntry icsCalendar = new CalendarEntry();
        icsCalendar.getProperties().add(new ProdId("-//Events Calendar//iCal4j 1.0//EN"));
        icsCalendar.getProperties().add(CalScale.GREGORIAN);
        
        icsCalendar.getComponents().add(vEvent);
        
        return icsCalendar;
    }
    
}
