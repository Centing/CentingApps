package com.c241ps220.centingapps.views.ZoomImage

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.c241ps220.centingapps.R
import com.c241ps220.centingapps.databinding.ActivityZoomImageBinding

class ZoomImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityZoomImageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityZoomImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupZoom()

    }

    private fun setupZoom(){
//        binding.ivZoomImage.visibility = View.GONE
//        binding.progressBar.visibility = View.VISIBLE
        val intent = intent
        if (intent != null) {
            val imageUrl = intent.getStringExtra("imageUrl")

            Glide.with(this)
                .load(imageUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.progressBar.visibility = View.GONE
//                        binding.ivZoomImage.visibility = View.VISIBLE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: com.bumptech.glide.load.DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.progressBar.visibility = View.GONE
//                        binding.ivZoomImage.visibility = View.VISIBLE
                        return false
                    }
                })
                .into(binding.ivZoomImage)
            Toast.makeText(this@ZoomImageActivity, "Cubit layar untuk memperbesar gambar", Toast.LENGTH_SHORT).show()

        }else{
            finish()
        }
    }

    private fun setupToolbar(){
        with(binding.divToolbar){
            ivBack.setOnClickListener { finish() }
            tvTitleToolbar.text = getResources().getString(R.string.tips_trik)
        }
    }
}