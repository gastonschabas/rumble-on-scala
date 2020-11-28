package com.gaston.controller

import com.gaston.repository.HelloRepository
import javax.inject.Inject
import play.api.i18n.{Lang, Langs}
import play.api.mvc.{
  AbstractController,
  Action,
  AnyContent,
  ControllerComponents
}
import scala.concurrent.ExecutionContext.Implicits.global

class HelloController @Inject() (
  cc: ControllerComponents,
  helloRepository: HelloRepository,
  langs: Langs
) extends AbstractController(cc) {

  def index: Action[AnyContent] =
    Action.async { request =>
      val lang = request.acceptLanguages.headOption
        .orElse(langs.availables.headOption)
        .getOrElse(Lang.defaultLang)

      helloRepository.find(lang.language).map {
        case Some(hello) => Ok(hello.msg)
        case None => NotFound(s"hello not found for lang ${lang.language}")
      }
    }

}
