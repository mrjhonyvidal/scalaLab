package lectures.advanced

object CurriedAndPartiallyAppliedFunctions extends App {

  // curried functions
  val superAdder: Int => Int => Int =
    x => y => x + y

 val add3 = superAdder(3)
  println(add3(5))
  println(superAdder(3)(5))

  // Method
  def curriedAdder(x: Int)(y: Int) = x + y // curried method

  val add4: Int => Int = curriedAdder(4)
  // lifting = ETA-EXPANSION - creating functions out of methods

  // function != methods (JVM limitation)
  // example
  def inc(x: Int) = x + 1
  List(1,2,3).map(inc) // ETA-expansion - map is HOF, compiler writes inc as in a lambda function: x => inc(x)

  // Partial function applications
  val add5 = curriedAdder(5) _ // with the _ will say the compiler to translate into Int => Int

  val simpleAddFunction = (x: Int, y: Int) => x + y
  def simpleAddMethod(x: Int, y: Int) = x + y
  def curriedAddMethod(x: Int)(y: Int) = x + y

  val add7 = (x: Int) => simpleAddFunction(7, x) // simplest
  val add7_2 = simpleAddFunction.curried(7)
  val add7_6 = simpleAddFunction(7, _: Int)

  val add7_3 = curriedAddMethod(7) _
  val add7_3_1 = curriedAddMethod(7)(_) // PAF = alternative syntax

  val add7_5 = simpleAddMethod(7, _: Int) // alternative syntax for turning method into function values

  /**
   * (1)
   * Process a list of numbers and return their String representation with different formats
   * Use the %4.2f, %8.6f, and %14.12f with a curried formatter function
   */
  def curriedFormatter(s: String)(number: Double): String = s.format(number)

  val simpleFormat = curriedFormatter("%4.2f") _ // lift
  val seriousFormat = curriedFormatter("%8.6f") _
  val preciseFormat = curriedFormatter("%14.12f") _
  val numbers = List(Math.PI, Math.E, 1, 9.8, 1.3e-12)

  println(numbers.map(simpleFormat)) // =~ numbers.map(curriedFormatter("%14.12f")) Compiler does the ETA-expansion

  // underscores are powerful
  def myAPIConcatenator(a: String, b: String, c:String) = a + b + c
  val concreteImplementationOfMyAPIConcatenator = myAPIConcatenator("Hello, the message is: ", _: String, " rocks") // x: String => myAPIConcatenator(hello, x, rocks)
  println(concreteImplementationOfMyAPIConcatenator("Scala"))

  /**
   * (2)
   * Difference between
   * - functions vs methods
   * - parameters: by-name vs 0-lambda
   */
  def byName(n: => Int) = n + 1
  def byFunction(f: () => Int) = f() + 1

  def methodExample: Int = 42
  def parentMethod(): Int = 42 // Partial Apply Function

  /**
   * Calling byName and byFunction
   * - int
   * - method
   * - parentMethod
   * - lambda
   * - PAF
   */
  byName(23) // ok
  byName(methodExample) // ok -> It will be evaluated to its call
  byName(parentMethod())
  byName(parentMethod) // ok but beware == byName(parentMethod()) -> => bind parameter is not a HOF
  //byName(() => 42)) // not ok
  byName((() => 42)()) // ok because it will evaluate the value of the lambda expression
  //byName(parentMethod _) // not ok, because it's an expression, partial function not a value

  //byFunction(45) // not ok
  //byFunction(methodExample) // not ok!! Cannot be passed to HOF, compiler does not do ETA-expansion
  byFunction(parentMethod)
  byFunction(() => 46) // ok - With a function value
  byFunction(parentMethod _) // does not need the _ because will make the compiler will do ETA-expansion

}
