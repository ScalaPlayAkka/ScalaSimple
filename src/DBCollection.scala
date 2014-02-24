import com.mongodb.{DBCollection => MongoDBCollection, DBObject}

/**
 *
 * @param underlying
 */
class DBCollection(override val underlying: MongoDBCollection) extends ReadOnly {

  private def collection(name: String) = underlying.getCollection(name)

  def readOnlyCollection(name: String) = new DBCollection(collection(name)) with Memoizer

  def administrableCollection(name: String) = new
      DBCollection(collection(name)) with Administrable with Memoizer

  def updatableCollection(name: String) = new
      DBCollection(collection(name)) with Updatable with Memoizer
}

/**
 *
 */
sealed trait QueryOption

/**
 *
 */
case object NoOption extends QueryOption

/**
 *
 * @param sorting
 * @param anotherOption
 */
case class Sort(sorting: DBObject, anotherOption: QueryOption) extends QueryOption

/**
 *
 * @param number
 * @param anotherOption
 */
case class Skip(number: Int, anotherOption: QueryOption)
  extends QueryOption

/**
 *
 * @param limit
 * @param anotherOption
 */
case class Limit(limit: Int, anotherOption: QueryOption)
  extends QueryOption