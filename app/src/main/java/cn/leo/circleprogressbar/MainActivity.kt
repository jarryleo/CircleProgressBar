package cn.leo.circleprogressbar

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import cn.leo.circleprogressbar.view.CircleProgressBar
import cn.leo.circleprogressbar.view.ClickStringUtil
import cn.leo.circleprogressbar.view.NodeProgressBar

class MainActivity : AppCompatActivity(), ClickStringUtil.onTextClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        val nodeProgressBar = findViewById<NodeProgressBar>(R.id.npb_node)
        nodeProgressBar.setNodes(arrayOf("投保信息", "确认投保", "支付"))
        nodeProgressBar.setProgress(2)

        val circleProgressBar = findViewById<CircleProgressBar>(R.id.cpb_progress)
        object : CountDownTimer(15000, 10) {
            override fun onFinish() {
                this.start()
            }

            override fun onTick(millisUntilFinished: Long) {
                circleProgressBar.downProgress((millisUntilFinished / 15).toInt())
            }

        }.start()

        val textView = findViewById<TextView>(R.id.tv_text)
        ClickStringUtil.setClickText(textView, Color.RED, false, this, 16, 22, 23, 29)
    }

    override fun onClick(text: String?) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


}