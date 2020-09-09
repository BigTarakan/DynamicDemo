package com.bigtarakan.dynamicdemo.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bigtarakan.dynamicdemo.MainActivity
import com.bigtarakan.dynamicdemo.R
import com.bigtarakan.dynamicdemo.databinding.*
import com.bigtarakan.dynamicdemo.model.*
import com.bigtarakan.dynamicdemo.viewmodel.AboutViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.about_fragment_layout.*

class AboutFragment : Fragment() {
    lateinit var binding: AboutFragmentLayoutBinding
    lateinit var mContext: FragmentActivity

    private lateinit var viewModel: AboutViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.about_fragment_layout, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mContext = requireActivity()
        viewModel = ViewModelProvider(this).get(AboutViewModel::class.java)
        viewModel.getData()
        viewModel.repositoryObservable.observe(mContext, Observer<BaseModel> {
            it?.let {
                (mContext as MainActivity).supportActionBar?.title = it.title
                about_holder.setBackgroundColor(Color.parseColor("#${it.bg_color}"))
                about_holder.removeAllViews()
                it.blocks.forEach { blockData ->
                    when (blockData.type) {
                        "logo_block" -> {
                            blockData as AboutModelBlockLogo
                            val blockBind: AboutLogoLayoutBinding = DataBindingUtil.inflate(
                                LayoutInflater.from(mContext),
                                R.layout.about_logo_layout, about_holder, false
                            )
                            blockBind.logo = blockData
                            about_holder.addView(blockBind.root)
                            Picasso.get().load(blockData.image).into(blockBind.aboutLogoImage)
                        }
                        "link_list_item" -> {
                            blockData as AboutModelBlockLink
                            val blockBind: AboutLinkLayoutBinding = DataBindingUtil.inflate(
                                LayoutInflater.from(mContext),
                                R.layout.about_link_layout, about_holder, false
                            )
                            blockBind.link = blockData
                            about_holder.addView(blockBind.root)
                        }
                        "blue_link_block" -> {
                            blockData as AboutModelBlockBlueLink
                            val blockBind: AboutBlueLinkLayoutBinding = DataBindingUtil.inflate(
                                LayoutInflater.from(mContext),
                                R.layout.about_blue_link_layout, about_holder, false
                            )
                            blockBind.link = blockData
                            about_holder.addView(blockBind.root)
                        }
                        "copyright_block" -> {
                            blockData as AboutModelBlockCopyright
                            val blockBind: AboutCopyrightLayoutBinding = DataBindingUtil.inflate(
                                LayoutInflater.from(mContext),
                                R.layout.about_copyright_layout, about_holder, false
                            )
                            blockBind.copyright = blockData
                            about_holder.addView(blockBind.root)
                        }
                    }
                }
            }
        })
    }
}