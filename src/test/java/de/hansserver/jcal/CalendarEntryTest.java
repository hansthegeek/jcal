/*
 * created: 01.02.2014
 */

package de.hansserver.jcal;

import de.hansserver.jcal.model.Attendee;
import de.hansserver.jcal.model.CalendarEntry;
import de.hansserver.jcal.model.CalendarEntryFactory;
import de.hansserver.jcal.model.UIDGenerator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
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
    
    CalendarEntryFactory factory;
    
    UIDGenerator uidGen = new UIDGenerator() {

        public String generate() {
            return "12345";
        }
    };
 
    public CalendarEntryTest(String testName) {
        super(testName);
    }

    public void testCalendarEntryFactory(){
        factory = new CalendarEntryFactory(uidGen);
        
        assertNotNull(factory);
        
        Attendee hans = new Attendee(URI.create("hans@exampel.com"), "Hans Maier");
        Attendee peter = new Attendee(URI.create("peter@exampel.com"), "Peter Mueller");
        
        List<Attendee> attendees = new LinkedList<Attendee>();
        attendees.add(hans);
        attendees.add(peter);
            CalendarEntry entry = factory.create(
                    "This is a test event...",
                    new Date(), new Date(),
                    hans);
            entry.setAttendees(attendees);
        try {
            String s = entry.createRequest();
            System.out.println("LL "+s);
            assertNotNull(s);
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
        
    }
    
    public void testParsing(){
        factory = new CalendarEntryFactory(uidGen);
        File inFile = new File("src/test/java/de/hansserver/jcal/request.ics");
        try {
            CalendarEntry entry = factory.create(new FileInputStream(inFile));
            assertNotNull(entry);
            List<Attendee> attendees = entry.getAttendees();
            Attendee hans = attendees.get(0);
            //assertEquals("Hans Maier", hans.getName());
            hans.accepted();
            System.out.println("OUT "+entry.createReply());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CalendarEntryTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(CalendarEntryTest.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
