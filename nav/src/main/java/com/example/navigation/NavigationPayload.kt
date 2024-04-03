package com.example.navigation

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel

interface NavigationPayload : Parcelable {
    @IgnoredOnParcel
    val payloadKey: String
}
