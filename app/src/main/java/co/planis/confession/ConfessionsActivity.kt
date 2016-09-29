package co.planis.confession

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.OnItemTouchListener
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import co.planis.confession.model.CommentModel
import co.planis.confession.model.ConfessionModel
import co.planis.confession.model.UserModel
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_confessions.*
import kotlinx.android.synthetic.main.row_confessions.view.*
import java.util.*
import org.jetbrains.anko.startActivity

class ConfessionsActivity : AppCompatActivity() {

    private var layoutManager: LinearLayoutManager? = null

    private var databaseReference : DatabaseReference? = null
    private var confessionsReference : DatabaseReference? = null


    private var adapter : FirebaseRecyclerAdapter<ConfessionModel,ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confessions)

        initFirebase()
        loadData()

        confessionListAddFab.setOnClickListener { startActivity<AddConfessionActivity>() }
    }

    private fun initFirebase() {

        databaseReference = FirebaseDatabase.getInstance().reference


    }

    private fun loadData() {

        confessionsReference = databaseReference?.child(CONFESSIONS)

        layoutManager = LinearLayoutManager(this)



        adapter = object : FirebaseRecyclerAdapter<ConfessionModel,ViewHolder>(ConfessionModel::class.java,
                R.layout.row_confessions,ViewHolder::class.java,confessionsReference){
            override fun populateViewHolder(viewHolder: ViewHolder, model: ConfessionModel, position: Int) {
                viewHolder.bindConfessions(model)
            }
        }
        //TODO research infix function problems

/*

        adapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                val friendlyMessageCount = adapter?.itemCount
                val lastVisiblePosition = layoutManager?.findLastCompletelyVisibleItemPosition()
                // If the recycler view is initially being loaded or the
                // user is at the bottom of the list, scroll to the bottom
                // of the list to show the newly added message.
                if (lastVisiblePosition == -1 || friendlyMessageCount?.minus(1)<=positionStart && lastVisiblePosition == positionStart - 1) {
                    confessionListRv.scrollToPosition(positionStart)
                }
            }
        })
*/

        confessionListRv.layoutManager = layoutManager
        confessionListRv.adapter = adapter

    }







    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindConfessions(confessions: ConfessionModel) {
            with(confessions) {

                itemView.confessionSimpleAuthorTv.text = confessions.op.name
                itemView.confessionSimpleContentTv.text = confessions.text
                itemView.confessionSimpleDateTv.text = confessions.date
                itemView.confessionsCommentsNumberTv.text = confessions.comments.size.toString()
                itemView.confessionsLikesNumberTv.text = confessions.likes.toString()

            }
        }
    }



}



