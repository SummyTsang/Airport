package towersim.tasks;

import java.util.Iterator;
import java.util.List;

/**
 * Represents a circular list of tasks for an aircraft to cycle through.
 */
public class TaskList {

    /**
     * List of tasks
     */
    private List<Task> tasks;
    /**
     * Current task in list of tasks
     */
    private Task task;
    /**
     * Iterator of list of tasks
     */
    private Iterator tasksIterator;

    /**
     * Creates a new TaskList with the given list of tasks.
     *
     * @param tasks list of tasks
     */
    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
        tasksIterator = tasks.listIterator();
        task = (Task) tasksIterator.next();
    }

    /**
     * Returns the current task in the list.
     *
     * @return current task
     */
    public Task getCurrentTask() {
        return task;
    }

    /**
     * Returns the task in the list that comes after the current task.
     *
     * @return next task
     */
    public Task getNextTask() {
        int index = tasks.indexOf(task);
        if (index == tasks.size() - 1) {
            index = -1;
        }
        return tasks.get(index + 1);
    }

    /**
     * Moves the reference to the current task forward by one in the circular
     * task list.
     */
    public void moveToNextTask() {
        if (tasksIterator.hasNext()) {
            task = (Task) tasksIterator.next();
        } else {
            tasksIterator = tasks.listIterator();
            task = (Task) tasksIterator.next();
        }
    }

    /**
     * Returns the human-readable string representation of this task list.
     *
     * @return string representation of this task list
     */
    @Override
    public String toString() {
        return "TaskList currently on " + getCurrentTask()
                + " [" + (tasks.indexOf(task) + 1) + "/" + tasks.size() + "]";
    }
}
