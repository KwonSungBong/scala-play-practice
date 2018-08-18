package models

import java.util.Date
import javax.inject.Inject

import anorm.SqlParser.{get, scalar}
import anorm._
import play.api.db.DBApi
import repositories.DatabaseExecutionContext

import scala.concurrent.Future


case class Notice(id: Option[Long] = None,
                  title: String,
                  content: String,
                  createdDate: Option[Date],
                  modifiedDate: Option[Date])

object Notice {
  implicit def toParameters: ToParameterList[Notice] =
    Macro.toParameters[Notice]
}

//case class Page[A](items: Seq[A], page: Int, offset: Long, total: Long) {
//  lazy val prev = Option(page - 1).filter(_ >= 0)
//  lazy val next = Option(page + 1).filter(_ => (offset + items.size) < total)
//}

@javax.inject.Singleton
class NoticeRepository @Inject()(dbapi: DBApi)(implicit ec: DatabaseExecutionContext) {

  private val db = dbapi.database("default")

  private val simple = {
    get[Option[Long]]("notice.id") ~
    get[String]("notice.title") ~
    get[String]("notice.content") ~
    get[Option[Date]]("notice.createdDate") ~
    get[Option[Date]]("notice.modifiedDate") map {
      case id ~ title ~ content ~ createdDate ~ modifiedDate =>
        Notice(id, title, content, createdDate, modifiedDate)
    }
  }

  def findById(id: Long): Future[Option[Notice]] = Future {
    db.withConnection { implicit connection =>
      SQL"select * from notice where id = $id".as(simple.singleOpt)
    }
  }(ec)

  def list(page: Int = 0, pageSize: Int = 10, orderBy: Int = 1, filter: String = "%"): Future[Page[Notice]] = Future {
    val offset = pageSize * page

    db.withConnection { implicit connection =>
      val notices = SQL"""
           select * from notice
           where title like ${filter}
           order by ${orderBy}
           limit ${pageSize} offset ${offset}
        """.as(simple.*)

      val totalRows = SQL"""
          select count(*) from notice
          where title like ${filter}
        """.as(scalar[Long].single)

      Page(notices, page, offset, totalRows)
    }
  }(ec)

  def update(id: Long, notice: Notice) = Future {
    db.withConnection {implicit connection =>
      SQL(
        """
          update notice set title = {title}, content = {content},
          createdDate = {createdDate}, modifiedDate = {modifiedDate}
          where id = {id}
        """).bind(notice.copy(id = Some(id))).executeUpdate()
    }
  }(ec)

  def insert(notice: Notice): Future[Option[Long]] = Future {
    db.withConnection { implicit connection =>
      SQL(
        """
          insert into notice(title, content, createdDate, modifiedDate)
          values ({title}, {content}, {createdDate}, {modifiedDate})
        """).bind(notice).executeInsert()
    }
  }(ec)

  def delete(id: Long) = Future {
    db.withConnection { implicit connection =>
      SQL"delete from notice where id = ${id}".executeUpdate()
    }
  }

}
