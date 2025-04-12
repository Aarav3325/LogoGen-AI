import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.ViewModel
import com.aarav.aisamadhan.data.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImageViewModel : ViewModel() {

    private val _bitmap = MutableStateFlow<Bitmap?>(null)
    val bitmap: StateFlow<Bitmap?> = _bitmap.asStateFlow()
    var _error = MutableStateFlow<Boolean?>(false)
    val error: StateFlow<Boolean?> = _error.asStateFlow()

    fun generateImage(prompt: String) {
        val request = ImageGenerationRequest(prompt)
        val call = RetrofitInstance.instance.generateImage(request)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val inputStream = response.body()?.byteStream()
                    val bmp = BitmapFactory.decodeStream(inputStream)
                    _bitmap.value = bmp
                    Log.d("ImageGen", "${response.body().toString()}")
                } else {
                    Log.e("ImageGen", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                _error.value = true
                Log.e("ImageGen", "Failure: ${t.message}")
            }
        })
    }

    // Test error card on Result screen
    fun error(){
        _error.value = true
    }
}
