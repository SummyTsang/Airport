package towersim.aircraft;

import towersim.tasks.TaskList;
import towersim.tasks.TaskType;
import towersim.util.EmergencyState;
import towersim.util.OccupancyLevel;
import towersim.util.Tickable;

/**
 * Represents an aircraft whose movement is managed by the system.
 */
public abstract class Aircraft
        extends Object
        implements OccupancyLevel, Tickable, EmergencyState {
    /**
     * unique callsign of aircraft
     */
    private String callsign;
    /**
     * characteristics that describe this aircraft
     */
    private AircraftCharacteristics characteristics;
    /**
     * task list to be used by aircraft
     */
    private TaskList tasks;
    /**
     * current amount of fuel onboard, in litres
     */
    private double fuelAmount;
    /**
     * Weight of a litre of aviation fuel, in kilograms
     */
    public static final double LITRE_OF_FUEL_WEIGHT = 0.8;
    /**
     * If emergency occurs
     */
    private boolean emergencyOccur;

    /**
     * Creates a new aircraft with the given callsign, task list, fuel
     * capacity and amount.
     *
     * @param callsign        the callsign of the aircraft
     * @param characteristics the characteristics of the aircraft
     * @param tasks           the tasks of the aircraft
     * @param fuelAmount      the fuelAmount of the aircraft
     */
    protected Aircraft(String callsign,
                       AircraftCharacteristics characteristics,
                       TaskList tasks, double fuelAmount) {
        this.callsign = callsign;
        this.characteristics = characteristics;
        this.tasks = tasks;
        this.fuelAmount = fuelAmount;
        if (this.fuelAmount < 0 || this.fuelAmount
                > getCharacteristics().fuelCapacity) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Returns the callsign of the aircraft.
     *
     * @return aircraft callsign
     */
    public String getCallsign() {
        return callsign;
    }

    /**
     * Returns the current amount of fuel onboard, in litres.
     *
     * @return current fuel amount, in litres
     */
    public double getFuelAmount() {
        return fuelAmount;
    }

    /**
     * Returns this aircraft's characteristics.
     *
     * @return aircraft characteristics
     */
    public AircraftCharacteristics getCharacteristics() {
        return characteristics;
    }

    /**
     * Returns the percentage of fuel remaining, rounded to the nearest whole
     * percentage, 0 to 100
     *
     * @return percentage of fuel remaining
     */
    public int getFuelPercentRemaining() {
        return Math.round(Math.round(100
                * fuelAmount / characteristics.fuelCapacity));
    }

    /**
     * Returns the total weight of the aircraft in its current state.
     *
     * @return total weight of aircraft in kilograms
     */
    public double getTotalWeight() {
        return characteristics.emptyWeight + fuelAmount * LITRE_OF_FUEL_WEIGHT;
    }

    /**
     * Returns the task list of this aircraft.
     *
     * @return task list of this aircraft
     */
    public TaskList getTaskList() {
        return tasks;
    }

    /**
     * Returns the number of ticks required to load the aircraft at the gate.
     *
     * @return time to load aircraft, in ticks
     */
    public abstract int getLoadingTime();

    /**
     * Updates the aircraft's state on each tick of the simulation.
     */
    public void tick() {
        if (tasks.getCurrentTask().getType() == TaskType.AWAY) {
            if (this.fuelAmount - characteristics.fuelCapacity * 0.1 <= 0) {
                this.fuelAmount = 0;
            } else {
                this.fuelAmount -= characteristics.fuelCapacity * 0.1;
            }
        }
        if (tasks.getCurrentTask().getType() == TaskType.LOAD) {
            if (this.fuelAmount
                    + characteristics.fuelCapacity / getLoadingTime()
                    >= characteristics.fuelCapacity) {
                this.fuelAmount = characteristics.fuelCapacity;
            } else {
                this.fuelAmount +=
                        characteristics.fuelCapacity / getLoadingTime();
            }
        }
    }

    /**
     * Returns the human-readable string representation of this aircraft.
     *
     * @return string representation of this aircraft
     */
    public String toString() {
        if (hasEmergency()) {
            return "" + characteristics.type + " " + callsign
                    + " " + characteristics.toString() + " "
                    + tasks.getCurrentTask().getType() + " (EMERGENCY)";
        } else {
            return "" + characteristics.type + " " + callsign
                    + " " + characteristics.toString() + " "
                    + tasks.getCurrentTask().getType();
        }
    }

    /**
     * Declares a state of emergency.
     */
    public void declareEmergency() {
        emergencyOccur = true;
    }

    /**
     * Clears any active state of emergency.
     */
    public void clearEmergency() {
        if (hasEmergency()) {
            emergencyOccur = false;
        }
    }

    /**
     * Returns whether or not a state of emergency is currently active.
     *
     * @return true if in emergency; false otherwise
     */
    public boolean hasEmergency() {
        if (emergencyOccur) {
            return true;
        }
        return false;
    }
}
