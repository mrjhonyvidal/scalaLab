package lectures.part3fp

import scala.annotation.tailrec

object TuplesAndMaps extends App {

  /**
   * IN THIS CLASS
   * Tuples val tuple = (42, "RockTheJVM")
   * tuple._1 ------------------> retrieve elements using _n notation
   * tuple.copy(_1 = 0) --------> create new tuples (0, RockTheJVM)
   * tuple.toString ------------> "(42, RockTheJVM)"
   * tuple.swap ---------------->  (RockTheJVM,42) swap elements
   *
   * Maps
   * val phonebook = Map("A" -> "aValue", "B" -> "bValue")
   * phonebook.contains("A")
   * val anotherbook = phonebook + ("C", "cValue"))
   *
   *  Functional: filterKeys, mapValues
   *  map, filter, flatMap
   *  To/From other collections
   *  .toList, .toMap, groupBy
   */

  // tuples = finite ordered "lists"
  val aTuple = new Tuple2(2, "hello, Scala") // Tuple2[Int, String] = (Int, String)
  val aTupleSyntaticSugar = (2, "hello, Scala") // Tuple2[Int, String] = (Int, String)

  println(aTuple._1) // 2
  println(aTuple.copy(_2 = "goodbye"))
  println(aTuple.swap) // ("hello, Scala", 2)

  // Maps - keys -> values
  val aMap: Map[String, Int] = Map()
  val phonebookWithTwoIdenticalKeys = Map(("Jim", 555), "Daniel" -> 789, "JIM" -> 11235).withDefaultValue(-1) // "->" is syntatic sugar for tuples
  // a -> b is sugar for (a,b)
  println(phonebookWithTwoIdenticalKeys)

  // map ops
  println(phonebookWithTwoIdenticalKeys.contains("Jim"))

  // java.uyil.NoSuchElementException if withDefaultValue(-1) not used / key not found: Mary
  println(phonebookWithTwoIdenticalKeys("Mary"))

  // add a pairing
  val newPairing = "Mary" -> 678
  val newPhoneBook = phonebookWithTwoIdenticalKeys + newPairing
  println(newPhoneBook)

  // functionals on maps
  // map, flatMap, filter
  println(phonebookWithTwoIdenticalKeys.map(pair => pair._1.toLowerCase -> pair._2))

  // filterKeys
  println(phonebookWithTwoIdenticalKeys.filterKeys(x => x.startsWith("J")))
  // mapValues
  println(phonebookWithTwoIdenticalKeys.mapValues(number => "0245-" + number))

  // conversions to other collections
  println(phonebookWithTwoIdenticalKeys.toList)
  println(List(("Daniel", 555)).toMap)
  val names = List("Bob", "James", "Angela", "Mary", "Daniel", "Jim")
  println(names.groupBy(name => name.charAt(0)))

  /**
   * EXERCISES
   * 1. What would happen if I had two original entries "Jim" -> 555 and "JIM" -> 900?
   *
   * !!! careful with mapping keys
   *
   * 2. Overly simplified social network based on maps
   *  Person = String
   *  - add a person to the network
   *  - remove
   *  - friend (mutual)
   *  - unfriend
   *
   *  - number of friends of a person
   *  - person with most friends
   *  - how many people have NO friends
   *  - if there is a social network between two people (direct or not)
   */
  def add(network: Map[String, Set[String]], person: String): Map[String, Set[String]] =
    network + (person -> Set())

  def friend(network: Map[String, Set[String]], a: String, b: String): Map[String, Set[String]] = {
    val friendA = network(a)
    val friendB = network(b)

    network + (a -> (friendA + b)) + (b -> (friendB + a))
  }

  def unfriend(network: Map[String, Set[String]], a: String, b: String): Map[String, Set[String]] = {
    val friendA = network(a)
    val friendB = network(b)

    network + (a -> (friendA - b)) + (b -> (friendB - a))
  }

  def remove(network: Map[String, Set[String]], person: String): Map[String, Set[String]] = {
    def removeAux(friends: Set[String], networkAcc: Map[String, Set[String]]): Map[String, Set[String]] =
      if (friends.isEmpty) networkAcc
      else removeAux(friends.tail, unfriend(networkAcc, person, friends.head))

    // Removes all the friends before remove person
    // unfriended - clean map with no friendship to person
    val unfriended = removeAux(network(person), network)
    unfriended - person
  }

  // Testing our methods (add, friend, unfriend, remove)
  val empty: Map[String, Set[String]] = Map()
  val network = add(add(empty, "Bob"), "Mary") // TODO move the add() to its own class
  println(network) // Map(Bob -> Set(), Mary -> Set())
  println(friend(network, "Bob", "Mary"))
  println(unfriend(friend(network, "Bob", "Mary"), "Bob", "Mary"))
  println(remove(friend(network, "Bob", "Mary"), "Bob"))

  val people = add(add(add(empty, "Bob"), "Mary"), "Jim")
  val jimBob = friend(people, "Bob", "Jim")
  val testNet = friend(jimBob, "Bob", "Mary")

  println(testNet)

  /**
   * Number of friends
   * @param network
   * @param person
   * @return
   */
  def nFriends(network: Map[String, Set[String]], person: String): Int =
    if (!network.contains(person)) 0
    else network(person).size

  println(nFriends(testNet, "Bob"))

  /**
   * Person with most friends
   * Use map.maxBy on value pair._2 and get key ._1 on the result
   * @param network
   * @return
   */
  def mostFriends(network: Map[String, Set[String]]): String =
    network.maxBy(pair => pair._2.size)._1 //._2 refers to VALUE on the maxBy(lambda λ) and get ._1 (KEY)

  println(mostFriends(testNet))

  /**
   * How many people have NO friends
   * Use filter map by keys
   * @param network
   * @return
   */
  def nPeopleWithNoFriends(network: Map[String, Set[String]]): Int =
    network.filterKeys(k => network(k).isEmpty).size

  /**
   * How many people have NO friends
   * Use map.count on lambda pair._2
   * @param network
   * @return
   */
  def nPeopleWithNoFriends_anotherWay(network: Map[String, Set[String]]): Int =
    network.count(pair => pair._2.isEmpty) // same network.count(_._2.isEmpty)

  println(nPeopleWithNoFriends(testNet))
  println(nPeopleWithNoFriends_anotherWay(testNet))

  /**
   * Social network between two people (direct or not)
   * @param network
   * @param a
   * @param b
   * @return
   */
  def socialConnection(network: Map[String, Set[String]], a: String, b: String): Boolean = {
    // Solution using a Breadth First Search
    // TODO investigate more on the algorithm: https://en.wikipedia.org/wiki/Breadth-first_search
    @tailrec
    def bfs(target: String, consideredPeople: Set[String], discoveredPeople: Set[String]): Boolean = {
      if (discoveredPeople.isEmpty) false
      else {
        val person = discoveredPeople.head
        if (person == target) true
        else if (consideredPeople.contains(person)) bfs(target, consideredPeople, discoveredPeople.tail)
        else bfs(target, consideredPeople + person, discoveredPeople.tail ++ network(person))
      }
    }
    bfs(b, Set(), network(a) + a)
  }
  println(socialConnection(testNet, "Mary", "Jim"))
  println(socialConnection(testNet, "Mary", "Jack"))
}
