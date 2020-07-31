package models

import play.api.data.Form
import play.api.data.Forms._

case class Comment(user: String, title:String, content: String)

object Comment{
  val form = Form[Comment](
    mapping(
      "user" -> text,
      "title" -> nonEmptyText,
      "content" -> nonEmptyText,
    )(Comment.apply)(Comment.unapply)
  )
}