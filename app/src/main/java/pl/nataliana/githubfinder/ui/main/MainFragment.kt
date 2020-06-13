package pl.nataliana.githubfinder.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject
import pl.nataliana.githubfinder.R
import pl.nataliana.githubfinder.adapter.GithubRepositoryAdapter
import pl.nataliana.githubfinder.adapter.RepositoryListener
import pl.nataliana.githubfinder.databinding.FragmentMainBinding
import pl.nataliana.githubfinder.model.RepositoryListViewModel
import pl.nataliana.githubfinder.model.RepositoryListViewModelFactory
import timber.log.Timber

class MainFragment : Fragment() {

    private lateinit var mainAdapter: GithubRepositoryAdapter
    private val repoViewModel: RepositoryListViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentMainBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        val application = requireNotNull(this.activity).application
        val viewModelFactory = RepositoryListViewModelFactory(application)

        val repositoryListViewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(RepositoryListViewModel::class.java)

        binding.lifecycleOwner = this
        binding.repositoryListViewModel = repositoryListViewModel
        binding.searchForRepo.setOnClickListener {
            val userInput = binding.userInputRepo.text.toString().split("/").toTypedArray()
            val searchedUser = userInput.first()
            val searchedRepo = userInput.last()
            setSearchButton(searchedUser, searchedRepo)
        }

        mainAdapter = GithubRepositoryAdapter(RepositoryListener { userLogin, repoName ->
            setClick(userLogin, repoName)
        })
        binding.recyclerView.adapter = mainAdapter

        checkIfRecyclerViewIsEmpty()
        activity?.title = getString(R.string.app_name_title)

        return binding.root
    }

    private fun setSearchButton(userLogin: String, repoName: String) {
        view?.findNavController()?.navigate(
            MainFragmentDirections
                .actionMainFragmentToDetailFragment(userLogin, repoName)
        )
        Timber.i("Searched for repo: $userLogin $repoName")
        repoViewModel.onRepoDetailNavigated()
    }

    private fun checkIfRecyclerViewIsEmpty() {
        if (mainAdapter.itemCount != 0) {
            empty_view_layout.visibility = View.GONE
            previously_searched_text_view.visibility = View.VISIBLE
        }
    }

    private fun setClick(name: String, repo: String) {
        view?.findNavController()?.navigate(
            MainFragmentDirections
                .actionMainFragmentToDetailFragment(name, repo)
        )
        repoViewModel.onRepoDetailNavigated()
    }
}