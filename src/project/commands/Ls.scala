package project.commands

import project.files.DirEntry
import project.filesystem.State

class Ls extends Command{
  override def apply(state: State): State = {
    val contents = state.workingDirectory.contents
    val output = createOutput(contents)
    state.setMessage(output)
  }

  def createOutput(contents: List[DirEntry]): String = {
    if (contents.isEmpty) ""
    else {
      val entry = contents.head
      entry.name + "[" + entry.getType + "]\n" + createOutput(contents.tail)
    }
  }
}
