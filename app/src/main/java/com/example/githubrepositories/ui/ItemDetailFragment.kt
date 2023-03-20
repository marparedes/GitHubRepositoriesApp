package com.example.githubrepositories.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.githubrepositories.databinding.FragmentItemDetailBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ItemDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ItemDetailFragment : Fragment() {
    private lateinit var binding : FragmentItemDetailBinding
    private val args : ItemDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentItemDetailBinding.inflate(inflater, container, false)

        // topics list
        val topics = args.topics

        binding.detailTitle.text = args.name
        binding.detailFullName.text = "Full name: " + args.fullName
        binding.detailAuthorAvatar.load(args.ownerAvatar)
        binding.detailAuthorName.text = "Owner username: " +  args.ownerName
        binding.detailAuthorProfile.text = "Owner profile link: " + args.ownerProfile
        binding.detailRepoLink.text = "Repository link: " + args.repoLink
        binding.detailDescription.text = "Description: " + args.description
        binding.detailDate.text = "Created at: " + args.createdDate
        binding.detailVisibility.text = "Repo visibility: " + args.visibility


        return binding.root
    }

}