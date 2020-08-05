package com.androiddevs.mvvmnewsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.androiddevs.mvvmnewsapp.Constants
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.ui.news.BreakingNewsFragment
import com.androiddevs.mvvmnewsapp.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_breaking_news.*


class MainActivity : AppCompatActivity() {

    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(
            NewsViewModel::class.java)
        bottomNavigationView.setupWithNavController(newsNavHostFragment.findNavController())
        //双击tab
        bottomNavigationView.setOnNavigationItemReselectedListener {
            when(it.itemId) {
                R.id.breakingNewsFragment -> {
                    //回到列表顶部
                    Constants.breakingNewsPosition = 0
                    (newsNavHostFragment.childFragmentManager.fragments[0] as BreakingNewsFragment).rvBreakingNews.scrollToPosition(0)
                }
            }
        }
    }
}
