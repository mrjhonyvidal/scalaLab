package lectures.part2oop

object AbstractDataTypes extends App {

  /**
   * Abstract: Classes which contain unimplemented or abstract fields or methods
   */
  abstract class Animal {
    val creatureType: String = "wild"
    def eat: Unit
  }

  class Dog extends Animal {
    override val creatureType: String = "Canine"
    override def eat: Unit = println("crunch crunch")
  }

  // Traits - ultimate abstract data types
  trait Carnivore {
    def eat(animal: Animal): Unit
    val preferredMeal: String = "fresh meat"
  }

  trait ColdBlooded
  // Mixin Pattern
  class Crocodile extends Animal with Carnivore with ColdBlooded {
    override val creatureType: String = "croc"
    def eat: Unit = "nomnomom"
    def eat(animal: Animal): Unit = s"I'm a croc and I'm eating ${animal.creatureType}"
  }

  val dog = new Dog
  val croc = new Crocodile
  croc.eat(dog)

  // traits vs abstract classes
  /**
   * Abstract Classes and Traits can have both abstract and non abstract members
   * 1 - traits do not have constructor parameters
   * 2 - multiple traits may be inherited by the same class
   * 3 - traits = behaviour, abstract class = "thing"
   */

  /**
   * Scala Type Hierarchy
   * scala.Any --> scala.AnyRef (=~ Java.lang.Object) --> String, List, Set ... --> scala.Null
   * scala.Any --> scala.AnyVal --> Int, Unit, Boolean, Float
   * --- "sub instance of all" --> scala.Nothing: means NOT AT ALL, not even unit, not even anything
   * --- example of use of scala.Nothing, when throwing exceptions that return nothing.
   */
}
