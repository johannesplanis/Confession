package co.planis.confession.model

import java.util.*

/**
 * Created by janpluta on 28.09.16.
 */
data class ConfessionModel(val id : Int = 1, val text: String = "", var likes : Int = 0, var comments : List<CommentModel> = ArrayList<CommentModel>(), val op : UserModel = UserModel(), val date : Date = Date()) {


    fun incrementLikes(){
        likes = likes.inc()
    }

    fun decrementLikes(){
        likes  = likes.dec()
    }
}