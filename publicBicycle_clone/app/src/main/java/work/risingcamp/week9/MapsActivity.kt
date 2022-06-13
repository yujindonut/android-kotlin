package work.risingcamp.week9

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationRequest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import android.os.Looper
//import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import okhttp3.*
import org.json.JSONObject
import work.risingcamp.week9.databinding.ActivityMapsBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

class MapsActivity : AppCompatActivity(),
    GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener,
    OnMapReadyCallback, GoogleMap.OnMarkerClickListener{
    lateinit var mLastLocation: Location // 위치 값을 가지고 있는 객체
    internal lateinit var mLocationRequest: LocationRequest // 위치 정보 요청의 매개변수를 저장하는
    private val REQUEST_PERMISSION_LOCATION = 10
//    private lateinit var locationCallback: LocationCallback
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val bicycleViewModel by viewModels<BicycleViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // 쓰레드 생성
        val thread = NetworkThread()
        thread.start()
        thread.join()

//        mMap.setOnMarkerClickListener(this)
//        locationCallback = object : LocationCallback() {
//            override fun onLocationResult(locationResult: LocationResult?) {
//                locationResult ?: return
//                for (location in locationResult.locations){
//                    // Update UI with location data
//                    // ...
//                }
//            }
//        }
//        mLocationRequest =  LocationRequest.create().apply {
//
//            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//
//        }


//      MVVP모델
//        bicycleViewModel.communicateNetWork(setUpBicycleParameter())
//        observeBicycleData()

//        okhttp3
//        //post
//        val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
//        var url = "http://openapi.seoul.go.kr:8088/6d4748497a62626234384854785472/json/bikeList/1/5/"
////        var url = "http://openapi.seoul.go.kr:8088/" + getString(R.string.bicycle_api_key) + "/json/bikeList/1/5/"
//        val client = OkHttpClient()
//
////        //body로 넘길 json에 필요한 것들 넣기 (네이버 API 참고)
//        val json = JSONObject()
////        json.put("source", "ko")
////        json.put("target", "en")
////        json.put("text", "만나서 반가워")
////
//        val body = RequestBody.create(JSON, json.toString())
//        val request = Request.Builder()
////            .header("X-Naver-Client-Id", "Su1zPHvcoLrA49Cd2uMF")
////            .addHeader("X-Naver-Client-Secret", "H17GVymjiN")
//            .url(url)
//            .post(body)
//            .build()
//
//        val response = client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                TODO("Not yet implemented")
//            }
//            // main thread말고 별도의 thread에서 실행해야 함.
//            override fun onResponse(call: Call, response: Response) {
//                Thread{
//                    var str = response.body?.string()
//                    if (str != null) {
//                        Log.d("bicycle", str)
//                    }
//                    println(str)
//                }.start()
//            }
//        })

    }
    // 네트워크를 이용할 때는 쓰레드를 사용해서 접근해야 함
    inner class NetworkThread: Thread(){
        override fun run() {

            // 키 값
            val key = "키값"

            // API 정보를 가지고 있는 주소
            val site="http://openapi.seoul.go.kr:8088/"+ getString(R.string.bicycle_api_key)+"/json/bikeList/850/900/"
//            val site = "http://api.visitkorea.or.kr/openapi/service/rest/GoCamping/basedList?serviceKey="+key+pageNo+numOfRows+MobileOS+MobileApp+"&_type=json"

            val url = URL(site)
            val conn = url.openConnection()
            val input = conn.getInputStream()
            val isr = InputStreamReader(input)
            // br: 라인 단위로 데이터를 읽어오기 위해서 만듦
            val br = BufferedReader(isr)

            // Json 문서는 일단 문자열로 데이터를 모두 읽어온 후, Json에 관련된 객체를 만들어서 데이터를 가져옴
            var str: String? = null
            val buf = StringBuffer()

            do{
                str = br.readLine()

                if(str!=null){
                    buf.append(str)
                }
            }while (str!=null)

            // 전체가 객체로 묶여있기 때문에 객체형태로 가져옴
            val root = JSONObject(buf.toString())
//            val totalCount = Integer.parseInt(root.getJSONObject("rentBikeStatus").getJSONObject("list_total_count")
//                .toString())
            val row = root.getJSONObject("rentBikeStatus").getJSONArray("row")
//            val item = response.getJSONArray("item") // 객체 안에 있는 item이라는 이름의 리스트를 가져옴

            // 화면에 출력
            runOnUiThread {

                // 페이지 수만큼 반복하여 데이터를 불러온다.
                for(i in 0 until row.length()){

                    // 쪽수 별로 데이터를 읽는다.
                    val jObject = row.getJSONObject(i)

                    println("1.대여소이름${ JSON_Parse(jObject,"stationName")}")
                    println("2.자전거주차총건수${ JSON_Parse(jObject,"parkingBikeTotCnt")}")
                    println("3.위도${ JSON_Parse(jObject,"stationLatitude")}")
                    println("4.경도${ JSON_Parse(jObject,"stationLongitude")}")

                    val latitude : Double = "${ JSON_Parse(jObject,"stationLatitude")}".toDouble()
                    val longtitude : Double =  "${ JSON_Parse(jObject,"stationLongitude")}".toDouble()
                    // Add a marker in Sydney and move the camera
                    val item = LatLng(latitude, longtitude)
                    mMap.addMarker(MarkerOptions().position(item).title("${ JSON_Parse(jObject,"stationName")}"))
//                    mMap.moveCamera(CameraUpdateFactory.newLatLng(item))

                }
            }
        }

        // 함수를 통해 데이터를 불러온다.
        fun JSON_Parse(obj:JSONObject, data : String): String {

            // 원하는 정보를 불러와 리턴받고 없는 정보는 캐치하여 "없습니다."로 리턴받는다.
            return try {

                obj.getString(data)

            } catch (e: Exception) {
                "없습니다."
            }
        }
    }
    override fun onMarkerClick(marker: Marker): Boolean {

        // Retrieve the data from the marker.
        val clickCount = marker.tag as? Int

        // Check if a click count was set, then display the click count.
        clickCount?.let {
            val newClickCount = it + 1
            marker.tag = newClickCount
            Toast.makeText(
                this,
                "${marker.title} has been clicked $newClickCount times.",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false
    }
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val mylocation = LatLng(37.51198196, 127.08505249)
        mMap.addMarker(MarkerOptions().position(mylocation).title("잠실새내역 5번 출구 뒤"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mylocation))
        mMap.setMinZoomPreference(16F)
        mMap.setMaxZoomPreference(22F)
        // TODO: Before enabling the My Location layer, you must request
        // location permission from the user. This sample does not include
        // a request for location permission.
    if (ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        return
    }
        googleMap.isMyLocationEnabled = true
        googleMap.setOnMyLocationButtonClickListener(this)
        googleMap.setOnMyLocationClickListener(this)
    }

    override fun onMyLocationClick(location: Location) {
        Toast.makeText(this, "Current location:\n$location", Toast.LENGTH_LONG)
            .show()
    }

    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT)
            .show()
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

//    override fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
//
//        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(37.51198196, 127.08505249)
//        mMap.addMarker(MarkerOptions().position(sydney).title("잠실새내역 5번 출구 뒤"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
//    }
//    private fun observeBicycleData(){
//        bicycleViewModel.bicycleData.observe(this){
//            it?.let {
//                Log.e("Parsing Bicycle ::", it.toString())
//            }
//        }
//    }
//
//    private fun setUpBicycleParameter(): HashMap<String, String>{
//
//        return hashMapOf(
//            "serviceKey" to "BICYCLE_DECODING_SERVICE_KEY", // OPEN API 의 인증키 중 Decoding된 것을 사용
//            "returnType" to "json",
//            "numOfRows" to "100",
//            "pageNo" to "1",
//            "sidoName" to "서울",
//            "ver" to "1.0"
//        )
//
//    }
}
