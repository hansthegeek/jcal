/*
 * created: 01.02.2014
 */
package de.hansserver.jcal.reciver;

/**
 *
 * @author Raphael Esterle
 * 
 * Implemented methods will be caled when needed.
 * 
 */
public interface ReciverInterface {
    
    public void onNewEventRecived();
    public void onEventConfirmationRecived();
    
}
