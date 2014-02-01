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
public class Attendee extends net.fortuna.ical4j.model.property.Attendee{
    
    private final String name;
    private final String mail;
    
    public Attendee(String mail, String name) {
        super(URI.create("mailto:"+mail));
        getParameters().add(new Cn(name));
        this.mail=mail;
        this.name=name;
    }

    public String getMail() {
        return mail;
    }

    public static String getNAME() {
        return NAME;
    }
    
}
