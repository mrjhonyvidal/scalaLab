package lectures.part3fp

object HOFsCurries extends App {

  /**
   * High Order functions are functions that accept functions as parameters
   * Return functions as results
   *
   * Currying = functions with multiple parameter lists
   */

  val superFunction: (Int, (String, (Int => Boolean)) => Int) => (Int => Int) = null

  // High order function (HOF)
  // map, flatmap, filter in MyList

  // function that applies a function a times over a value x
  // nTimes(f, n, x)
  // f - Function that will be applied
  // n - Number of times of the application of the function
  // x -> is the subject of the applications of this function
  // nTimes(f, 3, x) = f(f(f(x))) = nTimes(f, 2, f(x)) = f(f(fx)))
  // nTimes(f, n, x) = f(f((...(x))) = nTimes(f, n-1, f(x)) ---> this is the recursion applied

  def nTimes(f: Int => Int, n: Int, x:Int): Int =
    if (n <= 0) x
    else nTimes(f, n-1, f(x))

    // Functional programming turn into abstract math, FP is direct mapping over a number of branches of mathematics
    // Example of High Order function used to compute things
    val plusOne = (x: Int) => x + 1
    println(nTimes(plusOne, 10, 1))

    // ntb(f, n) = x => f(f(f...(x)
    // Instead of actually applying f n times to X, a lambda is returned that can be used later
    // increment10 = ntb(plusOne, 10) = x => plusOne(plusOne...(x))
    // val y = increment10(1)
    def nTimesBetter(f: Int => Int, n: Int): (Int => Int) =
      if (n <= 0) (x: Int) => x
      else (x: Int) => nTimesBetter(f, n-1) (f(x)) // nTimesBetter returns a function and them applied () to f(x) (f(x))

    val plus10 = nTimesBetter(plusOne, 10)
    println(plus10(1))

    // curried functions are very useful if you want to define helper functions
    //  that you want to use later on various values
    val superAdder: Int => (Int => Int) = (x: Int) => (y: Int) => x + y
    val add3 = superAdder(3) // y => 3 + y
    println(add3(10))
    println(superAdder(3)(10))

    // functions with multiple parameter lists
    def curriedFormatter(c: String)(x: Double): String = c.format(x) // Format function of the String class


    // We have to define the type (Double => String) otherwise the code will not compile
    // Partial function applications is the way to handle it, this is will be convered on the advanced section.
    val standardFormat: (Double => String) = curriedFormatter("%4.2f")
    val preciseFormat: (Double => String) = curriedFormatter("%10.8f")

    // Use
    println(standardFormat(Math.PI))
    println(preciseFormat(Math.PI))

  /**
   * TASKS
   *  1. Expand MyList.scala by adding:
   *
   *    - foreach method A => Unit
   *    [1,2,3].foreach(x => println(x))
   *
   *    - sort function ((A, A)) => Int) => MyList Compare two functions
   *    [1,2,3].sort((x, y) => y - x) => [3,2,1]
   *
   *    - zipWith (list, (A, A) => B) => MyList[B]
   *    [1,2,3].zipWith((4,5,6), x * y) => [1 * 4, 2 * 5, 3 * 6] = [4, 10, 18]
   *
   *    - fold function is going to be curried
   *    fold(start)(function) => a.value
   *    [1,2,3].fold(0)(x+y) = 6
   *
   *  2. toCurry(f: (Int, Int) => Int) => (Int => Int => Int)
   *  fromCurry(f: (Int => Int => Int)) => (Int, Int) => Int
   *
   *  3. compose(f,g) => x => f(g(x)) Function Composition
   *  andThen(f,g) => x => g(f(x))
   *
   */
  def toCurry(f: (Int, Int) => Int): (Int => Int => Int) =
    x => y => f(x,y)

  def fromCurry(f: (Int => Int => Int)): (Int, Int) => Int =
    (x,y) => f(x)(y)

  // FunctionX
  def compose[A, B, T](f: A => B, g: T => A): T => B =
    x => f(g(x))

  def andThen[A, B, C](f: A => B, g: B => C): A => C =
    x => g(f(x))

  def superAdder2: (Int => Int => Int) = toCurry(_ + _)
  def add4 = superAdder2(4)
  println(add4(17))

  val simpleAdder = fromCurry(superAdder)
  println(simpleAdder(4,17))

  val add2 = (x: Int) => x + 2
  val time3 = (x: Int) => x * 3

  val composed = compose(add2, time3)
  val ordered = andThen(add2, time3)

  println(composed(4))
  println(ordered(4))
}
