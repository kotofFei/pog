package com.example.pog

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Environment
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.beust.klaxon.Klaxon
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_weather.*
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.File
import java.io.IOException


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WeatherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WeatherFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var api: API = API()
    var MainCity: String = "Paris"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        MainCity = "Paris"

        var strokatexta: String = ""

        var City = if (MainCity == "") "Paris" else MainCity
        api.URL = City

        val request = Request.Builder().url(api.URL).build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) { Log.e("Error", e.toString()) }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                api.strokatexta = response!!.body()!!.string()
            }
        })
        var a = api

        while (strokatexta == "")
            strokatexta = a.strokatexta

        api.Weather.opisanie = ((JSONObject(strokatexta).getJSONArray("weather").getJSONObject(0)).get("description")).toString()
        api.Weather.anim = ((JSONObject(strokatexta).getJSONArray("weather").getJSONObject(0)).get("main")).toString()
        api.Weather.main.temperatura =  (JSONObject(strokatexta).getJSONObject("main").get("temp")).toString() + " °c"
        api.Weather.main.for_skin =  (JSONObject(strokatexta).getJSONObject("main").get("feels_like")).toString() + " °c"
        api.Weather.main.at_day =  (JSONObject(strokatexta).getJSONObject("main").get("temp_max")).toString() + " °c"
        api.Weather.main.at_night =  (JSONObject(strokatexta).getJSONObject("main").get("temp_min")).toString() + " °c"
        api.Weather.main.davlenie =  (JSONObject(strokatexta).getJSONObject("main").get("pressure")).toString()
        api.Weather.main.vlaznost =  (JSONObject(strokatexta).getJSONObject("main").get("humidity")).toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_weather, container, false)
        var opisanie = view?.findViewById<TextView>(R.id.strokaOpisaniya)
        var sovet = view?.findViewById<TextView>(R.id.strokasoveta)
        var temperatura = view.findViewById<TextView>(R.id.temperatura)
        var for_skin = view?.findViewById<TextView>(R.id.for_skin)
        var at_day = view?.findViewById<TextView>(R.id.at_day)
        var at_night = view?.findViewById<TextView>(R.id.at_night)
        var vlaznost = view?.findViewById<TextView>(R.id.vlaznost)
        var davlenie = view?.findViewById<TextView>(R.id.davlenie)
        var Gorod = view?.findViewById<TextView>(R.id.Gorod)
        var imageView = view?.findViewById<ImageView>(R.id.Giff)

        Gorod?.text = MainCity
        opisanie?.text =  api.Weather.opisanie
        sovet?.text =  api.Weather.opisanie
        temperatura?.text = api.Weather.main.temperatura
        for_skin?.text = api.Weather.main.for_skin
        at_day?.text = api.Weather.main.at_day
        at_night?.text = api.Weather.main.at_night
        vlaznost?.text = api.Weather.main.vlaznost
        davlenie?.text = api.Weather.main.davlenie

        when (api.Weather.anim) {"Thunderstorm" -> {
                Glide.with(this@WeatherFragment).load(R.drawable.storm).into(imageView)
            strokasoveta.text = "Следует оставаться дома."
            } "Drizzle" -> {
            Glide.with(this@WeatherFragment).load(R.drawable.dozd).into(imageView)
            strokasoveta.text = "Следует надеть непромокаему одежду"
        } "Rain" -> {
            Glide.with(this@WeatherFragment).load(R.drawable.dozd).into(imageView)
            strokasoveta.text = "Следует надеть непромокаему одежду"
        }  "Clouds" -> {
            Glide.with(this@WeatherFragment).load(R.drawable.oblaka).into(imageView)
            opisanie.text = "Следует взять с собой зонтик"
        } "Clear" -> {
            Glide.with(this@WeatherFragment).load(R.drawable.solnce).into(imageView)
            strokasoveta.text = "Благосклонная для прогулок погода"
        } "Fog" -> {
            Glide.with(this@WeatherFragment).load(R.drawable.tuman).into(imageView)
            strokasoveta.text = "Будьте аккуратны возле дорог"
        }"Snow" -> {
            Glide.with(this@WeatherFragment).load(R.drawable.sneg).into(imageView)
            strokasoveta.text = "Отлична погода для игры в снежки"
        }
        }
        return view
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WeatherFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WeatherFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
