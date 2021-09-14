package com.ddmukhin.stroylandtestappapi.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ddmukhin.stroylandtestappapi.R
import com.ddmukhin.stroylandtestappapi.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<GiphyViewModel>()

    private var viewBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding?.root)

        val giphyList = viewBinding?.giphyList!!
        giphyList.layoutManager = LinearLayoutManager(this)
        giphyList.isVisible = false
        viewBinding?.errorText!!.isVisible = false
        viewBinding?.progressIndicator!!.isVisible = false
        viewBinding?.foundLabel!!.isVisible = false
        viewBinding?.fetchQuery!!.setOnClickListener {
            viewModel.search(viewBinding?.queryInput!!.text.toString())
            viewBinding?.giphyList!!.scrollToPosition(0)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is GiphyViewModel.GiphyAdapterState.Loading -> showProgressIndicator()
                        is GiphyViewModel.GiphyAdapterState.Success -> showGiphyList(state.giphyAdapter)
                        is Error -> showError()
                        else -> {
                        }
                    }
                }
            }
        }
    }

    private fun showError(){
        viewBinding?.progressIndicator!!.isVisible = false
        viewBinding?.foundLabel!!.isVisible = false
        viewBinding?.giphyList!!.isVisible = false
        viewBinding?.errorText!!.isVisible = true
        viewBinding?.errorText!!.text = "Error!"
    }

    private fun showGiphyList(adapter: GiphyAdapter) {
        viewBinding?.giphyList!!.adapter = adapter
        viewBinding?.progressIndicator!!.isVisible = false
        viewBinding?.giphyList!!.isVisible = true
        viewBinding?.foundLabel!!.isVisible = true
        viewBinding?.foundLabel!!.text = "Found: ${adapter.itemCount}"
    }

    private fun showProgressIndicator() {
        viewBinding?.progressIndicator!!.isVisible = true
        viewBinding?.foundLabel!!.isVisible = false
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }
}