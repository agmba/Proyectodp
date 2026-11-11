import java.util.List;
import java.util.ArrayList;

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
    private int arrivalStep;
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
        arrivalStep = Integer.MAX_VALUE;
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

    /**
     * @return The maximum capacity of the battery in wh.
     */
    public int getBatteryCapacity()
    {
        return batteryCapacity;
    }

    /**
     * @return The current battery level in wh.
     */
    public int getBatteryLevel()
    {
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
     * Set the required final target location.
     * @param location Where to go. Must not be null.
     * @throws NullPointerException If location is null.
     */
    public void setTargetLocation(Location location)
    {
        if (location == null) {
            throw new NullPointerException("Target location must not be null");
        }
        targetLocation = location;
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
     * @return The step in which the bike reached its target, or Integer.MAX_VALUE if it has not arrived yet.
     */
    public int getArrivalStep()
    {
        return arrivalStep;
    }

    /**
     * @return The list of docks where the bike has recharged, in chronological order.
     */
    public List<Dock> getDocks()
    {
        return docks;
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
        String route = location + " -> ";
        if (rechargingLocation != null) {
            route += rechargingLocation + " -> ";
        }
        route += targetLocation;
        return route;
    }

    /**
     * Checks if the current battery level is enough to reach the final target location,
     * knowing that each step consumes {@link #MOVING_COST} wh.
     * @return true if the bike can reach its target without recharging, false otherwise.
     */
    public boolean enoughBattery()
    {
        return distanceToTheTargetLocation() * MOVING_COST <= batteryLevel;
    }

    /**
     * Determines the optimal intermediate {@link BikeStation} to visit for recharging
     * if the bike cannot reach the final target directly. The chosen station is the one,
     * reachable with the current battery and different from the current location, that
     * minimizes the distance to the station plus the distance from the station to the target.
     * In case of a tie, the first station analysed is kept.
     * Sets {@code rechargingLocation} to the chosen station's location, or null if none is reachable.
     */
    public void calculateRechargingPosition()
    {
        rechargingLocation = null;
        int bestDistance = Integer.MAX_VALUE;
        for (BikeStation station : company.getStations()) {
            Location stationLocation = station.getLocation();
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
     * Get the Chebyshev distance to the final target location from the current location.
     * @return The distance (number of steps) to the target location.
     */
    public int distanceToTheTargetLocation()
    {
        return location.distance(targetLocation);
    }
<<<<<<< HEAD
=======
    
    
    
    /**
     * Simulates the recharging process when the bike arrives at a {@code rechargingLocation}.
     * The battery is charged to full capacity, the cost is calculated, and the route is recalculated.
     * //TODO complete the JavaDoc comments
     */
    public void recharge(   )
    {
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
     * Recharges the bike at the station where it is located.
     * Uses the first free dock, charges the battery to full capacity,
     * updates costs and counters, and recalculates the route.
     * If there is no station or no free dock at the current location, nothing happens.
     */
    public void recharge()
    {
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

    /**
     * Increments by one the count of recharges performed by this bike.
     */
    public void incrementCharges()
    {
        chargesCount++;
    }

    /**
     * Adds a cost amount to the total charges cost.
     * @param cost The cost (€) of the last recharge.
     */
    public void incrementChargesCost(float cost)
    {
        chargesCost += cost;
    }

    /**
     * Carries out a single step of the bike's actions.
     * Moves one step towards the recharging location (if any) or the final target.
     * If it is already at the target, or it cannot reach where it is going, it stays idle.
     * When it arrives at the recharging station it recharges, and when it arrives
     * at the target it registers the arrival step.
     * @param step The current step of the simulation.
     */
    public void act(int step)
    {
        Location destination = rechargingLocation;
        if (destination == null) {
            destination = targetLocation;
        }

        if (location.equals(targetLocation) || location.distance(destination) * MOVING_COST > batteryLevel) {
            idleCount++;
        }
        else {
            location = location.nextLocation(destination);
            reduceBatteryLevel();

            if (location.equals(targetLocation)) {
                arrivalStep = step;
                rechargingLocation = null;
                System.out.println("(step: " + step + " - ElectricBike: " + idBike + " at target destination ********)");
            }
            else if (location.equals(rechargingLocation)) {
                int levelBefore = batteryLevel;
                int chargesBefore = chargesCount;
                float costBefore = chargesCost;
                recharge();
                if (chargesCount > chargesBefore) {
                    Dock lastDock = docks.get(docks.size() - 1);
                    System.out.println("(step: " + step + " - ElectricBike: " + idBike + " recharges: "
                                       + (batteryCapacity - levelBefore) + "wh at dock: " + lastDock.getIdDock()
                                       + " with cost: " + (chargesCost - costBefore) + "€ ********)");
                }
            }
        }
    }

    /**
     * Reduces the battery level by the cost of one movement step ({@link #MOVING_COST}).
     */
    public void reduceBatteryLevel()
    {
        batteryLevel = batteryLevel - MOVING_COST;
    }

    /**
     * Returns a detailed string representation of the electric bike.
     * @return A string with id, battery capacity and level, charges count, cost, idle count,
     * current location, recharging location (if any) and target location.
     */
    @Override
    public String toString()
    {
        String text = "ElectricBike: " + idBike + ", " + batteryCapacity + "wh, " + batteryLevel + "wh, "
                      + chargesCount + ", " + chargesCost + "€, " + idleCount + ", " + location + ", ";
        if (rechargingLocation != null) {
            text += rechargingLocation + ", ";
        }
        text += targetLocation;
        return text;
    }
}
