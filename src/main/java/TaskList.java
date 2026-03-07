import java.util.ArrayList;

/**
 * Represents the list of tasks managed by the chatbot.
 * Provides operations to add, delete, mark, unmark,
 * and search tasks.
 */
public class TaskList {
    private static final int MAX_ITEMS = 100;
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the task list.
     *
     * @param task Task to be added.
     * @throws HogriderException If the task list exceeds the maximum size.
     */
    public void addTask(Task task) throws HogriderException {
        if (tasks.size() >= MAX_ITEMS) {
            throw new HogriderException("eh bro cannot add already, max 100 items");
        }
        tasks.add(task);
    }

    /**
     * Removes the task at the specified index.
     *
     * @param index Index of the task to be removed.
     * @return The removed task.
     */
    public Task deleteTask(int index) {
        return tasks.remove(index);
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }

    public void markTask(int index) {
        tasks.get(index).mark();
    }

    public void unmarkTask(int index) {
        tasks.get(index).unmark();
    }

    public int size() {
        return tasks.size();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

    /**
     * Returns a list of tasks that contain the specified keyword
     * in their description.
     *
     * @param keyword Keyword used for searching tasks.
     * @return TaskList containing matching tasks.
     */
    public TaskList findTasks(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();

        for (Task task : tasks) {
            if (task.getDescription().contains(keyword)) {
                matchingTasks.add(task);
            }
        }

        return new TaskList(matchingTasks);
    }

}
