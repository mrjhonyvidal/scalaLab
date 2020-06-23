package lectures.part3fp

object TuplesAndMaps extends App {
   // tuples = finite ordered "lists"
  val aTuple = new Tuple2(2, "hello, Scala") // Tuple2[Int, String] = (Int, String)
  val aTupleSyntaticSugar = (2, "hello, Scala") // Tuple2[Int, String] = (Int, String)

  println(aTuple._1) // 2
  println(aTuple.copy(_2 = "goodbye"))
  println(aTuple.swap) // ("hello, Scala", 2)

  // Maps - keys -> values
  val aMap: Map[String, Int] = Map()
  val phonebook = Map(("Jim", 555), "Daniel" -> 789).withDefaultValue(-1) // "->" is syntatic sugar for tuples
  // a -> b is sugar for (a,b)
  println(phonebook)

  // map ops
  println(phonebook.contains("Jim"))

  // java.uyil.NoSuchElementException if withDefaultValue(-1) not used / key not found: Mary
  println(phonebook("Mary"))

  // add a pairing
  val newPairing = "Mary" -> 678
  val newPhoneBook = phonebook + newPairing
  println(newPhoneBook)

  // functionals on maps
  // map, flatMap, filter
  println(phonebook.map(pair=> pair._1.toLowerCase -> pair._2))

  // filterKeys
  println(phonebook.filterKeys(x => x.startsWith("J")))
  // mapValues
  println(phonebook.mapValues(number => "0245-" + number))

  // conversions to other collections
  println(phonebook.toList)
  println(List(("Daniel", 555)).toMap)
}
