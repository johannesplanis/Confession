package co.planis.confession

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import co.planis.confession.model.ConfessionModel
import com.firebase.ui.database.MyFirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import kotlinx.android.synthetic.main.activity_confessions.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.startActivity

class ConfessionsActivity : AppCompatActivity(),AnkoLogger{


    private var itemListener : OnItemListener? = null


    private var databaseReference : DatabaseReference? = null
    private var confessionsReference : DatabaseReference? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confessions)



        confessionListRv.layoutManager = LinearLayoutManager(this)

        itemListener = object : OnItemListener() {
            override fun onItemClick(model: ConfessionModel) {
                debug { "Item click: "+model.toString() }
            }

            override fun onLikeClick(model: ConfessionModel) {
                debug { "Like click: "+model.toString() }
            }

        }
        initFirebase()

        confessionListAddFab.setOnClickListener { startActivity<AddConfessionActivity>() }
    }



    private fun initFirebase() {

        databaseReference = FirebaseDatabase.getInstance().reference
        confessionsReference = databaseReference?.child(CONFESSIONS)


        val confessionsAdapter = MyFirebaseRecyclerAdapter(R.layout.row_confessions,confessionsReference as Query,{
            Toast.makeText(this,"item clicked: "+it.toString(),Toast.LENGTH_LONG).show()
        },{
            Toast.makeText(this,"like clicked: "+it.likes,Toast.LENGTH_LONG).show()
        })

        confessionListRv.adapter = confessionsAdapter

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
*/    }




}



