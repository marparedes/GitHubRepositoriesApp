package com.example.githubrepositories.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubrepositories.databinding.FragmentMainBinding
import com.example.githubrepositories.model.ItemAction
import com.example.githubrepositories.service.ApiResult
import com.example.githubrepositories.ui.adapter.ReposListAdapter
import com.example.githubrepositories.viewmodel.RepositoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val mainViewModel by viewModels<RepositoryViewModel>()
    private lateinit var reposListAdapter: ReposListAdapter
    lateinit var binding: FragmentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchData()
    }

    private fun fetchData() {
        mainViewModel.fetchGitHubApi()
        mainViewModel.response.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiResult.Success -> {
                    // bind data to the view
                    response.data?.let {
                        binding.listRecyclerView.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE

                        reposListAdapter = ReposListAdapter(it.items)

                        binding.container.let { container ->
                            container.setOnRefreshListener {
                                container.isRefreshing = false
                                fetchData()
                            }
                        }
                        reposListAdapter.itemClickListener = object : ReposListAdapter.ItemClickListener {
                            override fun onItemClicked(action: ItemAction) {
                                val direction = MainFragmentDirections.actionMainFragmentToItemDetailFragment(
                                    action.name, action.full_name, action.owner_avatar, action.owner_name, action.owner_profile,
                                    action.repo_link, action.description, action.created_date, action.visibility, action.topics.toTypedArray()
                                )
                                findNavController().navigate(direction)
                            }
                        }

                        binding.mainTitle.text = "Repositories found using 'kotlin': ${reposListAdapter.itemCount}"
                        binding.listRecyclerView.adapter = reposListAdapter
                        binding.listRecyclerView.setHasFixedSize(true)
                        binding.listRecyclerView.isNestedScrollingEnabled = false
                        binding.listRecyclerView.layoutManager = LinearLayoutManager(context)
                    }
                }
                is ApiResult.Error -> {
                    // show error message
                    Log.d("Error: ", response.message.toString())
                    Toast.makeText(context, "Fetch API Error", Toast.LENGTH_SHORT).show()
                }
                is ApiResult.Loading -> {
                    // show a progress bar
                    binding.listRecyclerView.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }
}