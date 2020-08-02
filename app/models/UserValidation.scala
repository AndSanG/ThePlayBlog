package models
import javax.inject.Inject

@javax.inject.Singleton
class UserValidation @Inject()() {
  def lookupUser(u: User): Boolean = {
    print(u)
    findUser(u.name).exists { user =>
      if (u.password == user.password) {
        true
      } else {
        false
      }
    }
  }

  var users = Set(
    User("Barney","barney123"),
    User("Tedd","tedd123"),
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

  }
}




