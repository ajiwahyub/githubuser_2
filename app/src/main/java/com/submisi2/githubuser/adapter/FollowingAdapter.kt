package com.submisi2.githubuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.submisi2.githubuser.R
import com.submisi2.githubuser.model.UserList
import kotlinx.android.synthetic.main.item_user.view.*

class FollowingAdapter: RecyclerView.Adapter<FollowingAdapter.RecyclerViewHolder>() {

    private val mData = ArrayList<UserList>()
    fun setData(items: ArrayList<UserList>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) =
        holder.bind(mData[position])

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(list: UserList) {
            with(itemView) {
                tv_username.text = list.username
                Glide.with(itemView.context)
                    .load(list.avatar)
                    .apply { RequestOptions().override(100, 100) }
                    .into(ivuser_avatar)
            }
        }
    }

}