package lectures.part2oop

import playground.{AnotherRandom, Random => Rx1}
//import playground._ // Best practices, only use this way if you only need

import java.util.Date
import java.sql.{Date => SqlDate}

object PackagingAndImports extends App {

  /**
   * Topics:
   *  - package = a group of definitions under the same name
   *  - To use a definition: be in the same package
   *  - or import the package
   *
   *  - Best Practice: mirror the file structure
   *  - Fully qualified name
   *  !!- <package objects> hold standalone methods/constants - one per package
   */

  // package members are accesible by their simple name
  val writer = new Writer("Daniel", "RockTheJVM", 2018)

  // Import the package
  val random = new Rx1 // playground.Random full qualified name

  // packages are in hierarchy
  // matching folder structure

  // package object
  // Universal constants or universal methods that reside out of a class
  sayHello
  println(SPEED_OF_LIGHT)

  // Imports
  val anotherRandom = new AnotherRandom

  val date = new Date // Compiler will asume the first import
  //val sqlDate = new java.sql.Date(2018, 5, 4) // "May 4th be with you"
  val sqlDate = new SqlDate(2018, 5, 4) // "May 4th be with you"

  // 2. use aliasing

  // default imports
  // java.lang - String, Object, Exception
  // scala - Int, Nothing, Function
  // scala.Predef - println, ???
}
