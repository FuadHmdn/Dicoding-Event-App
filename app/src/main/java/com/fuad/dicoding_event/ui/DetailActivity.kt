package com.fuad.dicoding_event.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.fuad.dicoding_event.R
import com.fuad.dicoding_event.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    companion object{
        const val EXTRA_ID = "extra_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra(EXTRA_ID, 0)
        getDetailData(id)

        viewModel.item.observe(this){ event ->
            binding.name.text = event?.name
            Glide.with(this)
                .load(event?.imageLogo)
                .into(binding.imageLogo)
            binding.description.text = event?.description
            binding.ownerName.text = event?.ownerName
            binding.beginTime.text = event?.beginTime
            binding.registrant.text = event?.registrants.toString()
        }
    }

    private fun getDetailData(id: Int) {
        viewModel.getDataById(id)
    }
}