import slick.driver.MySQLDriver.api._
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

case class Person(id:Int,name:String, email:String, password:String)

object PersonalDetails extends App {

  class Details(tag:Tag) extends Table[Person](tag,"person"){

    val id = column[Int]("ID", O.PrimaryKey,O.AutoInc)
    val name=column[String]("Name",O.SqlType("VARCHAR(50)"))
    val email = column[String]("Email",O.SqlType("VARCHAR(50)") )
    val password = column[String]("Password", O.SqlType("VARCHAR(16)"))
    def * = (id,name,email,password) <> (Person.tupled,Person.unapply)
  }
  val details=TableQuery[Details]

  val setup = (details.schema).create

  val db= Database.forConfig("mysql2")

  val insertStatement = DBIO.seq (
    details+=Person(1,"Akanksha","akanksha@yahoo.com","iamakanksha"),
    details+=Person(1,"Shikhar","shikhar@niit.com","iamidiot"),
    details+=Person(1,"Ashima","ashima@niit.com","socute"),
    details+=Person(1,"Rishabh","rishabh@knoldus.com","iamdude")
  )

  val setupFuture=db.run{insertStatement}

  val result = Await.result(setupFuture, 2 second)
  println("result:::   " + result)

}
