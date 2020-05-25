package lectures.part1basics

object StringOps extends App {

  val str: String = "Hello, I am solidifying my Scala skills"

  println(str.charAt(2))
  println(str.substring(7,11))
  println(str.split(" ").toList)
  println(str.startsWith("Hello"))
  println(str.replace(" ", "-"))
  println(str.toLowerCase())
  println(str.length)

  val aNumberString = "2"
  val aNumber = aNumberString.toInt
  println('a' +: aNumberString :+ 'z') // prepending +: and appending :+ operator
  println(str.reverse)
  println(str.take(2))

  // Scala-specific: String interpolation

  // S-interpolators
  val name = "David"
  val age = 20
  val greeting = s"Hello, my name is $name and my age is $age"
  println(greeting)

  // F-Interpolation
  val speed = 1.2f
  println(f"$name%s can eat $speed%2.2f of burgers per minute")

  // raw-interpolation
  println(raw"This is a \n newline")
  val escaped = "This is a \n newline"
  println(raw"$escaped") // interpret \n
}
