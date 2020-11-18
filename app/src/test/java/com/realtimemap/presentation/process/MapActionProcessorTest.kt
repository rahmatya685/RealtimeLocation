package com.realtimemap.presentation.process

import com.google.common.truth.IterableSubject
import com.google.common.truth.Truth
import com.realtimemap.FlowRecorder
import com.realtimemap.domain.usecase.FetchLocationUpdates
import com.realtimemap.domain.usecase.FetchLocations
import com.realtimemap.presentation.event.PostExecutionThreadImpl
import com.realtimemap.presentation.map.MapViewAction
import com.realtimemap.presentation.map.MapViewResult
import com.realtimemap.recordWith
import com.realtimemap.repo.FakeLocationRepository
import com.realtimemap.repo.ResponseType
import com.realtimemap.repo.remote.DummyData
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import java.net.SocketTimeoutException

class MapActionProcessorTest {

    private lateinit var thread :PostExecutionThreadImpl
    private lateinit var locationRepository:FakeLocationRepository
    private lateinit var actionProcessor : MapActionProcessor
    private lateinit var resultRecorder: FlowRecorder<MapViewResult>

    @Before
    fun setUp() {
        thread = PostExecutionThreadImpl()
        locationRepository = FakeLocationRepository()
        actionProcessor = MapActionProcessor(
            FetchLocations(locationRepository, thread),
            FetchLocationUpdates(locationRepository, thread)
        )
        resultRecorder = FlowRecorder(TestCoroutineScope())
    }

    @Test
    fun `When RetryFetchAction is passed Then RetryFetchResult Loaded is returned`() =
        runBlockingTest {
            recordResult(MapViewAction.RetryFetchAction, ResponseType.DATA)
            Truth.assertThat(resultRecorder.takeAll())
                .contains(
                    MapViewResult.RetryFetchResult.Loading,
                    MapViewResult.RetryFetchResult.Loaded(DummyData.userLocations)
                )
        }


    @Test
    fun `When RetryFetchAction is passed Then RetryFetchResult Empty is returned`() =
        runBlockingTest {
            recordResult(MapViewAction.RetryFetchAction, ResponseType.EMPTY)
            Truth.assertThat(resultRecorder.takeAll())
                .contains(
                    MapViewResult.RetryFetchResult.Loading,
                    MapViewResult.RetryFetchResult.Empty
                )
        }


    @Test
    fun `When RetryFetchAction is passed Then RetryFetchResult Error is returned`() =
        runBlockingTest {
            recordResult(MapViewAction.RetryFetchAction, ResponseType.ERROR)
            val errorResults = resultRecorder.takeAll()
            Truth.assertThat(errorResults.map { it.javaClass })
                .contains(
                    MapViewResult.RetryFetchResult.Loading::class.java,
                    MapViewResult.RetryFetchResult.Error::class.java
                )
            val errorResult: MapViewResult.RetryFetchResult.Error =
                errorResults.last() as MapViewResult.RetryFetchResult.Error
            Truth.assertThat(errorResult.cause).isInstanceOf(SocketTimeoutException::class.java)
            Truth.assertThat(errorResult.cause.message).isEqualTo(FakeLocationRepository.ERROR_MSG)
        }


    @Test
    fun `When LoadLocationsAction is passed Then LoadLocationsResult Loaded is returned`() =
        runBlockingTest {
            recordResult(MapViewAction.LoadLocationsAction, ResponseType.DATA)
            Truth.assertThat(resultRecorder.takeAll())
                .contains(
                    MapViewResult.LoadLocationsResult.Loading,
                    MapViewResult.LoadLocationsResult.Loaded(DummyData.userLocations)
                )
        }

    @Test
    fun `When LoadLocationsAction is passed Then LoadLocationsResult Empty is returned`() =
        runBlockingTest {
            recordResult(MapViewAction.LoadLocationsAction, ResponseType.EMPTY)
            Truth.assertThat(resultRecorder.takeAll())
                .contains(
                    MapViewResult.LoadLocationsResult.Loading,
                    MapViewResult.LoadLocationsResult.Empty
                )
        }

    @Test
    fun `When LoadLocationsAction is passed Then LoadLocationsResult Error is returned`() =
        runBlockingTest {
            recordResult(MapViewAction.LoadLocationsAction, ResponseType.ERROR)
            val errorResults = resultRecorder.takeAll()
            Truth.assertThat(errorResults.map { it.javaClass })
                .contains(
                    MapViewResult.LoadLocationsResult.Loading::class.java,
                    MapViewResult.LoadLocationsResult.Error::class.java
                )
            val errorResult: MapViewResult.LoadLocationsResult.Error =
                errorResults.last() as MapViewResult.LoadLocationsResult.Error
            Truth.assertThat(errorResult.cause).isInstanceOf(SocketTimeoutException::class.java)
            Truth.assertThat(errorResult.cause.message).isEqualTo(FakeLocationRepository.ERROR_MSG)
        }


    @Test
    fun `When GetLocationUpdatesAction is passed Then GetLocationUpdates Updated is returned`() =
        runBlockingTest {
            recordResult(MapViewAction.GetLocationUpdatesAction, ResponseType.DATA)
            Truth.assertThat(resultRecorder.takeAll())
                .contains(
                    MapViewResult.GetLocationUpdates.Loading,
                    MapViewResult.GetLocationUpdates.Updated(DummyData.locationUpdate)
                )
        }


    @Test
    fun `When GetLocationUpdatesAction is passed Then GetLocationUpdates Empty is returned`() =
        runBlockingTest {
            recordResult(MapViewAction.GetLocationUpdatesAction, ResponseType.EMPTY)
            Truth.assertThat(resultRecorder.takeAll())
                .contains(
                    MapViewResult.GetLocationUpdates.Loading,
                    MapViewResult.GetLocationUpdates.Empty
                )
        }


    @Test
    fun `When GetLocationUpdatesAction is passed Then GetLocationUpdates Error is returned`() =
        runBlockingTest {
            recordResult(MapViewAction.GetLocationUpdatesAction, ResponseType.ERROR)
            val errorResults = resultRecorder.takeAll()
            Truth.assertThat(errorResults.map { it.javaClass })
                .contains(
                    MapViewResult.GetLocationUpdates.Loading::class.java,
                    MapViewResult.GetLocationUpdates.Error::class.java
                )
            val errorResult: MapViewResult.GetLocationUpdates.Error =
                errorResults.last() as MapViewResult.GetLocationUpdates.Error
            Truth.assertThat(errorResult.cause).isInstanceOf(SocketTimeoutException::class.java)
            Truth.assertThat(errorResult.cause.message).isEqualTo(FakeLocationRepository.ERROR_MSG)
        }


    private fun recordResult(action: MapViewAction, type: ResponseType) {
        when (action) {
            MapViewAction.LoadLocationsAction,
            MapViewAction.RetryFetchAction -> {
                locationRepository.responseTypeLocationList = type
                actionProcessor.actionToResult(action)
                    .recordWith(resultRecorder)
            }
            MapViewAction.GetLocationUpdatesAction -> {
                locationRepository.responseTypeLocationUpdates =
                    type
                actionProcessor.actionToResult(action)
                    .recordWith(resultRecorder)
            }
            MapViewAction.DoNothing -> TODO()
            is MapViewAction.ShowLocationDetailAction -> TODO()
        }

    }

    private inline fun <reified T> IterableSubject.contains(vararg instance: T) {
        containsExactlyElementsIn(instance).inOrder()
    }
}