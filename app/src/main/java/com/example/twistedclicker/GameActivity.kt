package com.example.twistedclicker

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.SurfaceHolder
import kotlin.math.pow
import android.view.MotionEvent
import android.view.SurfaceView
import android.app.Activity
import android.content.Intent
import android.graphics.*
import android.graphics.Color
import android.util.AttributeSet
import java.util.*
import android.graphics.Shader
import android.graphics.LinearGradient


class GameActivity : Activity() {
    private val paintCircle = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paintText = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paintTextGO = Paint(Paint.ANTI_ALIAS_FLAG)
    private var random = Random()
    private var score = 0
    private var hitPoints = 3
    private var initX = 300f
    private var initY = 500f
    private var radius = 60f
    private var targetX = 0f
    private var targetY = 0f
    private var drawing = true


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(MySurfaceView(this))
    }

    inner class MySurfaceThread(
        private val myThreadSurfaceHolder: SurfaceHolder,
        private val myThreadSurfaceView: MySurfaceView,
    ) : Thread() {
        private var myThreadRun = false
        fun setRunning(b: Boolean) {
            myThreadRun = b
        }

        override fun run() {
            super.run();
            while (myThreadRun) {
                var c: Canvas? = null
                try {
                    c = myThreadSurfaceHolder.lockCanvas(null)
                    synchronized(myThreadSurfaceHolder) { myThreadSurfaceView.onDraw(c) }
                    sleep(1)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } finally {
                    if (c != null) {
                        myThreadSurfaceHolder.unlockCanvasAndPost(c)
                    }
                }
            }
        }
    }

    inner class MySurfaceView : SurfaceView, SurfaceHolder.Callback {
        private var thread: MySurfaceThread? = null
        public override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas);
            if (drawing) {
                canvas.drawRGB(160, 180, 255)
                canvas.drawText("Score:", 100f, 40f,paintText)
                canvas.drawText(score.toString(), 260f, 40f, paintText)
                canvas.drawText("HP:", 570f, 40f,paintText)
                canvas.drawText(hitPoints.toString(), 650f, 40f,paintText)
                canvas.drawCircle(initX, initY, radius, paintCircle)
                if (circleP(targetX, targetY, initX, initY) <= radius.pow(2)) {
                    if (score < 50){
                        score += 1
                    }
                    else if ((score >= 50) && score < 100){
                        score += 1
                    }
                    else if ((score >= 100) && score < 150){
                        score += 2
                    }
                    else if ((score >= 150) && score < 200){
                        score += 3
                    }
                    else if ((score >= 200) && score < 300){
                        score += 3
                    }
                    else if ((score >= 300) && score < 450){
                        score += 4
                    }
                    else if ((score >= 450) && score < 600){
                        score += 4
                    }
                    else if ((score >= 600) && score < 750){
                        score += 5
                    }
                    else if ((score >= 750) && score < 950){
                        score += 10
                    }
                    else if ((score >= 950) && score < 1250){
                        score += 10
                    }
                    else if(score >= 1250) {
                        score += 20
                    }
                    targetX = 0f
                    targetY = 0f
                    randomPoints()
                } else {
                    if (score <= 50){
                        checkAlpha(1, canvas)
                    }
                    else if ((score > 50) && score <= 100){
                        checkAlpha(2, canvas)
                    }
                    else if ((score > 100) && score <= 150){
                        checkAlpha(3, canvas)
                    }
                    else if ((score > 150) && score <= 200){
                        checkAlpha(4, canvas)
                    }
                    else if ((score > 200) && score <= 300){
                        checkAlpha(5, canvas)
                    }
                    else if ((score > 300) && score <= 450){
                        checkAlpha(6, canvas)
                    }
                    else if ((score > 450) && score <= 600){
                        checkAlpha(7, canvas)
                    }
                    else if ((score > 600) && score <= 750){
                        checkAlpha(8, canvas)
                    }
                    else if ((score > 750) && score <= 950){
                        checkAlpha(10, canvas)
                    }
                    else if ((score > 950) && score <= 1250){
                        checkAlpha(12, canvas)
                    }
                    else if(score > 1250) {
                        checkAlpha(15, canvas)
                    }
                }
            }
        }

        override fun onTouchEvent(event: MotionEvent): Boolean {
            // return super.onTouchEvent(event);
            val action = event.action
            if (action == MotionEvent.ACTION_DOWN) {
                targetX = event.x
                targetY = event.y
                drawing = true
            }
            return true
        }

        private fun circleP(targX: Float,
                            targY: Float,
                            centerX : Float,
                            centerY : Float): Float {
            return (targX - centerX).pow(2) + (targY - centerY).pow(2)
        }

        private fun randomPoints(){
            initX = random.nextInt(550).toFloat() + 50
            initY = random.nextInt(1100).toFloat() + 100
            paintCircle.alpha = 247
        }

        private fun checkAlpha(unit : Int, canvas: Canvas){
            if (paintCircle.alpha >= unit){
                paintCircle.alpha -= unit
            }
            else{
                randomPoints()
                if (hitPoints > 1) {
                    hitPoints -= 1
                } else {
                    Data.scores += score
                    var intent = Intent(this@GameActivity, MainActivity::class.java)
                    canvas.drawText("Game Over", 60f, 750f, paintTextGO)
                    thread?.setRunning(false)
                    startActivity(intent)
                }
            }
        }

        private fun createGradientShader() : LinearGradient {
            return LinearGradient(100f, 0f, 0f, 100f,
                intArrayOf(Color.parseColor("#EB0202"), Color.parseColor("#D76B08")),
                floatArrayOf(0.1f, 1f),
                Shader.TileMode.MIRROR)
        }

        private fun createUkraineShader() : LinearGradient {
            return LinearGradient(0f, 0f, 0f, 100f,
                intArrayOf(Color.parseColor("#f6ee19"), Color.parseColor("#115ede")),
                floatArrayOf(0.1f, 1f),
                Shader.TileMode.MIRROR)
        }

        private fun createGreenShader() : LinearGradient{
            return LinearGradient(100f, 0f, 0f, 100f,
                intArrayOf(Color.parseColor("#67e536"), Color.parseColor("#104517")),
                floatArrayOf(0.1f, 1f),
                Shader.TileMode.MIRROR)
        }

        constructor(context: Context?) : super(context) {
            init()
        }

        constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
            init()
        }

        constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
            init()
        }

        private fun init() {
            var colorInfo = intent.extras?.getString("color")
            holder.addCallback(this)
            thread = MySurfaceThread(holder, this)
            isFocusable = true // make sure we get key events
            paintText.style = Paint.Style.FILL
            paintText.textSize = 40f
            paintText.color = Color.WHITE
            paintTextGO.style = Paint.Style.FILL
            paintTextGO.textSize = 120f
            paintTextGO.color = Color.parseColor("#f0f0f0")
            when(colorInfo){
                null -> paintCircle.color = Color.WHITE
                "White" -> {
                    paintCircle.style = Paint.Style.FILL
                    paintCircle.color = Color.parseColor("#FFFFFF")
                }
                "Green" -> {
                    paintCircle.style = Paint.Style.FILL
                    paintCircle.shader = createGreenShader()
                }
                "Yellow" -> {
                    paintCircle.style = Paint.Style.FILL
                    paintCircle.color = Color.parseColor("#d8db2a")
                }
                "Black" -> {
                    paintCircle.style = Paint.Style.FILL
                    paintCircle.color = Color.parseColor("#000000")
                }
                "Gradient" -> {
                    paintCircle.style = Paint.Style.FILL
                    paintCircle.shader = createGradientShader()
                }
                "Ukraine" -> {
                    paintCircle.style = Paint.Style.FILL
                    paintCircle.shader = createUkraineShader()
                }
            }
            randomPoints()
        }

        override fun surfaceChanged(arg0: SurfaceHolder, arg1: Int, arg2: Int, arg3: Int ) {
        }

        override fun surfaceCreated(holder: SurfaceHolder) {
            thread!!.setRunning(true)
            thread!!.start()
        }

        override fun surfaceDestroyed(holder: SurfaceHolder) {
            var retry = true
            thread!!.setRunning(false)
            while (retry) {
                try {
                    thread!!.join()
                    retry = false
                } catch (e: InterruptedException) {
                    intent = Intent(this@GameActivity, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}