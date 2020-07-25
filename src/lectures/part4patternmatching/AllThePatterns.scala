package lectures.part4patternmatching

import exercises.{Cons, Empty, MyList}

object AllThePatterns extends App {
  // 1 - constants
  val x: Any = "Scala"
  val constants = x match {
    case 1 => "a number"
    case "Scala" => "The Scala"
    case true => "The Truth"
    case AllThePatterns => "A singleton object"
  }

  // 2 - match anything
  // 2.1 wildcard
  val matchAnything = x match {
    case _ =>
  }

  // 2.2 variable
  val matchAVariable = x match {
    case something => s"I've found $something"
  }

  // 3 - tuples
  val aTuple = (1,2)
  val matchATuple = aTuple match {
    case (1,1) =>
    case (something, 2) => s"I've found $something"
  }

  val nestedTuple = (1, (2,3))
  val matchANestedTuple = nestedTuple match {
    case (_, (2, v)) =>
  }

  // PM can be NESTED!

  // 4 - case classes = constructor pattern
  // Partner Match can be nested
  val aList: MyList[Int] = Cons(1, Cons(2, Empty))
  val matchAList = aList match {
    case Empty =>
    case Cons(head, Cons(subhead, subtail)) =>
  }

  // 5 - list patterns
  val aStandardList = List(1,2,3,43)
  val aStandardListMatching = aStandardList match {
    case List(1, _, _, _) => //extractor - advance
    case List(1, _*) => // List of arbitrary length - advanced
    case 1 :: List(_) => // infix pattern
    case List(1,2,3) :+ 42 => // infix pattern
  }

  // & - type specifiers
  val unknown: Any = 2
  val unknownMatch = unknown match {
    case list: List[Int] => // explicit type specifier
    case _ => //
  }

  // 7 - name binding
  val nameBindingMatch = aList match {
    case nonEmptyList @ Cons(_, _) => // name binding => use the name later(here)
    case Cons(1, rest @ Cons(2, _)) => //name binding inside nested patterns
  }

  // 9 - multi-pattern
  val multipattern = aList match {
    case Empty | Cons(0, _) => // compound pattern ( multi pattern)
  }

  // 9 - if guards
  val secondElementSpecial = aList match {
    case Cons(_, Cons(specialElement, _)) if specialElement % 2 == 0 =>
  }

  //All.
  /*
  Question
   */
//  val numbers = List(1,2,3)
//  val numberMatch = numbers match {
//    case listOfString: List[String] => "a list of strings"
//    case listOfNumbers: List[Int] => "a list of numbers "
//    case _ => ""
//  }
//
//  println(numberMatch
  // JVM trick question
  // ---> JVM will habdle it as String

}
