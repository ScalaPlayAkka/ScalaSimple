import com.mongodb.{DBCollection => MongoDBCollection, DBCursor, DBObject}

/**
 *
 */
trait ReadOnly {
  val underlying: MongoDBCollection

  def name = underlying getName

  def fullName = underlying getFullName

  /**
   *
   * @param query
   * @return
   */
  def find(query: Query): DBCursor = {
    /**
     *
     * @param cursor
     * @param option
     * @return
     */
    def applyOptions(cursor: DBCursor, option: QueryOption): DBCursor = {
      option match {
        case Skip(skip, next) => applyOptions(cursor.skip(skip), next)
        case Sort(sorting, next) => applyOptions(cursor.sort(sorting), next)
        case Limit(limit, next) => applyOptions(cursor.limit(limit), next)
        case NoOption => cursor
      }
    }
    applyOptions(find(query.q), query.option)
  }

  def find(doc: DBObject) = underlying find doc

  def findOne(doc: DBObject) = underlying findOne doc

  def findOne = underlying findOne

  def getCount(doc: DBObject) = underlying getCount doc
}
