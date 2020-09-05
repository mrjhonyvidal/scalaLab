package project.commands

import project.files.{DirEntry, File}
import project.filesystem.State

class Touch(name: String) extends CreateEntry(name) {
  override def createSpecificEntry(state: State): DirEntry =
    File.empty(state.workingDirectory.path, name)
}
