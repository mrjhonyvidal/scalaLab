package lectures.part2oop

object Exceptions extends App {

  /**
   * NOTES: Exceptions come from the Java language
   * they are JVM specific thing not a Scala specific thing
   * Throwing returns Nothing
   */

  val x: String = null
  //println(x.length)
  // this ^^ will crash with a NPE

  // 1. throwing exceptions

  //val aWeirdValue: String = throw new NullPointerException // This expression returns nothing, exceptions are Expressions in Scala

  // throwable classes extend the Throwable class.
  // Exception and Error are the major Throwable subtypes
  // Both Exceptions and Error are going to break the JVM
  // Exceptions and errors are different in their semantics
  // Exceptions denote something that wrong with the program, like a NullPointerException
  // Errors: something wrong with the system, like stack overflow error, exceed the memory of JVM

  // 2. how to catch exceptions
  def getInt(withExceptions: Boolean): Int =
    if (withExceptions) throw new RuntimeException("No int for you!")
    else 42

  // AnyVal
  val potentialFail: AnyVal = try {
    getInt(true) // should return int
  } catch {
    case e: RuntimeException => println("Caught a Runtime Exception") // Println returns Unit
  } finally {
    // Code that will get executed no MATTER WHAT
    // optional
    // does not influence the return type of this expression
    // use finally only for side effects like: logging something to a file when we write distributed applications
    println("finally")
  }

  println(potentialFail)

  // 3. Exceptions are instances of special classes that derived from exception
  // Custom exceptions because they are classes like any other classes
  // they can have class parameters
  class MyException extends Exception
  val exception = new MyException

  // throw exception

  /**
   * TASKS
   *  1. Crash program with an OutOfMemoryError
   *  2. Crash with StackOverFlow Error
   *  3. Create PocketCalculator
   *    - add (x, y)
   *    - multiply (x, y)
   *    - divide(x ,y)
   *    - subtract(x, y)
   *
   *    Throw
   *      - OverflowException if add(x, y) exceeds Int.MAX_VALUE
   *      - UnderFlowException if substract(x,y) exceeds Int.MIN_VALUE
   *      - MathCalculationException for division by 0
   */
  // OOM
  //val array = Array.ofDim(Int.MaxValue) // Allocate an Array with n elements that we want
  //java.lang.OutOfMemoryError: Requested array size exceeds VM limit

  // SO
  //def infinite: Int = 1 + infinite
  //val noLimit = infinite

  // Our Custom Exceptions
  class OverflowException extends RuntimeException
  class UnderflowException extends RuntimeException
  class MathCalculationException extends RuntimeException("Division by 0")

  object PocketCalculator {

    def add(x: Int, y: Int) = {
     val result =  x + y

      if (x > 0 && y > 0 && result < 0) throw new OverflowException
      else if (x < 0 && y < 0 && result > 0) throw new UnderflowException
      else result
    }

    def substract(x: Int, y: Int): Int = {
      val result = x - y
      if (x > 0 && y < 0 && result < 0) throw new OverflowException
      else if (x < 0 && y > 0 && result > 0) throw new UnderflowException
      else result
    }

    def multiply(x: Int, y: Int) = {
      val result = x * y
      if (x > 0 && y > 0 && result < 0) throw new OverflowException
      else if (x < 0 && y < 0 && result < 0) throw new OverflowException
      else if (x > 0 && y < 0 && result > 0) throw new UnderflowException
      else if (x < 0 && y > 0 &&  result < 0) throw new UnderflowException
      else result
    }

    def divide(x: Int, y: Int) = {
      if (y == 0) throw new MathCalculationException
      else x / y
    }
  }

  //println(PocketCalculator.add(Int.MaxValue, 10)) // Positive value plues a positive value giving a negative value = overflow integer
  println(PocketCalculator.divide(2, 0))
}
