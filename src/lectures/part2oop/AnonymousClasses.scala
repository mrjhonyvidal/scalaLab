package lectures.part2oop

object AnonymousClasses extends App {

  /**
   * Notes: We can instantiate types and override fields or methods on the spot
   * Anonymous class works for traits and classes
   * Anonymous class works for abstract and not abstract data types just as well
   */

  abstract class Animal {
    def eat: Unit
  }

  // Anonymous class
  val funnyAnimal: Animal = new Animal {
    override def eat: Unit = println("ahahahahaha") // class lectures.AnonymousClasses$$anon$1
  }

  /*
    Equivalent with
     class AnonymousClasses$$anon$1 extends Animal {
      override def eat: Unit = println("ahahahahaha")
      }
     val funnyAnimal: Animal = new AnonymousClasses$$anon$1

     That is what compiler does behind scenes
   */

  println(funnyAnimal.getClass)

  class Person(name: String) {
    def sayHi: Unit = println(s"Hi, my name is $name, how can I help?")
  }

  // Anonymous class works for abstract and not abstract data types just as well
  val jim = new Person("Jim") {
    override def sayHi: Unit = println(s"Hi, my name is Jim, how I be of service?")
  }

  /**
   * Tasks:
   * 1. Create a generic trait MyPredicate[-T] (Contravariant) with a little method test(T) => Boolean
   * 2. Generic trait MyTransformer[-A, B] with a method transformer(A) => B
   * 3. MyList:
   *    -map(transformer) => MyList
   *    -filter(predicate) => MyList
   *    -flatMap(transformer from A to MyList[B]) => MyList
   *
   *    class EvenPredicate extends MyPredicate[Int]
   *    class StringToIntTransformer extends MyTransformer[String, Int]
   *
   *    [1,2,3].map(n * 2) = [2,4,6]
   *    [1,2,3,4].filter(n % 2) = [2,4]
   *    [1,2,3].flatMap(n => [n, n+1]) => [1,2,2,3,3,4]
   */

}
