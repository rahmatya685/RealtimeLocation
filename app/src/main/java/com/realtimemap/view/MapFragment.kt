package com.realtimemap.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.realtimemap.R
import com.realtimemap.databinding.FragmentMapBinding
import com.realtimemap.di.inject
import com.realtimemap.ext.observe
import com.realtimemap.navigation.NavigationDispatcher
import com.realtimemap.presentation.map.MapViewIntent
import com.realtimemap.presentation.map.MapViewState
import com.realtimemap.presentation.mvi.MVIView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Provider


/**
 *  a [Fragment] to show users locations on map
 */
class MapFragment :
    Fragment(R.layout.fragment_map),
    MVIView<MapViewIntent, MapViewState> {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    @Inject
    lateinit var navigator: Provider<NavigationDispatcher>

    private val viewModel: MapViewModel by viewModels { factory }

    private val binding: FragmentMapBinding by viewBinding(FragmentMapBinding::bind)


    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.processIntent(intents)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewState.observe(viewLifecycleOwner, ::render)
    }


    override fun render(state: MapViewState) {
        when {
            state.isDataUnavailable -> binding.renderEmptyState(state)
            state.isNoDataError -> binding.renderNoDataErrorState(state)
            state.isLoading -> binding.renderLoadingState()
            else -> binding.renderSuccessState(state)
        }
    }

    private val loadInitialIntent: Flow<MapViewIntent.LoadInitialViewIntent>
        get() = flow { MapViewIntent.RetryFetchViewIntent }


    override val intents: Flow<MapViewIntent>
        get() = loadInitialIntent

    private fun FragmentMapBinding.renderEmptyState(state: MapViewState) {

    }

    private fun FragmentMapBinding.renderLoadingState() {

    }

    private fun FragmentMapBinding.renderNoDataErrorState(state: MapViewState) {
        state.errorEvent?.consume { error ->
            Snackbar.make(root, error, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun FragmentMapBinding.renderSuccessState(state: MapViewState) {

    }
}



