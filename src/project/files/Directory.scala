package project.directory

import project.files.DirEntry

class Directory(override val parentPath: String, override val name: String, val contents: List[DirEntry])
  extends DirEntry(parentPath, name) {

  def hasEntry(name: String): Boolean = ???

  def getFoldersTree: List[String] =
    path.substring(1).split(Directory.SEPARATOR).toList
    // /a/b/c/d => List["a", "b", "c", "d"]

  def findDescendant(path: List[String]): Directory = ???

  def addEntry(newEntry: DirEntry): Directory = ???

  def findEntry(entryName: String): DirEntry = ???

  def replaceEntry(entryName: String, newEntry: DirEntry): Directory = ???

  def asDirectory: Directory = ???
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
