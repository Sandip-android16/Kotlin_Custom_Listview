package com.example.kotlin_custom_listview

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import java.util.ArrayList

class ListViewDemo : AppCompatActivity() {

    var listview_demo:ListView?=null
    lateinit var progressBar1: ProgressBar
    val adapter:CustomAdapter?=null

    var list_type: ArrayList<Model> =
        ArrayList<Model>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view_demo)

        listview_demo=findViewById(R.id.listview_demo);
        progressBar1=findViewById(R.id.progressBar);

        progressBar1.visibility = View.GONE


        GetBuilding_DDL()


    }

    fun GetBuilding_DDL() {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url: String = "http://www.ebs-applications.com:2000/api/mobileapp/GetCheckinList?CompanyID=2038"
        progressBar1.visibility = View.VISIBLE

        // Request a string response from the provided URL.
        val stringReq = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->

                var strResp = response.toString()

                val jsonArray = JSONArray(response)
                var str_user: String = ""
                for (i in 0 until jsonArray.length()) {
                    progressBar1.visibility = View.GONE

                    var json_objectdetail: JSONObject =jsonArray.getJSONObject(i)
                    var model:Model= Model();
                    model.id=json_objectdetail.getString("ReservationCode")
                    model.name=json_objectdetail.getString("EmployeeName")
                    model.email=json_objectdetail.getString("CheckInDate")
                    list_type.add(model)
                }
                listview_demo!!.adapter = CustomAdapter1(applicationContext,list_type)
            },
            Response.ErrorListener {  "That didn't work!" })
        queue.add(stringReq)
    }










    class CustomAdapter1(context: Context,arrayListDetails:ArrayList<Model>) : BaseAdapter(){

        private val layoutInflater: LayoutInflater
        private val arrayListDetails:ArrayList<Model>

        init {
            this.layoutInflater = LayoutInflater.from(context)
            this.arrayListDetails=arrayListDetails
        }

        override fun getCount(): Int {
            return arrayListDetails.size
        }

        override fun getItem(position: Int): Any {
            return arrayListDetails.get(position)
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
            val view: View?
            val listRowHolder: ListRowHolder
            if (convertView == null) {
                view = this.layoutInflater.inflate(R.layout.adapter_layout, parent, false)
                listRowHolder = ListRowHolder(view)
                view.tag = listRowHolder
            } else {
                view = convertView
                listRowHolder = view.tag as ListRowHolder
            }

            listRowHolder.tvName.text = arrayListDetails.get(position).name
            listRowHolder.tvEmail.text = arrayListDetails.get(position).email
            listRowHolder.tvId.text = arrayListDetails.get(position).id
            return view
        }
    }

    private class ListRowHolder(row: View?) {
        public val tvName: TextView
        public val tvEmail: TextView
        public val tvId: TextView
        public val linearLayout: LinearLayout

        init {
            this.tvId = row?.findViewById<TextView>(R.id.tvId) as TextView
            this.tvName = row?.findViewById<TextView>(R.id.tvName) as TextView
            this.tvEmail = row?.findViewById<TextView>(R.id.tvEmail) as TextView
            this.linearLayout = row?.findViewById<LinearLayout>(R.id.linearLayout) as LinearLayout
        }
    }






}