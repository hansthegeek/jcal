/*
 * created: 01.02.2014
 */

package de.hansserver.jcal;

import de.hansserver.jcal.model.Attendee;
import de.hansserver.jcal.model.CalendarEntry;
import de.hansserver.jcal.model.CalendarEntryFactory;
import de.hansserver.jcal.model.UIDGenerator;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.TestCase;

/**
 *
 * @author Raphael Esterle
 */
public class CalendarEntryTest extends TestCase {
    
    UIDGenerator uidGen = new UIDGenerator() {

        public String generate() {
            return "12345";
        }
    };
 
    public CalendarEntryTest(String testName) {
        super(testName);
    }

    public void testCalendarEntryFactory(){
        CalendarEntryFactory factory = new CalendarEntryFactory(uidGen);
        
        assertNotNull(factory);
        
        Attendee hans = new Attendee("hans@exampel.com", "Hans Maier");
        Attendee peter = new Attendee("peter@exampel.com", "Peter Mueller");
        
        List<Attendee> attendees = new LinkedList<Attendee>();
        attendees.add(hans);
        attendees.add(peter);
        try {
            CalendarEntry entry = factory.createInvite(new Date(), new Date(),"This is a test event...", hans, attendees);
            System.out.println("Entry: "+entry);
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
            assertTrue(false);
        }
    }
}
