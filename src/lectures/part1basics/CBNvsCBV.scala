package lectures.part1basics

object CBVvsCBN extends App{

  /**
   * Call by value
   *  - value is computed before call
   *  - same value used everwhere
   *  def myFunction(x: Int): String ... myFunction(2) where 2 actual value
   *
   * versus
   *
   * Call by name
   *  - expression is passed literally
   *  - expression is evaluated at every use within (Lazyness evaluation)
   *  def myFunction(x: => Int): String ... myFunction(2) where 2 is an expression
   */

  def calledByValue(x: Long): Unit = {
    println("by value: " + x) // 45138157225459L
    println("by value: " + x) // same 45138157225459L
  }

  // By using => (funky arrow), the parameter is called by name
  def calledByName(x: => Long): Unit = {
    println("by value: " + x) // 45138288842485L
    println("by value: " + x) // different 45138288874547L
  }

  // the Data Type of x is Long because time functions in Scala and on the JVM returns longs
  calledByValue(System.nanoTime())
  calledByName(System.nanoTime())

  // ==========================================
  def infinite(): Int = 1 + infinite()
  def printFirst(x: Int, y: => Int) = println(x)

  // printFirst(infinite(), 34) Generates a StackOverflow
  printFirst(34, infinite()) // Does not crash since (y) is not used and never evaluated, BY NAME evaluation of the expression
}
