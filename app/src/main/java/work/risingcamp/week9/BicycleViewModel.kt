package work.risingcamp.week9

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import work.risingcamp.week9.NetWorkClient.bicycleNetWork
import work.risingcamp.week9.data.Bicycle

class BicycleViewModel: ViewModel(){

    private val _bicycleData = MutableLiveData<Bicycle>()
    val bicycleData: LiveData<Bicycle>
        get() = _bicycleData

    fun communicateNetWork(param: HashMap<String, String>) = viewModelScope.launch(Dispatchers.IO){

        val responseData = bicycleNetWork.getBicycle(param)

        withContext(Dispatchers.Main) {
            _bicycleData.value = responseData
        }

    }

}