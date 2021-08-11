package towersim.ground;
import org.junit.Before;
import org.junit.Test;
import towersim.aircraft.AircraftCharacteristics;
import towersim.aircraft.FreightAircraft;
import towersim.aircraft.PassengerAircraft;
import towersim.tasks.Task;
import towersim.tasks.TaskList;
import towersim.tasks.TaskType;
import towersim.util.NoSpaceException;
import towersim.util.NoSuitableGateException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
public class TerminalTest {
    private AirplaneTerminal airplaneTerminal1;
    private AirplaneTerminal airplaneTerminal2;
    private HelicopterTerminal helicopterTerminal1;
    private HelicopterTerminal helicopterTerminal2;
    private Gate gate1;
    private Gate gate2;
    private Gate gate3;
    private Gate gate4;
    private Gate gate5;
    private Gate gate6;
    private Gate gate7;
    private FreightAircraft freightAircraft;
    private PassengerAircraft passengerAircraft;
    private TaskList taskList;
    private Task task1;
    private Task task2;
    private Task task3;
    private Task task4;


    @Before
    public void setup() {
        this.airplaneTerminal1 = new AirplaneTerminal(1);
        this.airplaneTerminal2 = new AirplaneTerminal(2);
        this.helicopterTerminal1 = new HelicopterTerminal(1);
        this.helicopterTerminal2 = new HelicopterTerminal(2);
        this.gate1 = new Gate(1);
        this.gate2 = new Gate(2);
        this.gate3 = new Gate(3);
        this.gate4 = new Gate(4);
        this.gate5 = new Gate(5);
        this.gate6 = new Gate(6);
        this.gate7 = new Gate(7);
        this.task1 = new Task(TaskType.WAIT);
        this.task2 = new Task(TaskType.TAKEOFF);
        this.task3 = new Task(TaskType.LAND);
        this.task4 = new Task(TaskType.LOAD);
        List<Task> taskList = new ArrayList<Task>();
        taskList.add(this.task1);
        taskList.add(this.task2);
        taskList.add(this.task3);
        taskList.add(this.task4);
        this.taskList = new TaskList(taskList);
        this.freightAircraft = new FreightAircraft("ziqiyuan", AircraftCharacteristics.BOEING_747_8F,
                this.taskList, 10000, 20000);
        this.passengerAircraft = new PassengerAircraft("David", AircraftCharacteristics.AIRBUS_A320, this.taskList, 10000, 50);
    }

    @Test
    public void getTermianlNumberTest1() {
        int expected = 1;
        assertEquals("The terminalNumber should be 1",airplaneTerminal1.getTerminalNumber(), expected);
    }
    @Test
    public void addGateTest1() throws NoSpaceException {
        this.airplaneTerminal1.addGate(gate1);
        List<Gate> expected = new ArrayList<Gate>();
        expected.add(gate1);
        assertEquals("gate1 should be add to the airplaneTermianl1",airplaneTerminal1.getGates(), expected);
    }
    @Test (expected = NoSpaceException.class )
    public void addGateTest2() throws NoSpaceException {
        this.airplaneTerminal2.addGate(gate1);
        this.airplaneTerminal2.addGate(gate2);
        this.airplaneTerminal2.addGate(gate3);
        this.airplaneTerminal2.addGate(gate4);
        this.airplaneTerminal2.addGate(gate5);
        this.airplaneTerminal2.addGate(gate6);
        this.airplaneTerminal2.addGate(gate7);

    }
    @Test
    public void getGatesTest1() {
        List<Gate> expected = new ArrayList<Gate>();
        assertEquals("should be null", airplaneTerminal1.getGates(), expected);
    }
    @Test
    public void getGatesTest2() throws NoSpaceException {
        List<Gate> expected = new ArrayList<Gate>();
        expected.add(this.gate1);
        expected.add(this.gate2);
        this.airplaneTerminal1.addGate(gate1);
        this.airplaneTerminal1.addGate(gate2);
        assertEquals("the Gatelist is not same as expected", this.airplaneTerminal1.getGates(), expected);
    }
    @Test
    public void getGatesTest3() throws NoSpaceException {
        List<Gate> expected = new ArrayList<Gate>();
        expected.add(this.gate1);
        this.airplaneTerminal1.addGate(this.gate1);
        this.airplaneTerminal1.getGates().add(this.gate2);
        assertEquals("the gatelist should not be modify through the return value", this.airplaneTerminal1.getGates(), expected);
    }
    @Test (expected = NoSuitableGateException.class)
    public void findUnoccupiedGateTest1() throws NoSuitableGateException {
        List<Gate> expected = new ArrayList<>();
        this.airplaneTerminal1.findUnoccupiedGate();
    }
    @Test
    public void findUnoccupiedGateTest2() throws NoSpaceException, NoSuitableGateException {
        Gate expected = this.gate1;
        this.airplaneTerminal1.addGate(this.gate1);
        this.airplaneTerminal1.addGate(this.gate2);
        assertEquals("gate1 should be return", this.airplaneTerminal1.findUnoccupiedGate(), expected);

    }
    @Test
    public void findUnoccupiedGateTest3() throws NoSpaceException, NoSuitableGateException {
        Gate expected = this.gate1;
        this.airplaneTerminal1.addGate(this.gate1);
        this.airplaneTerminal1.addGate(this.gate2);
        this.gate2.parkAircraft(this.freightAircraft);
        assertEquals("gate2 should be return" , this.airplaneTerminal1.findUnoccupiedGate(),expected);
    }
    @Test (expected = NoSuitableGateException.class)
    public void findUnoccupiedGateTest4 () throws NoSpaceException, NoSuitableGateException {
        this.gate1.parkAircraft(this.freightAircraft);
        this.gate2.parkAircraft(this.passengerAircraft);
        this.airplaneTerminal1.addGate(gate1);
        this.airplaneTerminal1.addGate(gate2);
        assertEquals("shoud throw NosuitableGateException", this.airplaneTerminal1.findUnoccupiedGate());
    }
    @Test
    public void declareEmergency1() {
        boolean expected = true;
        this.airplaneTerminal1.declareEmergency();
        assertEquals("should be true", this.airplaneTerminal1.hasEmergency(),expected);
    }
    @Test
    public void clearEmergencyTest1() {
        boolean expected = false;
        this.airplaneTerminal1.clearEmergency();
        assertEquals("Should be false", this.airplaneTerminal1.hasEmergency(),expected);
    }
    @Test
    public void clearEmergencyTest2() {
        boolean expected = false;
        this.airplaneTerminal1.declareEmergency();
        this.airplaneTerminal1.clearEmergency();
        assertEquals("should be false", this.airplaneTerminal1.hasEmergency(),expected);
    }
    @Test
    public void hasEmergencyTest1() {
        boolean expected = false;
        assertEquals("should be false", this.helicopterTerminal1.hasEmergency(),expected);
    }
    @Test
    public void hasEmergencyTest2() {
        boolean expected = true;
        this.helicopterTerminal1.declareEmergency();
        assertEquals("should be true", this.helicopterTerminal1.hasEmergency(),expected);
    }
    @Test
    public void calculateOccupancyLevelTest1() throws NoSpaceException {
        this.airplaneTerminal1.addGate(this.gate1);
        this.airplaneTerminal1.addGate(this.gate2);
        this.airplaneTerminal1.addGate(this.gate3);
        int expected = 67;
        this.gate1.parkAircraft(this.passengerAircraft);
        this.gate3.parkAircraft(this.freightAircraft);
        assertEquals("the ration percentage did not match", this.airplaneTerminal1.calculateOccupancyLevel(),expected);
    }
    @Test
    public void calculateOccupancyLevelTest2() {
        int expected = 0;
        assertEquals("0 should be return",this.airplaneTerminal1.calculateOccupancyLevel(),expected);
    }
    @Test
    public void calculateOccupancyLevelTest3() throws NoSpaceException {
        int expected = 33;
        this.airplaneTerminal1.addGate(this.gate1);
        this.airplaneTerminal1.addGate(this.gate2);
        this.airplaneTerminal1.addGate(this.gate3);
        this.gate2.parkAircraft(this.freightAircraft);
        assertEquals("33 should be return", this.airplaneTerminal1.calculateOccupancyLevel(), expected);
    }
    @Test
    public void toStringTest1() throws NoSpaceException {
        String expected = "AirplaneTerminal 2, 5 gates (EMERGENCY)";
        this.airplaneTerminal2.addGate(this.gate1);
        this.airplaneTerminal2.addGate(this.gate2);
        this.airplaneTerminal2.addGate(this.gate3);
        this.airplaneTerminal2.addGate(this.gate4);
        this.airplaneTerminal2.addGate(this.gate5);
        this.airplaneTerminal2.declareEmergency();
        assertEquals("toString did not match", this.airplaneTerminal2.toString(),expected);

    }
    @Test
    public void toStringTest2() {
        String expected = "HelicopterTerminal 1, 0 gates";
        assertEquals("toString did not match", this.helicopterTerminal1.toString(),expected);
    }
}
