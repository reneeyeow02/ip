package duke.commands;

import duke.Storage;
import duke.TaskList;
import duke.Ui;

public class FindCommand extends Command {

    private String userInput;

    public FindCommand(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        tasks.findTask(userInput);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
