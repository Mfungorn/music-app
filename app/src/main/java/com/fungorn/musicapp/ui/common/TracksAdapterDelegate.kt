package com.fungorn.musicapp.ui.common

import com.fungorn.musicapp.databinding.ItemTrackBinding
import com.fungorn.musicapp.domain.model.Track
import com.fungorn.musicapp.ui.common.utils.setSafeOnClickListener
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun tracksAdapterDelegate(
    onItemClick: (Track) -> Unit
) = adapterDelegateViewBinding<Track, Track, ItemTrackBinding>(
    viewBinding = { layoutInflater, parent ->
        ItemTrackBinding.inflate(
            layoutInflater,
            parent,
            false
        )
    }
) {
    bind {
        with(binding) {
            artist.text = item.artist
            song.text = item.song
            year.text = item.year.toString()
            root.setSafeOnClickListener {
                onItemClick(item)
            }
        }
    }
}