package lectures

import scala.annotation.tailrec

object DefaultArgs extends App {

  /**
   * We define a default value for the function and overrides if wanted on the Function calling like below
   */
  @tailrec
  def trFact(n: Int, acc: Int = 1): Int =
    if (n <= 1) acc
    else trFact(n-1, n*acc)

  val fact10 = trFact(10, 2)
  val fact12 = trFact(12)

  def savePictures(format: String = "jpg", width: Int = 1920, height: Int = 1080): Unit = println(s"Saving picture with format $format, width: $width and height: $height")

  /**
   * Possible ways of override default values
   * 1. pass in every leading argument
   * 2. name the arguments, no matter the order (we used this one)
   */
  savePictures(width = 800, format = "bmp")
}
