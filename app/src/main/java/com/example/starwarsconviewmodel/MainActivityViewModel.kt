package com.example.starwarsconviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.*
import okhttp3.*
import java.io.IOException

class MainActivityViewModel: ViewModel() {

    var  planetaResponse= PlanetaResponse(mutableListOf())

    private val _isVisible by lazy { MediatorLiveData<Boolean>() }//_que sea privada y que no sea accesible lazy si no la usas no se crea la variable
    val isVisible: LiveData<Boolean>
        get() = _isVisible

    private val _responsePlaneta by lazy { MediatorLiveData<PlanetaResponse>() }
    val responsePlaneta: LiveData<PlanetaResponse>
        get() = _responsePlaneta

    private val _responseText by lazy { MediatorLiveData<String>() }
    val responseText: LiveData<String>
        get() = _responseText

    suspend fun setResponseTextInMainThread(value: String) = withContext(Dispatchers.Main) {
        _responseText.value = value
    }

    suspend fun setIsVisibleInMainThread(value: Boolean) = withContext(Dispatchers.Main) {
        _isVisible.value = value
    }

    suspend fun setResponseUsuarioInMainThread(planetaResponse: PlanetaResponse) = withContext(Dispatchers.Main) {
        _responsePlaneta.value =planetaResponse //mandarlo al hilo principal
    }

    fun infoplanetas() {
        viewModelScope.launch {

            withContext(Dispatchers.IO) {
                setIsVisibleInMainThread(true)

                val client = OkHttpClient()

                val request = Request.Builder()
                request.url("http://swapi.dev/api/planets/")


                val call = client.newCall(request.build())
                call.enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        println(e.toString())
                        CoroutineScope(Dispatchers.Main).launch {
                            delay(2000)
                            setResponseTextInMainThread("Algo ha ido mal")
                            setIsVisibleInMainThread(false)
                        }

                    }

                    override fun onResponse(call: Call, response: Response) {
                        println(response.toString())
                        response.body?.let { responseBody ->

                            val body = responseBody.string()
                            println(body)
                            val gson = Gson()

                           planetaResponse = gson.fromJson(body, PlanetaResponse::class.java)

                            println(planetaResponse)


                            CoroutineScope(Dispatchers.Main).launch {
                                delay(2000)
                                setResponseUsuarioInMainThread(planetaResponse)
                                setIsVisibleInMainThread(false)
                            }
                        }
                    }
                })
            }
        }
    }

}
