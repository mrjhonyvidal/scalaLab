package project.files

import project.directory.Directory

abstract class DirEntry(val parentPath: String, val name: String) {

  def path: String = parentPath + Directory.SEPARATOR + name
}