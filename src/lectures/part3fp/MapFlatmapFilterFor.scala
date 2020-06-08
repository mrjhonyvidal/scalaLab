package lectures.part3fp

object MapFlatmapFilterFor extends App {

  /**
   * TOPICS
   *  - List Scala Implementation with head and tail
   *  - Map
   *  - filter
   *  - flatMap
   *  - Iteration using HOF
   *  - For-Comprehension
   *  - Syntax Overload
   *  - Own collection Maybe[+T] that denotes the posibility of absence of a value
   */

  // Standard Scala Library List implementation
  // We are creating the List by calling the apply() to the List Companion Object
  val list = List(1,2,3)
  println(list.head)
  println(list.tail)

  // MAP
  println(list.map(_ + 1))
  println(list.map(_ + " is a number"))

  // FILTER
  println(list.filter(_ % 2 == 0))

  // FLATMAP
  val toPair = (x: Int) => List(x, x + 1)
  println(list.flatMap(toPair))

  // Task: print all combinations between two lists
  val numbers = List(1,2,3,4)
  val chars = List('a','b','c','d')
  val colors = List("black", "white")

  // List("al", "a2"...."d4")
  // In Functional Programming instead of using loops
  // We use Functions and Recursive Functions

  // ITERATING
  val combinations = numbers.flatMap(n => chars.map(c => "" + c + n ))
  val combinationsNumbersCharsColors = numbers.filter(_ % 2 == 0).flatMap(n => chars.flatMap(c => colors.map(color => "" + c + n + "-" + color)))

  println(combinations)
  println(combinationsNumbersCharsColors)

  // FOREACH
  list.foreach(println)

  // FOR-COMPREHENSIONS - another way to write the iterations
  val forCombinations = for {
    n <- numbers if n % 2 == 0
    c <- chars
    color <- colors
  } yield "" + c + n + "-" + color
  println(forCombinations) // identical result of println(combinationsNumbersCharsColors)

  for {
    n <- numbers
  } println(n) // identical to numbers.foreach(println)

  // SYNTAX OVERLOAD
  list.map { x =>
    x * 2
  }

  /**
   * EXERCISES
   * 1. MyList supports for comprehensions?
   *
   * Our function need to have the following FUNCTION SIGNATURE
   * map(f: A => B) => MyList[B]
   * filter(p: A => Boolean) => MyList[A]
   * flatMap(f: A => MyList[B]) => MyList[B]
   *
   * 2. A small collection of at most ONE element - Maybe[+T]
   *  - map, flatMap, filter
   */
  // 1. MyList.scala

  // 2. Maybe Collection - Denote Option values
}
