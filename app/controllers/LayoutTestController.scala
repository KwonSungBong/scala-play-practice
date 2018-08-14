package controllers

import javax.inject.Inject
import play.api.mvc._

class LayoutTestController @Inject()(cc: ControllerComponents) (implicit assetsFinder: AssetsFinder)
    extends AbstractController(cc) {


  def test = Action {
    Ok(views.html.test("test"))
  }

  def plain = Action {
    Ok(views.html.plain("plain"))
  }

}
