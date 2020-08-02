package models
import javax.inject.Inject

@javax.inject.Singleton
class UserValidation @Inject()() {
  def lookupUser(u: User): Boolean = {
    print(u)
    if (u.name == "andsangue" && u.password == "123456") true else false
  }
}




