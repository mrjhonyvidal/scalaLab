package playground

/**
 * Created by Jhony Vidal on 23-May-20.
 */
object ScalaPlayground extends App{
  println("Hello, Scala!")

  // functional programming
  val incrementer = new Function[Int, Int] {
    override def apply(v1: Int): Int = v1 + 1
  }
  print(incrementer(1))

  // ƛ
  val anonymousIncrementer = (x: Int) => x + 1
  List(1,2,3).map(anonymousIncrementer) // High Order Function
  // map, flatMap, filter

  // for-comprehension
  val pairs = for {
    num <- List(1,2,3) // if condition
    char <- List('a','b','c')
  } yield num + "-" + char

  // Scala collections: Seq, Arrays, Lists, Vectors, Maps, Tuples
  val aMap = Map(
    "MyKey" -> 111,
    "AnotherKeyString" -> 444
  )

  // "collections": Options, Try // abstract computations
  val anOption = Some(2)
}
