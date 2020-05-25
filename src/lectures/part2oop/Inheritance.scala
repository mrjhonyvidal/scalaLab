package lectures.part2oop

object Inheritance extends App {

  /**
   * TOPICS of the lecture:
   *  - Basic Inheritance
   *  - Access Modifiers
   *  - Constructors
   *  - Overriding and Overriding fields direct in the constructor
   *  - OverRIDDING versus OverLOADING
   *  - Polymorphism
   *  - Super
   *  - Preventing overriding
   *    - final methods
   *    - final classes
   *    - sealed
   */

  // Single Class Inheritance
  sealed class Animal {
    val creatureType = "Wild"
    def eat = println("nomnom")
  }

  class Cat extends Animal {
    def crunch = {
      eat
      println("Now a different behaviour: crunch crunch")
    }
  }

  val cat = new Cat
  cat.crunch

  // =================================
  // constructors
  class Person(name: String, age: Int){
    def this(name: String) = this(name, 0)
  }
  class Adult(name: String, age: Int, idCard: String) extends Person(name)

  // =================================
  // Override
  class Dog extends Animal {
    override val creatureType: String = "domestic"
    override def eat = {
      super.eat
      println("crunch, crunch")
    }
  }

  val dog = new Dog
  dog.eat
  println(dog.creatureType)

  // =================================
  // Same as Dog, just that the override val we will be set on the Class definition
  class Bird(override val creatureType: String) extends Animal {
    // override val creatureType: String = "domestic"
    override def eat = println("Who knows what sound birds yield :-) when eating")
  }

  val bird = new Bird("domestic")
  bird.eat
  println(dog.creatureType)

  // ==================================
  // type substitution (braod: polymorphism)
  // Most overriden
  val unknownAnimal: Animal = new Bird("K9")
  unknownAnimal.eat

  // overRIDING versus overLOADING
  // Overriding: supply a different implementation in derived classes
  // Overloading: supply multiple methods with different signatures

  // super: Check class Dog

  // Preventing overrides
  // 1 - Use the keyword final
  // 2 - Use final on the class definition
  // Note: Numerical and String classes in Scala are set as Final
  // 3. Seal the class = allow extend classes in THIS FILE, prevent extension in others files
}
