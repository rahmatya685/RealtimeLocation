package com.realtimemap.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.realtimemap.R
import com.realtimemap.databinding.FragmentLocationInfoBinding
import com.realtimemap.di.inject
import com.realtimemap.ext.observe
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

    @Inject
    lateinit var imageLoader: ImageLoader

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewState.observe(viewLifecycleOwner, ::render)
        viewModel.processIntent(intents)
        binding.imgUser
        if (savedInstanceState == null) {
            loadAddressIntent.offer(LocationDetailViewIntent.LoadInfoIntent(args.userLocation))
        }
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
        when {
            state.isLoading -> binding.renderLoadingState()
            state.hasError -> binding.renderError(state)
            state.noData -> binding.renderError(state)
            state.hasLocationWithAddress -> binding.renderInfo(state)
        }
    }

    private fun FragmentLocationInfoBinding.renderError(state: LocationDetailViewState) {
        state.errorEvent?.consume { error ->
            Snackbar.make(binding.root, error, Snackbar.LENGTH_LONG).show()
        }
        state.error?.let { error ->
            Snackbar.make(binding.root, error, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun FragmentLocationInfoBinding.renderInfo(state: LocationDetailViewState) {
        state.locationWithAddress?.let { model ->
            binding.run {
                progressBar.visibility =View.INVISIBLE
                imgUser.visibility = View.VISIBLE

                imageLoader.loadImage(imgUser, model.imageUrl)
                tvAddress.text = model.address
                tvName.text = model.name
            }
        }

    }

    private fun ViewBinding.renderLoadingState() {
        binding.run {
            progressBar.visibility =View.VISIBLE
            imgUser.visibility = View.INVISIBLE
            tvAddress.text = ""
            tvName.text = ""
        }
    }


    override val intents: Flow<LocationDetailViewIntent>
        get() = loadAddressIntent.asFlow()
}







