package work.risingcamp.week9.data

import android.view.inspector.IntFlagMapping
import com.google.gson.annotations.SerializedName

data class Bicycle(val response:BicycleResponse)

data class BicycleResponse(
    @SerializedName("body")
    val bicycleBody : BicycleBody,
    @SerializedName("header")
    val bicycleHeader : BicycleHeader
)
data class BicycleBody(
    val totalCount:Int,
    val bicycleItem : MutableList<BicycleItem>?,
    val pageNo : Int,
    val numOfRows:Int
)
data class BicycleHeader(
    val resultCode:String,
    val resultMsg:String
)
data class BicycleItem(
    val rackTotCnt:String,
    val stationName : String,
    val parkingBikeTotCnt: String,
    val shared : String,
    val stationLatitude : String,
    val stationLongitude : String,
    val stationId : String
)