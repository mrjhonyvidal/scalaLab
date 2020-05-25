package lectures.part1basics

object Functions extends App {

  def aFunction(a: String, b: Int): String = {
    a + " " + b
  }

  println(aFunction("hello", 3))

  def aParameterLessFunction(): Int = 42
  println(aParameterLessFunction())
  println(aParameterLessFunction) // Same behaviour without the parenthesis

  def aRepeatedFunction(aString: String, n: Int): String = {
    if (n == 1) aString
    else aString + aRepeatedFunction(aString, n-1)
  }

  println(aRepeatedFunction("hello", 3))

  // WHEN YOU NEED LOOPS, USE RECURSIONS.

  def aFunctionWithSideEffects(aString: String): Unit = println(aString) // Expression return a Unit
  // We normally need side effect functions when don't need computation
  // like display something to the screen, logguing, write to a file

  def aBigFunction(n: Int): Int = {
    def aSmallerFunction(a: Int, b: Int): Int = a + b

    aSmallerFunction(n, n-1) // auxiliary functions inside aBigFunction Block
  }

  /**
   * Exercises: write a function to the following statements:
   * 1. A greeting function (name, age) => "Hi, my name is $name and I amd $age years old."
   * 2. Factorial function (1 * 2 * 3 * .. * n
   * 3. A Fibonacci function
   *  f(1) = 1
   *  f(2) = 1
   *  f(n) = f(n - 1) + f(n -2)
   * 4. Test if a number is prime
   */
   // 1
  def greetingTrailblazers(name: String, mainSkill: String): String =
    "Hi, my name is " + name + "and my power skill is " + mainSkill

  // 2
  def factorial(n: Int): Int =
    if (n <= 0) 1
    else n * factorial(n-1)

  println(factorial(5)) // 120

  // 3
  def fibonacci(n: Int): Int =
    if (n <= 2) 1
    else fibonacci(n - 1) + fibonacci(n-2)

  // 1 1 2 3 5 8 13 21
  println(fibonacci(8))

  def isPrime(n: Int): Boolean = {
    def isPrimeUntil(t: Int): Boolean =
      if (t <= 1) true
      else n % t != 0 && isPrimeUntil(t - 1) // IF ELSE condition is matched then isPrimeUntil(t - 1) is not called

    isPrimeUntil(n / 2)
  }

  println(isPrime(7))
  println(isPrime(37 * 17))
}
