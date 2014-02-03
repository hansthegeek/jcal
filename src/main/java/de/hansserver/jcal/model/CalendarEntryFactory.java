/*
 * created: 01.02.2014
 */
package de.hansserver.jcal.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.parameter.PartStat;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.util.CompatibilityHints;

/**
 *
 * @author Raphael Esterle
 */
public class CalendarEntryFactory {
    
    private class CalendarEntryExt extends CalendarEntry{
        
        public CalendarEntryExt(
                String uid, Attendee organizer, Date start, 
                Date end, String summary, Calendar calendar){
            super(uid, organizer, start, end, summary);
            this.calendar=calendar;
            this.event=new VEvent(
                    calendar.getComponent(Component.VEVENT).getProperties());
        }
        
    }
    
    private class AttendeeExt extends Attendee{
    
        public AttendeeExt(Property property){
            super(URI.create(property.getValue()),"");
            System.out.println("NULL? "+property);
            Parameter paraName = property.getParameter(Parameter.CN);
            if(null!=paraName)
                this.name=paraName.getValue();
            
        }
        
        private void setStatus(PartStat status){
            this.status=status;
        }
    }
    
    private final UIDGenerator uidgenerator;
    private final CalendarBuilder builder;
    
    public CalendarEntryFactory(UIDGenerator uidgenerator){
        this.uidgenerator=uidgenerator;
        //Relexed Pharsing to prevent parsing errors.
        CompatibilityHints.setHintEnabled(
                CompatibilityHints.KEY_RELAXED_UNFOLDING, true);
        CompatibilityHints.setHintEnabled(
                CompatibilityHints.KEY_RELAXED_PARSING, true);
        builder = new CalendarBuilder();
    }
    
    public CalendarEntry create(
            String discription,
            Date start,
            Date end,
            Attendee organizer){
        return new CalendarEntry(
                uidgenerator.generate(), organizer, 
                start, end, discription);
    }
    
    public CalendarEntry create(InputStream in){
        
        CalendarEntry entry = null;
        
        try {
            // Parsig action!!
            Calendar calendar = builder.build(in);
            
            // Get the main event.
            VEvent event = new VEvent(
                    calendar.getComponent(Component.VEVENT).getProperties());
            
            // Get the uid.
            String uid = event.getProperty(Property.UID).getValue();
            Method method = new Method(
                    calendar.getProperty(Property.METHOD).getValue());
            
            Date start = new Date(new net.fortuna.ical4j.model.Date(
                    event.getProperty(Property.DTSTART).getValue()).getTime());
            
            Date end = new Date(new net.fortuna.ical4j.model.Date(
                    event.getProperty(Property.DTEND).getValue()).getTime());
            
            Property oProperty = event.getProperty(Property.ORGANIZER);
            Attendee organizer = new AttendeeExt(oProperty);
            
            String summary = event.getProperty(Property.SUMMARY).getValue();
            
            entry = new CalendarEntryExt(
                    uid, organizer, start, end, summary,calendar);
            
            List<Property> propAttendees = event.getProperties(Property.ATTENDEE);
            List<Attendee> attendees = new LinkedList<Attendee>();
            Property property;
            for(Iterator<Property> i = propAttendees.iterator(); i.hasNext();){
                property = i.next();
                System.out.println("ATTENDEE "+property);
                attendees.add(new AttendeeExt(property));
            }
            
            entry.setAttendees(attendees);
            
        } catch (ParseException ex) {
            Logger.getLogger(CalendarEntryFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CalendarEntryFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserException ex) {
            Logger.getLogger(CalendarEntryFactory.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return entry;
    }
    
}