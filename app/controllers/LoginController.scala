package controllers

import javax.inject.Inject

import play.api.mvc._

class LoginController @Inject()(cc: ControllerComponents)
    extends AbstractController(cc) {

  def login = Action {
    Ok(views.html.login("login"))
  }

}
