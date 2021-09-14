package com.ddmukhin.stroylandtestappapi.ui

import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.ddmukhin.stroylandtestappapi.R
import com.ddmukhin.stroylandtestappapi.ui.data.Giphy
import java.io.FileNotFoundException

class GiphyAdapter(
    private val giphyList: List<Giphy>
) : RecyclerView.Adapter<GiphyAdapter.GiphyViewHolder>() {

    class GiphyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val giphyHolder: VideoView = itemView.findViewById(R.id.video)

        fun bindGiphy(giphy: Giphy) {
            giphyHolder.setVideoURI(Uri.parse(giphy.link))
            giphyHolder.setOnPreparedListener {
                it.isLooping = true
                it.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING)
                it.start()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GiphyViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.gif_item, parent, false)
    )

    override fun onBindViewHolder(holder: GiphyViewHolder, position: Int) {
        holder.bindGiphy(giphyList[position])
    }

    override fun getItemCount() = giphyList.size

}