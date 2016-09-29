package co.planis.confession.model

import java.util.*

/**
 * Created by janpluta on 28.09.16.
 */
data class ConfessionModel(val id : Int = 1, val text: String, val likes : Int = 0, val comments : List<CommentModel> = ArrayList<CommentModel>(), val op : UserModel, val date : String) {

        constructor() : this(text = "", op = UserModel(),date = "")
}