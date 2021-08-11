package towersim.aircraft;

import org.junit.*;
import towersim.tasks.Task;
import towersim.tasks.TaskList;
import towersim.tasks.TaskType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FreightAircraftTest {

    private FreightAircraft aircraft1;
    private FreightAircraft aircraft2;
    private FreightAircraft aircraft3;
    private FreightAircraft aircraft4;
    private FreightAircraft aircraft5;
    private TaskList taskList;
    private List<Task> tasks;
    private Task task1;
    private Task task2;
    private Task task3;
    private Task task4;

    @Before
    public void setup() {
        List<Task> tasks = new ArrayList<>();
        Task task1 = new Task(TaskType.AWAY);
        Task task2 = new Task(TaskType.LOAD, 20);
        Task task3 = new Task(TaskType.LOAD, 1);
        Task task4 = new Task(TaskType.LOAD, 100);
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        tasks.add(task4);
        taskList = new TaskList(tasks);
        aircraft1 = new FreightAircraft("ABC123",
                AircraftCharacteristics.BOEING_747_8F,
                taskList, 5000, 5000);
        aircraft2 = new FreightAircraft("ABC789",
                AircraftCharacteristics.SIKORSKY_SKYCRANE,
                taskList, 3328, 0);
        aircraft3 = new FreightAircraft("ABC789",
                AircraftCharacteristics.SIKORSKY_SKYCRANE,
                taskList, 72, 9100);
        aircraft4 = new FreightAircraft("ABC123",
                AircraftCharacteristics.BOEING_747_8F,
                taskList, 5000, 5000);
        aircraft5 = new FreightAircraft("ABC789",
                AircraftCharacteristics.SIKORSKY_SKYCRANE,
                taskList, 72, 8500);
    }

    @Test
    public void getTotalWeightTest() {
        double expected1 = 206131;
        double delta = 0.0001;
        assertEquals(expected1, aircraft1.getTotalWeight(), delta);
    }

    @Test
    public void getLoadingTimeTest1() {
        aircraft1.getTaskList().moveToNextTask();
        int expected = 2;
        assertEquals(expected, aircraft1.getLoadingTime());
    }

    @Test
    public void getLoadingTimeTest2() {
        int expected = 2;
        aircraft1.getTaskList().moveToNextTask();
        aircraft1.getTaskList().moveToNextTask();
        assertEquals(expected, aircraft1.getLoadingTime());
    }

    @Test
    public void getLoadingTimeTest3() {
        int expected = 1;
        aircraft2.getTaskList().moveToNextTask();
        aircraft2.getTaskList().moveToNextTask();
        aircraft2.getTaskList().moveToNextTask();
        aircraft2.getTaskList().moveToNextTask();
        aircraft2.getTaskList().moveToNextTask();
        aircraft2.getTaskList().moveToNextTask();
        assertEquals(expected, aircraft2.getLoadingTime());
    }

    @Test
    public void calculateOccupancyLevelTest() {
        int expected = 4;
        assertEquals(expected, aircraft1.calculateOccupancyLevel());
    }

    @Test
    public void tickTest1() {
        //Move to next task has problem?
        //Test occupancy level when the task type is AWAY
        int expected = 4;
        aircraft1.tick();
        assertEquals(expected, aircraft1.calculateOccupancyLevel());
    }

    @Test
    public void tickTest2() {
        //Test when the task type is Load and freight to be load is less than
        // remaining space for freight
        int expected = 14;
        aircraft4.getTaskList().moveToNextTask();
        aircraft4.tick();
        assertEquals(expected, aircraft4.calculateOccupancyLevel());
    }

    @Test
    public void tickTest3() {
        //Test when the task type is Load and freight to be load is more than
        // remaining space for freight
        int expected = 100;
        aircraft5.getTaskList().moveToNextTask();
        aircraft5.getTaskList().moveToNextTask();
        aircraft5.getTaskList().moveToNextTask();
        aircraft5.tick();
        assertEquals(expected, aircraft5.calculateOccupancyLevel());
    }
}
