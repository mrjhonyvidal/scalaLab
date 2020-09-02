package project.files

import project.directory.Directory

abstract class DirEntry(val parentPath: String, val name: String) {

  def path: String = parentPath + Directory.SEPARATOR + name

  // TODO for files this should throw an exception, files cannot be convert to directory or vice-versa
  def asDirectory: Directory
}