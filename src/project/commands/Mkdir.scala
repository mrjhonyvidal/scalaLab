package project.commands
import project.directory.Directory
import project.filesystem.State

class Mkdir(folderName: String) extends Command {

  override def apply(state: State): State = {
    val workingDirectory = state.workingDirectory

    // TODO Refactor if/else and apply Scala Design Pattern
    if(workingDirectory.hasEntry(folderName)){
      state.setMessage("Entry " + folderName + " already exists!")
    } else if (folderName.contains(Directory.SEPARATOR)) {
      state.setMessage(folderName + " must not contain separators!")
    }else if (checkIllegal(folderName)) {
      state.setMessage(folderName + ": illegal entry name!")
    } else {
      executeMkdir(state, folderName)
    }
  }

  def checkIllegal(name: String): Boolean = {
    name.contains(".")
  }

  def executeMkdir(state: State, str: String): State = {

    ??? // Not implemented yet - TODO

    //val workingDirectory = state.workingDirectory
    //val fullPath = workingDirectory.path

    // Traverse this entire data structure starting from the root

    // 1. All the directories in the full path

    // 2. Create new directory entry in the working directory

    // 3. Update the whole directory structure starting from the root, now with the new directory
    // (the directory structure is INMUTABLE)

    // 4. Find new working directory INSTANCE given working directory's full path, in the NEW directory structure
  }
}
