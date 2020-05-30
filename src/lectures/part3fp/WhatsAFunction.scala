package lectures.part3fp

object WhatsAFunction extends App {

  /**
   * Use functions as first class elements
   *  - pass functions as parameters
   *  - use functions as values
   *
   * Problem: oop
   * Function types Function2[A, B, R] === (A,B) => R
   * ALL SCALA FUNCTIONS ARE OBJECTS OR INSTANCES OF CLASSES DERIVED FROM FUNCTION1/FUNCTION2...FUNCTION-n
   * The JVM was designed with object orientation in mind, first-class elements: objects (instances of classes)
   * SOLUTION: All Scalas are objects
   * Function traits, up to 22 params
   *
   * Syntactic sugar function types: Function2[Int, String, Int] ---> (Int, String) => Int
   */
  val doubler = new MyFunction[Int, Int] {
    override def apply(element: Int): Int = element * 2
  }

  println(doubler(2))

  /**
   * Scala supports function types = Function1[A, B] until 22 parameters
   */
  val stringIntConverter = new Function[String, Int] {
    override def apply(string: String): Int = string.toInt
  }

  println(stringIntConverter("3") + 4)

  // ((Int, Int) => Int) is the syntax sugar for val adder: Function2[Int, Int, Int] = new Function..
  val adder: ((Int, Int) =>  Int) = new Function2[Int, Int, Int] {
    override def apply(a: Int, b: Int): Int = a + b
  }

  /*
  TASKS:
    1- a function which takes 2 strings and concatenate them
    2- On MyList.scala exercise folder: transform the MyPrediction and MyTransformer into function types
    3- (Higher-order function HOF) Define a function which takes an int and returns another function which takes an int and returns an int
      - what's the type of this function
      - how to do it
 */
  // 1. a function which takes 2 strings and concatenate them
  def concatenate: (String, String) => String = new Function2[String, String, String] {
    override def apply(a: String, b: String): String = a + b
  }

  println(concatenate("Hello,", "Scala"))

   // 2.A function that takes an Int parameter and return another function
   // Function[Int, Function[Int, Int]]
   val superAdder: Function1[Int, Function[Int, Int]] = new Function1[Int, Function[Int, Int]] {
     override def apply(x: Int): Function1[Int, Int] = new Function1[Int, Int] {
       override def apply(y: Int): Int = x + y
     }
   }

  val adder3 = superAdder(3)
  println(adder3(4))
  println(superAdder(3)(4)) // Curried Function have the property that they can be called with multiple parameter lists just by their mere definition

}

/**
 * Check MyList.scala
 * We are using predicates and transformers with quote/unquote functions
 * But they are actually instances of these classes with override method definitions
 * Function simulations.. (They are going to be removed after this lecture to implement Function Types instead)
 *
 * Below, this is at most what an object oriented language would be able to do
 * Example of a Function Trait
 */
trait MyFunction [A, B]{
  def apply(element: A): B = ???
}

// Just for example - Ignore the Variance Modifiers for now
trait MyFunction1 [-A, +B]{
  def apply(element: A): B
}
