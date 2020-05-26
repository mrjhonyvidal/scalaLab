package lectures.part2oop

object Generics extends App {

  // Generic Class
  // Traits also can be type parameterised, trait MyTrait[A]
  // We define MyList as Covariant
  // Variance Problems when designing generic frameworks
  class MyList[+A] {
    // use the type A

    // If to a list of A I put a B which is a super type of A
    // Then this list will turn into a list of B
    def add[B >: A](element: B): MyList[B] = ???

    /**
     * A = Cat
     * B = Animal
     * I add an animal (Dog, Bird) to a list of Cats then that will turn into a list of Animals because there are cats and dogs...
     */
  }

  // Key and Value are both generic types
  class MyMap[Key, Value]
  val listOfIntegers = new MyList[Int]
  val listOfStrings = new MyList[String]

  // Generic Methods - objects cannot be parameterised
  object MyList {
    def empty[A]: MyList[A] = ???
  }

  //val emptyListOfIntegers = MyList.empty[Int] // Will throw Not Implemented Exception

  // Variance Problem
  class Animal
  class Cat extends Animal
  class Dog extends Animal

  // yes, List[Cat] extends List[Animal] = COVARIANCE
  class CovariantList[+A]
  val animal: Animal = new Cat
  val animalList: CovariantList[Animal] = new CovariantList[Cat]

  // animalList.add[new Dog]?? Adding a Dog to a list of Cats, will turn the list into a list of Animals

  // 2. NO = INVARIANCE
  class InvariantList[A]
  val invariantAnimalList: InvariantList[Animal] = new InvariantList[Animal]

  // 3. Hell no, CONTRAVARIANCE
  class ContravariantList[-A]
  val contravariantList: ContravariantList[Cat] = new ContravariantList[Animal]

  class Trainer[-A]
  val trainer: Trainer[Cat] = new Trainer[Animal] // I can use a trainer of Animal to be a trainer of Cat

  // Bounded types
  // Only accepts type parameters A which are sub types of Animal
  class Cage[A <: Animal](animal: A)
  val cage = new Cage(new Dog)

  // We also have lower bounded types >: Only accepts Super Types of Animals Cage[A >: Animal]
  // generic type needs proper bounded type

  //class Car
  //val newCage = new Cage(new Car) // Should Throw Error
}
