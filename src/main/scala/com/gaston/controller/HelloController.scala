package com.gaston.controller

import com.gaston.repository.HelloRepository
import javax.inject.Inject
import play.api.i18n.I18nSupport
import play.api.mvc.{
  AbstractController,
  Action,
  AnyContent,
  ControllerComponents
}

import scala.concurrent.ExecutionContext.Implicits.global

class HelloController @Inject() (
  cc: ControllerComponents,
  helloRepository: HelloRepository
) extends AbstractController(cc)
    with I18nSupport {

  def index: Action[AnyContent] =
    Action.async { request =>
      val lang = request.lang.language
      helloRepository.find(request.lang.language).map {
        case Some(value) => Ok(value.msg)
        case None => NotFound(s"hello not found for lang $lang")
      }
    }

}
