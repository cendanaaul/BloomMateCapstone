package com.cencen.bloommatecapstone.data.response

import android.os.Parcelable
import com.cencen.bloommatecapstone.data.model.DataFlower
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class PredictResponse (
    @SerializedName("status"  ) var status  : String? = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : @RawValue DataFlower? = null
): Parcelable

