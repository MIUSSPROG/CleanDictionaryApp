package com.example.cleandictionaryapp.feature_dictionary

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cleandictionaryapp.databinding.ItemWordBinding
import com.example.cleandictionaryapp.feature_dictionary.domain.model.WordInfo

class WordInfoAdapter: ListAdapter<WordInfo, WordInfoAdapter.WordInfoViewHolder>(DiffCallback()) {

    class WordInfoViewHolder(private val binding: ItemWordBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(wordInfo: WordInfo){
            binding.apply {
                tvWord.text = wordInfo.word
                tvPhonetic.text = wordInfo.phonetic
                tvMeaning.text = Html.fromHtml(wordInfo.meanings.map{"<b>${it.partOfSpeech}</b><br/>" +
                        it.definitions.mapIndexed{idx, value ->  "${idx+1}. ${value.definition}<br/>"  }.joinToString() }.joinToString(), Html.FROM_HTML_MODE_COMPACT)
            }
        }
    }

    class DiffCallback: DiffUtil.ItemCallback<WordInfo>(){
        override fun areItemsTheSame(oldItem: WordInfo, newItem: WordInfo): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: WordInfo, newItem: WordInfo): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordInfoViewHolder {
        val binding = ItemWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordInfoViewHolder, position: Int) {
        val curItem = getItem(position)
        holder.bind(curItem)
    }
}