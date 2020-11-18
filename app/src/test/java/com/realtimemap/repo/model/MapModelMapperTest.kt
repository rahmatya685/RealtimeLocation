package com.realtimemap.repo.model

import com.realtimemap.repo.remote.DummyData
import junit.framework.TestCase
import org.junit.Test

class MapModelMapperTest {


    @Test
    fun `check if UserLocation is converted to UserLocationModel correctly`() {
        val model = MapModelMapper().mapToModel(DummyData.userLocation)

        TestCase.assertEquals(model.id, DummyData.userLocation.id)
        TestCase.assertEquals(model.name, DummyData.userLocation.name)
        TestCase.assertEquals(model.imageUrl, DummyData.userLocation.imageUrl)
        TestCase.assertEquals(model.lat, DummyData.userLocation.lat)
        TestCase.assertEquals(model.long, DummyData.userLocation.long)
    }


    @Test
    fun `check if userLocationModel is UserLocation correctly`() {
        val domainEntity = MapModelMapper().mapToDomain(DummyData.userLocationModel)

        TestCase.assertEquals(domainEntity.id, DummyData.userLocationModel.id)
        TestCase.assertEquals(domainEntity.name, DummyData.userLocationModel.name)
        TestCase.assertEquals(domainEntity.imageUrl, DummyData.userLocationModel.imageUrl)
        TestCase.assertEquals(domainEntity.lat, DummyData.userLocationModel.lat)
        TestCase.assertEquals(domainEntity.long, DummyData.userLocationModel.long)
    }
}