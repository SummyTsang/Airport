package towersim.ground;

import org.junit.*;
import towersim.aircraft.Aircraft;
import towersim.aircraft.AircraftCharacteristics;
import towersim.aircraft.FreightAircraft;
import towersim.aircraft.PassengerAircraft;
import towersim.tasks.Task;
import towersim.tasks.TaskList;
import towersim.tasks.TaskType;
import towersim.util.NoSpaceException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GateTest {

    private Gate gate1;
    private Gate gate2;
    private Gate gate3;
    private FreightAircraft aircraft1;
    private PassengerAircraft aircraft2;
    private TaskList taskList;
    private List<Task> tasks;
    private Task task1;
    private Task task2;
    private Task task3;
    private Task task4;

    @Before
    public void setup() {
        gate1 = new Gate(10);
        gate2 = new Gate(20);
        gate3 = new Gate(30);
        List<Task> tasks = new ArrayList<>();
        Task task1 = new Task(TaskType.AWAY);
        Task task2 = new Task(TaskType.LOAD, 20);
        Task task3 = new Task(TaskType.LAND);
        Task task4 = new Task(TaskType.TAKEOFF);
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        tasks.add(task4);
        taskList = new TaskList(tasks);
        aircraft1 = new FreightAircraft("ABC123",
                AircraftCharacteristics.BOEING_747_8F,
                taskList, 50, 100000);
        aircraft2 = new PassengerAircraft("ABC789",
                AircraftCharacteristics.ROBINSON_R44,
                taskList, 50, 3);
    }

    @Test
    public void getGateNumberTest() {
        int expected = 10;
        assertEquals(expected, gate1.getGateNumber());
    }

    @Test
    public void parkAircraftTest1() throws NoSpaceException {
        //Test when there is no aircraft
        String expected = "Gate 10 [ABC123]";
        gate1.parkAircraft(aircraft1);
        assertEquals(expected, gate1.toString());
    }

    @Test
    public void parkAircraftTest2() throws NoSpaceException {
        //Test after aircraft parked
        String expected = "Gate 10 [ABC789]";
        gate1.parkAircraft(aircraft2);
        assertEquals(expected, gate1.toString());
    }

    @Test(expected = NoSpaceException.class)
    public void parkAircraftTest3() throws NoSpaceException {
        //Test after aircraft parked
        gate1.parkAircraft(aircraft1);
        gate1.parkAircraft(aircraft2);
    }

    @Test
    public void aircraftLeaves1() throws NoSpaceException {
        //Test when there is an aircraft
        String expected = "Gate 10 [ABC123]";
        gate1.parkAircraft(aircraft1);
        assertEquals(expected, gate1.toString());
    }

    @Test
    public void aircraftLeaves2() throws NoSpaceException {
        //Test after aircraft leaves
        String expected = "Gate 10 [empty]";
        gate1.parkAircraft(aircraft1);
        gate1.aircraftLeaves();
        assertEquals(expected, gate1.toString());
    }

    @Test
    public void isOccupiedTest1() throws NoSpaceException {
        boolean expected = false;
        assertEquals(expected, gate1.isOccupied());
    }

    @Test
    public void isOccupiedTest2() throws NoSpaceException {
        boolean expected = true;
        gate1.parkAircraft(aircraft1);
        assertEquals(expected, gate1.isOccupied());
    }

    @Test
    public void getAircraftAtGateTest1() throws NoSpaceException {
        //Test when there is no aircraft
        String expected = null;
        assertEquals(expected, gate1.getAircraftAtGate());
    }

    @Test
    public void getAircraftAtGateTest2() throws NoSpaceException {
        //Test after aircraft parked
        //callsign?
        String expected = "ABC123";
        gate1.parkAircraft(aircraft1);
        assertEquals(expected,
                gate1.getAircraftAtGate().getCallsign());
    }

    @Test
    public void toStringTest() throws NoSpaceException {
        String expected = "Gate 10 [ABC123]";
        gate1.parkAircraft(aircraft1);
        assertEquals(expected, gate1.toString());
    }
}
