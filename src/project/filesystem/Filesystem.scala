package project.filesystem

import java.util.Scanner

import project.commands.Command
import project.directory.Directory

/**
 * Main entry point of our Project Custom Filesystem
 */
object Filesystem extends App {

  // Creates the ROOT for us
  val root = Directory.ROOT
  var state = State(root, root) // Stateful TODO: Apply Pure Function approach
  val scanner = new Scanner(System.in)

  /**
   * We open a terminal waiting infitely for user input it
   */
  while(true) {
    state.show
    val input = scanner.nextLine()
    state = Command.from(input).apply(state)
  }
}
