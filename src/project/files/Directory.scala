package project.directory

import project.files.{DirEntry, File}
import project.filesystem.FilesystemException

import scala.annotation.tailrec

class Directory(override val parentPath: String, override val name: String, val contents: List[DirEntry])
  extends DirEntry(parentPath, name) {

  def hasEntry(name: String): Boolean =
    findEntry(name) != null

  def getFoldersTree: List[String] =
    path.substring(1).split(Directory.SEPARATOR).toList.filter(x => !x.isEmpty)
    // /a/b/c/d => List["a", "b", "c", "d"]

  def findDescendant(path: List[String]): Directory =
    if (path.isEmpty) this // If there is not descendant return myself
    else findEntry(path.head).asDirectory.findDescendant(path.tail)

  // Overload Method
  def findDescendant(relativePath: String): Directory =
    if (relativePath.isEmpty) this
    else findDescendant(relativePath.split(Directory.SEPARATOR).toList)

  def removeEntry(entryName: String): Directory =
    if (!hasEntry(entryName)) this
    else new Directory(parentPath, name, contents.filter(x => !x.name.equals(entryName)))

  def addEntry(newEntry: DirEntry): Directory =
    new Directory(parentPath, name, contents :+ newEntry) // :+ append a new value to the List[T]

  def findEntry(entryName: String): DirEntry = {
    @tailrec
    def findEntryHelper(name: String, contentList: List[DirEntry]): DirEntry =
      if(contentList.isEmpty) null
      else if (contentList.head.name.equals(name)) contentList.head
      else findEntryHelper(name, contentList.tail)

      findEntryHelper(entryName, contents)
  }

  def isRoot: Boolean = parentPath.isEmpty

  // Filter out all the folders that do have the Directory we're replacing
  def replaceEntry(entryName: String, newEntry: DirEntry): Directory =
    new Directory(parentPath, name, contents.filter(e => !e.name.equals(entryName)) :+ newEntry)

  def asDirectory: Directory = this

  def asFile: File = throw new FilesystemException("A directory cannot be converted to File")

  def isDirectory: Boolean = true
  def isFile: Boolean = false

  override def getType: String = "Directory "
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
