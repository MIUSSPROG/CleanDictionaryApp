package com.example.cleandictionaryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.cleandictionaryapp.core.util.Resource
import com.example.cleandictionaryapp.databinding.ActivityMainBinding
import com.example.cleandictionaryapp.feature_dictionary.WordInfoAdapter
import com.example.cleandictionaryapp.feature_dictionary.presentation.WordInfoViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val wordInfoAdapter by lazy { WordInfoAdapter() }
    private val viewModel: WordInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            rvWords.adapter = wordInfoAdapter

            viewModel.wordInfoLiveData.observe(this@MainActivity){
                when(it){
                    is Resource.Success -> {
                        progressBar.visibility = View.INVISIBLE
                        wordInfoAdapter.submitList(it.data)
                    }
                    is Resource.Loading -> {
                        progressBar.visibility = View.VISIBLE
                    }
                }
            }

            lifecycleScope.launchWhenStarted {
                viewModel.eventFlow.collectLatest { event ->
                    when(event) {
                        is WordInfoViewModel.UIEvent.ShowSnackbar -> {
                            Snackbar.make(binding.root, event.message, Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            etSearchWord.addTextChangedListener(object :TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    viewModel.onSearch(s.toString())
                }
                override fun afterTextChanged(p0: Editable?) {}
            })


        }
    }
}