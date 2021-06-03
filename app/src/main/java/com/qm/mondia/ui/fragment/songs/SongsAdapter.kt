package com.qm.mondia.ui.fragment.songs

import com.qm.mondia.R
import com.qm.mondia.base.view.BaseAdapter
import com.qm.mondia.base.view.BaseViewHolder

//MARK:- SongsAdapter @Docs

class SongsAdapter(itemClick: (SongsItem) -> Unit) : BaseAdapter<SongsItem>(itemClick) {

  override fun layoutRes(): Int = R.layout.item_song_view

  override fun onViewHolderCreated(viewHolder: BaseViewHolder<SongsItem>) {
  }
}