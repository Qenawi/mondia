package com.qm.mondia.ui.fragment.songs

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.qm.mondia.base.view.BaseParcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Statistics(

	@field:SerializedName("estimatedRecentCount")
	val estimatedRecentCount: String? = null,

	@field:SerializedName("popularity")
	val popularity: String? = null,

	@field:SerializedName("estimatedTotalCount")
	val estimatedTotalCount: String? = null
) : Parcelable

@Parcelize
data class MainArtist(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
) : Parcelable

@Parcelize
data class IdBag(

	@field:SerializedName("isrc")
	val isrc: String? = null,

	@field:SerializedName("ean")
	val ean: String? = null,

	@field:SerializedName("upc")
	val upc: String? = null,

	@field:SerializedName("roviId")
	val roviId: String? = null,

	@field:SerializedName("roviTrackId")
	val roviTrackId: String? = null,

	@field:SerializedName("roviReleaseId")
	val roviReleaseId: String? = null
) : Parcelable

@Parcelize
data class Cover(

	@field:SerializedName("small")
	val small: String? = null,

	@field:SerializedName("template")
	val template: String? = null,

	@field:SerializedName("default")
	val jsonMemberDefault: String? = null,

	@field:SerializedName("large")
	val large: String? = null,

	@field:SerializedName("tiny")
	val tiny: String? = null,

	@field:SerializedName("medium")
	val medium: String? = null
) : Parcelable

@Parcelize
data class Release(

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("title")
	val title: String? = null
) : Parcelable

@Parcelize
data class SongsItem(

	@field:SerializedName("volumeNumber")
	val volumeNumber: String? = null,

	@field:SerializedName("mainArtist")
	val mainArtist: MainArtist? = null,

	@field:SerializedName("trackNumber")
	val trackNumber: String? = null,

	@field:SerializedName("release")
	val release: Release? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("publishingDate")
	val publishingDate: String? = null,

	@field:SerializedName("duration")
	val duration: String? = null,

	@field:SerializedName("cover")
	val cover: Cover? = null,

	@field:SerializedName("additionalArtists")
	val additionalArtists: List<String?>? = null,

	@field:SerializedName("adfunded")
	val adfunded: Boolean? = null,

	@field:SerializedName("streamable")
	val streamable: Boolean? = null,

	@field:SerializedName("genres")
	val genres: List<String?>? = null,

	@field:SerializedName("bundleOnly")
	val bundleOnly: Boolean? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("idBag")
	val idBag: IdBag? = null,

	@field:SerializedName("statistics")
	val statistics: Statistics? = null,

	@field:SerializedName("natures")
	val natures: List<String?>? = null,

	@field:SerializedName("variousArtists")
	val variousArtists: Boolean? = null,

	@field:SerializedName("label")
	val label: String? = null,

	@field:SerializedName("partialStreamable")
	val partialStreamable: Boolean? = null,

	@field:SerializedName("numberOfTracks")
	val numberOfTracks: String? = null,

	@field:SerializedName("streamableTracks")
	val streamableTracks: String? = null,

	@field:SerializedName("mainRelease")
	val mainRelease: Boolean? = null
) : Parcelable, BaseParcelable {
	override fun unique() = id ?: "0"
}
