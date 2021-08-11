package towersim.tasks;

/**
 * Represents a task currently assigned to an aircraft.
 */
public class Task {

    /**
     * Type of task
     */
    private TaskType type;
    /**
     * Percentage of maximum capacity to load
     */
    private int loadPercent;

    /**
     * Creates a new Task of the given task type.
     *
     * @param type type of task
     */
    public Task(TaskType type) {
        this.type = type;
        this.loadPercent = 0;
    }

    /**
     * Creates a new Task of the given task type and stores the given load
     * percentage in the task.
     *
     * @param type        type of task
     * @param loadPercent percentage of maximum capacity to load
     */
    public Task(TaskType type, int loadPercent) {
        this.type = type;
        this.loadPercent = loadPercent;
    }

    /**
     * Returns the type of this task.
     *
     * @return task type
     */
    public TaskType getType() {
        return type;
    }

    /**
     * Returns the load percentage specified when constructing the task, or 0
     * if none was specified.
     *
     * @return task load percentage
     */
    public int getLoadPercent() {
        return loadPercent;
    }

    /**
     * Returns the human-readable string representation of this task.
     *
     * @return string representation of this task
     */
    @Override
    public String toString() {
        if (type == TaskType.LOAD) {
            String description = "LOAD at " + loadPercent + "%";
            return description;
        }
        return getType().toString();
    }
}
