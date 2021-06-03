package com.qm.mondia.util

import android.util.Log
import com.android.volley.*
import com.android.volley.VolleyLog.TAG
import com.android.volley.toolbox.HttpHeaderParser
import com.google.gson.JsonElement
import com.google.gson.JsonSyntaxException
import com.qm.mondia.constants.ConstString.BASE_URL
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

// MARK:- Class built for mapping any thing to Json

fun RequestQueue.requestGETCall(
  headers: MutableMap<String, String>? = null,
  params: MutableMap<String, String?>? = null,
  onErrorCallBack: (String?) -> Unit = {},
  onSuccessCallBack: (JsonElement) -> Unit
) {
  val mUrlBuilder = StringBuilder(BASE_URL).append("?")
  params?.forEach {
    if (it.value != null)
      mUrlBuilder.append(it.key).append("=").append(it.value).append("&")
  }

  fun onResponse(json: String?) {
    json?.let {
      onSuccessCallBack.invoke(json.mStringToJsonElement())
    } ?: onErrorCallBack.invoke("json is null")
  }

  //MARK:- minimal error handling
  fun onError(volleyError: VolleyError?) {
    Log.e(TAG, "onError: ", volleyError)
    onErrorCallBack.invoke(
      volleyError?.message
        ?: if (volleyError?.networkResponse?.statusCode == 401) "Update Your token pleas" else volleyError?.cause?.message
          ?: ""
    )
  }
  Log.e(TAG, "Url: $mUrlBuilder")
  val request = GsonRequestHelper(
    url = mUrlBuilder.toString(),
    requestType = Request.Method.GET,
    headers = headers,
    params = params,
    listener = ::onResponse,
    errorListener = ::onError
  )
  add(request)
}

private class GsonRequestHelper(
  url: String,
  requestType: Int,
  private val headers: MutableMap<String, String>?,
  private val params: MutableMap<String, String?>?,
  private val listener: Response.Listener<String>,
  errorListener: Response.ErrorListener
) : Request<String>(requestType, url, errorListener) {

  override fun getHeaders(): MutableMap<String, String> = headers ?: super.getHeaders()

  override fun deliverResponse(response: String) = listener.onResponse(response)

  override fun getParams(): MutableMap<String, String?>? = params

  override fun parseNetworkResponse(response: NetworkResponse?): Response<String>? {
    return try {
      val json = String(
        response?.data ?: ByteArray(0),
        Charset.forName(HttpHeaderParser.parseCharset(response?.headers))
      )
      Response.success(
        json,
        HttpHeaderParser.parseCacheHeaders(response)
      )
    } catch (e: UnsupportedEncodingException) {
      Response.error(ParseError(e))
    } catch (e: JsonSyntaxException) {
      Response.error(ParseError(e))
    }
  }
}