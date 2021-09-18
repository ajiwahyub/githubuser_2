package com.submisi2.githubuser

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.submisi2.githubuser.adapter.SectionsAdapter
import com.submisi2.githubuser.model.UserDetail
import com.submisi2.githubuser.model.UserList
import com.submisi2.githubuser.viewmodel.DetailViewmodel
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var detailUserViewModel: DetailViewmodel
    private lateinit var username: String

    companion object {
        const val PUT_EXTRA = "extra_parcelable"
    }
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        showLoadingUserDetails(true)
        getSelectedUser()
        setActionBar(username)
        pagerConfig()

        detailUserViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewmodel::class.java)
        detailUserViewModel.setDetailItems(username)
        detailUserViewModel.getDetailItems().observe(this,{ detailItems ->
            if (detailItems != null) {
                showUserDetails(detailItems)
                showLoadingUserDetails(false)
            }
        })
    }

    private fun showUserDetails(detailUserItems: UserDetail) {
        tv_detail_name.text = detailUserItems.name
        tv_detail_username.text = username
        tv_detail_company.text = detailUserItems.company
        tv_detail_location.text = detailUserItems.location
        tv_detail_repo.text = detailUserItems.repository.toString()
        tv_detail_follower.text = detailUserItems.followers.toString()
        tv_detail_following.text = detailUserItems.following.toString()
        Glide.with(this)
            .load(detailUserItems.avatar)
            .into(detail_avatar)
    }

    private fun pagerConfig() {
        val pager = SectionsAdapter(this, supportFragmentManager)
        pager?.setUsername(username)
        view_pager.adapter = pager
        tabs.setupWithViewPager(view_pager)

        supportActionBar?.elevation = 0f
    }

    private fun getSelectedUser() {
        val user = intent.getParcelableExtra<UserList>(PUT_EXTRA) as UserList
        username = user.username.toString()
    }

    private fun showLoadingUserDetails(state: Boolean) {
        if (state) detail_toolbarprogress.visibility = View.VISIBLE
        else detail_toolbarprogress.visibility = View.GONE
    }

    private fun setActionBar(title: String) {
        val icon = ResourcesCompat.getDrawable(resources, R.drawable.logo, null)?.toBitmap()
         val fixedIcon = BitmapDrawable(resources,
            icon.let { Bitmap.createScaledBitmap(it!!, 60, 60, true) })
        supportActionBar?.setHomeAsUpIndicator(fixedIcon)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (supportActionBar != null) {
            (supportActionBar as ActionBar).title = title
        }
    }
}