package com.gaston.controller

import com.gaston.exception.validation.HelloLangNotExistException
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
import scala.concurrent.Future

class HelloController @Inject() (
  cc: ControllerComponents,
  helloRepository: HelloRepository,
  langs: Langs
) extends AbstractController(cc) {

  def index: Action[AnyContent] =
    Action.async { request =>
      val lang = request.acceptLanguages.headOption
        .orElse(langs.availables.headOption)
        .toRight(
          HelloLangNotExistException("no languages were configured", None)
        )

      lang match {
        case Left(_) => Future(InternalServerError)
        case Right(lang) =>
          findHelloForLanguage(lang)
      }
    }

  private def findHelloForLanguage(lang: Lang) = {
    helloRepository.find(lang.language).map {
      case Some(hello) => Ok(hello.msg)
      case None => NotFound(s"hello not found for lang ${lang.language}")
    }
  }
}
