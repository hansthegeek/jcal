/*
 * created: 01.02.2014
 */
package de.hansserver.jcal.model;

/**
 *
 * @author Raphael Esterle
 */
public class Attendee {
    
    private final String mailto;
    private final String name;

    public Attendee(String mailto, String name) {
        this.mailto = mailto;
        this.name = name;
    }
    
    public String getMail(){
        return mailto;
    }
    
    public String getName(){
        return name;
    }
    
}
