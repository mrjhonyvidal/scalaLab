package lectures

/**
 * Created by Jhony Vidal on 24-May-20.
 * In order to run a recursive function the Java virtual machine on top of which Scala runs
 * uses a call stack to keep partial results so that it can get back to
 * computing the desired result.
 */
object Recursion extends App {

  def  factorial(n: Int): Int = {
    if (n <= 1) 1
    else {
      println("Computing factorial of " + n + "- I first need factorial of " + (n - 1))
      val result = n * factorial(n - 1)
      println("Computed factorial of " + n)

      result
    }
  }

  println(factorial(10))
  //println(factorial(5000)) ===> This causes a java.lang.StackOverflowError

  // WRITE CODE IN A SMART WAY
  // Using accumulator
  def superPowerFactorial(n: Int): BigInt = {
    def factHelper(x: Int, accumulator: BigInt): BigInt =
      if (x <= 1) accumulator
      else factHelper(x - 1, x * accumulator)

    factHelper(n, 1)
  }

  /**
   * anotherFactorial(10) = factHelper(10, 1)
   * = factHelper(9, 10 * 1)
   * = factHelper(8, 9 * 10 * 1)
   * = factHelper(7, 8 * 9 * 10 * 1)
   * ...
   * = factHelper(2, 3 * 4 * ... * 10 * 1)
   * = factHelper(1, 1 * 2 * 3 * 4 * ... * 10)
   * Last iteraction calls the accumulator 1 * 2 * 3 * 4 * ... * 10
   */
  println(superPowerFactorial(5000))
  // If we run superPowerFactorial with Return :Int we will run out of integer representation, showing on terminal a "zero"
  // As 5000 is "absolutely astronomical" and it overflows
  // Solve this problem using BigInt
}
