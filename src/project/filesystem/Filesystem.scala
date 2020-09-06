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

  /**
   * foldLeft behaviour: [1,2,3,4]
   * 0 (op) 1 => 1
   * 1 (op) 2 => 3
   * 3 (op) 3 => 6
   * 6 (op) 4 => 10 your last value, 10
   *
   * List(1, 2, 3, 4).foldLeft(0)((x, y) => x + y)
    */
  //io.Source.stdin.getLines = Iterator[String]
  io.Source.stdin.getLines().foldLeft(State(root, root))((currentState, newLine) => {
    currentState.show
    val newState = Command.from(newLine).apply(currentState)
    newState // or just Command.from(newLine).apply(currentState)
  })

  /**
  Code replaced by the above functional way of create new state using Lambda and Anonymous Function
  Avoid stateful "var" as much as posible!
  Recommended: process states in a functional way

  var state = State(root, root)
  val scanner = new Scanner(System.in)

  /**
   * We open a terminal waiting infitely for user input it
   */
  while(true) {
    state.show
    val input = scanner.nextLine()
    state = Command.from(input).apply(state)
  }
  **/
}
