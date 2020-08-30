package lectures.part2oop

import scala.language.postfixOps

object MethodNotations extends App {

  class Person(val name: String, favoriteMovie: String, val age: Int = 0) {

    def likes(movie: String): Boolean = movie == favoriteMovie
    def +(person: Person): String = "Yes we can use + as a method"
    def +(nickname: String): Person = new Person(s"${name} ${nickname}", favoriteMovie)
    def hangOutWith(person: Person): String = s"${this.name} is hanging out with ${person.name}"
    def unary_! : String = s"Look how bang symbol gives us the same value using unary"
    def unary_+ : Person = new Person(name, favoriteMovie, age + 1)
    def isAlive: Boolean = true
    def apply(): String = s"Hi, my name is $name and I like $favoriteMovie"
    def apply(n: Int): String = s"$name watched $favoriteMovie $n times"

    def learns(thing: String) = s"$name is learning $thing"
    def learnScala = this learns "Scala"
  }

  val mary = new Person("Mary", "Inception")
  println(mary.likes("Inception"))
  println(mary likes "Inception") // equivalent
  // we use Infix Notation or operator notation (syntactic sugar)

  /**
   * "operatos" in Scala
   * In this case the method hangOutWith acts like an operator between two things yielding a third thing
   */
  val tom = new Person("Tom", "Fight Club")
  println(mary hangOutWith tom)

  // Scala allows create a +, - method in the class
  // Arithmetic operators
  println(1 + 2)
  println(1.+(2))
  // ALL OPERATORS ARE METHODS

  // ==================================
  // Prefix Notations - Another form of "syntactic sugar"
  val x = -1 // equivalent with 1.unary_-1
  val y = 1.unary_-

  // unary_ only works with + - ~ ! operators

  println(!mary)
  println(mary.unary_!)

  // ==================================
  // Posting notation
  println(mary.isAlive)
  println(mary isAlive)

  // ==================================
  println(mary.apply())
  println(mary()) // equivalent

  // ==================================
  /**
   * TASKS
   * 1. Overload the + operator
   *    "mary + "the rockstars" => new person "Mary (the rockstars)"
   */
  println((mary + "The rockstars")())
  println((mary + "The rockstars").apply())

  /**
   * 2. Add an age to the Person class
   * Add a unary + operator => new person with the age +1
   * +maty => mary with the age increment
   */
  println((+mary).age) // This will return 1 as Mary Object was built with age:0

  /**
   * 3. Add a "learns" method in the Person class => "Mary learns Scala"
   * Add a learnScala method, calls learns method with Scala
   * Use it in postfix notations
   */
  println(mary learnScala)

  /**
   * 4. Overload the apply method
   * mary.apply(2) => "Marry wached Inception 2 times"
   */
  println(mary(10)) // Tell tell the compiler runs the apply() method
}
