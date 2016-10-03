package co.planis.confession

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Vibrator
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import co.planis.confession.model.ConfessionModel
import co.planis.confession.model.UserModel
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_confession.*
import org.jetbrains.anko.AlertDialogBuilder
import org.jetbrains.anko.toast
import java.util.*

class AddConfessionActivity : AppCompatActivity() {


    var sensorManager: SensorManager? = null

    var mAccel: Float = 0.0f
    var mAccelCurrent: Float = 0.0f

    var vibrator: Vibrator? = null

    var isSent: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_confession)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Dodaj wyznanie"

        //addConfessionPublishFab.setOnClickListener { publishConfession() }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> {
                val dialog = AlertDialogBuilder(this)
                dialog.message("Nie skończyłeś pisać. Czy na pewno chcesz wyjść?")
                dialog.positiveButton { finish() }
                dialog.negativeButton("ANULUJ")
                dialog.show()
                return true
            }
        }
        return false
    }


    var sensorListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {

            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            val accelLast = mAccelCurrent

            mAccelCurrent = Math.sqrt((x * x + y * y + z * z).toDouble()).toFloat()

            val delta: Float = mAccelCurrent.minus(accelLast)
            mAccel = mAccel * 0.9f + delta

            if (mAccel > 12) {
                publishConfession()
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }
    }


    private fun initShakeListener() {
        sensorManager = this.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager?.registerListener(sensorListener,
                sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL)
    }


    private fun publishConfession() {

        val confessionText = addConfessionPreviewEt.text.toString()
        val confessionNick = addConfessionAuthorEt.text.toString()

        if (confessionText.equals("")) {
            toast("Napisz coś!")
            vibrator?.vibrate(200)
        } else if (confessionNick.equals("")) {
            vibrator?.vibrate(200)
            toast("Uzupełnij swój nick")
        } else if(!isSent){

            isSent = true
            val confession: ConfessionModel = ConfessionModel(text = confessionText, op = UserModel(name = confessionNick), date = Date())
            val confessionsReference = FirebaseDatabase.getInstance().reference?.child(CONFESSIONS)
            toast("Wysyłam")
            confessionsReference?.push()?.setValue(confession)?.addOnCompleteListener { finalizeCreator() }
        }
    }

    private fun finalizeCreator() {
        toast("Wysłano")
        finish()
    }

    override fun onStart() {
        super.onStart()
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        initShakeListener()
    }

    override fun onStop() {
        super.onStop()
        sensorManager?.unregisterListener(sensorListener)
    }
}
