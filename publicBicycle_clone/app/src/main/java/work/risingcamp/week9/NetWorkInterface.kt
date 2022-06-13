package work.risingcamp.week9

import retrofit2.http.GET
import retrofit2.http.QueryMap
import work.risingcamp.week9.data.Bicycle

interface NetWorkInterface {
//    @GET("getCtprvnRltmMesureDnsty") //시도별 실시간 측정정보 조회 주소
    suspend fun getBicycle(@QueryMap param: HashMap<String, String>): Bicycle
}