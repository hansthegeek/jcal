/*
 * created: 01.02.2014
 */
package de.hansserver.jcal.model;

import java.net.URI;
import net.fortuna.ical4j.model.parameter.Cn;

/**
 *
 * @author Raphael Esterle
 */
public class Attendee {
    
    private final String name;
    private final String mail;
    
    public Attendee(String mail, String name) {
        this.mail=mail;
        this.name=name;
    }

    public final String getMail() {
        return mail;
    }

    public final String getName() {
        return name;
    }
    
}
