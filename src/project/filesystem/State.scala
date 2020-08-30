package project.filesystem

import project.directory.Directory

/**
 * State will hold the Root and working directory we're have in our filesystem
 * Output the message of the previous command executed on the shell.
 */
class State(val root: Directory, val workingDirectory: Directory, val output: String) {

  def show: Unit = {
    println(output)
    print(State.SHELL_TOKEN)
  }

  // emphasize immutability by returning a new State on setting a new message
  def setMessage(message: String): State =

    // This State with parenthesis call our apply method defined on the object State
    State(root, workingDirectory, message)
}

// Companion Object
object State {
  val SHELL_TOKEN = "$ "

  // Factory Method
  def apply(root: Directory, workingDirectory: Directory, output: String = ""): State =
     new State(root, workingDirectory, output)
}