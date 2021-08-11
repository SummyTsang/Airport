package towersim.ground;

import towersim.util.NoSpaceException;
import towersim.util.NoSuitableGateException;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an airport terminal building, containing several aircraft gates.
 */
public abstract class Terminal {

    /**
     * The terminal's terminal number.
     */
    private int terminalNumber;
    /**
     * Maximum possible number of gates allowed at a single terminal.
     */
    public static final int MAX_NUM_GATES = 6;
    /**
     * List of gates of the terminal
     */
    private List<Gate> gates;
    /**
     * If emergency occurs
     */
    private boolean emergencyOccur;

    /**
     * Creates a new Terminal with the given unique terminal number.
     *
     * @param terminalNumber identifying number of this terminal
     */
    protected Terminal(int terminalNumber) {
        this.terminalNumber = terminalNumber;
        this.gates = new ArrayList<>();
    }

    /**
     * Returns this terminal's terminal number.
     *
     * @return terminal number
     */
    public int getTerminalNumber() {
        return terminalNumber;
    }

    /**
     * Adds a gate to the terminal.
     *
     * @param gate gate to add to terminal
     * @throws NoSpaceException if there is no space at the terminal for the new gate
     */
    public void addGate(Gate gate) throws NoSpaceException {
        if (gates.size() < MAX_NUM_GATES) {
            gates.add(gate);
        } else {
            throw new NoSpaceException();
        }
    }

    /**
     * Returns a list of all gates in the terminal.
     *
     * @return list of terminal's gates
     */
    public List<Gate> getGates() {
        List<Gate> gatesCopy = new ArrayList<>(gates);
        return gatesCopy;
    }

    /**
     * Finds and returns the first non-occupied gate in this terminal.
     *
     * @return first non-occupied gate in this terminal
     * @throws NoSuitableGateException if all gates in this terminal are occupied
     */
    public Gate findUnoccupiedGate() throws NoSuitableGateException {
        for (Gate gate : gates) {
            if (!gate.isOccupied()) {
                return gate;
            }
        }
        throw new NoSuitableGateException();
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

    /**
     * Returns the ratio of occupied gates to total gates as a percentage from
     * 0 to 100.
     *
     * @return percentage of occupied gates in this terminal, 0 to 100
     */
    public int calculateOccupancyLevel() {
        int numberOfTotalGates = gates.size();
        List<Gate> occupiedGates = new ArrayList<>();
        if (numberOfTotalGates > 0) {
            for (Gate gate : gates) {
                if (gate.isOccupied()) {
                    occupiedGates.add(gate);
                }
            }
            int numberOfOccupiedGates = occupiedGates.size();
            double ratio =
                    (double) numberOfOccupiedGates
                            / (double) numberOfTotalGates;
            return Math.round(Math.round(ratio * 100));
        }
        return 0;
    }

    /**
     * Returns the human-readable string representation of this terminal.
     *
     * @return string representation of this terminal
     */
    public String toString() {
        if (hasEmergency()) {
            return this.getClass().getSimpleName() + " " + getTerminalNumber()
                    + ", " + gates.size() + " " + "gates" + " (EMERGENCY)";
        }
        return this.getClass().getSimpleName() + " " + getTerminalNumber()
                + ", " + gates.size() + " " + "gates";
    }
}