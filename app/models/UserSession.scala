package models
import scala.collection.mutable.ListBuffer

@javax.inject.Singleton
object UserSession {
  var SESSION_USERNAME_KEY = ""
  var liked= ListBuffer[Int]()
  def logOut = {
    SESSION_USERNAME_KEY = ""
    liked = ListBuffer[Int]()
  }
}



