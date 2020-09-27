package lectures.advanced

import scala.util.Try

object DarkSugars extends App{
  // syntax sugar #1: methods with single param
  def singleArgMethod(arg: Int): String = s"$arg little ducks..."

  // Supply the arg value as the implementation of the method
  val description = singleArgMethod {
    // write some complex code
    42
  }

  val aTryInstance = Try{
    // more like java's try {...}
    // under the hood we're using the apply() method with the expresion implemented
    throw new RuntimeException
  }

  List(1,2,3).map { x =>
    x + 1
  }

  // syntax sugar #2: instances of Trait with a single method can be reduced to lambdas
  // Single Abstract Method
  trait Action {
    def act(x: Int): Int
  }

  val anInstance: Action = new Action {
    override def act(x: Int): Int = x
  }

  // The above can be written like the following
  val aFunkyInstance: Action = (x: Int) => x + 1 // magic

  // example: Runnable
  val aThread = new Thread(new Runnable {
    override def run(): Unit = println("We're running a thread")
  })

  val aSweetThread = new Thread(() => println("sweet, Scala!"))

  abstract class AnAbstractType {
    def implemented: Int = 23
    def f(a: Int): Unit
  }

  val anAbstractInstance: AnAbstractType = (a: Int) => println("sweet")

  // syntax sugar #3: the :: and #:: methods are special
  val prependList = 2 :: List(3,4)
  // 2.::(List(3,4))
  // List(3,4).::(2)
  // ?!

  // scala spec: last char decides associativity of method
  1 :: 2 :: 3 :: List(4,5)
  List(4,5).::(3).::(2).::(1) // equivalent

  class MyStream[T] {
    def -->:(value: T): MyStream[T] =  this // actual implementation here, returns myself
  }

  val myStream = 1 -->: 2 -->: 3 -->: new MyStream[Int]

  // syntax sugar/language characteristic #4: multi-word method naming

  class SomeClass(name: String) {
    def `the words phrase`(message: String): Unit = println(s"$name said $message")
  }

  val someone = new SomeClass("Someone")
  someone `the words phrase` "Scala rocks"

  // syntax sugar #5: infix types
  class Composite[A, B] // == map key, value
  //val composite: Composite[Int, String] = ???
  val composite: Int Composite String = ???

  class -->[A, B]
  val towards: Int --> String = ??? // Infix Type

  // syntax sugar #6: update() is very special, much like apply()
  val anArray = Array(1,2,3)
  anArray(2) = 7 // rewritten to anArray.update(2, 7)
  // update used in mutable collections

  // sytax sugar #7: setters for mutable containers
  class Mutable {
    private var internalMember: Int = 0 // private for OO encapsulation
    def member: Int = internalMember // "getter"
    def member_=(value: Int): Unit =
      internalMember = value // "setter"
  }

  val aMutableContainer = new Mutable
  aMutableContainer.member = 42 // rewritten as aMutableContainer.member_=(42)
}
