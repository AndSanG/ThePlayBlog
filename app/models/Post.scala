package models

import play.api.data.Form
import play.api.data.Forms._

case class Post(id: Int, title: String, content: String, comments: List[String])
//case class Post(id: Int, title: String, content: String, comments: List[Option[Comment]])

object Post {

  val form = Form[Post](
    mapping(
      "id" -> number,
      "title" -> nonEmptyText,
      "content" -> nonEmptyText,
      "comments" -> list(text)
    )(Post.apply)(Post.unapply)
  )

  var posts = Set(
    Post(1,"2 Post 1","Post Content 1",List("Comment1","Comment2")),
    Post(2,"1 Post 2","Post Content 2",List("Comment1","Comment2"))
  )
  //1 by date
  //2 by vote
  def findAll(orderBy:Int) = if (orderBy ==2) posts.toList.sortBy(_.title) else posts.toList.sortBy(_.id)

  def findById(id: Long) = posts.find(_.id == id)

  def add(post: Post) {
    posts = posts + post
  }

}
