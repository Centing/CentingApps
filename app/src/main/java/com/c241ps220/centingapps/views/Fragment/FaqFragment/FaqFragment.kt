package com.c241ps220.centingapps.views.Fragment.FaqFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.c241ps220.centingapps.R
import com.c241ps220.centingapps.databinding.FragmentBerandaBinding
import com.c241ps220.centingapps.databinding.FragmentFaqBinding
import com.c241ps220.centingapps.utils.CustomFunction
import com.c241ps220.centingapps.views.Login.LoginActivity

class FaqFragment : Fragment() {

    private var _binding: FragmentFaqBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFaqBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            setupFAQ()
            btAskNow.setOnClickListener { isDevelopment(true) }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Membersihkan binding
    }

    fun isDevelopment(state: Boolean) {
        with(binding) {
            if (state) {
                divDevelopment.root.visibility = View.VISIBLE
            } else {
                divDevelopment.root.visibility = View.GONE
            }

            divDevelopment.btDismiss.setOnClickListener {
                isDevelopment(false)
            }
        }
    }

}