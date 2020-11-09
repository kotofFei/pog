package com.example.pog
import android.os.Parcelable
import android.util.Log
import com.beust.klaxon.Json
import com.example.pog.BuildConfig.API_KEY
import com.squareup.picasso.BuildConfig
import com.squareup.picasso.BuildConfig.*
import com.squareup.picasso.Picasso
import kotlinx.android.parcel.Parcelize
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.lang.reflect.Field
import retrofit2.Call
import retrofit2.http.GET



class API {
    private val API_KEY = com.example.pog.BuildConfig.API_KEY

    var URL: String = ""
        get() = field
        set(value) {
            field = ("https://api.openweathermap.org/data/2.5/weather?q=$value&appid=$API_KEY&lang=ru&units=metric")
        }

    var Weather: weather = weather()

    var strokatexta: String = ""

}

class weather {
    var main: main = main()
    var anim: String = ""
    var opisanie: String = ""
}

class main {
    var temperatura: String = ""
    var for_skin: String = ""
    var at_night: String = ""
    var at_day: String = ""
    var davlenie: String = ""
    var vlaznost: String = ""
}

class Cities (
    @Json(name = "Cities")
    var Cities: ArrayList<City>
)

class City (
    @Json(name = "City")
    var City: String
)