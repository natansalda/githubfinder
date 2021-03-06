package pl.nataliana.githubfinder.model.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job
import pl.nataliana.githubfinder.model.GithubRepository
import pl.nataliana.githubfinder.model.RepositoryCommits

class RepositoryDetailViewModel : ViewModel() {

    private val viewModelJob = Job()

    private val _repositoryCommits = MutableLiveData<RepositoryCommits>()
    val repositoryCommits: LiveData<RepositoryCommits>
        get() = _repositoryCommits

    private val _githubRepository = MutableLiveData<GithubRepository>()
    val githubRepository: LiveData<GithubRepository>
        get() = _githubRepository

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}