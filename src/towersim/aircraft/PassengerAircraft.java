package towersim.aircraft;

import towersim.tasks.TaskList;
import towersim.tasks.TaskType;

/**
 * Represents an aircraft capable of carrying passenger cargo.
 */
public class PassengerAircraft extends Aircraft {

    /**
     * Average weight of a single passenger including their baggage, in
     * kilograms
     */
    private int numPassengers;
    /**
     * the loading time of the aircraft
     */
    private int loadingTime;
    /**
     * Average weight of a single passenger including their baggage, in
     * kilograms
     */
    public static final double AVG_PASSENGER_WEIGHT = 90;

    /**
     * Creates a new passenger aircraft with the given callsign, task list,
     * fuel capacity, amount of fuel and number of passengers.
     *
     * @param callsign        the callsign of the aircraft
     * @param characteristics the characteristics of the aircraft
     * @param tasks           the tasks of the aircraft
     * @param fuelAmount      the fuelAmount of the aircraft
     * @param numPassengers   the number of passengers of the aircraft
     */
    public PassengerAircraft(String callsign,
                             AircraftCharacteristics characteristics,
                             TaskList tasks, double fuelAmount,
                             int numPassengers) {
        super(callsign, characteristics, tasks, fuelAmount);
        this.numPassengers = numPassengers;
        if (numPassengers < 0
                || numPassengers
                > this.getCharacteristics().passengerCapacity) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Returns the total weight of the aircraft in its current state.
     *
     * @return total weight of aircraft in kilograms
     */
    public double getTotalWeight() {
        int emptyWeight = this.getCharacteristics().emptyWeight;
        double fuelOnboard = this.getFuelAmount() * LITRE_OF_FUEL_WEIGHT;
        double passenger = this.numPassengers * AVG_PASSENGER_WEIGHT;
        return emptyWeight + fuelOnboard + passenger;
    }

    /**
     * Returns the number of ticks required to load the aircraft at the gate.
     *
     * @return loading time in ticks
     */
    public int getLoadingTime() {
        int passengerToBeLoaded =
                Math.round(
                        Math.round(this.getCharacteristics().passengerCapacity
                                * this.getTaskList().getCurrentTask()
                                .getLoadPercent() / 100.0));
        this.loadingTime =
                Math.round(Math.round(
                        Math.log(passengerToBeLoaded) / Math.log(10)));
        if (this.loadingTime < 1) {
            this.loadingTime = 1;
        }
        return this.loadingTime;
    }

    /**
     * Returns the ratio of passengers onboard to maximum passenger capacity
     * as a percentage between 0 and 100.
     *
     * @return occupancy level as a percentage
     */
    public int calculateOccupancyLevel() {
        return Math.round(Math.round(100.0 * this.numPassengers
                / this.getCharacteristics().passengerCapacity));
    }

    /**
     * Updates the aircraft's state on each tick of the simulation.
     */
    public void tick() {
        super.tick();
        if (this.getTaskList().getCurrentTask().getType() == TaskType.LOAD) {
            int passengerToBeLoaded = Math.round(
                    Math.round(this.getCharacteristics().passengerCapacity
                            * this.getTaskList().getCurrentTask()
                            .getLoadPercent() / 100.0));
            this.numPassengers +=
                    Math.round(passengerToBeLoaded / this.getLoadingTime());
            if (this.numPassengers
                    >= this.getCharacteristics().passengerCapacity) {
                this.numPassengers =
                        this.getCharacteristics().passengerCapacity;
            }
        }
    }
}
