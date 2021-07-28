package com.cornershop.counterstest.presentation.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PeopleToShare(val id: Int, @DrawableRes val imageResId: Int, val name: String): Parcelable {
    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<PeopleToShare>() {
            override fun areItemsTheSame(oldItem: PeopleToShare, newItem: PeopleToShare): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PeopleToShare, newItem: PeopleToShare): Boolean {
                return oldItem == newItem
            }
        }
    }
}
