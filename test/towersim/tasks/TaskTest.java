package towersim.tasks;

import org.junit.*;
import static org.junit.Assert.*;

public class TaskTest {

    Task task1;
    Task task2;
    Task task3;
    Task task4;

    @Before
    public void setup() {
        task1 = new Task(TaskType.LAND);
        task2 = new Task(TaskType.TAKEOFF);
        task3 = new Task(TaskType.WAIT);
        task4 = new Task(TaskType.LOAD,20);
    }

    @Test
    public void getTypeTest() {
        String expected = "Waiting in queue to land";
        assertEquals(expected, task1.getType().getDescription());
    }

    @Test
    public void getLoadPercentTest() {
        int expected = 0;
        assertEquals(expected, task1.getLoadPercent());
    }

    @Test
    public void toStringTest1() {
        String expected = "LAND";
        assertEquals(expected, task1.toString());
    }

    @Test
    public void toStringTest2() {
        String expected = "WAIT";
        assertEquals(expected, task3.toString());
    }

    @Test
    public void toStringTest3() {
        String expected = "LOAD at 20%";
        assertEquals(expected, task4.toString());
    }
}
