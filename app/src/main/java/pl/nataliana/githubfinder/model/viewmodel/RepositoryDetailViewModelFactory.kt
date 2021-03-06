package pl.nataliana.githubfinder.model.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RepositoryDetailViewModelFactory(
    private val userLogin: String = "",
    private val repoName: String = ""
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RepositoryDetailViewModel::class.java)) {
            return RepositoryDetailViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}