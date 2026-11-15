import java.util.*;
/**
 * Compares two {@link ElectricBike} objects by the step in which they arrived
 * at their target location, in ascending order. Bikes that have not arrived go last.
 * In case of a tie, by their id in ascending order (using {@link ComparatorEBIdBike}).
 * @author Álvaro Gómez Martín, Javier Ritoré Durán, Daniel Fernández Martín
 * @version 2026.11.15
 */
public class ComparatorEBArrivingStep implements Comparator<ElectricBike>
{
    /**
     * Compares its two {@link ElectricBike} arguments for order.
     * @param eb1 The first electric bike to be compared.
     * @param eb2 The second electric bike to be compared.
     * @return A negative integer, zero, or a positive integer as the first bike
     * arrived before, at the same step as, or after the second.
     */
    public int compare(ElectricBike eb1, ElectricBike eb2)
    {
        int result = Integer.compare(eb1.getArrivalStep(), eb2.getArrivalStep());
        if (result == 0) {
            result = new ComparatorEBIdBike().compare(eb1, eb2);
        }
        return result;
    }
}