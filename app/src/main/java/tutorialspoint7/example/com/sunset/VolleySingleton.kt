package tutorialspoint7.example.com.sunset

import android.app.Application
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.android.volley.VolleyLog.TAG



/**
 * Created by mibihi on 7/22/17.
 */
class VolleySingleton: Application() {
    override fun onCreate() {
        super.onCreate()
        var instance = this
    }
var requestQueue:RequestQueue?=null
    get(){
        if(field==null){
            return Volley.newRequestQueue(applicationContext)
        }
        return field
    }
    fun <T> addToRequestQueue(request: Request<T>){
        request.tag = TAG
        requestQueue?.add(request)
    }
    companion object {
        private val TAG = VolleySingleton::class.java.simpleName
        @get:Synchronized var instance:VolleySingleton?=null
        private set
    }
}