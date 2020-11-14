package com.realtimemap.repo.remote.mapper

import com.realtimemap.repo.remote.DummyData
import junit.framework.TestCase
import org.junit.Test

class RemoteLocationMapperTest {


    @Test
    fun `test if String is converted to the UserLocation`() {
        val data = DummyData.USER_LOCATION_STRING
        val model = RemoteLocationMapper().mapFromModel(data)

        TestCase.assertEquals(model.id,101)
        TestCase.assertEquals(model.name,"Jānis Bērziņš")
        TestCase.assertEquals(model.lat,56.9495677035)
        TestCase.assertEquals(model.long,24.1064071655)
        TestCase.assertEquals(model.imageUrl,"https://i4.ifrype.com/profile/000/324/v1559116100/ngm_324.jpg")
    }
}