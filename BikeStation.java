import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Models an Electric Vehicle Charging Station.
 * A charging station contains multiple {@link Dock} units.
 * @author DP classes 
 * @version 2026.10.13
 */             

public class BikeStation
{
    private String idStation;
    private Location location;
    private ArrayList<Dock> docks;

    /**
     * Constructor for objects of class BikeStation.
     * //TODO complete the JavaDoc comments
     */
    public BikeStation(  )
    {
        idStation = " ";
        location = null;
        docks = new ArrayList<>();
    }

    /**
     * Constructor for objects of class BikeStation.
     * //TODO complete the JavaDoc comments
     */
    public BikeStation(String id, Location location)
    {
        idStation = id;
        this.location = location;
        docks = new ArrayList<>();
    }
    
    /**
     * Retrieves the first free {@link Dock} found at the station.
     * @return A free {@link Dock}, or possibly throws an exception if none are found (depending on stream implementation details).
     * **Note: The implementation assumes at least one free dock exists if called.**
     */
    public Dock getFreeDock()
    {
        for (int i = 0; i < docks.size(); i++){
            if(docks.get(i).getFree()){
                return docks.get(i);
            }
        }
        return null;        
    }

    /**
     * @return The unique identifier of the charging station.
     */
    public String getId()
    {
        return idStation;
    }
    
    
    /**
     * Adds a new {@link Dock} to the station.
     * @param dock The new Dock unit.
     */
    public void addDock(Dock dock)
    {
       docks.add(dock);
    }
    
    /**
     * Set the current location of the charging station.
     * @param location Where it is. Must not be null.
     * @throws NullPointerException If location is null.
     */
    public void setLocation(Location location)
    {
       this.location = location;  
    }
    
    /**
     * @return the location of BikeStation
     */
    public Location getLocation(){
        return location;
    }
    
    /**
     * @return the set of docks that is called docks
     */
    public ArrayList<Dock> getdocks(){
        return docks;
    }
}
