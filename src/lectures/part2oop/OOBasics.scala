package lectures.part2oop

object OOBasics extends App {

  /**
   * Topics to review:
   *  - Defining classes
   *  - Instantiating
   *  - Parameters vs fields
   *  - Defining methods
   *  - Calling methods (syntax allowed for parameter-less methods)
   *      val someAction = className.action
   */
  val person = new Person("Jonh", 29)
  println(person.age)
  println(person.x) // A val defined inside the body of a class can be called as a Field
  person.greet("ElephWebb")

  val author = new Writer("Julio", "Cortázar", 1914)
  val novel = new Novel("La vuelta al día en ochenta mundos", 1967, author)

  println(novel.authorAge)
  println(novel.isWrittenBy(author))

  val counter = new Counter
  counter.inc.print
  counter.inc.inc.inc.print
  counter.inc(10).print
}

/**
 * Person Class with a constructor
 * @param name is a Parameter
 * @param age is a Field
 */
class Person(name: String, val age: Int = 0) {
  val x = 2

  // overloading
  // Overloading means defining methods with the same name but different signatures
  def greet(): Unit = println(s"Hi, I am $name") // ${this.name} is implied

  def greet(name: String): Unit = println(s"${this.name} says Hi, $name")

  // multiple constructors - multiple overloading
  def this(name: String) = this(name, 0)
  def this() = this("Jonh Doe")
}

/**
 * Writer (Author)
 * @param firstName
 * @param surname
 * @param year
 */
class Writer(firstName: String, surname: String, val year: Int) {
  def fullName: String = firstName + " " + surname
}

/**
 * Novel
 * @param name
 * @param year
 * @param author
 */
class Novel(name: String, year: Int, author: Writer) {
  def authorAge = year - author.year
  def isWrittenBy(author: Writer) = author == this.author
  def copy(newYear: Int): Novel = new Novel(name, newYear, author)
}

/**
 * Counter
 * @param count with default value equal zero
 */
class Counter(val count: Int = 0) {

  // Inmutability Principle extended to Object and Classes
  def inc = {
    println("incrementing")
    new Counter(count + 1)
  }

  def dec = {
    println("incrementing")
    new Counter(count - 1)
  }

  def inc(n: Int): Counter = {
    if (n <= 0) this
    else inc.inc(n-1)
  }

  def dec(n: Int): Counter = {
    if (n <= 0) this
    else dec.dec(n-1)
  }

  def print = println(count)
}