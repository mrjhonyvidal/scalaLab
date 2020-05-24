package lectures

import scala.annotation.tailrec

/**
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
    @tailrec
    def factHelper(x: Int, accumulator: BigInt): BigInt =
      if (x <= 1) accumulator
      else factHelper(x - 1, x * accumulator) // TAIL RECURSION = use recursive call as the LAST expression

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

  /**
   * WHEN YOU NEED LOOP, USE _TAIL_ RECURSION!
   * Any function can be turned into a tail recursive function.
   * HINT: You need as many <accumulators> as you have recursive calls on your code path
   * sometimes you need more than one accumulator.
   * <Accumulators> must return the same return type for the function
   */

  /**
   * EXERCISES:
   *  1. Concatenate a string n times
   *  2. IsPrime function tail recursive
   *  3. Fibonnaci function, tail recursive
   */

  // 1. Concatenate a string n times
  @tailrec
  def concatenateTailrec(aString: String, n: Int, accumulator: String): String =
    if (n <= 0) accumulator
    else concatenateTailrec(aString, n-1, aString + accumulator)

  println(concatenateTailrec("hello", 3, ""))

  // 2.IsPrime function tail recursive
  def isPrime(n: Int): Boolean = {
    @tailrec
    def isPrimeTailrec(t: Int, isStillPrime: Boolean): Boolean =
      if(!isStillPrime) false
      else if (t <= 1) true
      else isPrimeTailrec(t - 1, n % t != 0 && isStillPrime) // We store the result of n % t != 0 inside the accumulatoris(StillPrime)

    isPrimeTailrec(n / 2, true)
  }

  println(isPrime(2003))
  println(isPrime(629))

  // 3. Fibonnaci function, tail recursive / Two accumulators
  def fibonacci(n: Int): Int = {
    @tailrec
    def fiboTailrec(i: Int, last: Int, nextToLast: Int): Int = // however many recursive calls on the same code path, that is how many accumulators you need
      if(i >= n) last
      else fiboTailrec(i + 1, last + nextToLast, last)

    if (n <= 2) 1
    else fiboTailrec(2, 1, 1)
  }

  println(fibonacci(8))
}
