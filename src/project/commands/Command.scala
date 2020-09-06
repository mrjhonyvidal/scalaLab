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
  val TOUCH = "touch"
  val CD = "cd"
  val RM = "rm"
  val ECHO = "echo"
  val CAT = "cat"

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

    // We return a Scala PartialFunction
    val availableCommands: List[PartialFunction[String, Command]] = List(
      {
        case MKDIR => if (tokens.length < 2) incompleteCommand(MKDIR) else new Mkdir(tokens(1))
        case LS => new Ls
        case PWD => new Pwd
        case TOUCH => if (tokens.length < 2) incompleteCommand(TOUCH) else new Touch(tokens(1))
        case CD => if (tokens.length < 2) incompleteCommand(CD) else new Cd(tokens(1))
        case RM => if (tokens.length < 2) incompleteCommand(RM) else new Rm(tokens(1))
        case ECHO => if (tokens.length < 2) incompleteCommand(ECHO) else new Echo(tokens.tail)
        case CAT => if (tokens.length < 2) incompleteCommand(CAT) else new Cat(tokens(1))
        case _ => new UnknownCommand
      }
    )

    if (input.isEmpty || tokens.isEmpty) emptyCommand
    else {
      // Traverse the list of Commands and apply the .orElse function to the traversal as we defined as a PartialFunction
      availableCommands.tail.foldLeft(availableCommands.head)(_.orElse(_)) {
        tokens(0) // tokens(0) first input from the user
      }
    }
  }
}