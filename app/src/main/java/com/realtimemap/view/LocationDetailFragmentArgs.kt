package com.realtimemap.view

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavArgs
import com.realtimemap.domain.model.UserLocation
import com.realtimemap.navigation.NavigationDispatcherImpl
import java.io.Serializable


data class LocationDetailFragmentArgs(
    val userLocation: UserLocation
) : NavArgs {
    @Suppress("CAST_NEVER_SUCCEEDS")
    fun toBundle(): Bundle {
        val result = Bundle()
        if (Parcelable::class.java.isAssignableFrom(UserLocation::class.java)) {
            result.putParcelable("locationInfo", this.userLocation as Parcelable)
        } else if (Serializable::class.java.isAssignableFrom(UserLocation::class.java)) {
            result.putSerializable("locationInfo", this.userLocation as Serializable)
        } else {
            throw UnsupportedOperationException(UserLocation::class.java.name +
                    " must implement Parcelable or Serializable or must be an Enum.")
        }
        return result
    }

    companion object {
        @JvmStatic
        fun fromBundle(bundle: Bundle): LocationDetailFragmentArgs {
            bundle.setClassLoader(UserLocation::class.java.classLoader)
            val __userLocation : UserLocation?
            if (bundle.containsKey(NavigationDispatcherImpl.LOCATION_KEY)) {
                if (Parcelable::class.java.isAssignableFrom(UserLocation::class.java) ||
                    Serializable::class.java.isAssignableFrom(UserLocation::class.java)) {
                    __userLocation = bundle.get(NavigationDispatcherImpl.LOCATION_KEY) as UserLocation?
                } else {
                    throw UnsupportedOperationException(UserLocation::class.java.name +
                            " must implement Parcelable or Serializable or must be an Enum.")
                }
                if (__userLocation == null) {
                    throw IllegalArgumentException("Argument \"locationInfo\" is marked as non-null but was passed a null value.")
                }
            } else {
                throw IllegalArgumentException("Required argument \"locationInfo\" is missing and does not have an android:defaultValue")
            }
            return LocationDetailFragmentArgs(__userLocation)
        }
    }
}
