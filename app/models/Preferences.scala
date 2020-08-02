package models

@javax.inject.Singleton
object Preferences {
  var ORDER = 1 //1 or 2 for list order
  var DESC: Boolean = true
  def reset = {
    ORDER = 1
    DESC = true
  }
}
