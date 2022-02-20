package work.risingcamp.week6_7_game

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import work.risingcamp.week6_7_game.databinding.ActivityGameBinding
import java.util.*

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding

    var timer = Timer()
    var timerTask: TimerTask? = null
    var count = 46
    //고기마다 thread생성 : 고기의 시간 재줘야함
    var plusTr1: Thread? = null
    var plusTr2: Thread? = null
    var plusTr3: Thread? = null
    var plusTr4: Thread? = null
    var plusTr5: Thread? = null
    var plusTr6: Thread? = null
    var plusTr7: Thread? = null
    var plusTr8: Thread? = null

    var flag1: Boolean? = null
    var flag2: Boolean? = null
    var flag3: Boolean? = null
    var flag4: Boolean? = null
    var flag5: Boolean? = null
    var flag6: Boolean? = null
    var flag7: Boolean? = null
    var flag8: Boolean? = null

    //  고기 초 세주는 것
    var count1: Int = 0
    var count2: Int = 0
    var count3: Int = 0
    var count4: Int = 0
    var count5: Int = 0
    var count6: Int = 0
    var count7: Int = 0
    var count8: Int = 0

    var xValue: Float = 0.0f
    var yValue: Float = 0.0f

    var handler: Handler? = null
    var sc = 0
    var tag2 = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handler = Handler(Looper.getMainLooper())

        val meat1 : ImageView = binding.meat1
        meat1.tag = 1
        meat1.setOnTouchListener { v, event ->
//            부모 view의 width, height
            val width = (v.parent as ViewGroup).width - v.width
            val height = (v.parent as ViewGroup).height - v.width
//            Log.d("COUNT", count1.toString())
            when (event.action) {

//                처음 눌렀을때
                MotionEvent.ACTION_DOWN -> {
//                    터치한 지점의 상대좌표값
                    xValue = event.x
                    yValue = event.y
                }
//                누르고 움직였을때 (누른 상태에서 움직이면)
                MotionEvent.ACTION_MOVE -> {
                    v.x = event.rawX - xValue
//                    rawX = 절대좌표 ( 디바이스 화면의 최상단 )
//                    상대좌표 xValue (자식 view의 최상단 )
                    v.y = event.rawY - (yValue + v.height)

                }
//                누른 것을 때면 : view좌표가 음수일 때는(화면을 넘어가는 경우) 각 x, y좌표에 대해 0초기화
                MotionEvent.ACTION_UP -> {
//                    Log.d("Action", "Actioinn돌아감")
                    if (v.x > width && v.y > height) {
                        v.x = width.toFloat()
                        v.y = height.toFloat()
                    } else if (v.x < 0 && v.y > height) {
                        v.x = width.toFloat()
                        v.y = height.toFloat()
                    } else if (v.x > width && v.y < 0) {
                        v.x = width.toFloat()
                        v.y = 0f
                    } else if (v.x < 0 && v.y < 0) {
                        v.x = 0f
                        v.y = 0f
                    } else if (v.x < 0 || v.x > width) {
                        if (v.x < 0) {
                            v.x = 0f
                            v.y = event.rawY - yValue - v.height
                        } else {
                            v.x = width.toFloat()
                            v.y = event.rawY - yValue - v.height
                        }
                    } else if (v.y < 0 || v.y > height) {
                        if (v.y < 0) {
                            v.x = event.rawX - xValue
                            v.y = 0f
                        } else {
                            v.x = event.rawX - xValue
                            v.y = height.toFloat()
                        }
                    }
                    if (plusTr1 != null) {
                        handler?.post {
                            var resourceId: Int = 0
                            var tag: Int = meat1.tag as Int
                            kotlin.run {
//                                Log.d("COUNT1111", count1.toString())
                                if (count1 < 5) { //안익은 고기

                                    resourceId = resources.getIdentifier(
                                        "meat" + tag,
                                        "drawable",
                                        "work.risingcamp.week6_7_game"
                                    )
                                    binding.meat1.setImageResource(R.drawable.meat1)
                                    tag2 = resourceId
                                } else if (count1 >= 5 && count1 < 10) {
                                    resourceId = resources.getIdentifier(
                                        "goodmeat" + tag,
                                        "drawable",
                                        "work.risingcamp.week6_7_game"
                                    )
                                    binding.meat1.setImageResource(R.drawable.goodmeat1)
                                    tag2 = resourceId
                                } else if (count1 >= 10 && count1 < 15) {
                                    resourceId = resources.getIdentifier(
                                        "littleblackmeat" + tag,
                                        "drawable",
                                        "work.risingcamp.week6_7_game"
                                    )
                                    binding.meat1.setImageResource(R.drawable.littleblackmeat1)
                                    tag2 = resourceId
                                } else {
                                    resourceId = resources.getIdentifier(
                                        "blackmeat" + tag,
                                        "drawable",
                                        "work.risingcamp.week6_7_game"
                                    )
//                                    meat1!!.setImageResource(resourceId)
                                    binding.meat1.setImageResource(R.drawable.blackmeat1)
                                    tag2 = resourceId
                                }

                            }
                        }
                    }
                    flag1 = true
                    FireMeat1(v.x, v.y, v.tag as Int, v as ImageView)
                }
                else -> {}
            }

            Log.d("Clicked", "true")
            true
        }
        val meat2 : ImageView = binding.meat2
        meat2.tag = 2
        meat2.setOnTouchListener { v, event ->
//            부모 view의 width, height
            val width = (v.parent as ViewGroup).width - v.width
            val height = (v.parent as ViewGroup).height - v.width
//            Log.d("COUNT", count1.toString())
            when (event.action) {

//                처음 눌렀을때
                MotionEvent.ACTION_DOWN -> {
//                    터치한 지점의 상대좌표값
                    xValue = event.x
                    yValue = event.y
                }
//                누르고 움직였을때 (누른 상태에서 움직이면)
                MotionEvent.ACTION_MOVE -> {
                    v.x = event.rawX - xValue
//                    rawX = 절대좌표 ( 디바이스 화면의 최상단 )
//                    상대좌표 xValue (자식 view의 최상단 )
                    v.y = event.rawY - (yValue + v.height)

                }
//                누른 것을 때면 : view좌표가 음수일 때는(화면을 넘어가는 경우) 각 x, y좌표에 대해 0초기화
                MotionEvent.ACTION_UP -> {
//                    Log.d("Action", "Actioinn돌아감")
                    if (v.x > width && v.y > height) {
                        v.x = width.toFloat()
                        v.y = height.toFloat()
                    } else if (v.x < 0 && v.y > height) {
                        v.x = width.toFloat()
                        v.y = height.toFloat()
                    } else if (v.x > width && v.y < 0) {
                        v.x = width.toFloat()
                        v.y = 0f
                    } else if (v.x < 0 && v.y < 0) {
                        v.x = 0f
                        v.y = 0f
                    } else if (v.x < 0 || v.x > width) {
                        if (v.x < 0) {
                            v.x = 0f
                            v.y = event.rawY - yValue - v.height
                        } else {
                            v.x = width.toFloat()
                            v.y = event.rawY - yValue - v.height
                        }
                    } else if (v.y < 0 || v.y > height) {
                        if (v.y < 0) {
                            v.x = event.rawX - xValue
                            v.y = 0f
                        } else {
                            v.x = event.rawX - xValue
                            v.y = height.toFloat()
                        }
                    }
                    if (plusTr2 != null) {
                        handler?.post {
                            var resourceId: Int = 0
                            var tag: Int = meat2.tag as Int
                            kotlin.run {
//                                Log.d("COUNT1111", count1.toString())
                                if (count2 < 5) { //안익은 고기

                                    resourceId = resources.getIdentifier(
                                        "meat" + tag,
                                        "drawable",
                                        "work.risingcamp.week6_7_game"
                                    )
                                    binding.meat2.setImageResource(R.drawable.meat2)
                                    tag2 = resourceId
                                } else if (count2 >= 5 && count2 < 10) {
                                    resourceId = resources.getIdentifier(
                                        "goodmeat" + tag,
                                        "drawable",
                                        "work.risingcamp.week6_7_game"
                                    )
                                    binding.meat2.setImageResource(R.drawable.goodmeat2)
                                    tag2 = resourceId
                                } else if (count2 >= 10 && count2 < 15) {
                                    resourceId = resources.getIdentifier(
                                        "littleblackmeat" + tag,
                                        "drawable",
                                        "work.risingcamp.week6_7_game"
                                    )
                                    binding.meat2.setImageResource(R.drawable.littleblackmeat2)
                                    tag2 = resourceId
                                } else {
                                    resourceId = resources.getIdentifier(
                                        "blackmeat" + tag,
                                        "drawable",
                                        "work.risingcamp.week6_7_game"
                                    )
//                                    meat1!!.setImageResource(resourceId)
                                    binding.meat2.setImageResource(R.drawable.blackmeat2)
                                    tag2 = resourceId
                                }

                            }
                        }
                    }
                    flag2 = true
                    FireMeat2(v.x, v.y, v.tag as Int, v as ImageView)
                }
                else -> {}
            }

            Log.d("Clicked", "true")
            true
        }
        val meat3 : ImageView = binding.meat3
        meat3.tag = 3
        meat3.setOnTouchListener { v, event ->
//            부모 view의 width, height
            val width = (v.parent as ViewGroup).width - v.width
            val height = (v.parent as ViewGroup).height - v.width
//            Log.d("COUNT", count1.toString())
            when (event.action) {

//                처음 눌렀을때
                MotionEvent.ACTION_DOWN -> {
//                    터치한 지점의 상대좌표값
                    xValue = event.x
                    yValue = event.y
                }
//                누르고 움직였을때 (누른 상태에서 움직이면)
                MotionEvent.ACTION_MOVE -> {
                    v.x = event.rawX - xValue
//                    rawX = 절대좌표 ( 디바이스 화면의 최상단 )
//                    상대좌표 xValue (자식 view의 최상단 )
                    v.y = event.rawY - (yValue + v.height)

                }
//                누른 것을 때면 : view좌표가 음수일 때는(화면을 넘어가는 경우) 각 x, y좌표에 대해 0초기화
                MotionEvent.ACTION_UP -> {
//                    Log.d("Action", "Actioinn돌아감")
                    if (v.x > width && v.y > height) {
                        v.x = width.toFloat()
                        v.y = height.toFloat()
                    } else if (v.x < 0 && v.y > height) {
                        v.x = width.toFloat()
                        v.y = height.toFloat()
                    } else if (v.x > width && v.y < 0) {
                        v.x = width.toFloat()
                        v.y = 0f
                    } else if (v.x < 0 && v.y < 0) {
                        v.x = 0f
                        v.y = 0f
                    } else if (v.x < 0 || v.x > width) {
                        if (v.x < 0) {
                            v.x = 0f
                            v.y = event.rawY - yValue - v.height
                        } else {
                            v.x = width.toFloat()
                            v.y = event.rawY - yValue - v.height
                        }
                    } else if (v.y < 0 || v.y > height) {
                        if (v.y < 0) {
                            v.x = event.rawX - xValue
                            v.y = 0f
                        } else {
                            v.x = event.rawX - xValue
                            v.y = height.toFloat()
                        }
                    }
                    if (plusTr3 != null) {
                        handler?.post {
                            var resourceId: Int = 0
                            var tag: Int = meat3.tag as Int
                            kotlin.run {
//                                Log.d("COUNT1111", count1.toString())
                                if (count3 < 5) { //안익은 고기

                                    resourceId = resources.getIdentifier(
                                        "meat" + tag,
                                        "drawable",
                                        "work.risingcamp.week6_7_game"
                                    )
                                    binding.meat3.setImageResource(R.drawable.meat3)
                                    tag2 = resourceId
                                } else if (count3 >= 5 && count3 < 10) {
                                    resourceId = resources.getIdentifier(
                                        "goodmeat" + tag,
                                        "drawable",
                                        "work.risingcamp.week6_7_game"
                                    )
                                    binding.meat3.setImageResource(R.drawable.goodmeat3)
                                    tag2 = resourceId
                                } else if (count3>= 10 && count3 < 15) {
                                    resourceId = resources.getIdentifier(
                                        "littleblackmeat" + tag,
                                        "drawable",
                                        "work.risingcamp.week6_7_game"
                                    )
                                    binding.meat3.setImageResource(R.drawable.littleblackmeat3)
                                    tag2 = resourceId
                                } else {
                                    resourceId = resources.getIdentifier(
                                        "blackmeat" + tag,
                                        "drawable",
                                        "work.risingcamp.week6_7_game"
                                    )
//                                    meat1!!.setImageResource(resourceId)
                                    binding.meat3.setImageResource(R.drawable.blackmeat3)
                                    tag2 = resourceId
                                }

                            }
                        }
                    }
                    flag3 = true
                    FireMeat3(v.x, v.y, v.tag as Int, v as ImageView)
                }
                else -> {}
            }

            Log.d("Clicked", "true")
            true
        }
        val meat4 : ImageView = binding.meat4
        meat4.tag = 1
        meat4.setOnTouchListener { v, event ->
//            부모 view의 width, height
            val width = (v.parent as ViewGroup).width - v.width
            val height = (v.parent as ViewGroup).height - v.width
//            Log.d("COUNT", count1.toString())
            when (event.action) {

//                처음 눌렀을때
                MotionEvent.ACTION_DOWN -> {
//                    터치한 지점의 상대좌표값
                    xValue = event.x
                    yValue = event.y
                }
//                누르고 움직였을때 (누른 상태에서 움직이면)
                MotionEvent.ACTION_MOVE -> {
                    v.x = event.rawX - xValue
//                    rawX = 절대좌표 ( 디바이스 화면의 최상단 )
//                    상대좌표 xValue (자식 view의 최상단 )
                    v.y = event.rawY - (yValue + v.height)

                }
//                누른 것을 때면 : view좌표가 음수일 때는(화면을 넘어가는 경우) 각 x, y좌표에 대해 0초기화
                MotionEvent.ACTION_UP -> {
//                    Log.d("Action", "Actioinn돌아감")
                    if (v.x > width && v.y > height) {
                        v.x = width.toFloat()
                        v.y = height.toFloat()
                    } else if (v.x < 0 && v.y > height) {
                        v.x = width.toFloat()
                        v.y = height.toFloat()
                    } else if (v.x > width && v.y < 0) {
                        v.x = width.toFloat()
                        v.y = 0f
                    } else if (v.x < 0 && v.y < 0) {
                        v.x = 0f
                        v.y = 0f
                    } else if (v.x < 0 || v.x > width) {
                        if (v.x < 0) {
                            v.x = 0f
                            v.y = event.rawY - yValue - v.height
                        } else {
                            v.x = width.toFloat()
                            v.y = event.rawY - yValue - v.height
                        }
                    } else if (v.y < 0 || v.y > height) {
                        if (v.y < 0) {
                            v.x = event.rawX - xValue
                            v.y = 0f
                        } else {
                            v.x = event.rawX - xValue
                            v.y = height.toFloat()
                        }
                    }
                    if (plusTr4 != null) {
                        handler?.post {
                            var resourceId: Int = 0
                            var tag: Int = meat4.tag as Int
                            kotlin.run {
//                                Log.d("COUNT1111", count1.toString())
                                if (count4 < 5) { //안익은 고기

                                    resourceId = resources.getIdentifier(
                                        "meat" + tag,
                                        "drawable",
                                        "work.risingcamp.week6_7_game"
                                    )
                                    binding.meat4.setImageResource(R.drawable.meat1)
                                    tag2 = resourceId
                                } else if (count4 >= 5 && count4 < 10) {
                                    resourceId = resources.getIdentifier(
                                        "goodmeat" + tag,
                                        "drawable",
                                        "work.risingcamp.week6_7_game"
                                    )
                                    binding.meat4.setImageResource(R.drawable.goodmeat1)
                                    tag2 = resourceId
                                } else if (count4 >= 10 && count4 < 15) {
                                    resourceId = resources.getIdentifier(
                                        "littleblackmeat" + tag,
                                        "drawable",
                                        "work.risingcamp.week6_7_game"
                                    )
                                    binding.meat4.setImageResource(R.drawable.littleblackmeat1)
                                    tag2 = resourceId
                                } else {
                                    resourceId = resources.getIdentifier(
                                        "blackmeat" + tag,
                                        "drawable",
                                        "work.risingcamp.week6_7_game"
                                    )
//                                    meat1!!.setImageResource(resourceId)
                                    binding.meat4.setImageResource(R.drawable.blackmeat1)
                                    tag2 = resourceId
                                }

                            }
                        }
                    }
                    flag4 = true
                    FireMeat4(v.x, v.y, v.tag as Int, v as ImageView)
                }
                else -> {}
            }

            Log.d("Clicked", "true")
            true
        }
        val meat5 : ImageView = binding.meat5
        meat5.tag = 2
        meat5.setOnTouchListener { v, event ->
//            부모 view의 width, height
            val width = (v.parent as ViewGroup).width - v.width
            val height = (v.parent as ViewGroup).height - v.width
//            Log.d("COUNT", count1.toString())
            when (event.action) {

//                처음 눌렀을때
                MotionEvent.ACTION_DOWN -> {
//                    터치한 지점의 상대좌표값
                    xValue = event.x
                    yValue = event.y
                }
//                누르고 움직였을때 (누른 상태에서 움직이면)
                MotionEvent.ACTION_MOVE -> {
                    v.x = event.rawX - xValue
//                    rawX = 절대좌표 ( 디바이스 화면의 최상단 )
//                    상대좌표 xValue (자식 view의 최상단 )
                    v.y = event.rawY - (yValue + v.height)

                }
//                누른 것을 때면 : view좌표가 음수일 때는(화면을 넘어가는 경우) 각 x, y좌표에 대해 0초기화
                MotionEvent.ACTION_UP -> {
//                    Log.d("Action", "Actioinn돌아감")
                    if (v.x > width && v.y > height) {
                        v.x = width.toFloat()
                        v.y = height.toFloat()
                    } else if (v.x < 0 && v.y > height) {
                        v.x = width.toFloat()
                        v.y = height.toFloat()
                    } else if (v.x > width && v.y < 0) {
                        v.x = width.toFloat()
                        v.y = 0f
                    } else if (v.x < 0 && v.y < 0) {
                        v.x = 0f
                        v.y = 0f
                    } else if (v.x < 0 || v.x > width) {
                        if (v.x < 0) {
                            v.x = 0f
                            v.y = event.rawY - yValue - v.height
                        } else {
                            v.x = width.toFloat()
                            v.y = event.rawY - yValue - v.height
                        }
                    } else if (v.y < 0 || v.y > height) {
                        if (v.y < 0) {
                            v.x = event.rawX - xValue
                            v.y = 0f
                        } else {
                            v.x = event.rawX - xValue
                            v.y = height.toFloat()
                        }
                    }
                    if (plusTr5 != null) {
                        handler?.post {
                            var resourceId: Int = 0
                            var tag: Int = meat5.tag as Int
                            kotlin.run {
//                                Log.d("COUNT1111", count1.toString())
                                if (count5 < 5) { //안익은 고기

                                    resourceId = resources.getIdentifier(
                                        "meat" + tag,
                                        "drawable",
                                        "work.risingcamp.week6_7_game"
                                    )
                                    binding.meat5.setImageResource(R.drawable.meat2)
                                    tag2 = resourceId
                                } else if (count5 >= 5 && count5 < 10) {
                                    resourceId = resources.getIdentifier(
                                        "goodmeat" + tag,
                                        "drawable",
                                        "work.risingcamp.week6_7_game"
                                    )
                                    binding.meat5.setImageResource(R.drawable.goodmeat2)
                                    tag2 = resourceId
                                } else if (count5 >= 10 && count5 < 15) {
                                    resourceId = resources.getIdentifier(
                                        "littleblackmeat" + tag,
                                        "drawable",
                                        "work.risingcamp.week6_7_game"
                                    )
                                    binding.meat5.setImageResource(R.drawable.littleblackmeat2)
                                    tag2 = resourceId
                                } else {
                                    resourceId = resources.getIdentifier(
                                        "blackmeat" + tag,
                                        "drawable",
                                        "work.risingcamp.week6_7_game"
                                    )
//                                    meat1!!.setImageResource(resourceId)
                                    binding.meat5.setImageResource(R.drawable.blackmeat2)
                                    tag2 = resourceId
                                }

                            }
                        }
                    }
                    flag5 = true
                    FireMeat5(v.x, v.y, v.tag as Int, v as ImageView)
                }
                else -> {}
            }

            Log.d("Clicked", "true")
            true
        }
        val meat6 : ImageView = binding.meat6
        meat6.tag = 3
        meat6.setOnTouchListener { v, event ->
//            부모 view의 width, height
            val width = (v.parent as ViewGroup).width - v.width
            val height = (v.parent as ViewGroup).height - v.width
//            Log.d("COUNT", count1.toString())
            when (event.action) {

//                처음 눌렀을때
                MotionEvent.ACTION_DOWN -> {
//                    터치한 지점의 상대좌표값
                    xValue = event.x
                    yValue = event.y
                }
//                누르고 움직였을때 (누른 상태에서 움직이면)
                MotionEvent.ACTION_MOVE -> {
                    v.x = event.rawX - xValue
//                    rawX = 절대좌표 ( 디바이스 화면의 최상단 )
//                    상대좌표 xValue (자식 view의 최상단 )
                    v.y = event.rawY - (yValue + v.height)

                }
//                누른 것을 때면 : view좌표가 음수일 때는(화면을 넘어가는 경우) 각 x, y좌표에 대해 0초기화
                MotionEvent.ACTION_UP -> {
//                    Log.d("Action", "Actioinn돌아감")
                    if (v.x > width && v.y > height) {
                        v.x = width.toFloat()
                        v.y = height.toFloat()
                    } else if (v.x < 0 && v.y > height) {
                        v.x = width.toFloat()
                        v.y = height.toFloat()
                    } else if (v.x > width && v.y < 0) {
                        v.x = width.toFloat()
                        v.y = 0f
                    } else if (v.x < 0 && v.y < 0) {
                        v.x = 0f
                        v.y = 0f
                    } else if (v.x < 0 || v.x > width) {
                        if (v.x < 0) {
                            v.x = 0f
                            v.y = event.rawY - yValue - v.height
                        } else {
                            v.x = width.toFloat()
                            v.y = event.rawY - yValue - v.height
                        }
                    } else if (v.y < 0 || v.y > height) {
                        if (v.y < 0) {
                            v.x = event.rawX - xValue
                            v.y = 0f
                        } else {
                            v.x = event.rawX - xValue
                            v.y = height.toFloat()
                        }
                    }
                    if (plusTr6 != null) {
                        handler?.post {
                            var resourceId: Int = 0
                            var tag: Int = meat6.tag as Int
                            kotlin.run {
//                                Log.d("COUNT1111", count1.toString())
                                if (count6 < 5) { //안익은 고기

                                    resourceId = resources.getIdentifier(
                                        "meat" + tag,
                                        "drawable",
                                        "work.risingcamp.week6_7_game"
                                    )
                                    binding.meat6.setImageResource(R.drawable.meat3)
                                    tag2 = resourceId
                                } else if (count6 >= 5 && count6 < 10) {
                                    resourceId = resources.getIdentifier(
                                        "goodmeat" + tag,
                                        "drawable",
                                        "work.risingcamp.week6_7_game"
                                    )
                                    binding.meat6.setImageResource(R.drawable.goodmeat3)
                                    tag2 = resourceId
                                } else if (count2 >= 10 && count2 < 15) {
                                    resourceId = resources.getIdentifier(
                                        "littleblackmeat" + tag,
                                        "drawable",
                                        "work.risingcamp.week6_7_game"
                                    )
                                    binding.meat6.setImageResource(R.drawable.littleblackmeat3)
                                    tag2 = resourceId
                                } else {
                                    resourceId = resources.getIdentifier(
                                        "blackmeat" + tag,
                                        "drawable",
                                        "work.risingcamp.week6_7_game"
                                    )
//                                    meat1!!.setImageResource(resourceId)
                                    binding.meat6.setImageResource(R.drawable.blackmeat3)
                                    tag2 = resourceId
                                }

                            }
                        }
                    }
                    flag6 = true
                    FireMeat6(v.x, v.y, v.tag as Int, v as ImageView)
                }
                else -> {}
            }

            Log.d("Clicked", "true")
            true
        }
        val meat7 : ImageView = binding.meat7
        meat7.tag = 1
        meat7.setOnTouchListener { v, event ->
//            부모 view의 width, height
            val width = (v.parent as ViewGroup).width - v.width
            val height = (v.parent as ViewGroup).height - v.width
//            Log.d("COUNT", count1.toString())
            when (event.action) {

//                처음 눌렀을때
                MotionEvent.ACTION_DOWN -> {
//                    터치한 지점의 상대좌표값
                    xValue = event.x
                    yValue = event.y
                }
//                누르고 움직였을때 (누른 상태에서 움직이면)
                MotionEvent.ACTION_MOVE -> {
                    v.x = event.rawX - xValue
//                    rawX = 절대좌표 ( 디바이스 화면의 최상단 )
//                    상대좌표 xValue (자식 view의 최상단 )
                    v.y = event.rawY - (yValue + v.height)

                }
//                누른 것을 때면 : view좌표가 음수일 때는(화면을 넘어가는 경우) 각 x, y좌표에 대해 0초기화
                MotionEvent.ACTION_UP -> {
//                    Log.d("Action", "Actioinn돌아감")
                    if (v.x > width && v.y > height) {
                        v.x = width.toFloat()
                        v.y = height.toFloat()
                    } else if (v.x < 0 && v.y > height) {
                        v.x = width.toFloat()
                        v.y = height.toFloat()
                    } else if (v.x > width && v.y < 0) {
                        v.x = width.toFloat()
                        v.y = 0f
                    } else if (v.x < 0 && v.y < 0) {
                        v.x = 0f
                        v.y = 0f
                    } else if (v.x < 0 || v.x > width) {
                        if (v.x < 0) {
                            v.x = 0f
                            v.y = event.rawY - yValue - v.height
                        } else {
                            v.x = width.toFloat()
                            v.y = event.rawY - yValue - v.height
                        }
                    } else if (v.y < 0 || v.y > height) {
                        if (v.y < 0) {
                            v.x = event.rawX - xValue
                            v.y = 0f
                        } else {
                            v.x = event.rawX - xValue
                            v.y = height.toFloat()
                        }
                    }
                    if (plusTr7 != null) {
                        handler?.post {
                            var resourceId: Int = 0
                            var tag: Int = meat7.tag as Int
                            kotlin.run {
//                                Log.d("COUNT1111", count1.toString())
                                if (count7 < 5) { //안익은 고기

                                    resourceId = resources.getIdentifier(
                                        "meat" + tag,
                                        "drawable",
                                        "work.risingcamp.week6_7_game"
                                    )
                                    binding.meat7.setImageResource(R.drawable.meat1)
                                    tag2 = resourceId
                                } else if (count7 >= 5 && count7 < 10) {
                                    resourceId = resources.getIdentifier(
                                        "goodmeat" + tag,
                                        "drawable",
                                        "work.risingcamp.week6_7_game"
                                    )
                                    binding.meat7.setImageResource(R.drawable.goodmeat1)
                                    tag2 = resourceId
                                } else if (count7 >= 10 && count7 < 15) {
                                    resourceId = resources.getIdentifier(
                                        "littleblackmeat" + tag,
                                        "drawable",
                                        "work.risingcamp.week6_7_game"
                                    )
                                    binding.meat7.setImageResource(R.drawable.littleblackmeat1)
                                    tag2 = resourceId
                                } else {
                                    resourceId = resources.getIdentifier(
                                        "blackmeat" + tag,
                                        "drawable",
                                        "work.risingcamp.week6_7_game"
                                    )
//                                    meat1!!.setImageResource(resourceId)
                                    binding.meat7.setImageResource(R.drawable.blackmeat1)
                                    tag2 = resourceId
                                }

                            }
                        }
                    }
                    flag7 = true
                    FireMeat7(v.x, v.y, v.tag as Int, v as ImageView)
                }
                else -> {}
            }

            Log.d("Clicked", "true")
            true
        }
        val meat8 : ImageView = binding.meat8
        meat8.tag = 2
        meat8.setOnTouchListener { v, event ->
//            부모 view의 width, height
            val width = (v.parent as ViewGroup).width - v.width
            val height = (v.parent as ViewGroup).height - v.width
//            Log.d("COUNT", count1.toString())
            when (event.action) {

//                처음 눌렀을때
                MotionEvent.ACTION_DOWN -> {
//                    터치한 지점의 상대좌표값
                    xValue = event.x
                    yValue = event.y
                }
//                누르고 움직였을때 (누른 상태에서 움직이면)
                MotionEvent.ACTION_MOVE -> {
                    v.x = event.rawX - xValue
//                    rawX = 절대좌표 ( 디바이스 화면의 최상단 )
//                    상대좌표 xValue (자식 view의 최상단 )
                    v.y = event.rawY - (yValue + v.height)

                }
//                누른 것을 때면 : view좌표가 음수일 때는(화면을 넘어가는 경우) 각 x, y좌표에 대해 0초기화
                MotionEvent.ACTION_UP -> {
//                    Log.d("Action", "Actioinn돌아감")
                    if (v.x > width && v.y > height) {
                        v.x = width.toFloat()
                        v.y = height.toFloat()
                    } else if (v.x < 0 && v.y > height) {
                        v.x = width.toFloat()
                        v.y = height.toFloat()
                    } else if (v.x > width && v.y < 0) {
                        v.x = width.toFloat()
                        v.y = 0f
                    } else if (v.x < 0 && v.y < 0) {
                        v.x = 0f
                        v.y = 0f
                    } else if (v.x < 0 || v.x > width) {
                        if (v.x < 0) {
                            v.x = 0f
                            v.y = event.rawY - yValue - v.height
                        } else {
                            v.x = width.toFloat()
                            v.y = event.rawY - yValue - v.height
                        }
                    } else if (v.y < 0 || v.y > height) {
                        if (v.y < 0) {
                            v.x = event.rawX - xValue
                            v.y = 0f
                        } else {
                            v.x = event.rawX - xValue
                            v.y = height.toFloat()
                        }
                    }
                    if (plusTr8 != null) {
                        handler?.post {
                            var resourceId: Int = 0
                            var tag: Int = meat8.tag as Int
                            kotlin.run {
//                                Log.d("COUNT1111", count1.toString())
                                if (count8 < 5) { //안익은 고기

                                    resourceId = resources.getIdentifier(
                                        "meat" + tag,
                                        "drawable",
                                        "work.risingcamp.week6_7_game"
                                    )
                                    binding.meat8.setImageResource(R.drawable.meat2)
                                    tag2 = resourceId
                                } else if (count8 >= 5 && count8 < 10) {
                                    resourceId = resources.getIdentifier(
                                        "goodmeat" + tag,
                                        "drawable",
                                        "work.risingcamp.week6_7_game"
                                    )
                                    binding.meat8.setImageResource(R.drawable.goodmeat2)
                                    tag2 = resourceId
                                } else if (count8 >= 10 && count8 < 15) {
                                    resourceId = resources.getIdentifier(
                                        "littleblackmeat" + tag,
                                        "drawable",
                                        "work.risingcamp.week6_7_game"
                                    )
                                    binding.meat8.setImageResource(R.drawable.littleblackmeat2)
                                    tag2 = resourceId
                                } else {
                                    resourceId = resources.getIdentifier(
                                        "blackmeat" + tag,
                                        "drawable",
                                        "work.risingcamp.week6_7_game"
                                    )
//                                    meat1!!.setImageResource(resourceId)
                                    binding.meat8.setImageResource(R.drawable.blackmeat2)
                                    tag2 = resourceId
                                }

                            }
                        }
                    }
                    flag8 = true
                    FireMeat8(v.x, v.y, v.tag as Int, v as ImageView)
                }
                else -> {}
            }

            Log.d("Clicked", "true")
            true
        }
        timerTask()
    }

    private fun FireMeat1(x: Float, y: Float, tag: Int, v: ImageView) {
        val resourceId =
            resources.getIdentifier("goodmeat$tag", "drawable", "work.risingcamp.week6_7_game")
//        Log.d("flag", flag1.toString())
        //고기가 어디가있는지에 따라서, frame에 가있으면 초 세는 thread
        if ((binding.fireFrame.x < x && x < binding.fireFrame.x + binding.fireFrame.width) && binding.fireFrame.y < y && y < binding.fireFrame.y + binding.fireFrame.height) {
//            Log.d("frame에 들어왔음", "true")
            plusTr1 = Thread {
                while (flag1!!) {
                    try {
                        var past = System.currentTimeMillis()/1000
                        Thread.sleep(1000)
                        var now = System.currentTimeMillis()/1000
                        count1 += (now - past).toInt()
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
            plusTr1!!.start()

//            고기가 양념장에 가있으면 점수 측정 + 고기 사라지게 하기
        } else if (binding.endFrame.x < x && x < binding.endFrame.x + binding.endFrame.width && binding.endFrame.y < y && y < binding.endFrame.y + binding.endFrame.height) {
//            Log.d("양념장","true")
            v.visibility = View.GONE
            if (tag2 == resourceId) {
                sc += 500
                binding.score.text = "SCORE:$sc"
                if (sc >= 2000) {
                    val intent = Intent(this, LastActivity::class.java)
                    intent.putExtra("result", "성공적인 고기장수")
                    startActivity(intent)
                    finish()
                }
            } else {
            }
        }
    }
    private fun FireMeat2(x: Float, y: Float, tag: Int, v: ImageView) {
        val resourceId =
            resources.getIdentifier("goodmeat$tag", "drawable", "work.risingcamp.week6_7_game")
//        Log.d("flag", flag1.toString())
        //고기가 어디가있는지에 따라서, frame에 가있으면 초 세는 thread
        if ((binding.fireFrame.x < x && x < binding.fireFrame.x + binding.fireFrame.width) && binding.fireFrame.y < y && y < binding.fireFrame.y + binding.fireFrame.height) {
//            Log.d("frame에 들어왔음", "true")
            plusTr2 = Thread {
                while (flag2!!) {
                    try {
                        var past = System.currentTimeMillis()/1000
                        Thread.sleep(1000)
                        var now = System.currentTimeMillis()/1000
                        count2 += (now - past).toInt()
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
            plusTr2!!.start()

//            고기가 양념장에 가있으면 점수 측정 + 고기 사라지게 하기
        } else if (binding.endFrame.x < x && x < binding.endFrame.x + binding.endFrame.width && binding.endFrame.y < y && y < binding.endFrame.y + binding.endFrame.height) {
//            Log.d("양념장","true")
            v.visibility = View.GONE
            if (tag2 == resourceId) {
                sc += 500
                binding.score.text = "SCORE:$sc"
                if (sc >= 2000) {
                    val intent = Intent(this, LastActivity::class.java)
                    intent.putExtra("result", "성공적인 고기장수")
                    startActivity(intent)
                    finish()
                }
            } else {
            }
        }
    }
    private fun FireMeat3(x: Float, y: Float, tag: Int, v: ImageView) {
        val resourceId =
            resources.getIdentifier("goodmeat$tag", "drawable", "work.risingcamp.week6_7_game")
//        Log.d("flag", flag1.toString())
        //고기가 어디가있는지에 따라서, frame에 가있으면 초 세는 thread
        if ((binding.fireFrame.x < x && x < binding.fireFrame.x + binding.fireFrame.width) && binding.fireFrame.y < y && y < binding.fireFrame.y + binding.fireFrame.height) {
//            Log.d("frame에 들어왔음", "true")
            plusTr3 = Thread {
                while (flag3!!) {
                    try {
                        var past = System.currentTimeMillis()/1000
                        Thread.sleep(1000)
                        var now = System.currentTimeMillis()/1000
                        count3 += (now - past).toInt()
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
            plusTr3!!.start()

//            고기가 양념장에 가있으면 점수 측정 + 고기 사라지게 하기
        } else if (binding.endFrame.x < x && x < binding.endFrame.x + binding.endFrame.width && binding.endFrame.y < y && y < binding.endFrame.y + binding.endFrame.height) {
//            Log.d("양념장","true")
            v.visibility = View.GONE
            if (tag2 == resourceId) {
                sc += 500
                binding.score.text = "SCORE:$sc"
                if (sc >= 2000) {
                    val intent = Intent(this, LastActivity::class.java)
                    intent.putExtra("result", "성공적인 고기장수")
                    startActivity(intent)
                    finish()
                }
            } else {
            }
        }
    }
    private fun FireMeat4(x: Float, y: Float, tag: Int, v: ImageView) {
        val resourceId =
            resources.getIdentifier("goodmeat$tag", "drawable", "work.risingcamp.week6_7_game")
//        Log.d("flag", flag1.toString())
        //고기가 어디가있는지에 따라서, frame에 가있으면 초 세는 thread
        if ((binding.fireFrame.x < x && x < binding.fireFrame.x + binding.fireFrame.width) && binding.fireFrame.y < y && y < binding.fireFrame.y + binding.fireFrame.height) {
//            Log.d("frame에 들어왔음", "true")
            plusTr4 = Thread {
                while (flag4!!) {
                    try {
                        var past = System.currentTimeMillis()/1000
                        Thread.sleep(1000)
                        var now = System.currentTimeMillis()/1000
                        count4 += (now - past).toInt()
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
            plusTr4!!.start()

//            고기가 양념장에 가있으면 점수 측정 + 고기 사라지게 하기
        } else if (binding.endFrame.x < x && x < binding.endFrame.x + binding.endFrame.width && binding.endFrame.y < y && y < binding.endFrame.y + binding.endFrame.height) {
//            Log.d("양념장","true")
            v.visibility = View.GONE
            if (tag2 == resourceId) {
                sc += 500
                binding.score.text = "SCORE:$sc"
                if (sc >= 2000) {
                    val intent = Intent(this, LastActivity::class.java)
                    intent.putExtra("result", "성공적인 고기장수")
                    startActivity(intent)
                    finish()
                }
            } else {
            }
        }
    }
    private fun FireMeat5(x: Float, y: Float, tag: Int, v: ImageView) {
        val resourceId =
            resources.getIdentifier("goodmeat$tag", "drawable", "work.risingcamp.week6_7_game")
//        Log.d("flag", flag1.toString())
        //고기가 어디가있는지에 따라서, frame에 가있으면 초 세는 thread
        if ((binding.fireFrame.x < x && x < binding.fireFrame.x + binding.fireFrame.width) && binding.fireFrame.y < y && y < binding.fireFrame.y + binding.fireFrame.height) {
//            Log.d("frame에 들어왔음", "true")
            plusTr5 = Thread {
                while (flag5!!) {
                    try {
                        var past = System.currentTimeMillis()/1000
                        Thread.sleep(1000)
                        var now = System.currentTimeMillis()/1000
                        count5 += (now - past).toInt()
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
            plusTr5!!.start()

//            고기가 양념장에 가있으면 점수 측정 + 고기 사라지게 하기
        } else if (binding.endFrame.x < x && x < binding.endFrame.x + binding.endFrame.width && binding.endFrame.y < y && y < binding.endFrame.y + binding.endFrame.height) {
//            Log.d("양념장","true")
            v.visibility = View.GONE
            if (tag2 == resourceId) {
                sc += 500
                binding.score.text = "SCORE:$sc"
                if (sc >= 2000) {
                    val intent = Intent(this, LastActivity::class.java)
                    intent.putExtra("result", "성공적인 고기장수")
                    startActivity(intent)
                    finish()
                }
            } else {
            }
        }
    }
    private fun FireMeat6(x: Float, y: Float, tag: Int, v: ImageView) {
        val resourceId =
            resources.getIdentifier("goodmeat$tag", "drawable", "work.risingcamp.week6_7_game")
//        Log.d("flag", flag1.toString())
        //고기가 어디가있는지에 따라서, frame에 가있으면 초 세는 thread
        if ((binding.fireFrame.x < x && x < binding.fireFrame.x + binding.fireFrame.width) && binding.fireFrame.y < y && y < binding.fireFrame.y + binding.fireFrame.height) {
//            Log.d("frame에 들어왔음", "true")
            plusTr6 = Thread {
                while (flag6!!) {
                    try {
                        var past = System.currentTimeMillis()/1000
                        Thread.sleep(1000)
                        var now = System.currentTimeMillis()/1000
                        count6 += (now - past).toInt()
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
            plusTr6!!.start()

//            고기가 양념장에 가있으면 점수 측정 + 고기 사라지게 하기
        } else if (binding.endFrame.x < x && x < binding.endFrame.x + binding.endFrame.width && binding.endFrame.y < y && y < binding.endFrame.y + binding.endFrame.height) {
//            Log.d("양념장","true")
            v.visibility = View.GONE
            if (tag2 == resourceId) {
                sc += 500
                binding.score.text = "SCORE:$sc"
                if (sc >= 2000) {
                    val intent = Intent(this, LastActivity::class.java)
                    intent.putExtra("result", "성공적인 고기장수")
                    startActivity(intent)
                    finish()
                }
            } else {
            }
        }
    }
    private fun FireMeat7(x: Float, y: Float, tag: Int, v: ImageView) {
        val resourceId =
            resources.getIdentifier("goodmeat$tag", "drawable", "work.risingcamp.week6_7_game")
//        Log.d("flag", flag1.toString())
        //고기가 어디가있는지에 따라서, frame에 가있으면 초 세는 thread
        if ((binding.fireFrame.x < x && x < binding.fireFrame.x + binding.fireFrame.width) && binding.fireFrame.y < y && y < binding.fireFrame.y + binding.fireFrame.height) {
//            Log.d("frame에 들어왔음", "true")
            plusTr7 = Thread {
                while (flag7!!) {
                    try {
                        var past = System.currentTimeMillis()/1000
                        Thread.sleep(1000)
                        var now = System.currentTimeMillis()/1000
                        count7 += (now - past).toInt()
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
            plusTr7!!.start()

//            고기가 양념장에 가있으면 점수 측정 + 고기 사라지게 하기
        } else if (binding.endFrame.x < x && x < binding.endFrame.x + binding.endFrame.width && binding.endFrame.y < y && y < binding.endFrame.y + binding.endFrame.height) {
//            Log.d("양념장","true")
            v.visibility = View.GONE
            if (tag2 == resourceId) {
                sc += 500
                binding.score.text = "SCORE:$sc"
                if (sc >= 2000) {
                    val intent = Intent(this, LastActivity::class.java)
                    intent.putExtra("result", "성공적인 고기장수")
                    startActivity(intent)
                    finish()
                }
            } else {
            }
        }
    }
    private fun FireMeat8(x: Float, y: Float, tag: Int, v: ImageView) {
        val resourceId =
            resources.getIdentifier("goodmeat$tag", "drawable", "work.risingcamp.week6_7_game")
//        Log.d("flag", flag1.toString())
        //고기가 어디가있는지에 따라서, frame에 가있으면 초 세는 thread
        if ((binding.fireFrame.x < x && x < binding.fireFrame.x + binding.fireFrame.width) && binding.fireFrame.y < y && y < binding.fireFrame.y + binding.fireFrame.height) {
//            Log.d("frame에 들어왔음", "true")
            plusTr8 = Thread {
                while (flag8!!) {
                    try {
                        var past = System.currentTimeMillis()/1000
                        Thread.sleep(1000)
                        var now = System.currentTimeMillis()/1000
                        count8 += (now - past).toInt()
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
            plusTr8!!.start()

//            고기가 양념장에 가있으면 점수 측정 + 고기 사라지게 하기
        } else if (binding.endFrame.x < x && x < binding.endFrame.x + binding.endFrame.width && binding.endFrame.y < y && y < binding.endFrame.y + binding.endFrame.height) {
//            Log.d("양념장","true")
            v.visibility = View.GONE
            if (tag2 == resourceId) {
                sc += 500
                binding.score.text = "SCORE:$sc"
                if (sc >= 2000) {
                    val intent = Intent(this, LastActivity::class.java)
                    intent.putExtra("result", "성공적인 고기장수")
                    startActivity(intent)
                    finish()
                }
            } else {
            }
        }
    }
    private fun timerTask() {
        timerTask = object : TimerTask() {
            override fun run() {
                count--
                binding.time.post(Runnable {
                    if (count >= 0) {
                        binding.time.text = (count.toString() + "초")
                        binding.timePgbar.progress = count
                    } else {
                        timer.cancel()
                        val intent = Intent(applicationContext, LastActivity::class.java)
                        intent.putExtra("result","TimeOut!!!!")
                        startActivity(intent)
                        val sharedPreferences = getSharedPreferences("TimeOut", MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("score",sc.toString())
                        editor.apply()
                        finish()
                    }
                })
            }
        }
        timer.schedule(timerTask, 0, 1000)
//        1초마다 반복
    }

    override fun onPause() {
        super.onPause()

        timerTask?.cancel()
        val sharedPreferences = getSharedPreferences("GiveUP", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("score",sc.toString())
        editor.apply()

        val customDialogView = LayoutInflater.from(this).inflate(R.layout.activity_dialog, null)
        val builder = AlertDialog.Builder(this)
            .setView(customDialogView)
            .setTitle("일시 정지")
        val dialog = builder.show()

        val exitButton = customDialogView.findViewById<Button>(R.id.btn_exit)
        exitButton.setOnClickListener {
            val intent = Intent(this, LastActivity::class.java)
            intent.putExtra("result", "포기!")
            startActivity(intent)
            finish()
        }
        val restartButton = customDialogView.findViewById<Button>(R.id.btn_restart)
        restartButton.setOnClickListener {
            timerTask()
            dialog.dismiss()
        }
    }
}