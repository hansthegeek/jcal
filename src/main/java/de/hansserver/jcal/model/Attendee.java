/*
 * created: 01.02.2014
 */
package de.hansserver.jcal.model;

import java.net.URI;
import net.fortuna.ical4j.model.parameter.PartStat;

/**
 *
 * @author Raphael Esterle
 */
public class Attendee {
    
    protected String name;
    protected URI mail;
    protected PartStat status;
    
    public Attendee(URI mail, String name) {
        this.mail=mail;
        this.name=name;
        this.status=PartStat.NEEDS_ACTION;
    }
    
    public final URI getMail() {
        System.out.println("WARUUUUUM? "+mail);
        return mail;
    }

    public final String getName() {
        return name;
    }
    
    public void accepted(){
        status = PartStat.ACCEPTED;
    }
    
    public boolean hasAccepted(){
        return status.equals(PartStat.ACCEPTED);
    }
    
    public void declined(){
        status = PartStat.DECLINED;
    }
    
    public boolean hasDeclined(){
        return status.equals(PartStat.DECLINED);
    }
    
    public void tentative(){
        status = PartStat.TENTATIVE;
    }
    
    public boolean isTentative(){
        return status.equals(PartStat.TENTATIVE);
    }
    
    public boolean needsAction(){
        return status.equals(PartStat.NEEDS_ACTION);
    }
}
