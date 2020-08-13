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

case class Post(id: Int, author:String,title: String, content: String,var likes: Int = 0, var dislikes: Int = 0, var score:Int = 0, var date: Date, var comments: Set[String]=Set())
//case class Post(id: Int, title: String, content: String, comments: List[Option[Comment]])

object Post {
  var posts = Set(
    Post(877025344,"Barney","THE DON'T DRINK THAT","\"The Don't Drink That\" sees Barney run-up to a girl about to take a drink and urge her not to drink it because he saw someone slip something into it, pointing to Ted to blame.\n\nThis play would 100% not work because A) the person you blame could easily refute the fact, especially with cameras around and B) even if that part went unquestioned, why would this make someone want to sleep with you?",20,4,16,convertStringToDate("July 10,2020"),Set("Insane","Incredible")),
    Post(877015344,"Tedd","THE TED MOSBY","\"The Ted Mosby\" play requires you to dress up as Ted Mosby and tell your 'target' that you got left at the altar. This simple play relies on the sympathy card, which, while shady, would work very well in a bar or club with alcohol involved, especially for Barney's goals.",4,10,-6,convertStringToDate("July 16,2020"),Set("Pathetic")),
    Post(875025344,"Barney","THE TWO CAN PLAY AT THAT GAME","\"The Two Can Play At That Game,\" as demonstrated by Barney, saw him go to a random woman's apartment and tell her his wife and her husband are having an affair. He then says he wants revenge but does not know how inciting an invitation into the apartment. If you find the exact right apartment and do not bring the notebook Barney does, then this play could have a very high success rate, playing on women's or men's emotions once more.",30,14,16,convertStringToDate("July 21,2020"),Set("I can not believe this one ","This does not works")),
    Post(877725344,"Barney","THE CALL BARNEY STINSON","\"The Call Barney Stinson\" is simple, get a ticket to the Super Bowl, or a similar event, hold up a sign with your phone number, and wait.\n\nAssuming you can get a ticket, and get seen by the cameras, this play is bound to have at least some success. Your success level would not reach that of Barney in the show, but you can guarantee you would get at least a couple of phone calls.",23,4,19,convertStringToDate("July 25,2020"),Set("I am calling you","Nice one")),
    Post(872144341,"Barney","THE LORENZO VON MATTERHORN","\"The Lorenzo Von Matterhorn\" involves creating a series of elaborate websites claiming you to be extremely a famous, rich, and noble person by the name of Lorenzo Von Matterhorn. You make this clear to the girl you approach, so they look you up, leave, and come back to ask them out, knowing they have googled you. This play, unfortunately, could work very easily with the right people, despite its immense elaborateness.",10,3,7,convertStringToDate("August 1,2020"),Set("This is legendary","There is no failure")),
    Post(863107811,"Barney","THE BRIAN'S FRIEND","\"The Brian's Friend\" is a super simple play, requiring you to talk to the girl telling her you to recognize her from Brian's party, or Brian's event because everyone knows a Brian. The issue with the play is that there is no guarantee you will get what Barney wants. At the very least, though, in the right setting, you will have some drinks, some laughs, and maybe a date entirely based on lies.",6,2,4,convertStringToDate("August 2,2020"),Set("The best one","You only write BS here"))
  )

  def genId = {
    //validate that the id is new
    scala.util.Random.nextInt().abs
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
