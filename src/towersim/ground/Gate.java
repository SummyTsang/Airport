package towersim.ground;

import towersim.aircraft.Aircraft;
import towersim.util.NoSpaceException;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an aircraft gate with facilities for a single aircraft to be
 * parked.
 */
public class Gate {

    /**
     * Number of gate
     */
    private int gateNumber;
    /**
     * List of aircrafts
     */
    private List<Aircraft> aircrafts;
    /**
     * If there is an aircraft at gate
     */
    private boolean hasAircraftAtGate;

    /**
     * Creates a new Gate with the given unique gate number.
     *
     * @param gateNumber identifying number of this gate
     */
    public Gate(int gateNumber) {
        this.gateNumber = gateNumber;
        this.hasAircraftAtGate = false;
        this.aircrafts = new ArrayList<>();
    }

    /**
     * Returns this gate's gate number.
     *
     * @return gate number
     */
    public int getGateNumber() {
        return gateNumber;
    }

    /**
     * Parks the given aircraft at this gate, so that the gate becomes occupied.
     *
     * @param aircraft aircraft to park at gate
     * @throws NoSpaceException if the gate is already occupied by an aircraft
     */
    public void parkAircraft(Aircraft aircraft) throws NoSpaceException {
        if (!isOccupied()) {
            aircrafts.add(aircraft);
            this.hasAircraftAtGate = true;
        } else {
            throw new NoSpaceException();
        }
    }

    /**
     * Removes the currently parked aircraft from the gate.
     */
    public void aircraftLeaves() {
        if (isOccupied()) {
            aircrafts.remove(0);
            this.hasAircraftAtGate = false;
        }
    }

    /**
     * Returns true if there is an aircraft currently parked at the gate, or
     * false otherwise.
     *
     * @return whether an aircraft is currently parked
     */
    public boolean isOccupied() {
        if (hasAircraftAtGate) {
            return true;
        }
        return false;
    }

    /**
     * Returns the aircraft currently parked at the gate, or null if there is
     * no aircraft parked.
     *
     * @return currently parked aircraft
     */
    public Aircraft getAircraftAtGate() {
        if (isOccupied()) {
            return aircrafts.get(0);
        }
        return null;
    }

    /**
     * Returns the human-readable string representation of this gate.
     *
     * @return string representation of this gate
     */
    @Override
    public String toString() {
        if (isOccupied()) {
            return "Gate " + this.getGateNumber() + " ["
                    + this.getAircraftAtGate().getCallsign() + "]";
        }
        return "Gate " + this.getGateNumber() + " [empty]";
    }

}
