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
        .verifying("too few chars",  s => lengthIsGreaterThanNCharacters(s, 3))
        .verifying("too many chars", s => lengthIsLessThanNCharacters(s, 16)),
      "password" -> nonEmptyText
        .verifying("too few chars",  s => lengthIsGreaterThanNCharacters(s, 3))
        .verifying("too many chars", s => lengthIsLessThanNCharacters(s, 16)),
    )(User.apply)(User.unapply)
  )


  private def lengthIsGreaterThanNCharacters(s: String, n: Int): Boolean = {
    if (s.length > n) true else false
  }

  private def lengthIsLessThanNCharacters(s: String, n: Int): Boolean = {
    if (s.length < n) true else false
  }


}


