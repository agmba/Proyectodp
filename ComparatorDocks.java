import java.util.*;

/**
 * Compares two {@link Dock} objects by their charging speed in descending order.
 * In case of a tie, by their charging fee in ascending order, and if there is
 * still a tie, by their ID in ascending order (using {@link ComparatorDocksId}).
 * This is the order in which the docks are kept inside a {@link BikeStation}.
 * @author Álvaro Gómez Martín, Javier Ritoré Durán, Daniel Fernández Martín
 * @version 2026.11.15
 */
public class ComparatorDocks implements Comparator<Dock>
{
    /**
     * Compares its two {@link Dock} arguments for order.
     * @param d1 The first dock to be compared.
     * @param d2 The second dock to be compared.
     * @return A negative integer, zero, or a positive integer as the first dock
     * goes before, at the same position as, or after the second.
     */
    public int compare(Dock d1, Dock d2)
    {
        int result = Integer.compare(d2.getChargingSpeed(), d1.getChargingSpeed());
        if (result == 0) {
            result = Float.compare(d1.getChargingFee(), d2.getChargingFee());
        }
        if (result == 0) {
            result = new ComparatorDocksId().compare(d1, d2);
        }
        return result;
    }
}