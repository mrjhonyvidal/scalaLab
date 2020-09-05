package project.commands

import project.filesystem.State

class Pwd extends Command {
  override def apply(state: State): State =
    state.setMessage(state.workingDirectory.path)
}
