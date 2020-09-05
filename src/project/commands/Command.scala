package project.commands

import project.filesystem.State

/**
 * Traits are used to share interfaces and fields between classes.
 * Similar to Java 8's interfaces.
 * Classes and objects can extend traits, but traits cannot be instantiated and have no parameters.
 */
trait Command {

  // Transform a ourproject.filesystem.State into another ourproject.filesystem.State
  def apply(state: State): State
}

object Command {

  val MKDIR = "mkdir"
  val LS = "ls"
  val PWD = "pwd"

  // Just return the state
  def emptyCommand: Command = new Command {
    override def apply(state: State): State = state
  }

  def incompleteCommand(name: String): Command = new Command {
    override def apply(state: State): State =
      state.setMessage(name + ": incomplete command!")
  }

  // Command.from will create the command
  def from(input: String): Command = {
    val tokens: Array[String] = input.split(" ")

    // TODO Refactor applying design pattern
    if (input.isEmpty || tokens.isEmpty) emptyCommand
    else if(MKDIR.equals(tokens(0))) {
      if (tokens.length < 2) incompleteCommand("mkdir")
      else new Mkdir(tokens(1))
    } else if (LS.equals(tokens(0))) {
      new Ls
    } else if (PWD.equals(tokens(0))){
      new Pwd
    }else new UnknownCommand
  }
}