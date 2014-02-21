/**
 *
 */
trait Administrable extends ReadOnly {
  def drop: Unit = underlying drop

  def dropIndexes: Unit = underlying dropIndexes
}
