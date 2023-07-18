package com.example.carepetprac

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.TextView
import android.widget.Toast

class IntroActivity : AppCompatActivity() {

    private val splah_delay:Long=5000
    private var isLogin:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        loginCheck()

        val textView: TextView = findViewById(R.id.textView)

        val targetX = 0
        val targetY = -300

        val translateAnimation = TranslateAnimation(0f, targetX.toFloat(), 0f, targetY.toFloat())
        translateAnimation.duration = 1500 // 애니메이션 지속 시간 (밀리초)

        translateAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                // 애니메이션이 시작될 때 호출되는 콜백 메서드
            }

            override fun onAnimationEnd(animation: Animation) {
                // 애니메이션이 종료될 때 호출되는 콜백 메서드
                textView.clearAnimation() // 애니메이션 효과를 제거하여 제자리에 고정
                textView.translationX = targetX.toFloat() // x 좌표를 이동한 위치로 설정
                textView.translationY = targetY.toFloat() // y 좌표를 이동한 위치로 설정
            }

            override fun onAnimationRepeat(animation: Animation) {
                // 애니메이션이 반복될 때 호출되는 콜백 메서드
            }
        })

        // 애니메이션 적용
        textView.startAnimation(translateAnimation)

//        val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
//        textView.startAnimation(fadeInAnimation)

        val timer = object : CountDownTimer(splah_delay, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // 타이머가 틱마다 수행할 작업 (여기서는 필요 없음)
            }

            override fun onFinish() {
                // 타이머가 종료된 후 수행할 작업
                if(isLogin){
                    Toast.makeText(this@IntroActivity, "true", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@IntroActivity, MainActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish() // IntroActivity 종료
                }else{
                    Toast.makeText(this@IntroActivity, "false", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@IntroActivity, LoginActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish() // IntroActivity 종료
                }

            }
        }

        timer.start() // 타이머 시작
    }

    private fun loginCheck() {
        val sharedPreferences = getSharedPreferences("LoginSP", Context.MODE_PRIVATE)
        isLogin = sharedPreferences.getBoolean("isLogin", false) // 변수 값을 가져옴, 기본값은 ""로 설정
    }
}