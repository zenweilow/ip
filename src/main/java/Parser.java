/**
 * Parses user input and extracts relevant information such as
 * command words, task descriptions, and task indexes.
 */
public class Parser {
    private static final String DEADLINE_BY = " /by ";
    private static final String EVENT_FROM = " /from ";
    private static final String EVENT_TO = " /to ";

    /**
     * Returns the command word extracted from the user input.
     *
     * @param input Raw user input.
     * @return Command word entered by the user.
     */
    public String parseCommandWord(String input) {
        String trimmedInput = input.trim();

        if (trimmedInput.isEmpty()) {
            return "";
        }

        String[] parts = trimmedInput.split("\\s+", 2);
        return parts[0];
    }

    /**
     * Returns the description of a todo task from the user input.
     *
     * @param input User input containing a todo command.
     * @return Description of the todo task.
     * @throws HogriderException If the description is empty.
     */
    public String parseTodo(String input) throws HogriderException {
        String description = input.substring("todo".length()).trim();

        if (description.isEmpty()) {
            throw new HogriderException("todo cannot be empty leh. Use: todo <description>");
        }

        return description;
    }

    /**
     * Returns the description and deadline extracted from the user input.
     *
     * @param input User input containing a deadline command.
     * @return Array containing the task description and deadline.
     * @throws HogriderException If the input format is invalid.
     */
    public String[] parseDeadline(String input) throws HogriderException {
        String rest = input.substring("deadline".length()).trim();
        int byIndex = rest.indexOf(DEADLINE_BY);

        if (byIndex == -1) {
            throw new HogriderException("format wrong leh. Use: deadline <desc> /by <when>");
        }

        String description = rest.substring(0, byIndex).trim();
        String by = rest.substring(byIndex + DEADLINE_BY.length()).trim();

        if (description.isEmpty()) {
            throw new HogriderException("deadline description cannot be empty leh. Use: deadline <desc> /by <when>");
        }

        if (by.isEmpty()) {
            throw new HogriderException("deadline /by cannot be empty leh. Use: deadline <desc> /by <when>");
        }

        return new String[]{description, by};
    }

    /**
     * Returns the description, start time, and end time extracted
     * from the user input.
     *
     * @param input User input containing an event command.
     * @return Array containing the description, start time, and end time.
     * @throws HogriderException If the input format is invalid.
     */
    public String[] parseEvent(String input) throws HogriderException {
        String rest = input.substring("event".length()).trim();
        int fromIndex = rest.indexOf(EVENT_FROM);
        int toIndex = rest.indexOf(EVENT_TO);

        if (fromIndex == -1 || toIndex == -1 || toIndex < fromIndex) {
            throw new HogriderException("format wrong leh. Use: event <desc> /from <start> /to <end>");
        }

        String description = rest.substring(0, fromIndex).trim();
        String from = rest.substring(fromIndex + EVENT_FROM.length(), toIndex).trim();
        String to = rest.substring(toIndex + EVENT_TO.length()).trim();

        if (description.isEmpty()) {
            throw new HogriderException("event description cannot be empty leh. Use: event <desc> /from <start> /to <end>");
        }

        if (from.isEmpty() || to.isEmpty()) {
            throw new HogriderException("event /from and /to cannot be empty leh. Use: event <desc> /from <start> /to <end>");
        }

        return new String[]{description, from, to};
    }

    public int parseIndex(String input, String commandWord, int taskCount) throws HogriderException {
        String[] parts = input.trim().split("\\s+");

        if (parts.length != 2) {
            throw new HogriderException("format wrong leh. Use: " + commandWord + " <taskNumber>");
        }

        int oneBased;
        try {
            oneBased = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            throw new HogriderException("task number must be a number leh.");
        }

        int zeroBased = oneBased - 1;
        if (zeroBased < 0 || zeroBased >= taskCount) {
            throw new HogriderException("no such task number leh.");
        }

        return zeroBased;
    }

    /**
     * Returns the keyword used to search tasks.
     *
     * @param input User input containing the find command.
     * @return Keyword used to match task descriptions.
     * @throws HogriderException If the keyword is empty.
     */
    public String parseFind(String input) throws HogriderException {
        String keyword = input.substring("find".length()).trim();

        if (keyword.isEmpty()) {
            throw new HogriderException("find keyword cannot be empty leh. Use: find <keyword>");
        }

        return keyword;
    }
}
