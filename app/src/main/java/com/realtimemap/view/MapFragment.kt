package com.realtimemap.view

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonParser
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.geometry.LatLngBounds
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.plugins.annotation.Symbol
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions
import com.realtimemap.R
import com.realtimemap.databinding.FragmentMapBinding
import com.realtimemap.di.inject
import com.realtimemap.domain.model.LocationUpdate
import com.realtimemap.domain.model.UserLocation
import com.realtimemap.ext.observe
import com.realtimemap.ext.toJson
import com.realtimemap.navigation.NavigationDispatcher
import com.realtimemap.presentation.map.MapViewIntent
import com.realtimemap.presentation.map.MapViewState
import com.realtimemap.presentation.mvi.MVIView
import com.realtimemap.repo.model.UserLocationModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import javax.inject.Inject
import javax.inject.Provider


/**
 *  a [Fragment] to show users locations on map
 */
class MapFragment :
    Fragment(R.layout.fragment_map),
    MVIView<MapViewIntent, MapViewState> {

    var ID_MAP_ICON = "MAP_ICON"


    lateinit var symbolManager: SymbolManagerWrapper

    val channel: Channel<MapViewIntent> = Channel { }

    private val locations: MutableMap<Int, Symbol> = mutableMapOf()

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    @Inject
    lateinit var navigator: Provider<NavigationDispatcher>

    private val viewModel: MapViewModel by viewModels { factory }

    private val binding: FragmentMapBinding by viewBinding(FragmentMapBinding::bind)

    private lateinit var mapBox: MapboxMap

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject(this)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.viewState.observe(viewLifecycleOwner, ::render)
        viewModel.processIntent(intents)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this::onMapReady)
    }

    override fun render(state: MapViewState) {
        when {
            state.isDataUnavailable -> renderEmptyState(state)
            state.isNoDataError -> binding.renderNoDataErrorState(state)
            state.isLoading -> binding.renderLoadingState()
            state.isLocationUpdated -> locationUpdateReceived(state.updateLocation)
            state.isDataAvailableError -> binding.renderNoDataErrorState(state)
            state.hasLocationWithAddress -> showLocationDetail(state)
            else -> renderSuccessState(state)
        }
    }

    private fun showLocationDetail(state: MapViewState) {
        state.location?.consume(navigator.get()::openLocationInfoFragment)
    }

    private val openStepInfoIntent: Flow<MapViewIntent.ShowLocationDetailIntent>
        get() = symbolManager.symbolClicks.map { userLocationDetail ->
            MapViewIntent.ShowLocationDetailIntent(userLocation = userLocationDetail)
        }

    override val intents: Flow<MapViewIntent>
        get() = merge(channel.consumeAsFlow(), openStepInfoIntent)

    private fun renderEmptyState(state: MapViewState) {
        Snackbar.make(
            binding.root,
            getString(R.string.msg_info_no_data_found),
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun FragmentMapBinding.renderLoadingState() {

    }

    private fun FragmentMapBinding.renderNoDataErrorState(state: MapViewState) {
        state.errorEvent?.consume { error ->
            Snackbar.make(binding.root, error, Snackbar.LENGTH_LONG).show()
        }
        state.error?.let { error ->
            Snackbar.make(binding.root, error, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun renderSuccessState(state: MapViewState) {
        if (state.locations.isNotEmpty()) {
            addLocations(state.locations)
            moveCamera(state.locations.map { LatLng(it.lat, it.long) })
        }
    }

    private fun locationUpdateReceived(updateLocation: LocationUpdate?) {
        updateLocation?.let {
            this.locations[updateLocation.id]?.let {
                it.geometry = Point.fromLngLat(updateLocation.lat, updateLocation.long)
                it.iconImage = ID_MAP_ICON
                locations.put(updateLocation.id, it)
                symbolManager.update(it)
            }
            moveCamera(locations.values.map { LatLng(it.latLng.latitude, it.latLng.longitude) })
        }
    }

    private fun moveCamera(latLongs: List<LatLng>) {
        val latLngBounds = LatLngBounds.Builder()
            .includes(latLongs)
            .build()
        mapBox.easeCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 500), 200)

    }

    private fun onMapReady(mapboxMap: MapboxMap) {
        this.mapBox = mapboxMap
        this.mapBox.setStyle(Style.LIGHT) { style ->
            style.addImage(
                ID_MAP_ICON,
                BitmapFactory.decodeResource(
                    resources, R.drawable.mapbox_marker_icon_default
                )
            )
            symbolManager = SymbolManagerWrapper(binding.mapView, mapboxMap, style)
            symbolManager.addClickListener(this::onSymbolClickListener)
            channel.offer(MapViewIntent.LoadInitialViewIntent)
            channel.offer(MapViewIntent.GetLocationUpdatesIntent)
        }
    }

    private fun addLocations(locations: List<UserLocationModel>) {
        locations.forEach { l ->
            val symbolOptions = SymbolOptions()
                .withLatLng(LatLng(l.lat, l.long))
                .withIconImage(ID_MAP_ICON)
                .withData(JsonParser.parseString(l.toJson()))
            val symbol = symbolManager.create(symbolOptions)
            this.locations[l.id] = symbol
        }
    }

    private fun onSymbolClickListener(userLocation: UserLocation) {

    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }
}





