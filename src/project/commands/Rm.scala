package project.commands
import project.directory.Directory
import project.filesystem.State

class Rm(name: String) extends Command {
  override def apply(state: State): State = {
    // 1. get working directory
    val workingDirectory = state.workingDirectory

    // 2. get the absolute path
    val absolutePath = {
      if (name.startsWith(Directory.SEPARATOR)) name
      else if (workingDirectory.isRoot) workingDirectory.path + name
      else workingDirectory.path + Directory.SEPARATOR + name
    }

    // 3. do some checks
    if (Directory.ROOT_PATH.equals(absolutePath))
      state.setMessage("Nuclear war not support yet!") // rm /
    else
      doRm(state, absolutePath)
  }

  def doRm(state: State, path: String): State = {

    /**
     * Example of use of the rmAndReturnseNewRootHelper
     *
     * /a => ["a"]
     *  isEmpty?
     *    new root without the folder a
     *
     * /a/b => ["a", "b"]
     *  ? is Empty?
     *    nextDirectory = /a
     *      rmAndReturnsNewRootHelper(/a, ["b"])
     *          isEmpty?
     *            new /a
     *      root.relace("a", new /a) = new root
     */
    def rmAndReturnsNewRootHelper(currentDirectory: Directory, path: List[String]): Directory = {
      if(path.isEmpty) currentDirectory
      else if (path.tail.isEmpty) currentDirectory.removeEntry(path.head)
      else {
        val nextDirectory = currentDirectory.findEntry(path.head)
        if (!nextDirectory.isDirectory) currentDirectory // return currentDirectory no remove happened
        else {
          val newNextDirectory = rmAndReturnsNewRootHelper(nextDirectory.asDirectory, path.tail)
          if (newNextDirectory == nextDirectory) currentDirectory
          else currentDirectory.replaceEntry(path.head, newNextDirectory)
        }
      }
    }

    // 4. find the entry to remove
    // 5. update structure like we do for mkdir
    val tokens = path.substring(1).split(Directory.SEPARATOR).toList
    val newRoot: Directory = rmAndReturnsNewRootHelper(state.root, tokens)

    if (newRoot == state.root)
        state.setMessage(path + "no such file or directory")
    else
      State(newRoot, newRoot.findDescendant(state.workingDirectory.path.substring(1))) // state.workingDirectory.path.substring(1) relative path of old workingDirectory relative to the new root
  }
}
