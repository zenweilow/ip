import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private static final String SEPARATOR = " \\| ";
    private final Path filePath;

    public Storage(String filePath) {
        this.filePath = Paths.get(filePath);
    }

    public ArrayList<Task> load() throws HogriderException {
        createFileIfMissing();

        ArrayList<Task> loadedTasks = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(filePath);

            for (String line : lines) {
                Task task = parseTask(line);
                loadedTasks.add(task);
            }
        } catch (IOException e) {
            throw new HogriderException("cannot read save file leh.");
        }

        return loadedTasks;
    }

    public void save(ArrayList<Task> items) throws HogriderException {
        createFileIfMissing();

        ArrayList<String> lines = new ArrayList<>();
        for (Task task : items) {
            lines.add(formatTask(task));
        }

        try {
            Files.write(filePath, lines);
        } catch (IOException e) {
            throw new HogriderException("cannot save tasks leh.");
        }
    }

    private void createFileIfMissing() throws HogriderException {
        try {
            Path parent = filePath.getParent();

            if (parent != null && !Files.exists(parent)) {
                Files.createDirectories(parent);
            }

            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
        } catch (IOException e) {
            throw new HogriderException("cannot create data file leh.");
        }
    }

    private Task parseTask(String line) throws HogriderException {
        String[] parts = line.split(SEPARATOR);

        if (parts.length < 3) {
            throw new HogriderException("save file is corrupted leh.");
        }

        String taskType = parts[0];
        String isDoneText = parts[1];
        String description = parts[2];

        Task task;

        switch (taskType) {
        case "T":
            task = new Todo(description);
            break;
        case "D":
            if (parts.length != 4) {
                throw new HogriderException("save file is corrupted leh.");
            }
            task = new Deadline(description, parts[3]);
            break;
        case "E":
            if (parts.length != 5) {
                throw new HogriderException("save file is corrupted leh.");
            }
            task = new Event(description, parts[3], parts[4]);
            break;
        default:
            throw new HogriderException("save file is corrupted leh.");
        }

        if (isDoneText.equals("1")) {
            task.mark();
        } else if (!isDoneText.equals("0")) {
            throw new HogriderException("save file is corrupted leh.");
        }

        return task;
    }

    private String formatTask(Task task) {
        String doneFlag = task.isDone() ? "1" : "0";

        if (task instanceof Todo) {
            return "T | " + doneFlag + " | " + task.getDescription();
        } else if (task instanceof Deadline) {
            Deadline deadline = (Deadline) task;
            return "D | " + doneFlag + " | " + deadline.getDescription() + " | " + deadline.getBy();
        } else {
            Event event = (Event) task;
            return "E | " + doneFlag + " | " + event.getDescription() + " | " + event.getFrom()
                    + " | " + event.getTo();
        }
    }
}