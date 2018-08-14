package controllers

import javax.inject.Inject
import play.api.mvc._

class LayoutTestController @Inject()(cc: ControllerComponents) (implicit assetsFinder: AssetsFinder)
    extends AbstractController(cc) {


  def test = Action {
    Ok(views.html.test("Your new application is ready."))
  }

}
