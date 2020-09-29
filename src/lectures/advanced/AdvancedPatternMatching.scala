package lectures.advanced

object AdvancedPatternMatching extends App {
  val numbers = List(1)
  val description = numbers match {
    case head :: Nil => println(s"the only element is $head.") // :: here is a infix pattern
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
  }

  object isSingleDigit {
    def unapply(arg: Int): Boolean = arg > -10 && arg < 10
  }

  val n: Int = 8
  val mathProperty = n match {
    case singleDigit(_) => "single digit"
    case even(_) => "an even number"
    case _ => "no property"
  }

  println(mathProperty)

  // infix patterns
  case class Or[A, B](a: A, b: B) // Scala type Either // As case class it has its own companion and property
  val either = Or(2, "two")
  val humanDescription = either match {
    //case Or(number, string) => s"$number is written as $string"
    case number Or string => s"$number is written as $string"
  }
  println(humanDescription)

  // decompose sequences
  val vararg = numbers match {
    case List(1, _*) => "starting with 1"
  }

  abstract class MyList[+A] {
    def head: A = ???
    def tail: MyList[A] = ???
  }
  case object Empty extends MyList[Nothing]
  case class Cons[+A](override val head: A, override val tail: MyList[A]) extends MyList[A]

  object MyList {
    def unapplySeq[A](list: MyList[A]): Option[Seq[A]] =
      if (list == Empty) Some(Seq.empty)
      else unapplySeq(list.tail).map(list.head +: _)
  }


  val myList: MyList[Int] = Cons(1, Cons(2, Cons(3, Empty))) // Seq of 3 element list

  // Decompose
  // case MyPattern - in this case is MyList // _* we don't know how many values will be returned in the pattern
  val decomposed = myList match {
    case MyList(1, 2, _*) => "starting with 1,2"
    case _ => "something else"
  }

  println(decomposed)
  // custom return types for unapply
  // isEmpty: Boolean, get: something

  abstract class Wrapper[T] {
    def isEmpty: Boolean
    def get: T
  }

  object PersonWrapper {
    def unapply(person: Person): Wrapper[String] = new Wrapper[String] {
      def isEmpty = false
      def get = person.name
    }
  }

  println(bob match {
    case PersonWrapper(n) => s"This person's name is $n"
    case _ => "An Alien"
  })

  //Companion
  // Class Person(val name: String, val age: Int)
  // object Person {
  //  unapply(person: Person): Option[(String, Int)] = ----> results as an Option or Option(tuple)
  //   Some((person.name, person.age))
  //
  // person match { --> compiler looks at the TYPE of the thing being pattern match
  // case Person(name, _) => println(s"Hi, I'm $name)
  // the compiler searches the appropiate unapply for us
}
