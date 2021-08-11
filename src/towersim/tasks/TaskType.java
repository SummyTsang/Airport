package towersim.tasks;

/**
 * Enum to represent the possible types of tasks an aircraft can have.
 */
public enum TaskType {

    /**
     * AWAY means that aircraft are either flying or at other airports.
     */
    AWAY("Flying outside the airport"),
    /**
     * Aircraft in LAND are circling around the airport waiting for a slot to land.
     */
    LAND("Waiting in queue to land"),
    /**
     * WAIT tells an aircraft to stay stationary at a gate and not load any cargo.
     */
    WAIT("Waiting idle at gate"),
    /**
     * LOAD tasks represent the aircraft loading its cargo at the gate.
     */
    LOAD("Loading at gate"),
    /**
     * Aircraft in TAKEOFF are waiting on taxiways for a slot to take off.
     */
    TAKEOFF("Waiting in queue to take off");

    /**
     *  Explain what each task type means.
     */
    private final String description;

    /**
     * Creates a new task type with description
     *
     * @param writtenDescription the description of the task type
     */
    TaskType(String writtenDescription) {
        description = writtenDescription;
    }

    /**
     * Returns the written description of this task type.
     *
     * @return written description
     */
    public String getDescription() {
        return description;
    }
}
