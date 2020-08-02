package models

import play.api.data.Form
import play.api.data.Forms._

case class Comment (response: String)

object Comment{
  val form = Form(
    mapping(
      "response" -> nonEmptyText,
    )(Comment.apply)(Comment.unapply)
  )
}
