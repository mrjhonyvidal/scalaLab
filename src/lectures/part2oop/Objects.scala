package lectures.part2oop

object Objects{

  /**
   * Topics covered in this lecture
   * - Difference between Object and Class in Scala
   * - Class-level functionality in Java (static elements)
   * - COMPANIONS: classes and objects in the same scope
   * - Factory Pattern: Person.apply()
   * - Singleton Pattern
   * - Apply Factory Pattern on a Singleton Object Instance to build a new object
   */

  // Class Level Functionality
  // Functionality that does not depend on an instance of a class. See Java Playground example
  // SCALA DOES NOT HAVE CLASS-LEVEL FUNCTIONALITY ("static")
  // Objects different from the classes do not receive parameters
  object Person { // type + its only instance
    // "static"/"class" - level functionality
    val N_EYES = 2
    def canFly: Boolean = false

    // Factory Method
    def apply(mother: Person, father: Person): Person = new Person("Bobbie")
  }

  // COMPANIONS: class and object in the same scope
  class Person (val name: String){
    // instance-level functionality
  }

  /**
   *  Scala Applications = MUST be a Scala object with the following method:
   *  def main(args: Array[String]): Unit
   *
   *  Must be defined like this as Scala Applications are turned into
   *  Java Virtual Machine applications whose entry point have to be a static public static void main
   *  with an array of string as a parameter
   */
  def main(args: Array[String]): Unit = {
    println(Person.N_EYES)
    println(Person.canFly)

    /**
     * NOTES:
     * Scala object = SINGLETON INSTANCE
     */
    val person1 = Person
    val person2 = Person
    println(person1 == person2) // Both Mary and Jonh point to the same object

    val mary = new Person("Mary")
    val jonh = new Person("Jonh")
    println(mary == jonh)

    //val bobbie = Person.apply(mary, jonh)
    val bobbie = Person(mary, jonh) // same as above with the apply method in the Singleton Person Object
  }
}
