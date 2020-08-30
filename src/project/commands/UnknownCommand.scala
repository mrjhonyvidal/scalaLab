package project.commands
import project.filesystem.State

/**
 * Command executed whenever the command executed cannot be found
 */
class UnknownCommand extends Command {
  override def apply(state: State): State =
    state.setMessage("Command not found!")
}
