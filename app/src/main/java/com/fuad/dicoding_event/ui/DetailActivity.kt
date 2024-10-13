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
import com.fuad.dicoding_event.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()
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

        viewModel.item.observe(this){ event ->
            binding.name.text = event?.name
            Glide.with(this)
                .load(event?.imageLogo)
                .into(binding.imageLogo)
            val desc = event?.description
            binding.description.text = Html.fromHtml(desc, Html.FROM_HTML_MODE_COMPACT)
            binding.ownerName.text = event?.ownerName
            binding.beginTime.text = event?.beginTime
            binding.registrant.text = event?.registrants.toString()
            url = event?.link
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
    }

    private fun getDetailData(id: Int) {
        viewModel.getDataById(id)
    }

    private fun showLoading(b: Boolean){
        if (b) {
            binding.progressBarFinishedFragment.visibility = View.VISIBLE
        } else {
            binding.progressBarFinishedFragment.visibility = View.GONE
        }
    }
}