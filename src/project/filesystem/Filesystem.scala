package project.filesystem

import java.util.Scanner

object Filesystem extends App {

  val scanner = new Scanner(System.in)

  /**
   * We open a terminal waiting infitely for user input it
   */
  while(true) {
    print("$ ")
    println(scanner.nextLine())
  }
}
