package towersim.control;

import towersim.aircraft.Aircraft;
import towersim.aircraft.AircraftType;
import towersim.ground.Gate;
import towersim.ground.Terminal;
import towersim.tasks.TaskType;
import towersim.util.NoSpaceException;
import towersim.util.NoSuitableGateException;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a the control tower of an airport.
 */
public class ControlTower {

    /**
     * The terminals under the jurisdiction of this control tower.
     */
    private List<Terminal> governedTerminals;
    /**
     * The aircrafts under the jurisdiction of this control tower.
     */
    private List<Aircraft> governedAircrafts;

    /**
     * Creates a new ControlTower.
     */
    public ControlTower() {
        governedTerminals = new ArrayList<>();
        governedAircrafts = new ArrayList<>();
    }

    /**
     * Returns a list of all terminals currently managed by this control tower.
     *
     * @param terminal terminal to add
     */
    public void addTerminal(Terminal terminal) {
        governedTerminals.add(terminal);
    }

    /**
     * Returns a list of all terminals currently managed by this control tower.
     *
     * @return all terminals
     */
    public List<Terminal> getTerminals() {
        List<Terminal> governedTerminalsCopy
                = new ArrayList<>(governedTerminals);
        return governedTerminalsCopy;
    }

    /**
     * Adds the given aircraft to the jurisdiction of this control tower.
     *
     * @param aircraft aircraft to add
     * @throws NoSuitableGateException if there is no suitable gate for an
     *                                 aircraft with a current task type of
     *                                 WAIT or LOAD
     */
    public void addAircraft(Aircraft aircraft)
            throws NoSuitableGateException {
        if (governedTerminals.isEmpty()) {
            throw new NoSuitableGateException();
        }
        List<Terminal> allTerminals = new ArrayList<>();
        List<Gate> unoccupiedGates = new ArrayList<>();
        for (Terminal terminal : governedTerminals) {
            if (aircraft.getTaskList().getCurrentTask().getType()
                    == TaskType.WAIT
                    || aircraft.getTaskList().getCurrentTask().getType()
                    == TaskType.LOAD) {
                allTerminals.add(terminal);
                if (allTerminals.size() < governedTerminals.size()) {
                    try {
                        unoccupiedGates.add(findUnoccupiedGate(aircraft));
                        break;
                    } catch (NoSuitableGateException e) {
                        continue;
                    }
                }
                if (allTerminals.size() == governedTerminals.size()) {
                    unoccupiedGates.add(findUnoccupiedGate(aircraft));
                    break;
                }
            }
        }
        if (!unoccupiedGates.isEmpty()) {
            for (Gate compatibleGate : unoccupiedGates) {
                try {
                    compatibleGate.parkAircraft(aircraft);
                    governedAircrafts.add(aircraft);
                } catch (NoSpaceException e) {
                    continue;
                }
            }
        }
    }

    /**
     * Returns a list of all aircraft currently managed by this control tower.
     *
     * @return all aircraft
     */
    public List<Aircraft> getAircraft() {
        List<Aircraft> governedAircraftsCopy =
                new ArrayList<>(governedAircrafts);
        return governedAircraftsCopy;
    }

    /**
     * Attempts to find an unoccupied gate in a compatible terminal for the
     * given aircraft.
     *
     * @param aircraft aircraft for which to find gate
     * @return gate for given aircraft if one exists
     * @throws NoSuitableGateException if no suitable gate could be found
     */
    public Gate findUnoccupiedGate(Aircraft aircraft) throws
            NoSuitableGateException {
        for (Terminal compatibleTerminal : governedTerminals) {
            if (aircraft.getCharacteristics().type == AircraftType.AIRPLANE
                    && compatibleTerminal.getClass().getSimpleName()
                    .equals("AirplaneTerminal")) {
                try {
                    Gate compatibleGate =
                            compatibleTerminal.findUnoccupiedGate();
                    return compatibleGate;
                } catch (NoSuitableGateException e) {
                    continue;
                }
            }
            if (aircraft.getCharacteristics().type == AircraftType.HELICOPTER
                    && compatibleTerminal.getClass().getSimpleName()
                    .equals("HelicopterTerminal")) {
                try {
                    Gate compatibleGate =
                            compatibleTerminal.findUnoccupiedGate();
                    return compatibleGate;
                } catch (NoSuitableGateException e) {
                    continue;
                }
            }
        }
        throw new NoSuitableGateException();
    }

    /**
     * Finds the gate where the given aircraft is parked, and returns null if
     * the aircraft is not parked at any gate in any terminal.
     *
     * @param aircraft aircraft whose gate to find
     * @return gate occupied by the given aircraft; or null if none exists
     */
    public Gate findGateOfAircraft(Aircraft aircraft) {
        for (Terminal terminal : governedTerminals) {
            for (Gate gate : terminal.getGates()) {
                if (gate.isOccupied()) {
                    if (gate.getAircraftAtGate() == aircraft) {
                        return gate;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Advances the simulation by one tick.
     */
    public void tick() {
        for (Aircraft aircraft : governedAircrafts) {
            aircraft.tick();
        }
    }
}
