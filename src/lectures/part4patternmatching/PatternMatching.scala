package lectures.part4patternmatching

import scala.util.Random

object PatternMatching extends App {

  val random = new Random
  val x = random.nextInt(10)

  val description = x match {
    case 1 => "this ONE"
    case 2 => "double or nothing"
    case 3 => "third time is the charm"
    case _ => "something else" // _ = WILRDCARD
  }

  println(x)
  println(description)

  // 1. Decompose values
  case class Person(name: String, age: Int)
  val bob = Person("Bob", 20)

  val greeting = bob match {
    case Person(n, a) if a < 21 => s"Hi, my name is $n and I can't drink"
    case Person(n, a) => s"Hi, my name is $n and I am $a years old"
    case _ => "I don't know who I am"
  }
  println(greeting)

  /**
   * 1. cases are matched in order
   * 2. what if no cases match? MatchError
   * 3. type of the PM expression? unified type of all the types in all the cases
   */

  // PM on sealed hierarchies
  sealed class Animal
  case class Dog(breed: String) extends Animal
  case class Parrot(greeting: String) extends Animal

  val animal: Animal = Dog("Terra nova")

  /**
   * Case match and access param value inside match return section
   */
  animal match {
    case Dog(someBreed) => println(s"Method of dog of the $someBreed breed")
  }

  // match eveything ????
  val isEven = x match {
    case n if n % 2 == 0 => true
    case _ => false
  }

  // WHY???
  val isEvenCond = if(x % 2 == 0) true else false // ???
  val isEvenNorma = x % 2 == 0 //// right one!

  /**
   * Exercise
   *
   * simple function uses PM
   * takes an Expr => human readable form
   *
   * Sum(Number(2), Number(3)) => 2 + 3
   * Sum(Number(2), Number(3), Number (3)) => 2 + 3 + 4
   * Prod(Sum(Number(2), Number(1)), Number(3)) = (2 + 1) * 3
   */
  trait Expr
  case class Number(n: Int) extends Expr
  case class Sum(el: Expr, e2: Expr) extends Expr
  case class Prod(el:Expr, e2: Expr) extends Expr

  def show(e: Expr): String = e match {
    case Number(n) => s"$n"
    case Sum(e1, e2) => show(e1) + " + " + show(e2)
  }
}
