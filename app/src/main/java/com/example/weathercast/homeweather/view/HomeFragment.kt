package com.example.weathercast.homeweather.view

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab1.internetconnection.MyConnectionReceiver
import com.example.lab1.internetconnection.OnNetworkChange
import com.example.weathercast.R
import com.example.weathercast.data.reposatoru.WeatherReposatory
import com.example.weathercast.data.reposatoru.WeatherReposatoryInterface
import com.example.weathercast.databinding.FragmentHomeBinding
import com.example.weathercast.homeweather.viewmodel.HomeViewModel
import com.example.weathercast.homeweather.viewmodel.HomeViewModelFactory
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.asFlow
import com.example.mvvm.db.LocationLocalDataSource
import com.example.mvvm.network.RemoteDataSource
import com.example.mvvm.network.RemoteDataSourceInterface
import com.example.weathercast.viemodel.SharedPreferenceViewModel
import com.example.weathercast.viemodel.SharedPreferenceViewModelFactory
import com.example.weathercast.data.localdatasource.SharedPreferencelLocationData
import com.example.weathercast.data.localdatasource.WeatherLocalDataSource
import com.example.weathercast.homeweather.viewmodel.AppiState
import com.example.weathercast.viemodel.ToolBarTextViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority


class HomeFragment : Fragment(),OnNetworkChange  {
    lateinit var homeViewModel: HomeViewModel
    lateinit var remoteDataSource: RemoteDataSourceInterface
    lateinit var localRepository: WeatherLocalDataSource
    var sharedPreferenceLocation=""
    lateinit var binding: FragmentHomeBinding
    private lateinit var weatherReposatory: WeatherReposatoryInterface
    lateinit var todayAdapter: TodayWeatherDiffUtillAdapter
    private lateinit var todaRecyclersView: RecyclerView
    lateinit var fiveDaysAdapter: NextFiveDayWeatherDiffUtillAdapter
    private lateinit var fiveDaysRecyclersView: RecyclerView
    lateinit var receiver: MyConnectionReceiver
    lateinit var progressBar: FrameLayout
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var networkReceiver: BroadcastReceiver
    lateinit var sharedPreferenceViewModel: SharedPreferenceViewModel
    private val toolBarTextViewModel: ToolBarTextViewModel by activityViewModels()
    var latitude: Double? = null
    var longitude: Double? = null
    var isFromFavorits: Boolean? = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("toolbar", "home frag onCreate: start")
//        toolBarTextViewModel = ViewModelProvider(this).get(ToolBarTextViewModel::class.java)
        /*toolBarTextViewModel.setToolbarTitle("Home")
        Log.d("toolbar", "home frag onCreate: end Home")*/
//        (activity as? MainActivity)?.emitToolbarTitle("New Fragment Title")
        // Emit a new toolbar title from the ViewModel

        /*if (arguments != null){
           if( arguments?.getString("location")!=null){
               sharedPreferenceLocation=arguments?.getString("location").toString()
           }
        }*/
         remoteDataSource = RemoteDataSource()
         localRepository = WeatherLocalDataSource(SharedPreferencelLocationData.getInstance(requireContext()),LocationLocalDataSource.getInstance(requireContext()))
         weatherReposatory = WeatherReposatory.getInstance(remoteDataSource, localRepository)
        val viewModelFactory = SharedPreferenceViewModelFactory(weatherReposatory)
        sharedPreferenceViewModel =
            ViewModelProvider(this, viewModelFactory).get(SharedPreferenceViewModel::class.java)
        if (sharedPreferenceViewModel.getLocation()!="null,null"){
            sharedPreferenceLocation= sharedPreferenceViewModel.getLocation().toString()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         latitude = arguments?.getDouble("latitude")
         longitude = arguments?.getDouble("longitude")
        isFromFavorits= arguments?.getBoolean("isFromFavorits")
        Log.d("fav", "onViewCreated:${isFromFavorits},${latitude},${longitude} ")
        if (isFromFavorits==true) {
            viewLifecycleOwner.lifecycleScope.launch {
                toolBarTextViewModel.setToolbarTitle("Favorites")
            }
        }else{
            viewLifecycleOwner.lifecycleScope.launch {
                toolBarTextViewModel.setToolbarTitle("Home")
            }
        }


        progressBar=view.findViewById(R.id.progressBar)

        /*receiver= MyConnectionReceiver(this)
        val intentFilter= IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        activity?.registerReceiver(receiver,intentFilter, RECEIVER_NOT_EXPORTED)*/

        val isRtl = resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_RTL

        todaRecyclersView = view.findViewById(R.id.today_weather_recyclerView)

        /*todaRecyclersView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, isRtl)*/


        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        todaRecyclersView.layoutManager = layoutManager

// Check if the locale is RTL (Arabic in your case)
        if (ViewCompat.getLayoutDirection(view) == ViewCompat.LAYOUT_DIRECTION_RTL) {
            todaRecyclersView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        } else {
            todaRecyclersView.layoutDirection = View.LAYOUT_DIRECTION_LTR
        }
        todayAdapter = TodayWeatherDiffUtillAdapter()

        fiveDaysRecyclersView = view.findViewById(R.id.next_days_weather_recycler_view)
        fiveDaysRecyclersView.layoutManager = LinearLayoutManager(context)
        fiveDaysAdapter = NextFiveDayWeatherDiffUtillAdapter()

        val viewModelFactory = HomeViewModelFactory(weatherReposatory)
        homeViewModel =
            ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        binding.hfvModel = homeViewModel
        binding.lifecycleOwner = this

        val dateFormat = SimpleDateFormat("EEE, dd MMMM  h:mm a", Locale.getDefault())
        val currentDateAndTime: String = dateFormat.format(Date())
        binding.currentdate = currentDateAndTime
        Log.d("TAG", "onViewCreated:before registerNetworkBroadcastReceiver")
        registerNetworkBroadcastReceiver()
        Log.d("TAG", "onViewCreated:After registerNetworkBroadcastReceiver")
        lifecycleScope.launch {
            homeViewModel.forcastWeatherData.asFlow().collect() { forecastData ->
                Log.d("TAG", "forcastWeatherData:Start ")
                when(forecastData) {
                    is AppiState.Success -> {
                        Log.d("TAG", "AppiState.Success: ")
                        progressBar.visibility = View.GONE
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                            todayAdapter.submitList(forecastData.data.list.filter {
                                it.dtTxt?.contains(
                                    SimpleDateFormat(
                                        "yyyy-MM-dd",
                                        Locale.getDefault()
                                    ).format(Date())
                                ) == true
                            })
                            todaRecyclersView.adapter = todayAdapter
                            fiveDaysAdapter.submitList(forecastData.data.list
                                .filter {
                                    it.dtTxt?.contains(
                                        SimpleDateFormat(
                                            "yyyy-MM-dd",
                                            Locale.getDefault()
                                        ).format(Date())
                                    ) == false
                                }
                                .filter {
                                    it.dtTxt?.contains(
                                        SimpleDateFormat(
                                            "09:00:00",
                                            Locale.getDefault()
                                        ).format(Date())
                                    ) == true
                                }
                            )
                            fiveDaysRecyclersView.adapter = fiveDaysAdapter
                        Log.d("TAG", "AppiState.Success:end ")
                    }
                    is AppiState.Failure -> {
                        Log.d("TAG", "AppiState.Failure:${forecastData.message} ")
                        Toast.makeText(context, forecastData.message, Toast.LENGTH_SHORT).show()
                    }
                    is AppiState.Loading -> {
                        Log.d("TAG", "AppiState.Loading: ")
                        progressBar.visibility = View.VISIBLE
                        Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
                    }
                    is AppiState.Empty -> {
                        Log.d("TAG", "AppiState.Empty: ")
                        Toast.makeText(context, "Empty", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }
    private fun registerNetworkBroadcastReceiver() {
        Log.d("TAG", "registerNetworkBroadcastReceivery: start")
        receiver = MyConnectionReceiver { isConnected ->
            Log.d("TAG", "registerNetworkBroadcastReceivery: isConnected")
            onNetworkChange(isConnected)
        }
        Log.d("TAG", "registerNetworkBroadcastReceivery: isConnected After")
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        activity?.registerReceiver(receiver, intentFilter)
        Log.d("TAG", "registerNetworkBroadcastReceivery: end")
    }
      /*  networkReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
                val isConnected = activeNetwork?.isConnectedOrConnecting == true

                onNetworkChange(isConnected)
            }
        }

        /*val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        requireContext().registerReceiver(networkReceiver, filter)*/
    }*/

    override fun onNetworkChange(isOnline: Boolean) {
        Log.d("TAG", "onNetworkChange: start")
        val appContext = activity?.applicationContext ?: return  // Safely get the application context

        if (isOnline) {
            Log.d("TAG", "onNetworkChange:isOnline start")
            if (isFromFavorits==true) {
                if (latitude != null || longitude != null) {
                    Log.d("TAG", "isOnline:isFromFavorits ")
                    homeViewModel.getForecastData(latitude.toString(), longitude.toString(), "metric")
                    homeViewModel.getCurrentData(latitude.toString(), longitude.toString(), "metric")
                }
            }else if( sharedPreferenceLocation!=""){
                val lat:String = sharedPreferenceLocation.split(",")[0]
                val lon:String = sharedPreferenceLocation.split(",")[1]
                Log.d("TAG", "onNetworkChange:getFreshhLocation start ${lat},${lon}")
                homeViewModel.getForecastData(lat, lon, "metric")
                homeViewModel.getCurrentData(lat, lon, "metric")
            }
            else{
                getFreshhLocation()
                Log.d("TAG", "onNetworkChange:getFreshhLocation after")
            }

        } else {
            Log.d("TAG", "onNetworkChange: else")
            Toast.makeText(appContext, "No internet connection", Toast.LENGTH_SHORT).show()
        }

    }

    @SuppressLint("MissingPermission")
    private fun getFreshhLocation() {
        Log.d("TAG", "getFreshhLocation: start")
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        val locationRequest = LocationRequest.Builder(1).apply {
            setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY)
        }.build()

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    super.onLocationResult(locationResult)
//                    if (true) {
//                        locationReceived = true
                        var la = locationResult.lastLocation?.latitude
                        var lo = locationResult.lastLocation?.longitude
                        Log.d("TAG", "onLocationResult:${la},--${lo} ")
                        if (la != null && lo != null) {
                            homeViewModel.getForecastData(la.toString(), lo.toString(), "metric")
                            homeViewModel.getCurrentData(la.toString(), lo.toString(), "metric")
                        }
                    }
//                }
            },
            Looper.myLooper()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("TAG", "onDestroyView: start")

        // Check if the networkReceiver is initialized before attempting to unregister it
        if (::networkReceiver.isInitialized) {
            Log.d("TAG", "onDestroyView: networkReceiver=true")
            activity?.unregisterReceiver(networkReceiver)
        }
        Log.d("TAG", "onDestroyView: end")
    }


}
@BindingAdapter("countryCode", "cityName")
fun setCountryAndCityName(view: TextView, countryCode: String?, cityName: String?) {
    if (!countryCode.isNullOrEmpty() && !cityName.isNullOrEmpty()) {
        // Convert ISO code to full country name using Locale
        val fullCountryName = Locale("", countryCode).displayCountry
        view.text = "$cityName/ $fullCountryName"
    } else {
        view.text = ""
    }
}


@BindingAdapter("imageIcon", "error")
fun weatherImage(view: ImageView, icon: String?, error: Drawable) {
    if (!icon.isNullOrEmpty()) {
        val resourceId = view.context.resources.getIdentifier(
            "icon_$icon",
            "drawable",
            view.context.packageName
        )

        if (resourceId != 0) {
            view.setImageResource(resourceId)
        }
    } else {
        view.setImageDrawable(error)
    }
}
