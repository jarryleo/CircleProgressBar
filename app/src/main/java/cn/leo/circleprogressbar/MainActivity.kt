package cn.leo.circleprogressbar

import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import cn.leo.circleprogressbar.view.CircleProgressBar
import cn.leo.circleprogressbar.view.NodeProgressBar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        val nodeProgressBar = findViewById<NodeProgressBar>(R.id.npb_node)
        nodeProgressBar.setNodes(arrayOf("How", "Are", "You"))
        nodeProgressBar.setProgress(2)

        val circleProgressBar = findViewById<CircleProgressBar>(R.id.cpb_progress)
        object : CountDownTimer(15000, 10) {
            override fun onFinish() {
                //this.start()
            }

            override fun onTick(millisUntilFinished: Long) {
                circleProgressBar.downProgress((millisUntilFinished / 15).toInt())
            }

        }.start()
    }
}