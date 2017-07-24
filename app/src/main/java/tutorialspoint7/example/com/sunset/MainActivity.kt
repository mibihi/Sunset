package tutorialspoint7.example.com.sunset

import android.content.Context
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.logging.Logger


class MainActivity : AppCompatActivity() {
    var url:String?=null
    var requestQueue: RequestQueue? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun GetSunset(view: View) {
        var city = etcityname.text.toString().replace(" ","")
        val url = "https://query.yahooapis.com/v1/public/yql?q=select%20astronomy.sunset%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22"+city+"%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys"
        // var request = JsonObjectRequest(Request.Method.GET, url, null, ResponseListener(), Response.ErrorListener())
MyAsyncTask().execute(url)
       println("+++++++++++++++++++++++++++++ getsunset clicked+++++++++++++++++++")
    }
    inner class MyAsyncTask: AsyncTask<String, String, String>() {
        override fun onPreExecute() {
            //before start
        }
        override fun doInBackground(vararg p0: String?): String {
            println("+++++++++++++++++++++++++++++ inside do in background+++++++++++++++++++")
          try {
                var url= URL(p0[0])
              var urlConnect = url.openConnection() as HttpURLConnection
              urlConnect.connectTimeout=7000
              var inString = CovertStreamToString(urlConnect.inputStream)
              publishProgress(inString)
              println("+++++++++++++++++++++++++++++ getsunset clicked+++++++++++++++++++"+inString)
          }catch(ex:Exception){println("+++++++++++++++++++++++++++++cant connect issue+++++++++++++++++++"+ex.message)}
            return "ok"
        }

        override fun onProgressUpdate(vararg values: String?) {
            try {
                var json=JSONObject(values[0])
                val query=json.getJSONObject("query")
                val results=query.getJSONObject("results")
                val channel=results.getJSONObject("channel")
                val astronomy=channel.getJSONObject("astronomy")
                var sunset = astronomy.getString("sunset")
                tvresult.setText("Sunset is at  "+ sunset)

            }catch(ex:Exception){println("+++++++++++++++++++++++++++++parsing issue+++++++++++++++++++"+ex.message)}
        }
        override fun onPostExecute(result: String?) {
            //after execute
        }

    }

    private fun  CovertStreamToString(inputStream: InputStream?): String{
    val bufferedReader= BufferedReader(InputStreamReader(inputStream))
        var line:String
        var allString:String=""
        try {
            do {
                line =bufferedReader.readLine()
                if(line!==null){allString+=line}
            }while (line!==null)
            inputStream?.close()
        }catch(ex:Exception){}
    return allString

    }


}

