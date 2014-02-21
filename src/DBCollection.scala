import com.mongodb.{DBCollection => MongoDBCollection}

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