package controllers

import javax.inject.Inject

import models._
import play.api.data.Forms._
import play.api.data._
import play.api.i18n._
import play.api.mvc._
import views._

import scala.concurrent.{ExecutionContext, Future}

class NoticeController @Inject()(noticeService: NoticeRepository,
                                 cc: MessagesControllerComponents)(implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) {

  private val logger = play.api.Logger(this.getClass)

  var Home = Redirect(routes.NoticeController.list(0, 2, ""))

  val noticeForm = Form(
    mapping(
      "id" -> ignored(None: Option[Long]),
      "title" -> nonEmptyText,
      "content" -> nonEmptyText,
      "createdDate" -> optional(date("yyyy-MM-dd")),
      "modifiedDate" -> optional(date("yyyy-MM-dd"))
    )(Notice.apply)(Notice.unapply)
  )

  def index = Action {
    Home
  }

  def list(page: Int, orderBy: Int, filter: String) = Action.async { implicit request =>
    noticeService.list(page = page, orderBy = orderBy, filter = ("%" + filter + "%")).map { page =>
      Ok(html.notice.list(page, orderBy, filter))
    }
  }

//  def edit(id: Long) = Action.async { implicit request =>
//    noticeService.findById(id).flatMap {
//      OK(html.notice.editForm(id, noticeForm.fill(notice)))
//    }
//  }
//
//  def update(id: Long) = Action.async { implicit request =>
//    noticeForm.bindFromRequest.fold(
//      notice => {
//        noticeService.update(id, notice).map { _ =>
//          Home.flashing("success" -> "Notice %s has been updated".format(notice.title))
//        }
//      }
//    )
//  }
//
//  def create = Action.async { implicit request =>
//    Ok(html.createForm(noticeForm))
//  }
//
//  def save = Action.async { implicit request =>
//    noticeForm.bindFromRequest.fold(
//      notice => {
//        noticeService.insert(notice).map { _ =>
//          Home.flashing("success" -> "Notice %s has been created".format(notice.title))
//        }
//      }
//    )
//  }
//
//  def delete(id: Long) = Action.async {
//    noticeService.delete(id).map { _ =>
//      Home.flashing("success" -> "Notice has been deleted")
//    }
//  }

}
