package project.commands
import project.directory.Directory
import project.files.DirEntry
import project.filesystem.State

import scala.annotation.tailrec

class Cd(dir: String) extends Command {
  override def apply(state: State): State = {
    /**
     * cd /something/somethingElse/.../ ==> Absolute path
     * cd a/b/c = relative to the current working directory
     *
     * cd ..
     * cd .
     * cd a/./.././a/
     */

    // 1. find the root
    val root = state.root
    val workingDirectory = state.workingDirectory

    // 2. find the absolute path of the directory I want to cd to, wether if is absolute or relative
    val absolutePath = {
      if (dir.startsWith(Directory.SEPARATOR)) dir
      else if (workingDirectory.isRoot) workingDirectory.path + dir
      else workingDirectory.path + Directory.SEPARATOR + dir
    }

    // 3. find the directory to cd to, given the path
    val destinationDirectory = doFindEntry(root, absolutePath)

    // 4. change the state given the new directory
    if (destinationDirectory == null || !destinationDirectory.isDirectory)
      state.setMessage(dir + ": no such directory")
    else
      State(root, destinationDirectory.asDirectory)
  }

  def doFindEntry(root: Directory, path: String): DirEntry = {

    @tailrec
    def findEntryHelper(currentDirectory: Directory, path: List[String]): DirEntry = {
      if(path.isEmpty || path.head.isEmpty) currentDirectory
      else if (path.tail.isEmpty) currentDirectory.findEntry(path.head)
      else {
        val nextDir = currentDirectory.findEntry(path.head)
        if (nextDir == null || !nextDir.isDirectory) null
        else findEntryHelper(nextDir.asDirectory, path.tail)
      }
    }

    /**
     * Function following the principles of functional programming
     * @param path
     * @param result ACCUMULATOR
     * @return
     */
    @tailrec
    def collapseRelativeTokens(path: List[String], result: List[String]): List[String] = {
      /**
       * Validating @tailrec function behaviour:
       * /a/b = will be separated by slash (/) by: val tokens: List[String] = path.substring(1).split(Directory.SEPARATOR).toList
       *
       * ["a", "b"]
       *
       * path.isEmpty?
       *  no
       *  then: collapseRelativeTokens(path.tail, result :+ path.head)
       *  result accumulator start is a List() append path.head
       *  callRecursiveTail-cRT(["b"], result = List +: "a" => ["a"])
       *    path.isEmpty?
       *      no
       *      then: cRT([], result = ["a"] :+ "b" = ["a", "b"])
       *        path.isEmpty?
       *
       * /a/.. => ["a", ".."]
       * path.isEmpty?
       *  cRT([".."], [] :+ "a" = ["a"])
       *    path.isempty?
       *      cRt([], []) = [] (result (our accumulator))
       *
       * /a/b/.. => ["a", "b", ".."]
       *  path.isEmpty?
       *    cRT(["b", ".."], ["a"])
       *      path.isEmpty?
       *        cRT[".."], ["a", "b"]
       *          path.isEmpty?
       *           cRT([]. ["b"])
       *
       *  /a/b/c/..
       *    ...
       *      cRT([".."], ["a", "b", "c"])
       *        isEmpty?
       *          cRT([], ["a", "b"])
       */
      if( path.isEmpty) result
      else if (".".equals(path.head)) collapseRelativeTokens(path.tail, result)
      else if ("..".equals(path.head)) {
        if (result.isEmpty) null
        else collapseRelativeTokens(path.tail, result.init) // List init() It returns all the elements of the list except the last one
      } else collapseRelativeTokens(path.tail, result :+ path.head)
    }

    // 1. tokens
    val tokens: List[String] = path.substring(1).split(Directory.SEPARATOR).toList
    /**
     * TODO 1.1 eliminate/collapse relative tokens
     *
     * . => (means "relative path to the folder, does not change anything")
     * .. => (means move to the parent of the folder before the folder, /a/b/../ move to /a which is the parent folder of b)
     *
     * Examples on how it will work
     * ["a", "."] => [a]
     * /a/b/./. => ["a","b",".","."] => ["a","b"] (absolute path)
     *
     * /a/../ => ["a",".."] => []
     * /a/b/.. =>["a", "b", ".."] => ["a"]
     * /a/b/.. => ["a","b", ".."]
     */
    val newTokens = collapseRelativeTokens(tokens, List())
    // In the List() we will store the result of the computation of
    // collapseRelativeToken function with will be Tail Recursive

    /**
     * 2. navigate to correct entry
     * we have the checker if the folder we want to move is null
     * if (destinationDirectory == null || !destinationDirectory.isDirectory)
     * state.setMessage(dir + ": no such directory")
     */
    if (newTokens == null) null
    else findEntryHelper(root, newTokens)
  }
}
