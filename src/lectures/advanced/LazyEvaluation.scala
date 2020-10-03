package lectures.advanced

object LazyEvaluation extends App {

  // lazy DELAYS the evaluation of values
  lazy val x: Int = {
    println("hello")
    42
  }

  // evaluates only once but only when is called
  println(x)
  println(x)

  // examples of implications
  // side effects because in the example bellow the lazyCondition is never reached from the if statement
  // in case we count that the content of the lazy function would be evaluated to be used in the &&
  def sideEffectCondition: Boolean = {
    println("Boo")
    true
  }

  def simpleCondition: Boolean = false

  lazy val lazyCondition = sideEffectCondition
  println(if (simpleCondition && lazyCondition) "yes" else "no")

  // in conjunction with call by name
  def byNameMethod(n: => Int): Int = n + n + n + 1

  def retrieveMagicValue = {
    // side effect or a long computation
    println("waiting") // will be called 3 seconds for each n parameter
    Thread.sleep(1000)
    42
  }

  println(byNameMethod(retrieveMagicValue))


  // use lazy vals
  // techniquee called BY-NEEDED
  // We evaluate the n parameter once and use it many times
  def byNameMethodUsingLazy(n: => Int): Int = {
    lazy val t = n // only evaluated once
    t + t + t + 1
  }
  println(byNameMethodUsingLazy(retrieveMagicValue))

  // filtering with lazy vals
  def lessThan30(i: Int): Boolean = {
    println(s"$i is less than 30?")
    i < 30
  }

  def greaterThan20(i: Int): Boolean = {
    println(s"$i is greater than 20?")
    i > 20
  }

  val numbers = List(1, 35, 40, 5, 23)
  val lt30 = numbers.filter(lessThan30) // List(1, 25, 5, 23)
  val gt20 = lt30.filter(greaterThan20)
  println(gt20)

  val lt30Lazy = numbers.withFilter(lessThan30) // lazy vals under the hood
  val gt20Lazy = lt30Lazy.withFilter(greaterThan20)

  println(gt20Lazy) // Pattern FilterMonadic[Int, List[Int]]
  // scala.collection.TraversableLike$WithFilter
  println
  gt20Lazy.foreach(println)

  // for-comprehension use withFilter with guards
  for {
    a <- List(1,2,3) if a % 2 == 0 // use lazy vals
  } yield a + 1
  List(1,2,3).withFilter(_ % 2 == 0).map(_ + 1) // List[Int]
}
