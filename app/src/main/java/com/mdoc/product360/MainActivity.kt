package com.mdoc.product360

import android.os.Bundle
import android.view.MotionEvent
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MotionEventCompat
import java.util.Timer
import java.util.TimerTask


class MainActivity : AppCompatActivity() {
    private lateinit var imgView: ImageView
    private var mStartX = 0
    private var mStartY = 0
    private var mEndX = 0
    private var mEndY = 0
    private var mImageIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imgView = findViewById(R.id.imgRotations)


        val delay = 1000 // delay for 5 sec.

        val period = 128 // repeat every sec.

        var count = 0
        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if(count==19) count = 0

                imgView.setImageLevel(count)
                count++
            }
        }, delay.toLong(), period.toLong())


     }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        return when (MotionEventCompat.getActionMasked(event)) {
            MotionEvent.ACTION_DOWN -> {
                mStartX = event!!.x.toInt()
                mStartY = event.y.toInt()
                true
            }

            MotionEvent.ACTION_MOVE -> {
                mEndX = event!!.x.toInt()
                mEndY = event.y.toInt()
                if (mEndX - mStartX > 3) {
                    mImageIndex++
                    if (mImageIndex > 20) mImageIndex = 0
                    imgView.setImageLevel(mImageIndex)
                }
                if (mEndX - mStartX < -3) {
                    mImageIndex--
                    if (mImageIndex < 0) mImageIndex = 20
                    imgView.setImageLevel(mImageIndex)
                }
                mStartX = event.x.toInt()
                mStartY = event.y.toInt()
                true
            }

            MotionEvent.ACTION_UP -> {
                mEndX = event!!.x.toInt()
                mEndY = event.y.toInt()
                true
            }

            MotionEvent.ACTION_CANCEL -> true
            MotionEvent.ACTION_OUTSIDE -> true
            else -> super.onTouchEvent(event)
        }
    }
}