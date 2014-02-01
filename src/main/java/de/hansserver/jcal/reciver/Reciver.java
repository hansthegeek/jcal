/*
 * created: 31.01.2014
 */

package de.hansserver.jcal.reciver;

/**
 *
 * @author Raphael Esterle
 */
public abstract class Reciver {
    
    ReciverInterface reciverInterface;
    
    public Reciver(ReciverInterface reciverinterface){
        this.reciverInterface=reciverinterface;
    }
    
}
