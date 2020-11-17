package com.realtimemap.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.realtimemap.R
import com.realtimemap.databinding.FragmentLocationInfoBinding
import com.realtimemap.di.inject
import com.realtimemap.navigation.NavigationDispatcher
import com.realtimemap.presentation.locationdetail.LocationDetailViewIntent
import com.realtimemap.presentation.locationdetail.LocationDetailViewState
import com.realtimemap.presentation.mvi.MVIView
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject
import javax.inject.Provider


class LocationDetailFragment : BottomSheetDialogFragment(),
    MVIView<LocationDetailViewIntent, LocationDetailViewState> {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    @Inject
    lateinit var navigator: Provider<NavigationDispatcher>

    private val viewModel: LocationDetailViewModel by viewModels { factory }

    private val binding: FragmentLocationInfoBinding by viewBinding(FragmentLocationInfoBinding::bind)

    private val args: LocationDetailFragmentArgs by navArgs()

    private val loadAddressIntent = ConflatedBroadcastChannel<LocationDetailViewIntent>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject(this)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (savedInstanceState == null) {
            loadAddressIntent.offer(LocationDetailViewIntent.LoadInfoIntent(args.userLocation))
        }
        viewModel.processIntent(intents)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_location_info, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            LocationDetailFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun render(state: LocationDetailViewState) {
    }

    override val intents: Flow<LocationDetailViewIntent>
        get() = loadAddressIntent.asFlow()
}