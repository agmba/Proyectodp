import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test implementation of the {@link ElectricBike} class.
 * The fixture reproduces the SIMPLE scenario of the project statement:
 * four stations with four docks each, and the bikes EBCC0 and EBCC1.
 * @author DP classes
 * @version 2026.11.15
 */
public class ElectricBikeTest
{
    private static final float DELTA = 0.001f;

    private EBCompany company;
    private ElectricBike bike0;
    private ElectricBike bike1;
    private ElectricBike nearBike;

    /**
     * Default constructor for test class ElectricBikeTest.
     */
    public ElectricBikeTest()
    {
    }

    /**
     * Sets up the test fixture: the company with the four stations of the demo,
     * each one with its four docks, and three bikes.
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        company = new EBCompany("EBBike Cáceres");
        Location[] stationLocations = {new Location(10, 5), new Location(10, 11),
                                       new Location(14, 16), new Location(8, 4)};
        for (int i = 0; i < stationLocations.length; i++) {
            BikeStation station = new BikeStation("CC0" + i, stationLocations[i]);
            for (int j = 0; j < 4; j++) {
                station.addDock(new Dock(station.getId() + "_00" + j, (j + 1) * 20, (j + 1) * 0.20f));
            }
            company.addBikeStation(station);
        }
        bike0 = new ElectricBike(company, new Location(10, 13), new Location(1, 1), "EBCC0", 15);
        bike1 = new ElectricBike(company, new Location(8, 4), new Location(19, 19), "EBCC1", 30);
        nearBike = new ElectricBike(company, new Location(0, 0), new Location(0, 3), "EBCC9", 15);
        company.addElectricBike(bike0);
        company.addElectricBike(bike1);
        company.addElectricBike(nearBike);
    }

    /**
     * Tears down the test fixture.
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
        company = null;
        bike0 = null;
        bike1 = null;
        nearBike = null;
    }

    /**
     * Test the creation of {@link ElectricBike} objects: all the fields must be
     * correctly initialized and the battery must start full.
     */
    @Test
    public void testCreation()
    {
        assertEquals("EBCC0", bike0.getIdBike());
        assertEquals(company, bike0.getCompany());
        assertEquals(new Location(10, 13), bike0.getLocation());
        assertEquals(new Location(1, 1), bike0.getTargetLocation());
        assertNull(bike0.getRechargingLocation());
        assertEquals(15, bike0.getBatteryCapacity());
        assertEquals(15, bike0.getBatteryLevel());
        assertEquals(0, bike0.getIdleCount());
        assertEquals(0, bike0.getWsCharged());
        assertEquals(0, bike0.getChargesCount());
        assertEquals(0.0f, bike0.getChargesCost(), DELTA);
        assertEquals(Integer.MAX_VALUE, bike0.getArrivalStep());
        assertTrue(bike0.getDocks().isEmpty());
    }

    /**
     * Test that a bike cannot be created with a null location.
     */
    @Test(expected = NullPointerException.class)
    public void testCreationWithNullLocation()
    {
        new ElectricBike(company, null, new Location(1, 1), "EBCC8", 15);
    }

    /**
     * Test the {@code enoughBattery} method: each step consumes 5wh.
     */
    @Test
    public void testEnoughBattery()
    {
        assertFalse(bike0.enoughBattery());
        assertFalse(bike1.enoughBattery());
        assertTrue(nearBike.enoughBattery());
        nearBike.reduceBatteryLevel();
        assertFalse(nearBike.enoughBattery());
    }

    /**
     * Test the {@code calculateRoute} method: a bike without enough battery must go
     * to the station that minimizes the whole route, and a bike with enough battery
     * must go directly to its target.
     */
    @Test
    public void testCalculateRoute()
    {
        bike0.calculateRoute();
        assertEquals(new Location(10, 11), bike0.getRechargingLocation());
        bike1.calculateRoute();
        assertEquals(new Location(10, 5), bike1.getRechargingLocation());
        nearBike.calculateRoute();
        assertNull(nearBike.getRechargingLocation());
    }

    /**
     * Test the {@code recharge} method: the bike must be recharged in the first
     * free dock of the station, filling the battery and updating the cost,
     * the counters and the list of docks.
     */
    @Test
    public void testRecharge()
    {
        bike0.setLocation(new Location(10, 11));
        bike0.reduceBatteryLevel();
        bike0.reduceBatteryLevel();
        bike0.recharge();
        assertEquals(15, bike0.getBatteryLevel());
        assertEquals(1, bike0.getChargesCount());
        assertEquals(10, bike0.getWsCharged());
        assertEquals(8.0f, bike0.getChargesCost(), DELTA);
        assertEquals(1, bike0.getDocks().size());
        assertEquals("CC01_003", bike0.getDocks().get(0).getIdDock());
        assertTrue(bike0.getDocks().get(0).getBikesRecharged().contains(bike0));
    }

    /**
     * Test the {@code act} method: the bike must move one step towards its
     * recharging station and consume 5wh, as in step 0 of the statement example.
     */
    @Test
    public void testAct()
    {
        bike0.calculateRoute();
        bike0.act(0);
        assertEquals(new Location(10, 12), bike0.getLocation());
        assertEquals(10, bike0.getBatteryLevel());
        assertEquals(0, bike0.getIdleCount());
    }
}
