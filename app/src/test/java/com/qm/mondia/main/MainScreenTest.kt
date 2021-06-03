package com.qm.mondia.main

import com.qm.mondia.mock_server.MockRequest
import com.qm.mondia.ui.fragment.songs.SongsItem
import com.qm.mondia.util.mMapToArrayListFix
import com.qm.mondia.util.mStringToJsonElement
import junit.framework.Assert.assertEquals
import org.junit.Test

//MARK:- todo add Main Test Cases
class MainScreenTest {
  @Test
  fun mainTestFun() {
    assertEquals(10, 10 + 0)
  }

  @Test
  fun songSearchFail() {
    val jsonResponse = MockRequest.emptyResponse
    val songList = jsonResponse.mStringToJsonElement().asJsonArray.mMapToArrayListFix<SongsItem>()
    assertEquals(songList.size, 0)
  }

  @Test
  fun songSearchSucess() {
    //MARK:- todo move to Mock Class
    val jsonResponse = MockRequest.sucessResponse
    val songList = jsonResponse.mStringToJsonElement().asJsonArray.mMapToArrayListFix<SongsItem>()
    assertEquals(songList.size, 2)
  }
}