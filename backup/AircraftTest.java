package towersim.aircraft;

import org.junit.*;
import static org.junit.Assert.*;
import towersim.tasks.Task;
import towersim.tasks.TaskList;
import towersim.tasks.TaskType;

import java.util.ArrayList;
import java.util.List;

public class AircraftTest {

    private Aircraft aircraft1;
    private Aircraft aircraft2;
    private Aircraft aircraft3;
    private Aircraft aircraft4;
    private Aircraft aircraft5;
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
        Task task3 = new Task(TaskType.LAND, 50);
        Task task4 = new Task(TaskType.TAKEOFF, 100);
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        tasks.add(task4);
        taskList = new TaskList(tasks);
        aircraft1 = new Aircraft("ABC123", AircraftCharacteristics.AIRBUS_A320,
                taskList, 5000) {
            @Override
            public int calculateOccupancyLevel() {
                return 0;
            }

            @Override
            public int getLoadingTime() {
                return 2;
            }
        };
        aircraft2 = new Aircraft("ABC789", AircraftCharacteristics.ROBINSON_R44,
                taskList, 165) {
            @Override
            public int calculateOccupancyLevel() {
                return 0;
            }

            @Override
            public int getLoadingTime() {
                return 2;
            }
        };
        aircraft3 = new Aircraft("ABC789", AircraftCharacteristics.ROBINSON_R44,
                taskList, 72) {
            @Override
            public int calculateOccupancyLevel() {
                return 2;
            }

            @Override
            public int getLoadingTime() {
                return 2;
            }
        };

        aircraft4 = new Aircraft("ABC789", AircraftCharacteristics.ROBINSON_R44,
                taskList, 10) {
            @Override
            public int calculateOccupancyLevel() {
                return 0;
            }

            @Override
            public int getLoadingTime() {
                return 2;
            }
        };
        aircraft5 = new Aircraft("ABC789", AircraftCharacteristics.ROBINSON_R44,
                taskList, 160) {
            @Override
            public int calculateOccupancyLevel() {
                return 0;
            }

            @Override
            public int getLoadingTime() {
                return 2;
            }
        };

    }

    @Test
    public void getCallsignTest() {
        String expected = "ABC123";
        assertEquals(expected, aircraft1.getCallsign());
    }

    @Test
    public void getFuelAmountTest() {
        double expected = 5000;
        double delta = 0.01;
        assertEquals(expected, aircraft1.getFuelAmount(), delta);
    }

    @Test
    public void getCharacteristics() {
        AircraftCharacteristics expected = AircraftCharacteristics.AIRBUS_A320;
        assertEquals(expected, aircraft1.getCharacteristics());
    }

    @Test
    public void getFuelPercentRemainingTest1() {
        int expected = 18;
        assertEquals(expected, aircraft1.getFuelPercentRemaining());
    }

    @Test
    public void getFuelPercentRemainingTest2() {
        int expected = 87;
        assertEquals(expected, aircraft2.getFuelPercentRemaining());
    }

    @Test
    public void getTotalWeightTest1() {
        double expected = 46600;
        double delta = 0.0001;
        assertEquals(expected, aircraft1.getTotalWeight(), delta);
    }

    @Test
    public void getTotalWeightTest2() {
        double expected = 715.6;
        double delta = 0.0001;
        assertEquals(expected, aircraft3.getTotalWeight(), delta);
    }

    @Test
    public void getTaskListTest1() {
        String expected = "AWAY";
        assertEquals(expected,
                aircraft1.getTaskList().getCurrentTask().toString());
    }

    @Test
    public void getTaskListTest2() {
        String expected = "LOAD at 20%";
        assertEquals(expected,
                aircraft1.getTaskList().getNextTask().toString());
    }

    @Test
    public void tickTest1() {
        double expected = 2280;
        double delta = 0.001;
        aircraft1.tick();
        assertEquals(expected, aircraft1.getFuelAmount(), delta);
    }

    @Test
    public void tickTest2() {
        double expected = 0;
        double delta = 0.001;
        aircraft4.tick();
        assertEquals(expected, aircraft4.getFuelAmount(), delta);

    }

    @Test
    public void tickTest3() {
        double expected = 167;
        double delta = 0.001;
        taskList.moveToNextTask();
        aircraft3.tick();
        assertEquals(expected, aircraft3.getFuelAmount(), delta);
    }

    @Test
    public void tickTest4() {
        double expected = 190;
        double delta = 0.001;
        taskList.moveToNextTask();
        aircraft5.tick();
        assertEquals(expected, aircraft5.getFuelAmount(), delta);
    }

    @Test
    public void declareEmergencyTest() {
        String expected =  "AIRPLANE ABC123 AIRBUS_A320 AWAY (EMERGENCY)";
        aircraft1.declareEmergency();
        assertEquals(expected, aircraft1.toString());
    }

    @Test
    public void clearEmergencyTest() {
        String expected = "AIRPLANE ABC123 AIRBUS_A320 AWAY";
        aircraft1.declareEmergency();
        aircraft1.clearEmergency();
        assertEquals(expected, aircraft1.toString());
    }
}
