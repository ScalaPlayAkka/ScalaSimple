import com.mongodb.BasicDBObject

/**
 * Created by pro on 2/20/14.
 */
object ClientMain {

  /**
   *
   * @param args
   */
  def main(args: Array[String]) {
    def client = new MongoClient
    def db = client.createDB("mydb")
    def cols = for {name <- db.collectionNames} yield name
    for (col <- cols) println(f"- name = $col%s")

    println(f"Mongo Server Client: ${client.version}%s")

    val col = db.readOnlyCollection("test")
    println(col.name)

    val adminCol = db.administrableCollection("test")
    adminCol.drop

    val updatableCol = db.updatableCollection("test")

    val doc = new BasicDBObject()
    doc.put("name", "MongoDB")
    doc.put("type", "database")
    doc.put("count", 1)

    val info = new BasicDBObject()
    info.put("x", 203)
    info.put("y", 102)
    doc.put("info", info)
    updatableCol += doc
    println(updatableCol.findOne)

    updatableCol -= doc
    println(updatableCol.findOne)

    //    Add 100 documents to “test” collection
    for (i <- 1 to 100) updatableCol += new BasicDBObject("i", i)

    //    Query for 71st document in C collection
    val query = new BasicDBObject
    query.put("i", 71);
    val cursor = col.find(query)
    while (cursor.hasNext()) {
      println(cursor.next())
    }

    //    Search for where i > 20
    val rangeQuery = new BasicDBObject("i", new BasicDBObject("$gt", 20))

    //    Skip first 20 docs, return 10 docs
    val richQuery = Query(rangeQuery).skip(20).limit(10)
    val cursor1 = col.find(richQuery)
    while (cursor1.hasNext()) {
      println(cursor1.next());
    }
  }
}
