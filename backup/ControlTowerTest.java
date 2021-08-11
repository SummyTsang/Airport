package towersim.control;


// add any required imports here

import org.junit.Before;
import org.junit.Test;
import towersim.aircraft.Aircraft;
import towersim.aircraft.AircraftCharacteristics;
import towersim.aircraft.FreightAircraft;
import towersim.aircraft.PassengerAircraft;
import towersim.ground.AirplaneTerminal;
import towersim.ground.Gate;
import towersim.ground.HelicopterTerminal;
import towersim.ground.Terminal;
import towersim.tasks.Task;
import towersim.tasks.TaskList;
import towersim.tasks.TaskType;
import towersim.util.NoSpaceException;
import towersim.util.NoSuitableGateException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
public class ControlTowerTest {
    private ControlTower controlTower1;
    private ControlTower controlTower2;
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
    private Gate gate8;
    private Gate gate9;
    private Gate gate10;
    private Gate gate11;
    private FreightAircraft freightAircraft;
    private PassengerAircraft passengerAircraft;
    private FreightAircraft freightAircrafthelicopter;
    private TaskList taskList;
    private Task task1;
    private Task task2;
    private Task task3;
    private Task task4;

    @Before
    public void setup() throws NoSpaceException {
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
        this.gate8 = new Gate(8);
        this.gate9 = new Gate(9);
        this.gate10 = new Gate(10);
        this.gate11 = new Gate(11);
        this.airplaneTerminal1.addGate(this.gate1);
        this.airplaneTerminal1.addGate(this.gate2);
        this.airplaneTerminal2.addGate(this.gate3);
        this.airplaneTerminal2.addGate(this.gate4);
        this.airplaneTerminal2.addGate(this.gate5);
        this.helicopterTerminal1.addGate(this.gate6);
        this.helicopterTerminal1.addGate(this.gate7);
        this.helicopterTerminal2.addGate(this.gate8);
        this.helicopterTerminal2.addGate(this.gate9);
        this.helicopterTerminal2.addGate(this.gate10);
        this.helicopterTerminal2.addGate(this.gate11);
        this.task1 = new Task(TaskType.WAIT);
        this.task2 = new Task(TaskType.AWAY);
        this.task3 = new Task(TaskType.LAND);
        this.task4 = new Task(TaskType.LOAD, 33);
        List<Task> taskList = new ArrayList<Task>();
        taskList.add(this.task1);
        taskList.add(this.task2);
        taskList.add(this.task3);
        taskList.add(this.task4);
        this.taskList = new TaskList(taskList);
        this.freightAircraft = new FreightAircraft("ziqiyuan",
                AircraftCharacteristics.BOEING_747_8F,
                this.taskList, 10000, 20000);
        this.passengerAircraft = new PassengerAircraft("David",
                AircraftCharacteristics.AIRBUS_A320,
                this.taskList, 10000, 50);
        this.freightAircrafthelicopter = new FreightAircraft("helicopter",
                AircraftCharacteristics.SIKORSKY_SKYCRANE, this.taskList, 1000,
                1000);
        //        fuelcapcital = 226117 freight = 137756  loadingtime = 137756*0.33=2 totalfuelweight = 98446.8 137756*0.33 + 20000= 65460     197131 empyt
        /**
         * loadingtime = 2
         * fucap = 226117
         * fcap = 137756
         * amount of fuel should increase by  capacity/loadingTime(226117/2)  litres of fuel
         * total fuel = 10000 + (226117/2)*0.8
         * (percentage * totalfreightcap )/2(round) + 20000
         * total ferightweight = 0.33*137756/2 + 20000 = 42730
         * empty = 197131
         * total = 197131 + 42730 + 98446.8 =
         */
        this.controlTower1 = new ControlTower();
        this.controlTower2 = new ControlTower();
        this.controlTower2.addTerminal(airplaneTerminal1);
        this.controlTower2.addTerminal(airplaneTerminal2);
        this.controlTower2.addTerminal(helicopterTerminal1);
        this.controlTower2.addTerminal(helicopterTerminal2);

    }

    @Test
    public void addTerminalTest1() {
        List<Terminal> expected = new ArrayList<Terminal>();
        expected.add(airplaneTerminal1);
        this.controlTower1.addTerminal(this.airplaneTerminal1);

        assertEquals("correct", expected, this.controlTower1.getTerminals());
    }

    @Test
    public void getTerminalsTest1() {
        List<Terminal> expected = new ArrayList<Terminal>();
        expected.add(this.airplaneTerminal1);
        expected.add(this.airplaneTerminal2);
        expected.add(this.helicopterTerminal1);
        expected.add(this.helicopterTerminal2);
        assertEquals("correct", expected, this.controlTower2.getTerminals());
    }

    @Test
    public void getTermianlsTest2() {
        List<Terminal> expected = new ArrayList<Terminal>();
        assertEquals("correct", expected, this.controlTower1.getTerminals());
    }

    @Test(expected = NoSuitableGateException.class)
    public void addAircraftTest1() throws NoSuitableGateException {
        this.controlTower1.addAircraft(this.freightAircraft);
    }

    @Test
    public void addAircraftTest2() throws NoSuitableGateException {
        this.controlTower2.addAircraft(this.freightAircraft);
        List<Aircraft> expected = new ArrayList<Aircraft>();
        expected.add(this.freightAircraft);
        assertEquals("correct", expected, this.controlTower2.getAircraft());
    }

    @Test(expected = Test.None.class)
    public void addAircraftTest3() throws NoSuitableGateException {
        /**
         * controlTower2 has 2 airplaneterminal1/2 has 2 helicopterTerminal1/2
         * airterminal1 has 2 gate airterminal2 has 3 gate
         *
         */
        this.controlTower2.addAircraft(this.freightAircraft);
        this.controlTower2.addAircraft(this.freightAircraft);
        this.controlTower2.addAircraft(this.freightAircraft);
        this.controlTower2.addAircraft(this.freightAircraft);

    }

    @Test(expected = NoSuitableGateException.class)
    public void addAircraftTest4() throws NoSuitableGateException {
        //        this.controlTower2.addAircraft(this.freightAircrafthelicopter);
        this.controlTower2.addAircraft(this.freightAircraft);
        this.controlTower2.addAircraft(this.freightAircraft);
        this.controlTower2.addAircraft(this.freightAircraft);
        this.controlTower2.addAircraft(this.freightAircraft);
        this.controlTower2.addAircraft(this.freightAircraft);
        this.controlTower2.addAircraft(this.freightAircraft);
        //        boolean expected = false;
        //        assertEquals("correct", expected, this.controlTower2.getTerminals().get(0).getGates().get(0).isOccupied());
    }

    @Test
    public void addAircraftTest5() throws NoSuitableGateException {
        this.controlTower2.addAircraft(this.freightAircrafthelicopter);
        boolean expected = false;
        assertEquals("correct", expected,
                this.controlTower2.getTerminals().get(0).getGates().get(0)
                        .isOccupied());
    }

    @Test
    public void getAircraftTest1() throws NoSuitableGateException {
        List<Aircraft> expected = new ArrayList<Aircraft>();
        expected.add(this.passengerAircraft);
        this.controlTower2.addAircraft(this.passengerAircraft);
        assertEquals("correct", expected, this.controlTower2.getAircraft());
    }

    @Test
    public void getAircraftTest2() throws NoSuitableGateException {
        List<Aircraft> expected = new ArrayList<Aircraft>();
        expected.add(this.passengerAircraft);
        this.controlTower2.addAircraft(this.passengerAircraft);
        this.controlTower2.getAircraft().add(this.passengerAircraft);
        assertEquals("correct", expected, this.controlTower2.getAircraft());
    }

    @Test
    public void getAircraftTest3() throws NoSuitableGateException {
        List<Aircraft> expected = new ArrayList<Aircraft>();
        assertEquals("correct", expected, this.controlTower2.getAircraft());
    }

    @Test
    public void findUnoccupiedGateTest1() throws NoSuitableGateException {
        Gate expected = this.gate4;
        this.controlTower2.addAircraft(this.passengerAircraft);
        this.controlTower2.addAircraft(this.passengerAircraft);
        this.controlTower2.addAircraft(this.passengerAircraft);
        assertEquals("correct", expected,
                this.controlTower2.findUnoccupiedGate(this.passengerAircraft));
    }

    @Test
    public void findUnoccupiedGateTest2() throws NoSuitableGateException {
        Gate expected = this.gate9;
        this.controlTower2.addAircraft(this.freightAircrafthelicopter);
        this.controlTower2.addAircraft(this.freightAircrafthelicopter);
        this.controlTower2.addAircraft(this.freightAircrafthelicopter);
        assertEquals("correct", expected, this.controlTower2
                .findUnoccupiedGate(this.freightAircrafthelicopter));
    }

    @Test
    public void findGateOfAircraftTest1() {
        assertNull("correct",
                this.controlTower2.findGateOfAircraft(this.freightAircraft));
    }

    @Test
    public void findGateOfAircraftTest2() throws NoSuitableGateException {
        this.controlTower2.addAircraft(this.freightAircraft);
        Gate expected = this.gate1;
        assertEquals("coreect", expected,
                this.controlTower2.findGateOfAircraft(this.freightAircraft));
    }

    @Test
    public void findGateOfAircraftTest3() throws NoSuitableGateException {
        this.controlTower2.addAircraft(this.freightAircraft);
        this.controlTower2.addAircraft(this.passengerAircraft);
        this.controlTower2.addAircraft(this.freightAircrafthelicopter);
        Gate expected = this.gate6;
        assertEquals("coreect", expected, this.controlTower2
                .findGateOfAircraft(this.freightAircrafthelicopter));
    }

    @Test
    public void tickTest() throws NoSuitableGateException {
        this.controlTower2.addAircraft(this.passengerAircraft);

        this.controlTower2.findGateOfAircraft(this.passengerAircraft)
                .aircraftLeaves();
        this.controlTower2.getAircraft().get(0).getTaskList().moveToNextTask();
        this.controlTower2.tick();
        double expected = 7280;
        assertEquals("correct", expected,
                this.controlTower2.getAircraft().get(0).getFuelAmount(),
                0.00000001);
    }

    /**
     * loadingtime = 2
     * fucap = 226117
     * fcap = 137756
     * amount of fuel should increase by  capacity/loadingTime(226117/2)  litres of fuel
     * total fuel = 10000 + (226117/2)*0.8
     * (percentage * totalfreightcap )/2(round) + 20000
     * total ferightweight = 0.33*137756/2 + 20000 = 42730
     * empty = 197131
     * total = 197131 + 42730 + 98446.8 =
     */
    @Test
    public void tickTest2() throws NoSuitableGateException {
        this.controlTower2.addAircraft(this.freightAircraft);

        this.controlTower2.findGateOfAircraft(this.freightAircraft)
                .aircraftLeaves();
        this.controlTower2.getAircraft().get(0).getTaskList().moveToNextTask();
        this.controlTower2.getAircraft().get(0).getTaskList().moveToNextTask();
        this.controlTower2.getAircraft().get(0).getTaskList().moveToNextTask();
        this.controlTower2.tick();
        double expected = 338307.8;
        assertEquals("correct", expected,
                this.controlTower2.getAircraft().get(0).getTotalWeight(),
                0.0001);
    }
}