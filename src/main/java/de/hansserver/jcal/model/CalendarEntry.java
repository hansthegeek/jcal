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
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.parameter.Cn;
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
public class CalendarEntry {
    
    private final String uid;
    private Attendee organizer;
    private List<Attendee> attendees = new LinkedList<Attendee>();
    private Date start;
    private Date end;
    private String discription;
    private Method method;
    protected Calendar calendar = null;
    protected VEvent event = null;
    
    public CalendarEntry(String uid, Attendee organizer, Date start, Date end, String discription){
        this.uid=uid;
        this.organizer=organizer;
        this.start=start;
        this.end=end;
        this.discription=discription;
    }
    
   private URI createProperURI(URI uri){
        if(null==uri.getScheme())
            uri = URI.create("mailto:"+uri.toASCIIString());
        return uri;
   }
    
    public void setAttendees(List<Attendee> attendees){
        this.attendees=attendees;
    }
    
    public List<Attendee> getAttendees(){
        return attendees;
    }
    
    private String createICS() throws URISyntaxException{
        // The main Calendar
        Calendar c = new Calendar();
        c.getProperties().add(new ProdId("-//Test Calendar jCal//iCal4j 1.0//EN"));
        c.getProperties().add(Version.VERSION_2_0);
        c.getProperties().add(CalScale.GREGORIAN);
        c.getProperties().add(method);
        
        // Create the main event.
        VEvent event = new VEvent(
                new net.fortuna.ical4j.model.Date(start),
                new net.fortuna.ical4j.model.Date(end), 
                discription);
        
        // Add a unique id.
        event.getProperties().add(new Uid(uid));
        
        // Add organizer.
        Organizer vOrganizer = new Organizer(createProperURI(organizer.getMail()));
        vOrganizer.getParameters().add(new Cn(organizer.getName()));
        vOrganizer.getParameters().add(Role.REQ_PARTICIPANT);
        event.getProperties().add(vOrganizer);
        
        // Add attendees.
        Attendee ca;
        for(Iterator<Attendee> i=attendees.iterator(); i.hasNext();){
            ca = i.next();
            net.fortuna.ical4j.model.property.Attendee vAttendee = 
                    new net.fortuna.ical4j.model.property.Attendee(
                    createProperURI(ca.getMail()));
            vAttendee.getParameters().add(new Cn(ca.getName()));
            vAttendee.getParameters().add(Rsvp.TRUE);
            vAttendee.getParameters().add(ca.status);
            event.getProperties().add(vAttendee);
        }
        
        // Add event to calendar.
        c.getComponents().add(event);
        
        return c.toString();
    }
    
    public String createRequest() throws URISyntaxException{
        method = Method.REQUEST;
        return createICS();
    }
    
    public String createReply() throws URISyntaxException{
        method = Method.REPLY;
        return createICS();
    }
    
    public String getUid(){
        return uid;
    }
    
}