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
import com.bigtarakan.dynamicdemo.databinding.MainFragmentLayoutBinding
import com.bigtarakan.dynamicdemo.databinding.MainLinkLayoutBinding
import com.bigtarakan.dynamicdemo.databinding.MainProfileLayoutBinding
import com.bigtarakan.dynamicdemo.model.BaseModel
import com.bigtarakan.dynamicdemo.model.MainModelBlockLinks
import com.bigtarakan.dynamicdemo.model.MainModelBlockProfile
import com.bigtarakan.dynamicdemo.viewmodel.MainViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.main_fragment_layout.*

class MainFragment : Fragment() {
    lateinit var binding: MainFragmentLayoutBinding
    lateinit var mContext: FragmentActivity
//    companion object {
//        fun newInstance() = MainFragment()
//    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment_layout, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mContext = requireActivity()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.getData()
        viewModel.repositoryObservable.observe(mContext, Observer<BaseModel> {
            it?.let {
                (mContext as MainActivity).supportActionBar?.title = it.title
                main_holder.setBackgroundColor(Color.parseColor("#${it.bg_color}"))
                main_holder.removeAllViews()
                it.blocks.forEach { blockData ->
                    when (blockData.type) {
                        "profile_block" -> {
                            blockData as MainModelBlockProfile
                            val blockBind: MainProfileLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                                R.layout.main_profile_layout, main_holder, false)
                            blockBind.profile = blockData
                            main_holder.addView(blockBind.root)
                            Picasso.get().load(blockData.avatar).into(blockBind.mainProfileImage)
                        }
                        "links_block" -> {
                            blockData as MainModelBlockLinks
                            blockData.links.forEach { linkData ->
                                val linkBind: MainLinkLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                                    R.layout.main_link_layout, main_holder, false)
                                linkBind.link = linkData
                                linkBind.root.setOnClickListener {
                                    val fragment = when (linkData.link) {
                                        "education" -> EducationFragment()
                                        "help" -> HelpFragment()
                                        "about" -> AboutFragment()
                                        else -> MainFragment()
                                    }
                                    mContext.supportFragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit()
                                }
                                main_holder.addView(linkBind.root)
                                Picasso.get().load(linkData.image).into(linkBind.mainLinkImage)
                            }
                        }
                    }
                }
            }
        })
    }
}