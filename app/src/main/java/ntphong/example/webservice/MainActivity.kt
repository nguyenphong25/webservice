package ntphong.example.webservice

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.JsonReader
import android.util.Log
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StringReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    var urlgetdata :String="http://192.168.1.206/webservice/getdata.php"
    var mang : ArrayList<String> = ArrayList()
    var adapterKH : ArrayAdapter<String>? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getdata().execute(urlgetdata)
        adapterKH= ArrayAdapter(this,android.R.layout.simple_list_item_1,mang)
        lss.adapter=adapterKH

    }
    inner class getdata:AsyncTask<String,Void,String>(){
        override fun doInBackground(vararg params: String?): String {
            var content : StringBuilder = StringBuilder()
            var url : URL = URL(params[0])
            var urlConnection  : HttpURLConnection = url.openConnection() as HttpURLConnection
            val inputStreamReader : InputStreamReader = InputStreamReader(urlConnection.inputStream)
            val bufferedReader :BufferedReader = BufferedReader(inputStreamReader)
            var line :String=""
            try {
                do {
                    line= bufferedReader.readLine()
                    if (line!=null){
                        content.append(line)
                    }
                }while (line!= null)
                bufferedReader.close()
            }catch (e:Exception){
                Log.d("aa",e.toString())
            }
            return content.toString()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            var jsonArray : JSONArray = JSONArray(result)
            var ten :String = ""
            var hp :String=""
            for (khoaHoc in 0 ..jsonArray.length()-1){
                var objectKH : JSONObject = jsonArray.getJSONObject(khoaHoc)
                ten= objectKH.getString("tenkh")

                hp=objectKH.getString("hocphi") 
                mang.add(ten+"-"+hp)
            }
        }

    }
}
