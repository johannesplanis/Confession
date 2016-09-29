package co.planis.confession

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import co.planis.confession.model.ConfessionModel
import co.planis.confession.model.UserModel
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_confession.*
import kotlinx.android.synthetic.main.activity_confessions.*
import org.jetbrains.anko.AlertDialogBuilder
import org.jetbrains.anko.toast
import java.util.*

class AddConfessionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_confession)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Dodaj wyznanie"

        addConfessionPublishFab.setOnClickListener { publishConfession() }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> {
                var dialog = AlertDialogBuilder(this)
                dialog.message("Nie skończyłeś pisać. Czy na pewno chcesz wyjść?")
                dialog.positiveButton { finish() }
                dialog.negativeButton("ANULUJ")
                dialog.show()
                return true
            }
        }
        return false
    }

    private fun publishConfession() {

        val confessionText = addConfessionPreviewEt.text.toString()

        if (confessionText.equals(""))
            toast("Napisz coś!")
        else {

            val confession: ConfessionModel = ConfessionModel(text = confessionText, op = UserModel(name = "Johannes"), date = Date().toString())
            val confessionsReference = FirebaseDatabase.getInstance().reference?.child(CONFESSIONS)
            confessionsReference?.push()?.setValue(confession)?.addOnCompleteListener { finalizeCreator() }
        }
    }

    private fun finalizeCreator() {
        toast("Wysłano")
        finish()
    }


}
