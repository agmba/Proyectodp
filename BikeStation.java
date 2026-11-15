import java.util.ArrayList;
import java.util.Collections;

/**
 * Models an electric bike station.
 * A bike station is placed at a {@link Location} of the city and contains
 * multiple {@link Dock} units where the bikes can recharge.
 * @author Álvaro Gómez Martín, Javier Ritoré Durán, Daniel Fernández Martín
 * @version 2026.10.13
 */
public class BikeStation
{
    private String idStation;
    private Location location;
    private ArrayList<Dock> docks;

    /**
     * Default constructor for objects of class BikeStation.
     * Creates a station with no id, no location and no docks.
     */
    public BikeStation()
    {
        idStation = " ";
        location = null;
        docks = new ArrayList<>();
    }

    /**
     * Constructor for objects of class BikeStation.
     * Creates a station with the given id and location and no docks.
     * @param id The identifier of the station (two letters for the city 
     * and two digits, e.g. CC01).
     * @param location The {@link Location} of the station in the city.
     */
    public BikeStation(String id, Location location)
    {
        idStation = id;
        this.location = location;
        docks = new ArrayList<>();
    }

    /**
     * @return The unique identifier of the bike station.
     */
    public String getId()
    {
        return idStation;
    }

    /**
     * @return The {@link Location} of the bike station.
     */
    public Location getLocation()
    {
        return location;
    }

    /**
     * Set the location of the bike station.
     * @param location Where it is. Must not be null.
     * @throws NullPointerException If location is null.
     */
    public void setLocation(Location location)
    {
        if (location == null) {
            throw new NullPointerException("Location must not be null");
        }
        this.location = location;
    }

    /**
     * @return The list of {@link Dock}s of the bike station.
     */
    public ArrayList<Dock> getdocks()
    {
        return docks;
    }

    /**
     * Adds a new {@link Dock} to the station, keeping the docks ordered by
     * charging speed (descending), charging fee (ascending) 
     * and id (ascending).
     * @param dock The new Dock unit. Must not be null.
     * @throws NullPointerException If dock is null.
     */
    public void addDock(Dock dock)
    {
        if (dock == null) {
            throw new NullPointerException("Dock must not be null");
        }
        docks.add(dock);
        Collections.sort(docks, new ComparatorDocks());
    }

    /**
     * Retrieves the first free {@link Dock} found at the station.
     * @return The first free {@link Dock}, or null if all the docks
     * are occupied.
     */
    public Dock getFreeDock()
    {
        Dock freeDock = null;
        int index = 0;
        while (index < docks.size() && freeDock == null) {
            if (docks.get(index).getFree()) {
                freeDock = docks.get(index);
            }
            index++;
        }
        return freeDock;
    }

    /**
     * @return The total number of bikes recharged in all the docks 
     * of the station.
     */
    public int getNumberRecharged()
    {
        int total = 0;
        for (Dock dock : docks) {
            total += dock.getNumberEVRecharged();
        }
        return total;
    }

    /**
     * Returns a string representation of the bike station with the 
     * format of the simulation output.
     * @return A string with the id, number of bikes recharged and location,
     * e.g. {@code BikeStation: CC00, 0, 10-5}.
     */
    @Override
    public String toString()
    {
        return "BikeStation: " + idStation + ", " + getNumberRecharged() + ", " + location;
    }
}