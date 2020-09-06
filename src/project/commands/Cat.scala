package project.commands
import project.filesystem.State

class Cat(filename: String) extends Command {
  override def apply(state: State): State = {
    val workingDirectory = state.workingDirectory

    val dirEntry = workingDirectory.findEntry(filename)
    if (dirEntry == null || !dirEntry.isFile)
      state.setMessage(filename + ": no such file")
    else
      state.setMessage(dirEntry.asFile.contents)
  }
}
