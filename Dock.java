import java.util.ArrayList;
import java.util.List;
/**
 * Model a dock unit within a {@link BikeStation}.
 * It tracks its charging capabilities, fee, and the electric bikes it has recharged.
 * @author David J. Barnes and Michael Kölling
 * @author DP classes 
 * @version 2026.10.13
 */
public class Dock
{
    private String idDock;
    private int chargingSpeed;
    private float chargingFee;
    private ArrayList<ElectricBike> bikesRecharged;
    private int numberRecharged;
    private int amountCollected;
    private boolean free;

    /**
     * Constructor for objects of class Dock.
     * //TODO complete the JavaDoc comments
    */
    public Dock(String id, int speed, float fee)
    {
        idDock = id;
        chargingSpeed = speed;
        chargingFee = fee;
        bikesRecharged = new ArrayList<>();
        numberRecharged = 0;
        amountCollected = 0;
        free = true;
    }
    
    /**
     * @return the dock's charging speed in wh. Transforms an int into a string.
     */
    public String getChargingSpeed(){
        String speed = chargingSpeed + "wh";
        return speed;  
    }
    
    /**
     * @return the dock's charging speed in €/Wh. Transforms an int into a string.
     */
    public String getChargingFee(){
        return chargingFee + "€ / Wh";
    }
    
    /**
     * Returns a string representation of the dock, including its ID, speed, fee, and the number of EVs recharged.
     * @return A string representation of the dock.
     */
    public String ToString()
    {
        return idDock + ":" + getChargingSpeed() + ":" + getChargingFee() + ":" + getNumberEVRecharged();
    }    

    
    /**
     * @return The total number of {@link ElectricBike}s that have been recharged by this dock.
     */
    public int getNumberEVRecharged(){
        return numberRecharged;
    }
    
    
    /**
     * Simulates the charging process for an {@link ElectricBike}.
     * Increases the amount collected and registers the bike as recharged.
     * //TODO complete the JavaDoc comments
     */
    public float recharge(   ){
        //TODO: Complete this code
        return 0;
    }
    
    /**
     * Returns true if the dock is free to use or false if it isn't.
     */
    public boolean getFree(){
        return free;
    }
    //TODO: Complete this code

}
