package com.gaston.controller

import javax.inject.Inject
import play.api.mvc.{
  AbstractController,
  Action,
  AnyContent,
  ControllerComponents
}

class HelloController @Inject() (cc: ControllerComponents)
    extends AbstractController(cc) {

  def index: Action[AnyContent] =
    Action { _ =>
      Ok("hello world")
    }

}
