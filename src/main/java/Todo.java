/**
 * Represents a todo task without any associated date or time.
 */
public class Todo extends Task {
    public Todo(String description) {
        super(description);
}

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}