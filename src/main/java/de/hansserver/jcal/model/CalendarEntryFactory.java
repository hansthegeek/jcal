/*
 * created: 01.02.2014
 */
package de.hansserver.jcal.model;

import java.util.Date;
import java.util.List;

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
                String creator, 
                String discription,
                List<Attendee> attendees){
    
        return null;
    }
    
}
