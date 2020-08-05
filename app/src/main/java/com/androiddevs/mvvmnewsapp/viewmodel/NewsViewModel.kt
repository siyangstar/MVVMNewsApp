package com.androiddevs.mvvmnewsapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.androiddevs.mvvmnewsapp.model.Article
import com.androiddevs.mvvmnewsapp.model.NewsResponse
import com.androiddevs.mvvmnewsapp.model.Resource
import com.androiddevs.mvvmnewsapp.reporitory.NewsRepository
import com.androiddevs.mvvmnewsapp.util.InternetUtils
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class NewsViewModel(application: Application) : AndroidViewModel(application) {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1

    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1

    init {
        NewsRepository.init(application)
        getBreakingNews("us")
    }

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        try {
            val internetUtils = InternetUtils(getApplication())
            if (internetUtils.hasInternet()) {
                val response = NewsRepository.getBreakingNews(countryCode, breakingNewsPage)
                breakingNews.postValue(handleBreakingNewsResponse(response))
            } else {
                breakingNews.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> breakingNews.postValue(Resource.Error("Network Failure"))
                else -> breakingNews.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    fun searchNews(searchQuary: String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        try {
            val internetUtils = InternetUtils(getApplication())
            if (internetUtils.hasInternet()) {
                val response = NewsRepository.searchNews(searchQuary, searchNewsPage)
                searchNews.postValue(handleSearchNewsResponse(response))
            } else {
                searchNews.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> searchNews.postValue(Resource.Error("Network Failure"))
                else -> searchNews.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        NewsRepository.upsert(article)
    }

    fun getSavedNews() = NewsRepository.getSavedNews()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        NewsRepository.deleteArticle(article)
    }

}