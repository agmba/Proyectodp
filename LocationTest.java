import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test implementation of the {@link Location} class.
 * Provides unit tests for methods like {@code distance()} and {@code nextLocation()}.
 * The fixture uses the locations of the example in the project statement (Figure 1).
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29
 * @version 2026.10.13 DP classes (adaptado a Java 8+)
 */
public class LocationTest
{
    private Location p0;
    private Location p1;
    private Location p2;
    private Location p3;
    private Location p4;
    private Location p5;
    private Location p6;

    /**
     * Default constructor for test class LocationTest.
     */
    public LocationTest()
    {
    }

    /**
     * Sets up the test fixture with the locations of the project statement.
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        p0 = new Location(0, 0);
        p1 = new Location(1, 5);
        p2 = new Location(6, 6);
        p3 = new Location(5, 2);
        p4 = new Location(3, 4);
        p5 = new Location(3, 2);
        p6 = new Location(6, 0);
    }

    /**
     * Tears down the test fixture.
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
        p0 = null;
        p1 = null;
        p2 = null;
        p3 = null;
        p4 = null;
        p5 = null;
        p6 = null;
    }

    /**
     * Test the {@code distance} method of the {@link Location} class.
     * Checks the examples of the statement, that the distance is symmetric
     * and that the distance from a location to itself is zero.
     */
    @Test
    public void testDistance()
    {
        assertEquals(5, p0.distance(p1));
        assertEquals(4, p2.distance(p3));
        assertEquals(4, p3.distance(p2));
        assertEquals(4, p0.distance(p4));
        assertEquals(0, p0.distance(p0));
    }

    /**
     * Test the {@code nextLocation} method when the destination is adjacent
     * (one step away in any direction): the next location must be the destination.
     */
    @Test
    public void testAdjacentLocations()
    {
        Location center = new Location(5, 5);
        Location[] neighbours = {new Location(4, 4), new Location(4, 5), new Location(4, 6),
                                 new Location(5, 4), new Location(5, 6),
                                 new Location(6, 4), new Location(6, 5), new Location(6, 6)};
        for (Location neighbour : neighbours) {
            assertEquals(1, center.distance(neighbour));
            assertEquals(neighbour, center.nextLocation(neighbour));
        }
    }

    /**
     * Test the {@code nextLocation} method when the destination is not adjacent
     * (more than one step away), using the paths of the statement examples.
     */
    @Test
    public void testNonAdjacentLocations()
    {
        Location step = p0.nextLocation(p1);
        assertEquals(new Location(1, 1), step);
        step = step.nextLocation(p1);
        assertEquals(new Location(1, 2), step);

        assertEquals(new Location(5, 5), p2.nextLocation(p3));

        step = p5.nextLocation(p6);
        assertEquals(new Location(4, 1), step);
        step = step.nextLocation(p6);
        assertEquals(new Location(5, 0), step);
        step = step.nextLocation(p6);
        assertEquals(p6, step);
    }
}
