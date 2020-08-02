package models
import java.util.Date

import play.api.data.Form
import play.api.data.Forms._
import models.Utils.DateUtil._


case class PostData (title: String, content: String)

object PostData{
  val form = Form(
    mapping(
      "title" -> nonEmptyText,
      "content" -> nonEmptyText
    )(PostData.apply)(PostData.unapply)
  )
}

case class Post(id: Int, title: String, content: String,var likes: Int = 0, var dislikes: Int = 0, var score:Int = 0, var date: Date, var comments: Set[String]=Set())
//case class Post(id: Int, title: String, content: String, comments: List[Option[Comment]])

object Post {
  var posts = Set(
    Post(872144344,"THE LORENZO VON MATTERHORN","\"The Lorenzo Von Matterhorn\" involves creating a series of elaborate websites claiming you to be extremely a famous, rich, and noble person by the name of Lorenzo Von Matterhorn. You make this clear to the girl you approach, so they look you up, leave, and come back to ask them out, knowing they have googled you.",10,3,7,convertStringToDate("August 1,2020"),Set("Comment1","Comment2")),
    Post(863107811,"THE BRIAN'S FRIEND","\"The Brian's Friend\" is a super simple play, requiring you to talk to the girl telling her you to recognize her from Brian's party, or Brian's event because everyone knows a Brian.",6,2,4,convertStringToDate("August 2,2020"),Set("Comment1","Comment2"))
  )

  def genId = {
    scala.util.Random.nextInt()
  }

  //1 by date
  //2 by vote
  def findAll(orderBy:Int) = {
    if (orderBy == 2)
      posts.toList.sortBy(_.score)(Ordering[Int].reverse)
    else if (orderBy == -2) {
      posts.toList.sortBy(_.score)
    }
    else if (orderBy == -1) {
      posts.toList.sortBy(_.date)
    }else{
      posts.toList.sortBy(_.date)(Ordering[Date].reverse)
    }
  }

  def findById(id: Int) = posts.find(_.id == id)

  def add(post: Post) {
    posts = posts + post
  }

  def addComment(postId : Int, comment: String): Unit ={
    Post.findById(postId).map{ post_ =>
      post_.comments = post_.comments + comment
    }.getOrElse(0)
  }

}
