/**
  * Created by whilemouse on 17. 11. 28.
  */
import repositories.AuthorRepository
import models.Author
import org.scalatest.TestData
import org.scalatest.concurrent.ScalaFutures
import org.scalatestplus.play.{OneServerPerTest, PlaySpec}
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration.DurationInt

import scala.concurrent.Await

class AuthorRepositorySpec extends PlaySpec with OneServerPerTest with ScalaFutures {

  val inMemoryDatabaseConfig = Map(
    "slick.dbs.default.driver" -> "slick.driver.H2Driver$",
    "slick.dbs.default.db.driver" -> "org.h2.Driver",
    "slick.dbs.default.db.url" -> s"jdbc:h2:mem:kosmos_player${scala.util.Random.nextInt};MODE=MYSQL;DATABASE_TO_UPPER=false;DB_CLOSE_DELAY=0;"
  )
  override def newAppForTest(testData: TestData) = new GuiceApplicationBuilder().configure(inMemoryDatabaseConfig).build()

  "AuthorRepository" should {
    "work as expected" in {
      val app2repository = Application.instanceCache[AuthorRepository]
      val repository: AuthorRepository = app2repository(app)

      val testAuthors = Set(
        Author(1, "mason"),
        Author(2, "dennis"),
        Author(3, "chad"))

      Await.result(Future.sequence(testAuthors.map(repository.insert)), 1 seconds)
      val storedAuthors = Await.result(repository.all(), 1 seconds)

      storedAuthors.toSet must equal(testAuthors)
    }
  }
}