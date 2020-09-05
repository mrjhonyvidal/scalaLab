package project.commands
import project.directory.Directory
import project.files.DirEntry
import project.filesystem.State

class Mkdir(folderName: String) extends CreateEntry(folderName) {
  override def createSpecificEntry(state: State): DirEntry =
    Directory.empty(state.workingDirectory.path, folderName)
}
