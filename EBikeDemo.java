import java.util.*;

/**
 * Provides a simple demonstration and simulation environment for the 
 * Electric Bike (EB) and Bike Station model.
 * It sets up the environment and runs the simulation for a fixed number of steps.
 *
 * Scenarios: 
 * <ul>
 * <li>Demo SIMPLE (demo=DemoType.SIMPLE): Two bikes are created.</li>
 * <li>Demo MEDIUM (demo=DemoType.MEDIUM): Five bikes are created.</li>
 * <li>Demo ADVANCED(demo=DemoType.ADVANCED): Eight bikes are created.</li>
 * </ul>
 * @author DP Clasess
 * @version 2026
 */
public class EBikeDemo
{
    /** The maximum X coordinate for the grid (number of rows). */
    public static final int MAXX = 20; 
    /** The maximum Y coordinate for the grid (number of columns). */
    public static final int MAXY = 20; 
    /** The maximum number of turns. */
    public static final int MAXSTEPS = 50;

    /** The company managing the EBs and Bike stations. */
    private EBCompany company;
    /** The simulation's bikes; they are the electric bikes of the company. */
    private List<ElectricBike> bikes; 
    /** The bike stations available in the city. */
    private List<BikeStation> stations;
    /** Constant for selecting the demo scenario, using the {@link DemoType} enumeration. */
    private static final DemoType DEMO = DemoType.SIMPLE;

    /**
     * Constructor for objects of class EBikeDemo.
     * Initializes the company, bikes, and stations lists and calls {@code reset()}.
     */
    public EBikeDemo()
    {
        company = new EBCompany("EBBike Cáceres");
        bikes = new ArrayList<>();
        stations = new ArrayList<>();
        reset();
    }

    /**
     * Run the demo for a fixed number of steps (MAXSTEPS) and show the final information.
     */
    public void run()
    {
        for (int step = 0; step < MAXSTEPS; step++) {
            step(step);
        }
        showFinalInfo();
    }

    /**
     * Run the demo for one step by requesting all bikes to act, in ascending order of idBike.
     * @param step The current step number of the simulation.
     */
    public void step(int step)
    {
        for (ElectricBike bike : bikes) {
            bike.act(step);
            System.out.println("(step: " + step + " - " + bike + ")");
        }
    }

    /**
     * Reset the demo to a starting point.
     * Clears existing bikes and stations, resets the company, re-creates
     * the initial setup and shows the initial information.
     */
    public void reset()
    {
        bikes.clear();
        stations.clear();
        company.reset();
        createElectricBikes();
        createStations(); 
        createDocks();
        configureRoutes();
        showInitialInfo();
    }

    /**
     * Creates the {@link ElectricBike}s, assigns them starting and target {@link Location}s,
     * and adds them to the company. The bikes list is sorted by idBike.
     */
    private void createElectricBikes()
    {
        Location[] locations = {new Location(10, 13), new Location(8, 4), new Location(8, 4), new Location(15, 10), 
                                new Location(1, 1), new Location(2, 2), new Location(11, 13), new Location(14, 16)};
        Location[] targetLocations = {new Location(1, 1), new Location(19, 19), new Location(12, 17), new Location(4, 4), 
                                      new Location(1, 10), new Location(5, 5), new Location(8, 7), new Location(19, 19)};

        for (int i = 0; i < DEMO.getNumBikesToCreate(); i++) {
            ElectricBike eb = new ElectricBike(company, locations[i], targetLocations[i], "EBCC" + i, (i + 1) * 15);
            bikes.add(eb);
            company.addElectricBike(eb);
        }
        Collections.sort(bikes, new ComparatorEBIdBike());
    }

    /**
     * Creates predefined {@link BikeStation}s and adds them to the company.
     * The stations list is sorted by ID.
     */
    private void createStations()
    {
        Location[] locations = {new Location(10, 5), new Location(10, 11), new Location(14, 16), new Location(8, 4)};

        for (int i = 0; i < DEMO.getNumStationsToCreate(); i++) {
            BikeStation station = new BikeStation("CC0" + i, locations[i]);
            stations.add(station);
            company.addBikeStation(station);
        }
        Collections.sort(stations, new ComparatorBikeStationId());
    }

    /**
     * Creates a fixed number of {@link Dock} units for each {@link BikeStation}.
     * The docks are kept ordered inside each station by {@code addDock}.
     */
    private void createDocks()
    {
        for (BikeStation station : stations) {
            for (int i = 0; i < DEMO.getNumDocksToCreate(); i++) {
                Dock dock = new Dock(station.getId() + "_00" + i, (i + 1) * 20, (i + 1) * 0.20f);
                station.addDock(dock);
            }
        }    
    }

    /**
     * Instructs each {@link ElectricBike} to calculate its initial route.
     * This determines if an intermediate recharging stop is necessary.
     */
    private void configureRoutes()
    {
        for (ElectricBike bike : bikes) {
            bike.calculateRoute();
        }
    }

    /**
     * Displays the initial information for the simulation, including 
     * the details of all {@link ElectricBike}s and {@link BikeStation}s with their docks.
     */
    private void showInitialInfo()
    {
        System.out.println("( Compañía " + company.getName() + " )");
        System.out.println("(-------------------)");
        System.out.println("( Electric Bikes )");
        System.out.println("(-------------------)");
        for (ElectricBike bike : bikes) {
            System.out.println("(" + bike + ")");
        }

        System.out.println("(-------------------)");
        System.out.println("( Bike Stations )");
        System.out.println("(-------------------)");
        for (BikeStation station : stations) {
            System.out.println("(" + station + ")");
            for (Dock dock : station.getdocks()) {
                System.out.println("(" + dock + ")");
            }
        }

        System.out.println("(------------------)");
        System.out.println("( Simulation start )");
        System.out.println("(------------------)");
    }

    /**
     * Displays the final information after the simulation has run.
     * Bikes are sorted by arrival step (and idBike on ties).
     * Stations are sorted by number of recharged bikes descending (and id on ties).
     */
    private void showFinalInfo()
    {
        System.out.println("(-------------------)");
        System.out.println("( Final information )");        
        System.out.println("(-------------------)");

        System.out.println("(-------------------)");
        System.out.println("( Electric Bikes )");
        System.out.println("(-------------------)");
        List<ElectricBike> finalBikes = new ArrayList<>(bikes);
        Collections.sort(finalBikes, new ComparatorEBArrivingStep());
        for (ElectricBike bike : finalBikes) {
            System.out.println("(" + bike + ")");
            for (Dock dock : bike.getDocks()) {
                System.out.println("(Dock: " + dock.getIdDock() + ")");
            }
        }

        System.out.println("(-------------------)");
        System.out.println("( Bike Stations )");
        System.out.println("(-------------------)");
        List<BikeStation> finalStations = new ArrayList<>(stations);
        Collections.sort(finalStations, new ComparatorBikeStationNumberRecharged());
        for (BikeStation station : finalStations) {
            System.out.println("(" + station + ")");
            for (Dock dock : station.getdocks()) {
                System.out.println("(" + dock + ")");
                for (ElectricBike bike : dock.getBikesRecharged()) {
                    System.out.println("(" + bike + ")");
                }
            }
        }
    }

    /**
     * The main entry point for running the EBikeDemo simulation.
     * Creates an instance of {@code EBikeDemo} and starts the simulation.
     */
    public static void main()
    {
        EBikeDemo demo = new EBikeDemo();
        demo.run();
    }
}
