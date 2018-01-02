package controllers

import anorm._
import javax.inject.Inject

import play.api.db.Database
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

/**
  * Created by ksb on 2018. 1. 1..
  */
class TestApi @Inject()(db: Database) extends Controller {

  def json = Action {
    val json = Json.toJson("test")
    Ok(json)
  }

  def sql = Action {

    db.withConnection { implicit connection =>
      val id: Option[Long] =
        SQL("insert into User(email, password, fullname, isAdmin) values ({email}, {password}, {fullname}, {isAdmin})")
          .on('email -> "sql", 'password -> "sql", 'fullname -> "sql", 'isAdmin -> "1").executeInsert()
    }

    db.withConnection { implicit connection =>
      val result: Boolean = SQL("select 1").execute()
    }

    val sql = Json.toJson("sql")
    Ok(sql)
  }

}