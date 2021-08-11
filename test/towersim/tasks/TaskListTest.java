package towersim.tasks;

import org.junit.*;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class TaskListTest {

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
        Task task2 = new Task(TaskType.LOAD, 70);
        Task task3 = new Task(TaskType.LAND);
        Task task4 = new Task(TaskType.TAKEOFF);
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        tasks.add(task4);
        taskList = new TaskList(tasks);
    }

    @Test
    public void getCurrentTaskTest() {
        String expected = "AWAY";
        assertEquals(expected, taskList.getCurrentTask().toString());
    }

    @Test
    public void getNextTaskTest1() {
        String expected = "LOAD at 70%";
        assertEquals(expected, taskList.getNextTask().toString());
    }

    @Test
    public void getNextTaskTest2() {
        String expected = "AWAY";
        taskList.moveToNextTask();
        taskList.moveToNextTask();
        taskList.moveToNextTask();
        assertEquals(expected, taskList.getNextTask().toString());
    }

    @Test
    public void moveToNextTaskTest1() {
        String expected = "AWAY";
        assertEquals(expected, taskList.getCurrentTask().toString());
    }

    @Test
    public void moveToNextTaskTest2() {
        String expected = "LOAD at 70%";
        taskList.moveToNextTask();
        assertEquals(expected, taskList.getCurrentTask().toString());
    }

    @Test
    public void moveToNextTaskTest3() {
        String expected = "LAND";
        taskList.moveToNextTask();
        taskList.moveToNextTask();
        assertEquals(expected, taskList.getCurrentTask().toString());
    }

    @Test
    public void moveToNextTaskTest4() {
        String expected = "TAKEOFF";
        taskList.moveToNextTask();
        taskList.moveToNextTask();
        taskList.moveToNextTask();
        assertEquals(expected, taskList.getCurrentTask().toString());
    }

    @Test
    public void moveToNextTaskTest5() {
        String expected5 = "AWAY";
        taskList.moveToNextTask();
        taskList.moveToNextTask();
        taskList.moveToNextTask();
        taskList.moveToNextTask();
        assertEquals(expected5, taskList.getCurrentTask().toString());
    }

    @Test
    public void toStringTest1() {
        String expected = "TaskList currently on AWAY [1/4]";
        assertEquals(expected, taskList.toString());
    }

    @Test
    public void toStringTest2() {
        String expected = "TaskList currently on LOAD at 70% [2/4]";
        taskList.moveToNextTask();
        assertEquals(expected, taskList.toString());
    }

    @Test
    public void toStringTest3() {
        String expected = "TaskList currently on LAND [3/4]";
        taskList.moveToNextTask();
        taskList.moveToNextTask();
        assertEquals(expected, taskList.toString());
    }

    @Test
    public void toStringTest4() {
        String expected = "TaskList currently on TAKEOFF [4/4]";
        taskList.moveToNextTask();
        taskList.moveToNextTask();
        taskList.moveToNextTask();
        assertEquals(expected, taskList.toString());
    }

    @Test
    public void toStringTest5() {
        String expected = "TaskList currently on AWAY [1/4]";
        taskList.moveToNextTask();
        taskList.moveToNextTask();
        taskList.moveToNextTask();
        taskList.moveToNextTask();
        assertEquals(expected, taskList.toString());
    }
}
