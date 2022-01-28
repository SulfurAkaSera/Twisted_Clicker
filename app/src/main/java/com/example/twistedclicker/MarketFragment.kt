package com.example.twistedclicker

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.twistedclicker.databinding.FragmentMarketBinding

class MarketFragment : Fragment() {

    private var buttonWhite: Button? = null
    private var buttonGreen: Button? = null
    private var buttonYellow: Button? = null
    private var buttonBlack: Button? = null
    private var buttonGradient: Button? = null
    private var buttonUkraine: Button? = null
    private var buttonBack: Button? = null
    private var fullscreenContent: View? = null
    private var fullscreenContentControls: View? = null

    private var _binding: FragmentMarketBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentMarketBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.scoreView.text = Data.scores.toString()
        buttonWhite = binding.buttonWhite
        buttonGreen = binding.buttonGreen
        buttonYellow = binding.buttonYellow
        buttonBlack = binding.buttonBlack
        buttonGradient = binding.buttonGradient
        buttonUkraine = binding.buttonUkraine
        buttonBack = binding.buttonBack

        buttonWhite?.setOnClickListener {
            if (Data.scores >= 0){
                (activity as? Get)?.forActivity("White")
                buy(0)
            }
        }
        buttonGreen?.setOnClickListener {
            if (Data.scores >= 150){
                (activity as? Get)?.forActivity("Green")
                buy(150)
            }
            else{
                warning()
            }
        }
        buttonYellow?.setOnClickListener {
            if (Data.scores >= 4150){
                (activity as? Get)?.forActivity("Yellow")
                buy(4150)
            }
            else{
                warning()
            }
        }
        buttonBlack?.setOnClickListener {
            if (Data.scores >= 7300){
                (activity as? Get)?.forActivity("Black")
                buy(7300)
            }
            else{
                warning()
            }
        }
        buttonGradient?.setOnClickListener {
            if (Data.scores >= 10500){
                (activity as? Get)?.forActivity("Gradient")
                buy(10500)
            }
            else{
                warning()            }
        }
        buttonUkraine?.setOnClickListener {
            if (Data.scores >= 15700){
                (activity as? Get)?.forActivity("Ukraine")
                buy(15700)
            }
            else{
                warning()
            }
        }
        buttonBack?.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
        }
    }

    private fun buy(money : Int){
        Data.scores -= money
        binding.scoreView.text = Data.scores.toString()
        binding.textWarning.text = null
    }

    @SuppressLint("SetTextI18n")
    private fun warning(){
        binding.textWarning.text = "Not enough scores!"
    }

    override fun onDestroy() {
        super.onDestroy()
        buttonWhite = null
        buttonGreen = null
        buttonYellow = null
        buttonBack = null
        buttonGradient = null
        buttonUkraine = null
        buttonBack = null
        fullscreenContent = null
        fullscreenContentControls = null
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        fun newInstance(scoresInfo: ScoresInfo) : MarketFragment{
            val fragment = MarketFragment()
            fragment.arguments?.putParcelable("scores", scoresInfo)
            return fragment
        }
    }
}