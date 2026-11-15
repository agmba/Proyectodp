import java.util.*;

/**
 * Compares two {@link BikeStation} objects by the number of bikes recharged
 * in their docks, in descending order. In case of a tie, by their ID in
 * ascending order (using {@link ComparatorBikeStationId}).
 * @author Álvaro Gómez Martín, Javier Ritoré Durán, Daniel Fernández Martín
 * @version 2026.11.15
 */
public class ComparatorBikeStationNumberRecharged implements Comparator<BikeStation>
{
    /**
     * Compares its two {@link BikeStation} arguments for order.
     * @param st1 The first bike station to be compared.
     * @param st2 The second bike station to be compared.
     * @return A negative integer, zero, or a positive integer as the first station
     * goes before, at the same position as, or after the second.
     */
    public int compare(BikeStation st1, BikeStation st2)
    {
        int result = Integer.compare(st2.getNumberRecharged(), st1.getNumberRecharged());
        if (result == 0) {
            result = new ComparatorBikeStationId().compare(st1, st2);
        }
        return result;
    }
}