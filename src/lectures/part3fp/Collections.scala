package lectures.part3fp

object Collections {
  /**
   * NOTES
   * Scala offers both mutable and inmutable collections
   *  - mutable collections can be updated in place
   *  - immutable collections never change
   *
   *  package object scala {
   *    type List[+A] = immutable.List[A]
   *  }
   *  object Predef {
   *    type Map[A, +B] = immutable.Map[A, B]
   *    type Set[A]     = immutable.Set[A]
   *  }
   *
   *  scala.collections.immutable
   *  Traversable -> Iterable -> Seq -> LinearSeq -> List/Stream/Stack/Queue
   *  Traversable -> Iterable -> Seq -> IndexedSeq -> Vector/String/Range
   *  Traversable -> Iterable -> Set -> HashSet/SortedSet
   *  Traversable -> Iterable -> Map -> HashMap/SortedMap
   *
   *  scala.collections.mutable
   *  Traversable -> Iterable -> Set -> HashSet/LinkedHashSet
   *  Traversable -> Iterable -> Map -> Hashmap/MultiMap
   *  Traversable -> Iterable -> Seq -> IndexedSeq -> StringBuilder/ArrayBuffer
   *  Traversable -> Iterable -> Seq -> Buffer -> ArrayBuffer/ListBuffer
   *  Traversable -> Iterable -> Seq -> LinearSeq -> LinkedList/ MutableList
   *
   *  TRAVERSABLE
   *  Base Trait for all collections. Methods:
   *  maps: map, flatMap, collect
   *  conversions: toArray, toList, toSeq
   *  size info: isEmpty, size, noEmpty
   *  tests: exists, forall
   *  folds: foldLeft, foldRight, reduceLeft, reduceRight
   *  retrieval: head, find, tail
   *  string ops: mkString
   *
   */
}
