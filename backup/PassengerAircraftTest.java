package towersim.aircraft;

import org.junit.*;
import towersim.tasks.Task;
import towersim.tasks.TaskList;
import towersim.tasks.TaskType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PassengerAircraftTest {

    private PassengerAircraft aircraft1;
    private PassengerAircraft aircraft2;
    private PassengerAircraft aircraft3;
    private PassengerAircraft aircraft4;
    private PassengerAircraft aircraft5;
    private TaskList taskList;
    private List<Task> tasks;
    private Task task1;
    private Task task2;
    private Task task3;
    private Task task4;
    private Task task5;

    @Before
    public void setup() {
        List<Task> tasks = new ArrayList<>();
        Task task1 = new Task(TaskType.AWAY);
        Task task2 = new Task(TaskType.LOAD, 20);
        Task task3 = new Task(TaskType.LOAD, 1);
        Task task4 = new Task(TaskType.LOAD, 100);
        Task task5 = new Task(TaskType.TAKEOFF);
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        tasks.add(task4);
        tasks.add(task5);
        taskList = new TaskList(tasks);
        aircraft1 = new PassengerAircraft("ABC123",
                AircraftCharacteristics.AIRBUS_A320,
                taskList, 5000, 100);
        aircraft2 = new PassengerAircraft("ABC789",
                AircraftCharacteristics.ROBINSON_R44,
                taskList, 100, 0);
        aircraft3 = new PassengerAircraft("ABC789",
                AircraftCharacteristics.BOEING_787,
                taskList, 72, 70);
        aircraft4 = new PassengerAircraft("ABC123",
                AircraftCharacteristics.BOEING_747_8F,
                taskList, 5000, 0);
        aircraft5 = new PassengerAircraft("ABC789",
                AircraftCharacteristics.SIKORSKY_SKYCRANE,
                taskList, 72, 0);
    }

    @Test
    public void getTotalWeightTest() {
        double expected = 55600;
        double delta = 0.001;
        assertEquals(expected, aircraft1.getTotalWeight(), delta);
    }

    @Test
    public void getLoadingTimeTest1() {
        //Test when loading time more than 1
        int expected = 1;
        aircraft1.getTaskList().moveToNextTask();
        assertEquals(expected, aircraft1.getLoadingTime());
    }

    @Test
    public void getLoadingTimeTest2() {
        //Test when loading time less than 1
        int expected = 1;
        aircraft1.getTaskList().moveToNextTask();
        aircraft1.getTaskList().moveToNextTask();
        assertEquals(expected, aircraft1.getLoadingTime());
    }

    @Test
    public void getLoadingTimeTest3() {
        //Test when loading time more than 1
        int expected = 2;
        aircraft1.getTaskList().moveToNextTask();
        aircraft1.getTaskList().moveToNextTask();
        aircraft1.getTaskList().moveToNextTask();
        assertEquals(expected, aircraft1.getLoadingTime());
    }

    @Test
    public void calculateOccupancyLevelTest1() {
        //calculate occupancy level before load
        int expected = 67;
        assertEquals(expected, aircraft1.calculateOccupancyLevel());
    }

    @Test
    public void calculateOccupancyLevelTest2() {
        //calculate occupancy level after load
        int expected = 87;
        aircraft1.getTaskList().moveToNextTask();
        aircraft1.tick();
        assertEquals(expected, aircraft1.calculateOccupancyLevel());
    }

    @Test
    public void tickTest1() {
        //Test occupancy level when task type is AWAY
        int expected = 67;
        aircraft1.tick();
        assertEquals(expected, aircraft1.calculateOccupancyLevel());
    }

    @Test
    public void tickTest2() {
        //Test occupancy level when task type is Load and number of
        // passengers is maximized after load
        int expected = 100;
        aircraft1.getTaskList().moveToNextTask();
        aircraft1.getTaskList().moveToNextTask();
        aircraft1.getTaskList().moveToNextTask();
        aircraft1.tick();
        assertEquals(expected, aircraft1.calculateOccupancyLevel());
    }

    @Test
    public void tickTest3() {
        //Test occupancy level when task type is Load and number of
        // passengers is not maximized after load
        int expected = 39;
        aircraft3.getTaskList().moveToNextTask();
        aircraft3.tick();
        assertEquals(expected, aircraft3.calculateOccupancyLevel());
    }


}
