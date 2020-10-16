package com.noice.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.noice.R
import com.noice.model.Banner
import kotlinx.android.synthetic.main.fragment_details.*
/**
 * Created By Atiq
 */
class DetailsFragment : Fragment() {

    companion object {
        private const val BANNER_DATA = "BANNER_DATA"

        fun newInstance(banner: Banner) : DetailsFragment {
            val fragment = DetailsFragment()
            val bundle = Bundle()
            bundle.putParcelable(BANNER_DATA, banner)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var banner: Banner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        banner = arguments?.getParcelable(BANNER_DATA)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(!banner?.author.isNullOrEmpty()) {
            textAuthor.text = banner?.author
        }

        if(!banner?.description.isNullOrEmpty()) {
            textDescription.text = banner?.description
        }
    }
}