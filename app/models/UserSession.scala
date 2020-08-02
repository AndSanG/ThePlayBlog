package models

@javax.inject.Singleton
object UserSession {
  var SESSION_USERNAME_KEY = ""
  def logOut = {
    SESSION_USERNAME_KEY = ""
  }
}



