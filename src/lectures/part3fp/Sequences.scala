package lectures.part3fp

import scala.util.Random

object Sequences extends App {
  /**
   * NOTES
   * Sequences, a general interface for data structures
   *  - have a well defined order
   *  - can be indexed
   *  Support various operations
   *  - apply, iterator, length, reverse for indexing and iterating
   *  - concatenation, appending, prepending
   *  - grouping, sorting, zipping, searching, slicing
   */

  // Seq
  val aSequence = Seq(1,2,3,4)
  println(aSequence)
  println(aSequence.reverse)
  println(aSequence(2))
  println(aSequence ++ Seq(7,5,6))
  println(aSequence.sorted)

  // Ranges
  val aRange: Seq[Int] = 1 until 10
  aRange.foreach(println)

  (1 to 10).foreach(x => println("Hello"))

  // lists
  val aList = List(1,2,3)
  val prepended = 42 +: aList :+ 89
  println(prepended)

  val apples5 = List.fill(5)("apple")
  println(apples5)
  println(apples5.mkString("-|-"))

  /**
   * Arrays
   * final class Array[T]
   *    extends java.io.Serializable
   *    with java.lang.Cloneable
   *
   * can be mutable and interoperable with Java T[] arrays
   * indexing is fast
   */
  val numbers = Array(1,2,3,4)
  val threeElements = Array.ofDim[String](3)
  threeElements.foreach(println)

  // mutation
  numbers(2) = 0 // syntax sugar for numbers.update(2,0)
  println(numbers.mkString(" "))

  // arrays and seq
  val numbersSeq: Seq[Int] = numbers // implicit conversion
  println(numbersSeq)

  // vectors
  val vector: Vector[Int] = Vector(1,2,3)
  println(vector)

  /**
   * Benchmarking
   * vectors vs lists
   */
  val maxRuns = 1000
  val maxCapacity = 1000000

  def getWriteTime(collection: Seq[Int]): Double = {
    val r = new Random()
    val times = for {
      it <- 1 to maxRuns
    } yield {
      val currentTime = System.nanoTime()
      collection.updated(r.nextInt(maxCapacity), r.nextInt())
      System.nanoTime() - currentTime
    }
    times.sum * 1.0 / maxRuns
  }

  val numberList = (1 to maxCapacity).toList
  val numbersVector = (1 to maxCapacity).toVector

  // keeps reference to tail
  // updating an element in the middle takes long
  println(getWriteTime(numberList))

  // depth of the tree is small
  // needs to replace an entire 32-element chunk (TODO dive more on the specification)
  println(getWriteTime(numbersVector))

  // Result
  // 1.1159632525E7 milliseconds --> List
  // 7618.846 milliseconds --> Vector
}
