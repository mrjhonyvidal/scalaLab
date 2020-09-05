package project.commands

import project.directory.Directory
import project.files.DirEntry
import project.filesystem.State

abstract class CreateEntry(entryName: String) extends Command {
  override def apply(state: State): State = {
    val workingDirectory = state.workingDirectory

    if(workingDirectory.hasEntry(entryName)){
      state.setMessage("Entry " + entryName + " already exists!")
    } else if (entryName.contains(Directory.SEPARATOR)) {
      state.setMessage(entryName + " must not contain separators!")
    }else if (checkIllegal(entryName)) {
      state.setMessage(entryName + ": illegal entry name!")
    } else {
      doCreateEntry(state, entryName)
    }
  }

  def checkIllegal(name: String): Boolean = {
    name.contains(".")
  }

  def doCreateEntry(state: State, name: String): State = {

    // Returns the new root which is of type Directory
    def updateStructure(currentDirectory: Directory, path: List[String], newEntry: DirEntry): Directory = {
      if( path.isEmpty) currentDirectory.addEntry(newEntry)
      else {
        val oldDirEntry = currentDirectory.findEntry(path.head) .asDirectory// findEntry always will be a directory
        currentDirectory.replaceEntry(oldDirEntry.name, updateStructure(oldDirEntry, path.tail, newEntry))
      }
    }

    val workingDirectory = state.workingDirectory
    val pathFoldersTree = workingDirectory.getFoldersTree
    val newEntry: DirEntry = createSpecificEntry(state)
    val newRoot = updateStructure(state.root, pathFoldersTree, newEntry)
    val newWorkingDirectory = newRoot.findDescendant(pathFoldersTree)

    State(newRoot, newWorkingDirectory)
  }

  // Abstract methods
  def createSpecificEntry(state: State): DirEntry
}
