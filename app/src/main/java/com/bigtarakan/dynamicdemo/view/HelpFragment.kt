package com.bigtarakan.dynamicdemo.view

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bigtarakan.dynamicdemo.MainActivity
import com.bigtarakan.dynamicdemo.R
import com.bigtarakan.dynamicdemo.databinding.HelpFragmentLayoutBinding
import com.bigtarakan.dynamicdemo.databinding.HelpListItemLayoutBinding
import com.bigtarakan.dynamicdemo.model.BaseModel
import com.bigtarakan.dynamicdemo.model.HelpModelBlockLogo
import com.bigtarakan.dynamicdemo.viewmodel.HelpViewModel
import com.squareup.picasso.Picasso

class HelpFragment : Fragment() {
    lateinit var binding: HelpFragmentLayoutBinding
    lateinit var mContext: FragmentActivity

    private lateinit var viewModel: HelpViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.help_fragment_layout, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mContext = requireActivity()
        viewModel = ViewModelProvider(this).get(HelpViewModel::class.java)
        viewModel.getData()
        viewModel.repositoryObservable.observe(mContext, Observer<BaseModel> {
            it?.let {
                (mContext as MainActivity).supportActionBar?.title = it.title
                binding.helpList.setBackgroundColor(Color.parseColor("#${it.bg_color}"))
                it.blocks.forEach { blockData ->
                    when (blockData.type) {
                        "logo_block" -> {
                            blockData as HelpModelBlockLogo
                            binding.helpList.adapter = HelpListAdapter(blockData.logos)
                        }
                    }
                }
            }
        })
    }

    inner class HelpListAdapter(private val items: ArrayList<HelpModelBlockLogo.Logo>) : RecyclerView.Adapter<HelpListAdapter.HelpViewHolder>() {
        inner class HelpViewHolder(val binding: HelpListItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(itemData: HelpModelBlockLogo.Logo) {
                binding.item = itemData
                binding.executePendingBindings()
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelpViewHolder {
            val binding: HelpListItemLayoutBinding = DataBindingUtil
                .inflate(layoutInflater, R.layout.help_list_item_layout, parent, false)
            return HelpViewHolder(binding)
        }

        override fun getItemCount(): Int = items.size

        override fun onBindViewHolder(holder: HelpViewHolder, position: Int) {
            val item = items[position]
            holder.bind(item)
            Picasso.get().load(item.image).into(holder.binding.helpListItemImage)
            holder.binding.helpListItemButton.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW).apply { data = Uri.parse(item.link) })
            }
        }
    }
}