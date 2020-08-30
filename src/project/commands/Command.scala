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

  // Command.from will create the command
  def from(input: String): Command =
    new UnknownCommand
}