package aim4.map.cpm.components;

import aim4.map.SpawnPoint;
import aim4.map.lane.Lane;
import aim4.vehicle.VehicleSpec;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

/**
 * A SpawnPoint for CPM simulations.
 */
public class CPMSpawnPoint extends SpawnPoint {

    /////////////////////////////////
    // NESTED CLASSES
    /////////////////////////////////

    /** The specification of a spawn */
    public static class CPMSpawnSpec extends SpawnSpec {

        double parkingTime;

        /**
         * Create a spawn specification.
         *
         * @param spawnTime       the spawn time
         * @param vehicleSpec     the vehicle specification
         */
        public CPMSpawnSpec(double spawnTime, VehicleSpec vehicleSpec, double parkingTime) {
            super(spawnTime, vehicleSpec);
            this.parkingTime = parkingTime;
        }

        public double getParkingTime() { return parkingTime; }
    }

    /**
     * The interface of the spawn specification generator.
     */
    public static interface CPMSpawnSpecGenerator {
        List<CPMSpawnSpec> act(CPMSpawnPoint spawnPoint, double timestep);
        int getNumberOfVehiclesLeftToSpawn();
        double generateParkingTime();
    }

    /////////////////////////////////
    // PRIVATE FIELDS
    /////////////////////////////////

    private CPMSpawnSpecGenerator vehicleSpecChooser;

    /////////////////////////////////
    // CONSTRUCTORS
    /////////////////////////////////

    /**
     * Create a spawn point with a vehicleSpecChooser.
     *
     * @param currentTime   the current time
     * @param pos           the initial position
     * @param heading       the initial heading
     * @param steeringAngle the initial steering angle
     * @param acceleration  the initial acceleration
     * @param lane          the lane
     * @param noVehicleZone the no vehicle zone
     * @param vehicleSpecChooser  the vehicle spec chooser
     */
    public CPMSpawnPoint(double currentTime, Point2D pos,
                         double heading, double steeringAngle,
                         double acceleration, Lane lane,
                         Rectangle2D noVehicleZone,
                         CPMSpawnSpecGenerator vehicleSpecChooser) {
        super(currentTime, pos, heading, steeringAngle,
                acceleration, lane, noVehicleZone);
        this.vehicleSpecChooser = vehicleSpecChooser;
    }

    /**
     * Create a spawn point without a vehicleSpecChooser.
     *
     * @param currentTime         the current time
     * @param pos                 the initial position
     * @param heading             the initial heading
     * @param steeringAngle       the initial steering angle
     * @param acceleration        the initial acceleration
     * @param lane                the lane
     * @param noVehicleZone       the no vehicle zone
     */
    public CPMSpawnPoint(double currentTime,
                         Point2D pos,
                         double heading,
                         double steeringAngle,
                         double acceleration,
                         Lane lane,
                         Rectangle2D noVehicleZone) {
        super(currentTime, pos, heading, steeringAngle,
                acceleration, lane, noVehicleZone);
        this.vehicleSpecChooser = null;
    }

    /////////////////////////////////
    // PUBLIC METHODS
    /////////////////////////////////

    /**
     * Advance the time step.
     *
     * @param timeStep  the time step
     * @return The list of spawn spec generated in this time step
     */
    public List<CPMSpawnSpec> act(double timeStep) {
        if (vehicleSpecChooser == null) {
            throw new RuntimeException("The spawn point on the map doesn't have a SpawnSpecGenerator.");
        }
        List<CPMSpawnSpec> spawnSpecs = vehicleSpecChooser.act(this, timeStep);
        currentTime += timeStep;
        return spawnSpecs;
    }

    /**
     * Set the vehicle spec chooser.
     *
     * @param vehicleSpecChooser the vehicle spec chooser
     */
    public void setVehicleSpecChooser(CPMSpawnSpecGenerator vehicleSpecChooser) {
        this.vehicleSpecChooser = vehicleSpecChooser;
    }

    public CPMSpawnSpecGenerator getVehicleSpecChooser() { return vehicleSpecChooser; }
}