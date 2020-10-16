package com.noice.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Banner(
    var image_url: String? = null,
    var description: String? = null,
    var title: String? = null,
    var author: String? = null,
    var id: Int? = 0,
    var view: Int? = 0,
    var subscriber: Int? = 0
): Parcelable