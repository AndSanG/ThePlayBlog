package controllers

import javax.inject.Inject
import play.api.mvc._
import models.{UserSession,Preferences, User, UserValidation}
import play.api.data.Form

class UserController @Inject() (cc: MessagesControllerComponents,userValidation: UserValidation) extends MessagesAbstractController(cc) {

  private val formSubmitUrl = routes.UserController.processLoginAttempt

  def showLoginForm = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.userLogin(User.form, formSubmitUrl))
  }

  def processLoginAttempt = Action { implicit request: MessagesRequest[AnyContent] =>
    val errorFunction = { formWithErrors: Form[User] =>
      print(formWithErrors)
      BadRequest(views.html.userLogin(formWithErrors, formSubmitUrl))
    }
    val successFunction = { user: User =>
      // form validation/binding succeeded ...
      val foundUser: Boolean = userValidation.lookupUser(user)
      if (foundUser) {
        UserSession.SESSION_USERNAME_KEY = user.name
        val message = "Welcome " + user.name
        Redirect(routes.PostsController.feed(Preferences.ORDER))
          .flashing("success" -> message)
      } else {
        Redirect(routes.UserController.showLoginForm)
          .flashing("error" -> "Invalid username/password.")
      }
    }
    val formValidationResult: Form[User] = User.form.bindFromRequest
    formValidationResult.fold(
      errorFunction,
      successFunction
    )
  }
  def logOut = Action { implicit request: MessagesRequest[AnyContent] =>
    Preferences.reset
    UserSession.logOut
    Redirect(routes.PostsController.feed(Preferences.ORDER))
      .flashing("success" -> "Your are logged out")
  }


}
