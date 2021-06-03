package com.qm.mondia.ui.fragment.songs

import android.app.Application
import com.android.volley.toolbox.Volley
import com.google.gson.JsonElement
import com.qm.mondia.base.viewmodel.AndroidBaseViewModel
import com.qm.mondia.constants.ConstString
import com.qm.mondia.util.Resources
import com.qm.mondia.util.mMapToArrayListFix
import com.qm.mondia.util.requestGETCall

//MARK:- SongViewModel @Docs

class SongViewModel(app: Application) : AndroidBaseViewModel(app) {
  private val queue = Volley.newRequestQueue(app)
  val request = SongRequest()
  val adapter = SongsAdapter(::onItemClick)

  private fun onItemClick(songsItem: SongsItem) {
    setValue(songsItem)
  }

  fun getData() {
    if (request.name.isNullOrBlank()) return
    postResult(Resources.loading(null))
    val headers = mutableMapOf<String, String>()
    headers["Authorization"] = ConstString.AuthorizationKey
    headers["X-MM-GATEWAY-KEY"] = ConstString.GateWayKey
    val params = mutableMapOf<String, String?>()
    params["query"] = request.name
    params["limit"] = ConstString.RequestSongLimit.toString()

    queue.requestGETCall(
      headers = headers,
      params = params, {
      onError(it)
    }) {
      onResponse(it)
    }
  }

  override fun onCleared() {
    super.onCleared()
    queue.stop()
  }

  private fun onError(volleyError: String?) {
    postResult(Resources.message(volleyError))
  }

  private fun onResponse(json: JsonElement) {
    postResult(Resources.success(null))
    val list: ArrayList<SongsItem> = json.asJsonArray.mMapToArrayListFix()
    adapter.setList(list.toMutableList())
  }
}