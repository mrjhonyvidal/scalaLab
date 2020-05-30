package lectures.part3fp

object AnonymousFunction extends App{

  /**
   * NOTES: Instead of passing anonymous FunctionX instances every time
   *  - cumbersome
   *  - still object oriented
   *  We can use lambdas
   *  (x, y) => x + y
   *
   *  Anatomy of lambda: (name: String, age: Int) => name + "is" + age + "years old"
   *  parameter: name: String
   *  name: age
   *  type: Int(optional)
   *  (name: String, age: Int): return type always inferred
   *  name + "is" + age + "years old": Implementation expression
   *
   *  Further Sugar Syntactic
   *  val add: (Int, Int) => Int = _ + _
   */

  // Object oriented way of an anonymous function and instantiating it on the spot
  val doubleOP = new Function1[Int, Int] {
    override def apply(v1: Int): Int = v1 * 2
  }

  /**
   * Scala FP way
   * Anonymous Function with a LAMBDA
   * Lambda calculus, mathematical representation of functional programming
   * (v1: Int) => v1 * 2 is a value which is basically an instance of function 1
   */
  val doubleFP = (v1: Int) => v1 * 2
  // same
  val doubleFP_SyntacticSugar: Int => Int = v1 => v1 * 2 // Tyles match and Compiler with Type Infer

  // Multiple params in a lambda
  val adder: (Int, Int) => Int = (a: Int, b: Int) => a + b

  // No params
  val justDoSomething: () => Int = () => 3

  println(justDoSomething) // function itself lectures.part3fp.AnonymousFunction$$$Lambda$9/608188624@3f3afe78
  println(justDoSomething()) // the action call of the Lambda function. Result: 3

  // curly braces with lambdas
  val stringToInt = { (str: String) =>
    // write down the implementation
    str.toInt
  }

  // MOAR syntactic sugar - the _ is very contextual
  val niceIncrementer: Int => Int = _ + 1 // equivalent to x => x + 1

  //val niceAdder: (Int, Int) => Int = (a, b) => a + b
  val niceAdder: (Int, Int) => Int = _ + _ // equivalent to (a, b) => a + b

  /**
   * TASKS
   * 1. MyList: replace all FunctionX calls with lambdas
   * 2. Rewrite the WhatsAFunction.scala::superAdder (HOF) "special" adder as an anonymous function
   */
  val superAdd = (x: Int) => (y: Int) => x + y
  println(superAdd(3)(4))
}
