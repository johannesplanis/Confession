package co.planis.confession.ui.feed

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import co.planis.confession.AddConfessionActivity
import co.planis.confession.CONFESSIONS
import co.planis.confession.R
import co.planis.confession.model.ConfessionModel
import com.firebase.ui.database.MyFirebaseRecyclerAdapter
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_confessions.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivity
import timber.log.Timber

class ConfessionsActivity : AppCompatActivity(), AnkoLogger {

    private var databaseReference: DatabaseReference? = null
    private var confessionsReference: DatabaseReference? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confessions)

        initFirebase()
        initAdapter()
        Timber.i("Siema!")
        Timber.d("siema świecie")
        confessionListAddFab.setOnClickListener { startActivity<AddConfessionActivity>() }
    }


    private fun initFirebase() {

        databaseReference = FirebaseDatabase.getInstance().reference
        confessionsReference = databaseReference?.child(CONFESSIONS)
    }

    private fun initAdapter() {

        val confessionsAdapter = MyFirebaseRecyclerAdapter(R.layout.row_confessions, confessionsReference as Query, {


            Toast.makeText(this, "item clicked: " + it.toString(), Toast.LENGTH_LONG).show()
        }, {
            it.ref.runTransaction(object : Transaction.Handler {
                override fun onComplete(p0: DatabaseError?, p1: Boolean, p2: DataSnapshot?) {


                }

                override fun doTransaction(p0: MutableData): Transaction.Result {

                    val confession = p0.getValue(ConfessionModel::class.java);

                    if (!hasLiked)
                        confession.incrementLikes()
                    else
                        confession.decrementLikes()

                    notifyLikesChanged()

                    p0.value = confession
                    return Transaction.success(p0)
                }


            })
            Toast.makeText(this, "like clicked: " + it.toString(), Toast.LENGTH_LONG).show()
        })

        confessionListRv.layoutManager = LinearLayoutManager(this)
        confessionListRv.adapter = confessionsAdapter
    }

    var hasLiked: Boolean = false

    fun hasUserLiked(): Boolean {
        return hasLiked
    }

    private fun notifyLikesChanged() {
        hasLiked = !hasLiked
    }

    private fun loadData() {


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
    }
}



