package com.c241ps220.centingapps.views.Fragment.BerandaFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.c241ps220.centingapps.R
import com.c241ps220.centingapps.databinding.FragmentBerandaBinding
import com.c241ps220.centingapps.utils.CustomFunction
import com.c241ps220.centingapps.views.Deteksi.Guest.DetectionGuestActivity
import com.c241ps220.centingapps.views.Deteksi.User.DetectionUserActivity
import com.c241ps220.centingapps.views.ZoomImage.ZoomImageActivity
import com.c241ps220.centingapps.views.Profile.ProfileActivity
import com.denzcoskun.imageslider.constants.ActionTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.interfaces.TouchListener
import com.denzcoskun.imageslider.models.SlideModel


class BerandaFragment : Fragment() {

    private var _binding: FragmentBerandaBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBerandaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            tvInisial.text = CustomFunction.getInitials(getString(R.string.dummy_name))

            setupSlider()

            divName.setOnClickListener {
                startActivity(
                    Intent(
                        this@BerandaFragment.requireContext(),
                        ProfileActivity::class.java
                    )
                )
            }

            // Cek kondisi kalau ada data user yang tersimpan ke DetectionUserActivity, kalau tidak ada data kesimpan ke
            btDetect.setOnClickListener {
                startActivity(
                    Intent(
                        this@BerandaFragment.requireContext(),
                        DetectionUserActivity::class.java
                    )
                )
            }
        }
    }

    private fun setupSlider() {
        with(binding.imageSlider) {
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
                    val intent = Intent(
                        this@BerandaFragment.requireContext(),
                        ZoomImageActivity::class.java
                    ).apply {
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
                    if (touched == com.denzcoskun.imageslider.constants.ActionTypes.DOWN) {
                        stopSliding()
                    } else if (touched == com.denzcoskun.imageslider.constants.ActionTypes.UP) {
                        startSliding(1000)
                    }
                }
            })
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Membersihkan binding
    }

}