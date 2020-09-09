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
import com.bigtarakan.dynamicdemo.databinding.EducationFragmentLayoutBinding
import com.bigtarakan.dynamicdemo.databinding.EducationLinkLayoutBinding
import com.bigtarakan.dynamicdemo.model.BaseModel
import com.bigtarakan.dynamicdemo.model.EducationModelBlockLink
import com.bigtarakan.dynamicdemo.viewmodel.EducationViewModel
import kotlinx.android.synthetic.main.education_fragment_layout.*
import kotlinx.android.synthetic.main.main_fragment_layout.*

class EducationFragment : Fragment() {
    lateinit var binding: EducationFragmentLayoutBinding
    lateinit var mContext: FragmentActivity

    private lateinit var viewModel: EducationViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.education_fragment_layout, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mContext = requireActivity()
        viewModel = ViewModelProvider(this).get(EducationViewModel::class.java)
        viewModel.getData()
        viewModel.repositoryObservable.observe(mContext, Observer<BaseModel> {
            it?.let {
                (mContext as MainActivity).supportActionBar?.title = it.title
                education_holder.setBackgroundColor(Color.parseColor("#${it.bg_color}"))
                education_holder.removeAllViews()
                it.blocks.forEach { blockData ->
                    when (blockData.type) {
                        "link_list_item" -> {
                            blockData as EducationModelBlockLink
                            val blockBind: EducationLinkLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                                R.layout.education_link_layout, main_holder, false)
                            blockBind.link = blockData
                            education_holder.addView(blockBind.root)
                        }
                    }
                }
            }
        })
    }
}