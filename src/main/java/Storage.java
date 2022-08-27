import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {

    private String filePath = "./data/duke.txt";
    private static File saveFile;
    private ArrayList<Task> taskList = new ArrayList<>(100);

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    //TODO: file isn't being loaded even when one exists
    public void checkFileExists() throws IOException {
        //Create save file
        try {
            saveFile = new File(filePath);
            File parent = saveFile.getParentFile();

            if (parent == null) {
                saveFile.mkdirs();
            }

            if (!saveFile.exists()) {
                saveFile.createNewFile();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

        public void save(ArrayList taskList) {
            this.taskList = taskList;
            PrintWriter printWriter = null;
            try {
                printWriter = new PrintWriter(saveFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            PrintWriter finalPrintWriter = printWriter;

            taskList.forEach((t) -> {
                Task k = (Task) t;
                finalPrintWriter.println(k.getSaveData());
            });
            printWriter.close();
        }

    public ArrayList<Task> parseSaveFile(File txt) {
        try {
            txt.getAbsolutePath();
            txt.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner sc = null;
        try {
            sc = new Scanner(txt);
            while (sc.hasNextLine()) {
                String text = sc.nextLine();

                String[] split = text.split(" \\| ");
                String firstChar = split[0];

                switch (firstChar) {
                    case "T":
                        String taskName = split[2];

                        Task todo = new ToDos(taskName);
                        taskList.add(todo);

                        boolean isDone = Boolean.parseBoolean(split[1]);
                        if (isDone) {
                            todo.markAsDone();
                        }
                        break;

                    case "D":
                        taskName = split[2];

                        LocalDateTime date = LocalDateTime.parse(split[3]);

                        Task deadline = new Deadlines(taskName, date);
                        taskList.add(deadline);

                        isDone = Boolean.parseBoolean(split[1]);
                        if (isDone) {
                            deadline.markAsDone();
                        }
                        break;

                    case "E":
                        taskName = split[2];

                        String time = split[3];

                        Task event = new Events(taskName, time);
                        taskList.add(event);

                        isDone = Boolean.parseBoolean(split[1]);
                        if (isDone) {
                            event.markAsDone();
                        }
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return taskList;
    }

    public ArrayList<Task> load() {
        try {
            checkFileExists();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parseSaveFile(saveFile);
    }

    public void setTaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

}