package lectures.advanced

object AdvancedPatternMatching extends App {
  val numbers = List(1)
  val description = numbers match {
    case head :: Nil => println(s"the only element is $head.")
    case _ =>
  }

  /**
   * - constants
   * - wildcards
   * - case classes
   * - tuples
   * - some special magic likes above
   */
  class Person(val name: String, val age: Int)

  // Make a class Pattern Match
  object Person {
    def unapply(person: Person): Option[(String, Int)] = {
      if (person.age < 21) None
      else Some((person.name, person.age))
    }

    def unapply(age: Int): Option[String] =
      Some(if (age < 21) " minor" else "major")
  }

  val bob = new Person("Bob", 25)
  val greeting = bob match {
    case Person(n, a) => s"Hi, my name is $n and I am $a yo"
  }

  println(greeting)

  val legalStatus = bob.age match {
    case Person(status) => s"My legal status is $status"
  }

  println(legalStatus)

  /*
  Exercise
  Pattern using case match pattern have into account that we may have many options
  */

  object even {
    def unapply(arg: Int): Option[Boolean] =
      if(arg % 2 == 0) Some(true)
      else None
  }

  object singleDigit {
    def unapply(arg: Int): Option[Boolean] =
      if (arg > -10 && arg < 10) Some(true)
      else None
  }

  object isEven {
    def unapply(arg: Int): Boolean = arg % 2 == 0
    else None
  }

  object isSingleDigit {
    def unapply(arg: Int): Boolean = arg > -10 && arg < 10
    else None
  }

  val n: Int = 8
  val mathProperty = n match {
    case singleDigit(_) => "single digit"
    case even(_) => "an even number"
    case _ => "no property"
  }

  println(mathProperty)

}
