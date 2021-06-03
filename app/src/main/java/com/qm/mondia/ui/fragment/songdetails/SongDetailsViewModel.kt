package com.qm.mondia.ui.fragment.songdetails

import android.app.Application
import com.qm.mondia.base.viewmodel.AndroidBaseViewModel
import com.qm.mondia.ui.fragment.songs.SongsItem

//MARK:- SongDetailsViewModel  @Docs
class SongDetailsViewModel(app: Application) : AndroidBaseViewModel(app) {

  var item = SongsItem()

  fun gotData(args: SongDetailsFragmentArgs) {
    args.songitem?.let {
      this.item = it
      notifyChange()
    }
  }
}

