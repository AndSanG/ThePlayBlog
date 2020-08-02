package models

import com.typesafe.sslconfig.ssl.FakeChainedKeyStore.User
import play.api.data.Form
import play.api.data.Forms._

case class User (name: String, password: String)

object User {

  var isLogged : Boolean = false

  val form: Form[User] = Form (
    mapping(
      "name" -> nonEmptyText
        .verifying("too few chars",  s => lengthIsGreaterThanNCharacters(s, 4))
        .verifying("too many chars", s => lengthIsLessThanNCharacters(s, 16)),
      "password" -> nonEmptyText
        .verifying("too few chars",  s => lengthIsGreaterThanNCharacters(s, 4))
        .verifying("too many chars", s => lengthIsLessThanNCharacters(s, 16)),
    )(User.apply)(User.unapply)
  )



  var users = Set(
    User("Barney","barney123"),
    User("Ted","ted123"),
    User("Marshal","marshal123"),
    User("Robin","robin123"),
    User("Lily","lily123")
  )

  def findUser(name: String) = users.find(_.name == name)

  def validate(username: String, password: String) = {
    findUser(username).map{ user =>
      if (password == user.password){
        Some(user)
      }orElse(None)
    }.getOrElse(None)
    username match {
      case "bob" if password >= "" =>
        Some(User(username, password))
      case "admin" =>
        Some(User(username, password))
      case _ =>
        None
    }
  }

  private def lengthIsGreaterThanNCharacters(s: String, n: Int): Boolean = {
    if (s.length > n) true else false
  }

  private def lengthIsLessThanNCharacters(s: String, n: Int): Boolean = {
    if (s.length < n) true else false
  }


}


