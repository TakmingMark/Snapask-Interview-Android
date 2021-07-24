package com.mark.snapask_interview_android.ui.user.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.mark.snapask_interview_android.databinding.AdapterUserType1Binding
import com.mark.snapask_interview_android.databinding.AdapterUserType2Binding
import com.snapask.sdk.data.User


class UserAdapter :
    PagingDataAdapter<User, UserAdapter.ViewHolder>(userComparator) {
    companion object {
        private val userComparator = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                // Id is unique.
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }

    private var context: Context? = null
    private var realItemCount=0
    private var currentType = UserType.DETAIL

    fun changeType() {
        currentType = if (currentType == UserType.DETAIL)
            UserType.SIMPLE
        else
            UserType.DETAIL

        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (context == null)
            context = parent.context

        return ViewHolder.create(parent, viewType)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        when (getItemViewType(position)) {
            UserType.DETAIL.ordinal -> {
                val item = getItem(position) ?: return
                holder.bind(item)
            }
            UserType.SIMPLE.ordinal -> {
                val realPosition = position*4

                when (realItemCount - realPosition) {
                    1 -> holder.bind(getItem(realPosition))
                    2 -> holder.bind(
                        getItem(realPosition),
                        getItem(realPosition + 1)
                    )
                    3 -> holder.bind(
                        getItem(realPosition),
                        getItem(realPosition + 1),
                        getItem(realPosition + 2)
                    )
                    else -> holder.bind(
                        getItem(realPosition),
                        getItem(realPosition + 1),
                        getItem(realPosition + 2),
                        getItem(realPosition + 3)
                    )
                }
            }
        }
    }

    override fun getItemCount(): Int {
        realItemCount=super.getItemCount()
        return if (currentType == UserType.DETAIL)
            realItemCount
        else
            realItemCount / 4 + if (realItemCount % 4 > 0) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return currentType.ordinal
    }

    class ViewHolder(private val viewBinding: ViewBinding, private val userType: Int, private val context: Context) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(data1: User?, data2: User?, data3: User?, data4: User?) {
            bind(data1, data2, data3)
            bind(data4, viewBinding, UserTypeData.DATA4)
        }

        fun bind(data1: User?, data2: User?, data3: User?) {
            bind(data1, data2)
            bind(data3, viewBinding, UserTypeData.DATA3)
        }

        fun bind(data1: User?, data2: User?) {
            bind(data1)
            bind(data2, viewBinding, UserTypeData.DATA2)
        }

        fun bind(data1: User?) {
            bind(data1, viewBinding, UserTypeData.DATA1)
        }


        private fun bind(data: User?, viewBinding: ViewBinding, userTypeData: UserTypeData) {
            if (data == null) return

            when (userType) {
                UserType.DETAIL.ordinal -> {
                    val viewBinding = viewBinding as AdapterUserType1Binding
                    viewBinding.idContentTextView.text = data.id.toString()
                    viewBinding.loginContentTextView.text = data.login
                    viewBinding.urlContentTextView.text = data.htmlUrl
                    loadImageUrl(viewBinding.imageView, data.avatarUrl)
                }
                UserType.SIMPLE.ordinal -> {
                    val viewBinding = viewBinding as AdapterUserType2Binding

                    when (userTypeData) {
                        UserTypeData.DATA1 -> {
                            loadImageUrl(viewBinding.data1ImageView, data.avatarUrl)
                            viewBinding.data1TextView.text = data.login
                        }
                        UserTypeData.DATA2 -> {
                            loadImageUrl(viewBinding.data2ImageView, data.avatarUrl)
                            viewBinding.data2TextView.text = data.login
                        }
                        UserTypeData.DATA3 -> {
                            loadImageUrl(viewBinding.data3ImageView, data.avatarUrl)
                            viewBinding.data3TextView.text = data.login
                        }
                        UserTypeData.DATA4 -> {
                            loadImageUrl(viewBinding.data4ImageView, data.avatarUrl)
                            viewBinding.data4TextView.text = data.login
                        }
                    }
                }
            }
        }

        private fun loadImageUrl(
            imageView: ImageView,
            url: String
        ) {
            Glide
                .with(context)
                .load(url)
                .centerCrop()
                .into(imageView)
        }

        fun unbind() {

        }


        companion object {
            fun create(
                parent: ViewGroup,
                userType: Int
            ): ViewHolder {
                val viewBinding = when (userType) {
                    UserType.DETAIL.ordinal -> {
                        AdapterUserType1Binding
                            .inflate(LayoutInflater.from(parent.context), parent, false)
                    }
                    UserType.SIMPLE.ordinal -> {
                        AdapterUserType2Binding
                            .inflate(LayoutInflater.from(parent.context), parent, false)
                    }
                    else -> {
                        AdapterUserType1Binding
                            .inflate(LayoutInflater.from(parent.context), parent, false)
                    }
                }

                return ViewHolder(viewBinding, userType, parent.context)
            }
        }

        enum class UserTypeData {
            DATA1,
            DATA2,
            DATA3,
            DATA4
        }
    }

    enum class UserType {
        DETAIL,
        SIMPLE
    }
}