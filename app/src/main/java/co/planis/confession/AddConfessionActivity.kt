package co.planis.confession

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import co.planis.confession.model.ConfessionModel
import co.planis.confession.model.UserModel
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_confession.*
import kotlinx.android.synthetic.main.activity_confessions.*
import java.util.*

class AddConfessionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_confession)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Dodaj wyznanie")

        addConfessionPublishFab.setOnClickListener { publishConfession() }
    }

    private fun publishConfession() {

        val confession : ConfessionModel = ConfessionModel(text = addConfessionPreviewEt.text.toString(),op = UserModel(name = "Johannes"),date = Date().toString())

        val confessionsReference = FirebaseDatabase.getInstance().reference?.child(CONFESSIONS)




    }


}
