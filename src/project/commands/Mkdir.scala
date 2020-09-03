package project.commands
import project.directory.Directory
import project.files.DirEntry
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

  def executeMkdir(state: State, name: String): State = {

    // Returns the new root which is of type Directory
    def updateStructure(currentDirectory: Directory, path: List[String], newEntry: DirEntry): Directory = {
      /**
       * someDirectory
       *  /a
       *  /b
       *  (new) /d
       *
       *  => new someDir
       *  /a
       *    /...
       *  /b
       *    /...
       *  /d
       *
       *  Nested Paths
       *  /a/b/c
       *      /d
       *      /e
       *      (new) /f
       *
       * new a
       *    new /b (parent/a)
       *      new /c (parent b)
       *          /d
       *          /e
       *          /f
       *
       * We will use recursion for achieve the algorithm necessary to updateStructure
       * the recursion approach starts with the finish case in mind
       */
      if( path.isEmpty) currentDirectory.addEntry(newEntry)
      else {
        val oldDirEntry = currentDirectory.findEntry(path.head) .asDirectory// findEntry always will be a directory
        currentDirectory.replaceEntry(oldDirEntry.name, updateStructure(oldDirEntry, path.tail, newEntry))
      }

      /**
       * Example of how the updateStructure running recursively
       * /a/b
       *  (contents)
       *  (new entry) /e
       *
       *
       *  ==Read from bottom to up==
       *  newRoot = updateStructure(root, ["a","b"], /e) = root.replaceEntry("a", updateStructure(/a, ["b"], /e) = /a.replaceEntry("b", updateStructure(/b, [], /e) = /b.add(/e)
       *    => path.isEmpty?
       *    => oldEntry = /a
       *    root.replaceEntry("a", updateStructure(/a, ["b"], /e) = /a.replaceEntry("b", updateStructure(/b, [], /e) = /b.add(/e)
       *      => path.isEmpty?
       *      => oldEntry = /b
       *      /a.replaceEntry("b", updateStructure(/b, [], /e) = /b.add(/e)
       *        => path.isEmpty? => /b.add(/e) -- new Directory Structure
       */
    }


    val workingDirectory = state.workingDirectory

    // Traverse this entire data structure starting from the root
    val pathFoldersTree = workingDirectory.getFoldersTree

    // 2. Create new directory entry in the working directory
    val newDir = Directory.empty(workingDirectory.path, name)

    // 3. Update the whole directory structure starting from the root, now with the new directory
    // (the directory structure is INMUTABLE)
    val newRoot = updateStructure(state.root, pathFoldersTree, newDir)

    // 4. Find new working directory INSTANCE given working directory's full path, in the NEW directory structure
    val newWorkingDirectory = newRoot.findDescendant(pathFoldersTree)

    // Update State with the new root and working directory created
    State(newRoot, newWorkingDirectory)
  }
}
