package com.example.kotlin_custom_listview

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.kotlin_custom_listview.Incident_Type_Spinner_Model
import com.example.kotlin_custom_listview.R

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class Base : AppCompatActivity() {
    var list_type: ArrayList<Incident_Type_Spinner_Model> =
        ArrayList<Incident_Type_Spinner_Model>()
    var building_list: ArrayList<Incident_Type_Spinner_Model> =
        ArrayList<Incident_Type_Spinner_Model>()
    var floor_list: ArrayList<Incident_Type_Spinner_Model> =
        ArrayList<Incident_Type_Spinner_Model>()
    var room_id_list: ArrayList<Incident_Type_Spinner_Model> =
        ArrayList<Incident_Type_Spinner_Model>()
    var bed_list: ArrayList<Incident_Type_Spinner_Model> =
        ArrayList<Incident_Type_Spinner_Model>()
    var lin1: LinearLayout? = null
    var images1: ImageView? = null
    var SELECT_PICTURE = 1
    var incident_type: Spinner? = null
    var building_id: Spinner? = null
    var floor_id: Spinner? = null
    var room_id: Spinner? = null
    var bed_iD: Spinner? = null
    var CompanyID: String? = null
    var Server_ip: String? = null
    var UserName: String? = null
    var back: ImageView? = null
    var incident_description: EditText? = null
    var spinner_id: String? = null
    var spinner_name: String? = null
    var BuildingID: String? = null
    var BuildingName: String? = null
    var FloorID: String? = null
    var FloorName: String? = null
    var RoomID: String? = null
    var RoomName: String? = null
    var BedID: String? = null
    var BedName: String? = null
    var progressbar: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_incident__form)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MASK_ADJUST)
        val myPrefs = getSharedPreferences(
            "LoginPage",
            Context.MODE_PRIVATE
        ) //Activity1.class
        CompanyID = myPrefs.getString("CompanyID", "")
        Server_ip = myPrefs.getString("Server_ip", "")
        UserName = myPrefs.getString("name", "")
        progressbar = findViewById(R.id.progressbar)
        incident_type = findViewById(R.id.incident_type)
        back = findViewById(R.id.back)
        building_id = findViewById(R.id.building_id)
        floor_id = findViewById(R.id.floor_id)
        room_id = findViewById(R.id.room_id)
        bed_iD = findViewById(R.id.bed_id)
        incident_description = findViewById(R.id.incident_description)
        /*  incident_type.setOnItemSelectedListener(object : OnItemSelectedListener {
              override fun onItemSelected(
                  parent: AdapterView<*>?,
                  view: View,
                  position: Int,
                  id: Long
              ) {
                  spinner_id = list_type[position].id
                  spinner_name = list_type[position].incidentType
                  Log.e("aaa", "Id: $spinner_id,  Name :$spinner_name")
              }

              override fun onNothingSelected(parent: AdapterView<*>?) {}
          })
          building_id.setOnItemSelectedListener(object : OnItemSelectedListener {
              override fun onItemSelected(
                  parent: AdapterView<*>?,
                  view: View,
                  position: Int,
                  id: Long
              ) {
                  BuildingID = building_list[position].id
                  BuildingName = building_list[position].incidentType
                  Log.e("aaa", "Id: $BuildingID,  Name :$BuildingName")
              }

              override fun onNothingSelected(parent: AdapterView<*>?) {}
          })
          floor_id.setOnItemSelectedListener(object : OnItemSelectedListener {
              override fun onItemSelected(
                  parent: AdapterView<*>?,
                  view: View,
                  position: Int,
                  id: Long
              ) {
                  FloorID = floor_list[position].id
                  FloorName = floor_list[position].incidentType
                  Log.e("aaa", "Id: $FloorID,  Name :$FloorName")
              }

              override fun onNothingSelected(parent: AdapterView<*>?) {}
          })
          room_id.setOnItemSelectedListener(object : OnItemSelectedListener {
              override fun onItemSelected(
                  parent: AdapterView<*>?,
                  view: View,
                  position: Int,
                  id: Long
              ) {
                  RoomID = room_id_list[position].id
                  RoomName = room_id_list[position].incidentType
                  Log.e("aaa", "Id: $RoomID,  Name :$RoomName")
              }

              override fun onNothingSelected(parent: AdapterView<*>?) {}
          })
          bed_iD.setOnItemSelectedListener(object : OnItemSelectedListener {
              override fun onItemSelected(
                  parent: AdapterView<*>?,
                  view: View,
                  position: Int,
                  id: Long
              ) {
                  BedID = bed_list[position].id
                  BedName = bed_list[position].incidentType
                  Log.e("aaa", "Id: $BedID,  Name :$BedName")
              }

              override fun onNothingSelected(parent: AdapterView<*>?) {}
          })*/
        // back.setOnClickListener(View.OnClickListener { onBackPressed() })
        lin1 = findViewById(R.id.lin1)
        /*  lin1.setOnClickListener(View.OnClickListener {
              val Building_Id = BuildingID
              val Floor_id = FloorID
              val Room_ID = RoomID
              val Bed_ID = BedID
              val Incident_Description =
                  incident_description.getText().toString().trim { it <= ' ' }
              InsertIncidentRequest(
                  CompanyID,
                  Building_Id,
                  Floor_id,
                  Room_ID,
                  Bed_ID,
                  spinner_id,
                  Incident_Description,
                  UserName
              )
          })*/
        IncidentTypes()
        GetBuilding_DDL()
        GetFloor_DDL()
        GetRoom_DDL()
        GetBed_DDL()
    }

    fun pickImage() {
        /*Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/ *");
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("outputX", 256);
        intent.putExtra("outputY", 256);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("return-data", true);
        startActivityForResult(i,1);*/
        val i = Intent()
        i.type = "image/*"
        i.putExtra("crop", "true")
        i.putExtra("scale", true)
        i.putExtra("outputX", 256)
        i.putExtra("outputY", 256)
        i.putExtra("aspectX", 1)
        i.putExtra("aspectY", 1)
        i.putExtra("return-data", true)
        i.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                val selectedImageUri = data!!.data
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    val path = getPathFromURI(selectedImageUri)
                    Log.i("IMAGE PATH TAG", "Image Path : $path")
                    // Set the image in ImageView
                    images1!!.setImageURI(selectedImageUri)
                }
            }
        }
    }

    private fun getPathFromURI(contentUri: Uri): String? {
        var res: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor =
            contentResolver.query(contentUri, proj, null, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                res = cursor.getString(column_index)
            }
            cursor.close()
        } else {
            Toast.makeText(this, "Cursor null$proj", Toast.LENGTH_SHORT).show()
        }
        return res
    }

    inner class Incident_Type_SpinnerAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return list_type.size
        }

        override fun getItem(position: Int): Any {
            return list_type[position]
        }

        override fun getItemId(position: Int): Long {
            return list_type.size.toLong()
        }

        override fun getView(
            position: Int,
            convertView: View,
            parent: ViewGroup
        ): View {
            var convertView = convertView
            val type: TextView
            type = convertView.findViewById(R.id.txt_incident_type)
            type.setText(list_type[position].incidentType)
            return convertView
        }
    }

    inner class BuildingID_SpinnerAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return building_list.size
        }

        override fun getItem(position: Int): Any {
            return building_list[position]
        }

        override fun getItemId(position: Int): Long {
            return building_list.size.toLong()
        }

        override fun getView(
            position: Int,
            convertView: View,
            parent: ViewGroup
        ): View {
            var convertView = convertView
            val type: TextView
            type = convertView.findViewById(R.id.txt_incident_type)
            type.setText(building_list[position].incidentType)
            return convertView
        }
    }

    inner class GetFloor_DDL_SpinnerAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return floor_list.size
        }

        override fun getItem(position: Int): Any {
            return floor_list[position]
        }

        override fun getItemId(position: Int): Long {
            return floor_list.size.toLong()
        }

        override fun getView(
            position: Int,
            convertView: View,
            parent: ViewGroup
        ): View {
            var convertView = convertView
            val type: TextView
            type = convertView.findViewById(R.id.txt_incident_type)
            type.setText(floor_list[position].incidentType)
            return convertView
        }
    }

    inner class GetRoom_DDL_SpinnerAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return room_id_list.size
        }

        override fun getItem(position: Int): Any {
            return room_id_list[position]
        }

        override fun getItemId(position: Int): Long {
            return room_id_list.size.toLong()
        }

        override fun getView(
            position: Int,
            convertView: View,
            parent: ViewGroup
        ): View {
            var convertView = convertView
            val type: TextView
            type = convertView.findViewById(R.id.txt_incident_type)
            type.setText(room_id_list[position].incidentType)
            return convertView
        }
    }

    inner class GetBed_DDL_SpinnerAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return bed_list.size
        }

        override fun getItem(position: Int): Any {
            return bed_list[position]
        }

        override fun getItemId(position: Int): Long {
            return bed_list.size.toLong()
        }

        override fun getView(
            position: Int,
            convertView: View,
            parent: ViewGroup
        ): View {
            var convertView = convertView
            val type: TextView
            type = convertView.findViewById(R.id.txt_incident_type)
            type.setText(bed_list[position].incidentType)
            return convertView
        }
    }







    fun IncidentTypes() {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url: String = "http://www.ebs-applications.com:2000/api/mobileapp/GetIncidentTypes?CompanyID=2038"

        // Request a string response from the provided URL.
        val stringReq = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->

                var strResp = response.toString()

                val jsonArray = JSONArray(response)
                var str_user: String = ""
                for (i in 0 until jsonArray.length()) {
                    val jresponse = jsonArray.getJSONObject(i)
                    val IncidentType = jresponse.getString("IncidentType")
                    val ID = jresponse.getInt("ID").toString()
                    val model =
                        Incident_Type_Spinner_Model(IncidentType, ID)
                    list_type.add(model)
                }
                incident_type!!.adapter = Incident_Type_SpinnerAdapter()
            },
            Response.ErrorListener {  "That didn't work!" })
        queue.add(stringReq)
    }

    fun GetBuilding_DDL() {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url: String = "http://www.ebs-applications.com:2000/api/mobileapp/GetBuilding_DDL?CompanyID=2038"

        // Request a string response from the provided URL.
        val stringReq = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->

                var strResp = response.toString()

                val jsonArray = JSONArray(response)
                var str_user: String = ""
                for (i in 0 until jsonArray.length()) {
                    val jresponse = jsonArray.getJSONObject(i)
                    val IncidentType = jresponse.getString("BuildingName")
                    val ID = jresponse.getInt("BuildingID").toString()
                    val model =
                        Incident_Type_Spinner_Model(IncidentType, ID)
                    building_list.add(model)
                }
                building_id!!.adapter = BuildingID_SpinnerAdapter()
            },
            Response.ErrorListener {  "That didn't work!" })
        queue.add(stringReq)
    }

    fun GetFloor_DDL() {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url: String = "http://www.ebs-applications.com:2000/api/mobileapp/GetFloor_DDL?CompanyID=2038"

        // Request a string response from the provided URL.
        val stringReq = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->

                var strResp = response.toString()

                val jsonArray = JSONArray(response)
                var str_user: String = ""
                for (i in 0 until jsonArray.length()) {
                    val jresponse = jsonArray.getJSONObject(i)
                    val IncidentType = jresponse.getString("FloorName")
                    val ID = jresponse.getInt("FloorID").toString()
                    val model =
                        Incident_Type_Spinner_Model(IncidentType, ID)
                    floor_list.add(model)
                }
                floor_id!!.adapter = GetFloor_DDL_SpinnerAdapter()
            },
            Response.ErrorListener {  "That didn't work!" })
        queue.add(stringReq)
    }

    fun GetRoom_DDL() {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url: String = "http://www.ebs-applications.com:2000/api/mobileapp/GetRoom_DDL?CompanyID=2038"

        // Request a string response from the provided URL.
        val stringReq = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->

                var strResp = response.toString()

                val jsonArray = JSONArray(response)
                var str_user: String = ""
                for (i in 0 until jsonArray.length()) {
                    val jresponse = jsonArray.getJSONObject(i)
                    val IncidentType = jresponse.getString("RoomName")
                    val ID = jresponse.getInt("RoomID").toString()
                    val model =
                        Incident_Type_Spinner_Model(IncidentType, ID)
                    room_id_list.add(model)
                }
                room_id!!.adapter = GetRoom_DDL_SpinnerAdapter()
            },
            Response.ErrorListener {  "That didn't work!" })
        queue.add(stringReq)
    }
    fun GetBed_DDL() {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url: String = "http://www.ebs-applications.com:2000/api/mobileapp/GetBed_DDL?CompanyID=2038"

        // Request a string response from the provided URL.
        val stringReq = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->

                var strResp = response.toString()

                val jsonArray = JSONArray(response)
                var str_user: String = ""
                for (i in 0 until jsonArray.length()) {
                    val jresponse = jsonArray.getJSONObject(i)
                    val IncidentType = jresponse.getString("BedName")
                    val ID = jresponse.getInt("BedID").toString()
                    val model =
                        Incident_Type_Spinner_Model(IncidentType, ID)
                    bed_list.add(model)
                }
                bed_iD!!.adapter = GetBed_DDL_SpinnerAdapter()
            },
            Response.ErrorListener {  "That didn't work!" })
        queue.add(stringReq)
    }



}