import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Model the common elements of an Electric bike (EB) that operates 
 * within the simulation, moving towards a target and potentially recharging.
 * @author David J. Barnes and Michael Kölling
 * @author DP classes 
 * @version 2026.10.13
 */
public class ElectricBike 
{ 
    /** Energy (wh) consumed in each movement step. */
    public static final int MOVING_COST = 5; 

    private String idBike;
    private EBCompany company;
    private Location location;
    private Location targetLocation;
    private Location rechargingLocation;
    private int idleCount;
    private int batteryCapacity;
    private int batteryLevel;
    private int wsCharged;
    private int chargesCount;
    private float chargesCost;
    private ArrayList<Dock> docks;

    /**
     * Constructor of class ElectricBike.
     * @param company The EB's operating company. Must not be null.
     * @param location The EB's starting {@link Location}. Must not be null.
     * @param targetLocation The EB's final destination {@link Location}. Must not be null.
     * @param id The identificator of the bike.
     * @param batteryCapacity The maximum capacity of the battery.
     * @throws NullPointerException If company, location, or targetLocation is null.
     */
    public ElectricBike(EBCompany company, Location location, Location targetLocation, String id, int batteryCapacity)
    {
<<<<<<< HEAD
        if (company == null || location == null || targetLocation == null) {
            throw new NullPointerException("company, location and targetLocation must not be null");
        }
        this.company = company;
        this.location = location;
        this.targetLocation = targetLocation;
        idBike = id;
        this.batteryCapacity = batteryCapacity;
        batteryLevel = batteryCapacity;
        rechargingLocation = null;
        idleCount = 0;
        wsCharged = 0;
        chargesCount = 0;
        chargesCost = 0.0f;
=======
        idBike = id;
        idleCount = 0;
        this.company = company;
        this.location = location;
        this.targetLocation = targetLocation;
        idleCount = 0;
        batteryCapacity = 0;
        batteryLevel = 0;
        wsCharged = 0;
        chargesCount = 0;
        chargesCost = 0;
>>>>>>> 0669083ce5571ead08185cba565b0f185f462318
        docks = new ArrayList<>();
    }

    /**
     * @return The id of the electric bike.
     */
    public String getIdBike()
    {
        return idBike;  
    }

    public int getBatteryCapacity(){
        return batteryCapacity;
    }
    
    public int getBatteryLevel(){
        return batteryLevel;
    }
    
    /**
     * Set the current location.
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
     * Get the final target location.
     * @return Where this bike is ultimately headed.
     */
    public Location getTargetLocation()
    {
         return targetLocation;
    }

    /**
     * Get the temporary recharging location.
     * @return The {@link Location} of the next {@link BikeStation} to visit, or null if no recharge is planned.
     */
    public Location getRechargingLocation()
    {
        return rechargingLocation;
    }


    /**
     * Set the required final target location.
     * @param location Where to go. Must not be null.
     * @throws NullPointerException If location is null.
     */
    public void setTargetLocation(Location location)
    {
         if (location == null) {
            throw new NullPointerException("Target location must not be null");
        }
        this.targetLocation = location;
    }
    /**
     * Calculates the optimal route for the bike. 
     * If there isn't enough battery to reach the target, it attempts to find an intermediate 
     * {@link BikeStation} and sets it as the {@code rechargingLocation}.
     */
    public void calculateRoute()
    {
       if (enoughBattery()) {
            rechargingLocation = null;
        }
        else {
            calculateRechargingPosition();
        }
    }

    /**
     * Gets a string representation of the planned route, including the recharging stop if one exists.
     * @return A string showing the route: {@code currentLocation -> [rechargingLocation ->] targetLocation}.
     */
    public String getStringRoute()
    {
       //TODO: Complete this code
       return null;
    }

    /**
     * Checks if the current battery level is sufficient to cover a given distance.
     * //TODO complete the JavaDoc comments
     */
    public boolean enoughBattery(   )
    {
      return distanceToTheTargetLocation() * MOVING_COST <= batteryLevel;
    }

    /**
     * Determines the optimal intermediate {@link BikeStation} to visit for recharging
     * if the bike cannot reach the final target directly.
     * Sets {@code rechargingLocation} to the chosen station's location.
     */
    public void calculateRechargingPosition()
    {
        rechargingLocation = null;
        int bestDistance = Integer.MAX_VALUE;
        Iterator<BikeStation> it = company.getStations().iterator();
        while (it.hasNext()) {
            Location stationLocation = it.next().getLocation();
            boolean reachable = location.distance(stationLocation) * MOVING_COST <= batteryLevel;
            if (!stationLocation.equals(location) && reachable) {
                int total = location.distance(stationLocation) + stationLocation.distance(targetLocation);
                if (total < bestDistance) {
                    bestDistance = total;
                    rechargingLocation = stationLocation;
                }
            }
        }
    }

    /**
     * Get the Manhattan-like distance to the final target location from the current location.
     * @return The distance to the target location.
     */
    public int distanceToTheTargetLocation()
    {
        return location.distance(targetLocation);   
    }
    
    
    
    /**
     * Simulates the recharging process when the bike arrives at a {@code rechargingLocation}.
     * The battery is charged to full capacity, the cost is calculated, and the route is recalculated.
     * //TODO complete the JavaDoc comments
     */
    public void recharge()
    {
<<<<<<< HEAD
        BikeStation bikeStation = company.getBikeStation(location);
        if (bikeStation != null && bikeStation.getFreeDock() != null) {
            Dock dock = bikeStation.getFreeDock();
            int whCharged = batteryCapacity - batteryLevel;
            incrementChargesCost(dock.recharge(this));
            docks.add(dock);
            incrementCharges();
            wsCharged += whCharged;
            batteryLevel = batteryCapacity;
            calculateRoute();
        }
    }
=======
        BikeStation bikeStation = new BikeStation();
        bikeStation = company.getBikeStation(location);
        if (bikeStation != null && bikeStation.getFreeDock() != null){
            Dock dock = bikeStation.getFreeDock();
            int whToRecharge = batteryCapacity - batteryLevel;
            
            chargesCost += dock.recharge(); 
        }
    } 
>>>>>>> 0669083ce5571ead08185cba565b0f185f462318

    /**
     * Increments the count of recharges performed by this bike.
     * @param cost The cost (€) of the last recharge.
     */
    public void incrementCharges()
    {
        chargesCount++;
    }

    /**
     * Adds a cost amount to the total charges cost.
     * //TODO complete the JavaDoc comments
     */
    public void incrementChargesCost(float cost)
    {
        chargesCost += cost;
    }   

    /**
     * Carries out a single step of the bike's actions.
     * Moves one step towards the target (recharging or final) or stays idle.
     * //TODO complete the JavaDoc comments
     */
    public void act(   )
    {
       //TODO: Complete this code 
    }

    /**
     * Reduces the battery level by the cost of one movement step (defined in {@link EBCompany#MOVINGCOST}).
     * Ensures the battery level does not go below zero.
     */
    public void reduceBatteryLevel(){
        batteryLevel = batteryLevel - MOVING_COST;

    }

    /**
     * Returns a detailed string representation of the electric bike.
     * @return A string containing the bike's name, plate, battery info, charge counts, costs, idle count, and route.
     */
    @Override
    public String toString(){
        //TODO: Complete this code 
        return null;
    }

   
}
