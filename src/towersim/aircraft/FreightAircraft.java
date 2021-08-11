package towersim.aircraft;

import towersim.tasks.TaskList;
import towersim.tasks.TaskType;

/**
 * Represents an aircraft capable of carrying freight cargo.
 */
public class FreightAircraft extends Aircraft {

    /**
     * the freightAmount of the aircraft
     */
    private int freightAmount;
    /**
     * the loading time of the aircraft
     */
    private int loadingTime;

    /**
     * Creates a new freight aircraft with the given callsign, task list,
     * fuel capacity, amount of fuel and kilograms of freight.
     *
     * @param callsign        the callsign of the aircraft
     * @param characteristics the characteristics of the aircraft
     * @param tasks           the tasks of the aircraft
     * @param fuelAmount      the fuelAmount of the aircraft
     * @param freightAmount   the freightAmount of the aircraft
     */
    public FreightAircraft(String callsign,
                           AircraftCharacteristics characteristics,
                           TaskList tasks, double fuelAmount,
                           int freightAmount) {
        super(callsign, characteristics, tasks, fuelAmount);
        this.freightAmount = freightAmount;
        if (this.freightAmount < 0 || this.freightAmount
                > this.getCharacteristics().freightCapacity) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Returns the total weight of the aircraft in its current state.
     *
     * @return total weight of aircraft in kilograms
     */
    @Override
    public double getTotalWeight() {
        int emptyWeight = this.getCharacteristics().emptyWeight;
        double fuelOnboard = this.getFuelAmount() * LITRE_OF_FUEL_WEIGHT;
        double freightOnboard = this.freightAmount;
        return emptyWeight + fuelOnboard + freightOnboard;
    }

    /**
     * Returns the number of ticks required to load the aircraft at the gate.
     *
     * @return loading time in ticks
     */
    public int getLoadingTime() {
        double freightToBeLoaded =
                (double) this.getTaskList().getCurrentTask().getLoadPercent()
                        / 100.0
                        * (double) this.getCharacteristics().freightCapacity;
        if (freightToBeLoaded < 1000) {
            this.loadingTime = 1;
        }
        if (freightToBeLoaded >= 1000 && freightToBeLoaded <= 50000) {
            this.loadingTime = 2;
        }
        if (freightToBeLoaded > 50000) {
            this.loadingTime = 3;
        }
        return this.loadingTime;
    }

    /**
     * Returns the ratio of freight cargo onboard to maximum available
     * freight capacity as a percentage between 0 and 100.
     *
     * @return occupancy level as a percentage
     */
    public int calculateOccupancyLevel() {
        return Math.round(Math.round(100.0 * this.freightAmount
                / this.getCharacteristics().freightCapacity));
    }

    /**
     * Updates the aircraft's state on each tick of the simulation.
     */
    @Override
    public void tick() {
        super.tick();
        if (this.getTaskList().getCurrentTask().getType() == TaskType.LOAD) {
            int freightToBeLoaded =
                    Math.round(Math.round(
                            this.getCharacteristics().freightCapacity
                                    * this.getTaskList().getCurrentTask()
                                            .getLoadPercent() / 100.0));
            this.freightAmount += Math.round(Math.round(1.0
                    * freightToBeLoaded / this.loadingTime));
            if (this.freightAmount
                    >= this.getCharacteristics().freightCapacity) {
                this.freightAmount = this.getCharacteristics().freightCapacity;
            }
        }
    }
}
