package lectures.part2oop

object CaseClasses extends App {

  /**
   * Case classes are useful shorthand for defining a class and the companion object in one go
   * Quick lightweight data structure with little boilerplate
   * Sensible equals, hashCode, toString out of box
   * Auto-promoted params to fields
   * Cloning
   * Extractable in pattern matching
   */
  case class Person(name: String, age: Int)

  // 1. Case Classes "promote" all parameters to FIELDS
  val jim = new Person("Jim", 34)
  println(jim.name)

  // 2. Sensible toString
  // println(instance) = println(instance.toString) // syntactic sugar
  println(jim.toString)

  // 3. equals and hashCode implemented Out of Box
  val jim2 = new Person("Jim", 34)
  println(jim == jim2)

  // 4. Case Classes have handy copy method
  val jim3 = jim.copy(age = 45) // returns another instance of Person with same parameters of Jim but with different age
  println(jim3)

  // 5. Case Class have companion objects
  val thePerson = Person // No need of the keyword new to instantiate a Case Class
  val mary = Person("Mary", 23)

  // 6. Case Class are serializable
  // Which makes classes especially useful when dealing with distributed systems
  // You can send instances of case  classes through the network and in between JVM
  // Akka - our messages passed through the network are in general Case Classes

  // 7. Case Classes have extracted patterns = Case Classes can be used in PATTERN MATCHING

  case object UnitedKingdom {
    def name: String = "The UK of GB and NI"
  }
}
