package controllers

import javax.inject.Inject

import play.api.mvc._

class AuthController @Inject()(cc: ControllerComponents)
    extends AbstractController(cc) {

  def login = Action {
    Ok(views.html.auth.login("login"))
  }

  def register = Action {
    Ok(views.html.auth.register("register"))
  }

  def forgotPassword = Action {
    Ok(views.html.auth.forgotPassword("forgotPassword"))
  }

}
