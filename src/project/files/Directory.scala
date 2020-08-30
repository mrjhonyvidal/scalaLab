package project.directory

import project.files.DirEntry

class Directory(override val parentPath: String, override val name: String, val contents: List[DirEntry])
  extends DirEntry(parentPath, name) {

}

// Companion object of class Directory
object Directory {
  val SEPARATOR = "/"
  val ROOT_PATH = "/"

  // Simple utility method
  // Creates a Directory with no parent path and empty name
  def ROOT: Directory = Directory.empty("","")

  // Method Constructor call instead of direct{ instantiation
  def empty(parentPath: String, name: String): Directory = {
    new Directory(parentPath, name, List())
  }
}
