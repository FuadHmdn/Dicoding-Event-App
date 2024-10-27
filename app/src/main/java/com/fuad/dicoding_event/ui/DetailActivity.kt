package com.fuad.dicoding_event.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.fuad.dicoding_event.R
import com.fuad.dicoding_event.data.local.entity.EventEntity
import com.fuad.dicoding_event.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var isFavorite: Boolean = false
    private lateinit var eventEntity: EventEntity

    private val factory: ViewModelFactory by lazy {
        ViewModelFactory.getInstance(this)
    }

    private val viewModel: DetailViewModel by viewModels{
        factory
    }

    private var url: String? = null

    companion object{
        const val EXTRA_ID = "extra_id"
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra(EXTRA_ID, 0)
        getDetailData(id)

        eventFavoriteCheck(id)

        viewModel.item.observe(this){ event ->
            val quota: Int? = event?.quota
            val registrants: Int? = event?.registrants
            val result = (quota!! - registrants!!).toString()

            eventEntity = EventEntity(event.id.toString(), event.name!!, event.mediaCover!!)

            binding.name.text = event.name
            Glide.with(this)
                .load(event.imageLogo)
                .into(binding.imageLogo)
            val desc = event.description
            binding.description.text = Html.fromHtml(desc, Html.FROM_HTML_MODE_COMPACT)
            binding.ownerName.text = event.ownerName
            binding.beginTime.text = event.beginTime
            binding.registrant.text = result
            url = event.link
        }

        binding.btnRegister.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        viewModel.isLoading.observe(this){
            showLoading(it)
        }

        viewModel.failure.observe(this){ failure->
            if (failure) {
                viewModel.failureMessage.observe(this){ message ->
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.favorit.setOnClickListener {
            if (isFavorite){
                binding.favorit.setImageResource(R.drawable.baseline_favorite_border_24)
                viewModel.deleteFavoriteEvent(eventEntity)
                isFavorite = false
            } else {
                binding.favorit.setImageResource(R.drawable.baseline_favorite_24)
                viewModel.insertFavoriteEvent(eventEntity)
                isFavorite = true
            }
        }

    }

    private fun getDetailData(id: Int) {
        viewModel.getDataById(id)
    }

    private fun showLoading(b: Boolean){
        if (b) {
            binding.progressBarFinishedFragment.visibility = View.VISIBLE
            binding.favorit.visibility = View.GONE
            binding.description.visibility = View.GONE
            binding.ownerName.visibility = View.GONE
            binding.beginTime.visibility = View.GONE
            binding.registrant.visibility = View.GONE
        } else {
            binding.progressBarFinishedFragment.visibility = View.GONE
            binding.favorit.visibility = View.VISIBLE
            binding.description.visibility = View.VISIBLE
            binding.ownerName.visibility = View.VISIBLE
            binding.beginTime.visibility = View.VISIBLE
            binding.registrant.visibility = View.VISIBLE
        }
    }

    private fun eventFavoriteCheck(id: Int){
        viewModel.geFavoriteEvent(id).observe(this){ event->
            if (event == null) {
                binding.favorit.setImageResource(R.drawable.baseline_favorite_border_24)
                isFavorite = false
            } else {
                binding.favorit.setImageResource(R.drawable.baseline_favorite_24)
                isFavorite = true
            }
        }
    }
}