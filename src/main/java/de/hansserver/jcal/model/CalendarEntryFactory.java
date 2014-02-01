/*
 * created: 01.02.2014
 */
package de.hansserver.jcal.model;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.parameter.Cn;
import net.fortuna.ical4j.model.parameter.PartStat;
import net.fortuna.ical4j.model.parameter.Role;
import net.fortuna.ical4j.model.parameter.Rsvp;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.Organizer;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;

/**
 *
 * @author Raphael Esterle
 */
public class CalendarEntryFactory {
    
    private final UIDGenerator uidgenerator;
    
    public CalendarEntryFactory(UIDGenerator uidgenerator){
        this.uidgenerator=uidgenerator;
    }
    
    private CalendarEntry createCalendar(
            List<Component> components, List<Property> properties){
        
        // Create the mail calendar object.
        CalendarEntry icsCalendar = new CalendarEntry();
        icsCalendar.getProperties().add(new ProdId("-//Test Calendar jCal//iCal4j 1.0//EN"));
        icsCalendar.getProperties().add(Version.VERSION_2_0);
        icsCalendar.getProperties().add(CalScale.GREGORIAN);
        
        for(Iterator<Component> i = components.iterator(); i.hasNext();){
            icsCalendar.getComponents().add(i.next());
        }
        
        for(Iterator<Property> i = properties.iterator(); i.hasNext();){
            icsCalendar.getProperties().add(i.next());
        }
        
        return icsCalendar;
    }
    
    private VEvent createEvent(
        Date start, 
        Date end,
        String discription,
        Attendee organizer,
        List<Property> properties) throws URISyntaxException{
        
         // Craete new event witch contains startdate enddate and a discription.
        VEvent vEvent = new VEvent(
                new net.fortuna.ical4j.model.Date(start),
                new net.fortuna.ical4j.model.Date(end), 
                discription);
        
        // Add a unique id.
        vEvent.getProperties().add(new Uid(uidgenerator.generate()));
        
        // Create the organizer.
        Organizer vOrganizer = new Organizer("mailto:"+organizer.getMail());
        vOrganizer.getParameters().add(new Cn(organizer.getName()));
        vOrganizer.getParameters().add(Role.REQ_PARTICIPANT);
        vEvent.getProperties().add(vOrganizer);
        
        // Add properties
        for(Iterator<Property> i=properties.iterator(); i.hasNext();){
            vEvent.getProperties().add(i.next());
        }
        
        return vEvent;
    }
    
    public CalendarEntry createInvite(
        Date start, 
        Date end,
        String discription,
        Attendee organizer,
        List<Attendee> attendees) throws URISyntaxException{
        
        List<Component> components = new LinkedList<Component>();
        List<Property> eventProperties = new LinkedList<Property>();
        List<Property> calendarProperties = new LinkedList<Property>();
        
        // Add all attendees.
        Attendee ca;
        net.fortuna.ical4j.model.property.Attendee vAttendee;
        for(Iterator<Attendee> i = attendees.iterator(); i.hasNext();){
            ca = i.next();
            vAttendee = new net.fortuna.ical4j.model.property.Attendee(
                    URI.create("mailto:"+ca.getMail()));
            vAttendee.getParameters().add(new Cn(ca.getName()));
            vAttendee.getParameters().add(Rsvp.TRUE);
            vAttendee.getParameters().add(PartStat.NEEDS_ACTION);
            eventProperties.add(vAttendee);
        }
        
        components.add(createEvent(start, end, discription, organizer, eventProperties));
        
        calendarProperties.add(Method.REQUEST);
        
        return createCalendar(components, calendarProperties);
    }
    
}
