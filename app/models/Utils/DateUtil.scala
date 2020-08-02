package models.Utils

import java.text.SimpleDateFormat
import java.util.{Calendar, Date}

object DateUtil {

  val DATE_FORMAT = "MMMMM d,y"

  def getDateAsString(d: Date): String = {
    val dateFormat = new SimpleDateFormat(DATE_FORMAT)
    dateFormat.format(d)
  }

  def convertStringToDate(s: String): Date = {
    val dateFormat = new SimpleDateFormat(DATE_FORMAT)
    dateFormat.parse(s)
  }

  def getDate: Date = {
    Calendar.getInstance().getTime()
  }

}