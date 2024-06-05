package com.c241ps220.centingapps.views.Dashboard

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.c241ps220.centingapps.R
import com.c241ps220.centingapps.databinding.ActivityDashboardBinding
import com.c241ps220.centingapps.views.Deteksi.Guest.DetectionGuestActivity
import com.c241ps220.centingapps.views.WelcomeScreen.WelcomingActivity
import com.c241ps220.centingapps.views.ZoomImage.ZoomImageActivity
import com.c241ps220.centingapps.views.profile.ProfileActivity
import com.denzcoskun.imageslider.constants.ActionTypes
import com.denzcoskun.imageslider.constants.AnimationTypes
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.interfaces.TouchListener
import com.denzcoskun.imageslider.models.SlideModel

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            var appVersion = applicationContext.packageManager.getPackageInfo(
                applicationContext.packageName,
                0
            ).versionName

            tvAppVersion.text = "Apps Ver. : $appVersion"


        }

        setupAction()
        playAnimation()
        setupSlider()
        setupFAQ()
    }

    private fun setupSlider(){
        with(binding.imageSlider){
            val imageList = ArrayList<SlideModel>() // Create image list
            imageList.add(SlideModel("https://mediaedukasi.id/wp-content/uploads/2022/04/Generasi-bebas-stunding.png"))
            imageList.add(SlideModel("https://diskominfosp.lebakkab.go.id/wp-content/uploads/2024/02/d66b775045769e99783493e1d451ab78.webp"))
            imageList.add(SlideModel("https://assets-a1.kompasiana.com/items/album/2022/05/22/img-20220522-wa0004-62898f64bb448611ca25dad2.jpg"))
            imageList.add(SlideModel("https://rsupsoeradji.id/wp-content/uploads/2019/02/Nutrisi-dan-Makanan-Sehat-untuk-Ibu-Hamil.jpg"))
            imageList.add(SlideModel("https://indonesiabaik.id/public/uploads/post/1583/1583-1688970533-230707_IPP_Penanggulangan-Stunting_DV.jpg"))
            imageList.add(SlideModel("https://forumkeadilansumut.com/wp-content/uploads/2022/06/Stunting.jpg"))

            setImageList(imageList, com.denzcoskun.imageslider.constants.ScaleTypes.CENTER_CROP)

            setSlideAnimation(com.denzcoskun.imageslider.constants.AnimationTypes.ZOOM_OUT)

            setItemClickListener(object : ItemClickListener {
                override fun onItemSelected(position: Int) {
                    var data = imageList[position].imageUrl
                    val intent = Intent(this@DashboardActivity, ZoomImageActivity::class.java).apply {
                        putExtra("imageUrl", data)
                    }
                    startActivity(intent)
                }

                override fun doubleClick(position: Int) {
                    // Do not use onItemSelected if you are using a double click listener at the same time.
                    // Its just added for specific cases.
                    // Listen for clicks under 250 milliseconds.
                    println("its double")
                }
            })

            setTouchListener(object : TouchListener {
                override fun onTouched(touched: ActionTypes, position: Int) {
                    if (touched == com.denzcoskun.imageslider.constants.ActionTypes.DOWN){
                        stopSliding()
                    } else if (touched == com.denzcoskun.imageslider.constants.ActionTypes.UP ) {
                        startSliding(1000)
                    }
                }
            })
        }

    }

    private fun setupFAQ(){
        with(binding){
            ivExpand1.setOnClickListener {
                if (tvFAQ1.visibility == View.GONE){
                    tvFAQ1.visibility = View.VISIBLE
                    ivExpand1.setImageResource(R.drawable.baseline_keyboard_arrow_up_24)
                }else{
                    tvFAQ1.visibility = View.GONE
                    ivExpand1.setImageResource(R.drawable.baseline_keyboard_arrow_down_24)
                }
            }

            ivExpand2.setOnClickListener {
                if (tvFAQ2.visibility == View.GONE){
                    tvFAQ2.visibility = View.VISIBLE
                    ivExpand2.setImageResource(R.drawable.baseline_keyboard_arrow_up_24)
                }else{
                    tvFAQ2.visibility = View.GONE
                    ivExpand2.setImageResource(R.drawable.baseline_keyboard_arrow_down_24)
                }
            }

            ivExpand3.setOnClickListener {
                if (tvFAQ3.visibility == View.GONE){
                    tvFAQ3.visibility = View.VISIBLE
                    ivExpand3.setImageResource(R.drawable.baseline_keyboard_arrow_up_24)
                }else{
                    tvFAQ3.visibility = View.GONE
                    ivExpand3.setImageResource(R.drawable.baseline_keyboard_arrow_down_24)
                }
            }

            ivExpand4.setOnClickListener {
                if (tvFAQ4.visibility == View.GONE){
                    tvFAQ4.visibility = View.VISIBLE
                    ivExpand4.setImageResource(R.drawable.baseline_keyboard_arrow_up_24)
                }else{
                    tvFAQ4.visibility = View.GONE
                    ivExpand4.setImageResource(R.drawable.baseline_keyboard_arrow_down_24)
                }
            }
        }
    }

    private fun setupAction() {
        with(binding){
            ivUser.setOnClickListener {
//            showConfirmationLogoutDialog()
//                onDevelopment()
                startActivity(
                    Intent(
                        this@DashboardActivity,
                        ProfileActivity::class.java
                    )
                )
            }
//            ivSetting.setOnClickListener {
////            showConfirmationSettingDialog()
//                onDevelopment()
//            }
            btDetect.setOnClickListener {
//                onDevelopment()
                startActivity(
                    Intent(
                        this@DashboardActivity,
                        DetectionGuestActivity::class.java
                    )
                )
            }
            btHistoryDetection.setOnClickListener {
                onDevelopment()
            }
            divShareApps.setOnClickListener {
                onDevelopment()
            }

        }


    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.ivAnimate, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 2000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
    }

    private fun onDevelopment(){
        with(binding.divDevelopment){
            val fadeIn = AnimationUtils.loadAnimation(this@DashboardActivity, R.anim.fade_in2)
            val fadeOut = AnimationUtils.loadAnimation(this@DashboardActivity, R.anim.fade_out2)
            root.startAnimation(fadeIn)
            root.visibility = View.VISIBLE

            btDismiss.setOnClickListener {
                root.visibility = View.GONE
                root.startAnimation(fadeOut)
            }
        }
    }


}