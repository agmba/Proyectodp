import java.util.ArrayList;
import java.util.List;

/**
 * Model a dock unit within a {@link BikeStation}.
 * It tracks its charging capabilities, fee, and the electric 
 * bikes it has recharged.
 * @author Álvaro Gómez Martín, Javier Ritoré Durán, Daniel Fernández Martín
 * @version 2026.10.13
 */
public class Dock
{
    private String idDock;
    private int chargingSpeed;
    private float chargingFee;
    private ArrayList<ElectricBike> bikesRecharged;
    private float amountCollected;
    private boolean free;

    /**
     * Constructor for objects of class Dock.
     * The dock starts free, with no bikes recharged and no money collected.
     * @param id The identifier of the dock (station id + "_" + 
     * two digits, e.g. CC01_02).
     * @param speed The charging speed of the dock in wh.
     * @param fee The charging fee of the dock in € per wh.
     */
    public Dock(String id, int speed, float fee)
    {
        idDock = id;
        chargingSpeed = speed;
        chargingFee = fee;
        bikesRecharged = new ArrayList<>();
        amountCollected = 0.0f;
        free = true;
    }

    /**
     * @return The identifier of the dock.
     */
    public String getIdDock()
    {
        return idDock;
    }

    /**
     * @return The charging speed of the dock in wh.
     */
    public int getChargingSpeed()
    {
        return chargingSpeed;
    }

    /**
     * @return The charging fee of the dock in € per wh.
     */
    public float getChargingFee()
    {
        return chargingFee;
    }

    /**
     * @return The total amount of money (€) collected by this dock.
     */
    public float getAmountCollected()
    {
        return amountCollected;
    }

    /**
     * @return The list of {@link ElectricBike}s recharged in this dock,
     * in chronological order.
     */
    public List<ElectricBike> getBikesRecharged()
    {
        return bikesRecharged;
    }

    /**
     * @return The total number of {@link ElectricBike}s that have
     * been recharged by this dock.
     */
    public int getNumberEVRecharged()
    {
        return bikesRecharged.size();
    }

    /**
     * @return true if the dock is free to be used, false if it is occupied.
     */
    public boolean getFree()
    {
        return free;
    }

    /**
     * Sets the dock as free, so that it can be used by another bike.
     */
    public void setFree()
    {
        free = true;
    }

    /**
     * Adds a new electric bike to the collection of recharged bikes.
     * @param bike The bike that has been recharged. If it is null, 
     * it is not added.
     */
    public void addEVBike(ElectricBike bike)
    {
        if (bike != null) {
            bikesRecharged.add(bike);
        }
    }

    /**
     * Increases the amount of money collected by this dock.
     * @param amount The amount (€) to add. Only positive amounts are added.
     */
    public void incAmountCollected(float amount)
    {
        if (amount > 0) {
            amountCollected += amount;
        }
    }

    /**
     * Simulates the charging process for an {@link ElectricBike}.
     * The cost is the wh needed to fill the battery multiplied by the
     * charging fee.
     * Increases the amount collected and registers the bike as recharged.
     * @param bike The bike to recharge.
     * @return The cost (€) of the recharge, or 0 if the bike is null.
     */
    public float recharge(ElectricBike bike)
    {
        float cost = 0.0f;
        if (bike != null) {
            int whNeeded = bike.getBatteryCapacity() - bike.getBatteryLevel();
            cost = whNeeded * chargingFee;
            incAmountCollected(cost);
            addEVBike(bike);
        }
        return cost;
    }

    /**
     * Returns a string representation of the dock with the 
     * format of the simulation output.
     * @return A string with the id, charging speed, fee, 
     * number of bikes recharged and amount collected,
     * e.g. {@code Dock: CC00_003, 80wh, 0.8€, 0, 0.0€}.
     */
    @Override
    public String toString()
    {
        return "Dock: " + idDock + ", " + chargingSpeed + "wh, " + chargingFee + "€, "
               + getNumberEVRecharged() + ", " + amountCollected + "€";
    }
}
