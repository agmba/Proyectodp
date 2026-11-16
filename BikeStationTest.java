import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test implementation of the {@link BikeStation} class.
 * @author Álvaro Gómez Martín, Javier Ritoré Durán, Daniel Fernández Martín
 * @version 2026.11.15
 */
public class BikeStationTest
{
    private BikeStation station;
    private Dock slowDock;
    private Dock fastDock;

    /**
     * Default constructor for test class BikeStationTest.
     */
    public BikeStationTest()
    {
    }

    /**
     * Sets up the test fixture: an empty station and two docks with different speeds.
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        station = new BikeStation("CC01", new Location(10, 11));
        slowDock = new Dock("CC01_000", 20, 0.2f);
        fastDock = new Dock("CC01_003", 80, 0.8f);
    }

    /**
     * Tears down the test fixture.
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
        station = null;
        slowDock = null;
        fastDock = null;
    }

    /**
     * Test the {@code addDock} method: the docks must be added to the station
     * and kept ordered by charging speed in descending order.
     */
    @Test
    public void testAddDock()
    {
        assertEquals(0, station.getdocks().size());
        station.addDock(slowDock);
        assertEquals(1, station.getdocks().size());
        station.addDock(fastDock);
        assertEquals(2, station.getdocks().size());
        assertEquals(fastDock, station.getdocks().get(0));
        assertEquals(slowDock, station.getdocks().get(1));
    }

    /**
     * Test that the {@code addDock} method does not accept a null dock.
     */
    @Test(expected = NullPointerException.class)
    public void testAddNullDock()
    {
        station.addDock(null);
    }

    /**
     * Test the {@code getFreeDock} method: it must return the first free dock,
     * or null if the station has no docks.
     */
    @Test
    public void testGetFreeDock()
    {
        assertNull(station.getFreeDock());
        station.addDock(slowDock);
        station.addDock(fastDock);
        assertEquals(fastDock, station.getFreeDock());
    }

    /**
     * Test the {@code toString} method, which must follow the output template.
     */
    @Test
    public void testToString()
    {
        assertEquals("BikeStation: CC01, 0, 10-11", station.toString());
    }
}
