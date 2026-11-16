import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test implementation of the {@link Dock} class.
 * @author Álvaro Gómez Martín, Javier Ritoré Durán, Daniel Fernández Martín
 * @version 2026.11.15
 */
public class DockTest
{
    private static final float DELTA = 0.001f;

    private EBCompany company;
    private Dock dock;
    private ElectricBike bike;

    /**
     * Default constructor for test class DockTest.
     */
    public DockTest()
    {
    }

    /**
     * Sets up the test fixture: a dock of 80wh and 0.8€/wh and a bike
     * of 15wh that has spent 10wh (two steps), so it needs 10wh to be full.
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        company = new EBCompany("Test company");
        dock = new Dock("CC01_003", 80, 0.8f);
        bike = new ElectricBike(company, new Location(10, 11), new Location(1, 1), "EBCC0", 15);
        bike.reduceBatteryLevel();
        bike.reduceBatteryLevel();
    }

    /**
     * Tears down the test fixture.
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
        company = null;
        dock = null;
        bike = null;
    }

    /**
     * Test the {@code recharge} method: it must return the cost of the recharge
     * (wh needed multiplied by the fee), add it to the amount collected and
     * include the bike in the list of recharged bikes.
     */
    @Test
    public void testRecharge()
    {
        float cost = dock.recharge(bike);
        assertEquals(8.0f, cost, DELTA);
        assertEquals(8.0f, dock.getAmountCollected(), DELTA);
        assertEquals(1, dock.getNumberEVRecharged());
        assertTrue(dock.getBikesRecharged().contains(bike));
    }

    /**
     * Test the {@code recharge} method with a null bike: nothing must change.
     */
    @Test
    public void testRechargeNullBike()
    {
        assertEquals(0.0f, dock.recharge(null), DELTA);
        assertEquals(0.0f, dock.getAmountCollected(), DELTA);
        assertEquals(0, dock.getNumberEVRecharged());
    }

    /**
     * Test the {@code toString} method, which must follow the output template.
     */
    @Test
    public void testToString()
    {
        assertEquals("Dock: CC01_003, 80wh, 0.8€, 0, 0.0€", dock.toString());
    }
}
