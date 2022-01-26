package com.hdev.exoplayer

import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.fragment_video_player.*


const val URL = "url"

class VideoPlayerFragment : Fragment() {
    private var videoUrl: String? = null
    private var simplePlayer: SimpleExoPlayer? = null
    private var cacheDataSourceFactory: CacheDataSourceFactory? = null
    private val simpleCache = MyExoApp.simpleCache
    private var toPlayVideoPosition: Int = -1

    companion object {
        @JvmStatic
        fun newInstance(url: String) =
            VideoPlayerFragment().apply {
                arguments = Bundle().apply {
                    putString(URL, url)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        videoUrl = arguments?.getString(URL)
        initViews()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_video_player, container, false)
    }

    override fun onPause() {
        pauseVideo()
        super.onPause()
    }

    override fun onResume() {
        restartVideo(false)
        super.onResume()
    }

    override fun onDestroy() {
        releasePlayer()
        super.onDestroy()
    }

    /**
     * initialize all views and data
     */
    private fun initViews() {
        val simplePlayer = getPlayer()
        player_view.player = simplePlayer
        videoUrl?.let { prepareMedia(it) }
    }

    /**
     * prepare the video player with exo player
     */
    private fun prepareVideoPlayer() {
        simplePlayer = ExoPlayerFactory.newSimpleInstance(context)
        cacheDataSourceFactory = CacheDataSourceFactory(simpleCache,
            DefaultHttpDataSourceFactory(Util.getUserAgent(context, "app_name")))
    }

    /** get player */
    private fun getPlayer(): SimpleExoPlayer? {
        if (simplePlayer == null) {
            prepareVideoPlayer()
        }
        return simplePlayer
    }


    /**
     * prepare the media source
     */
    private fun prepareMedia(url: String) {
        val uri = Uri.parse(url)
        val mediaSource =
            ProgressiveMediaSource.Factory(cacheDataSourceFactory).createMediaSource(uri)
        simplePlayer?.prepare(mediaSource, true, true)
        simplePlayer?.repeatMode = Player.REPEAT_MODE_ONE
        simplePlayer?.playWhenReady = false
        simplePlayer?.addListener(playerCallback)
        toPlayVideoPosition = -1

    }

    /**
     * set the ArtWork / thumbnail of media
     */
    private fun setArtWork(drawable: Drawable, playerView: PlayerView) {
        playerView.useArtwork = true
        playerView.defaultArtwork = drawable
    }

    /** play the video  */
    private fun playVideo() {
        simplePlayer?.playWhenReady = true
    }

    /** pause the video */
    private fun pauseVideo() {
        simplePlayer?.playWhenReady = false
    }

    /** restart the video */
    private fun restartVideo(play: Boolean) {
        if (simplePlayer == null) {
            videoUrl?.let { prepareMedia(it) }
        } else {
            simplePlayer?.seekToDefaultPosition()
            simplePlayer?.playWhenReady = play
        }
    }

    /** release the player */
    private fun releasePlayer() {
        simplePlayer?.stop(true)
        simplePlayer?.release()
    }

    /** callback for the player */
    private val playerCallback: Player.EventListener? = object : Player.EventListener {
        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            super.onPlayerStateChanged(playWhenReady, playbackState)
            d("DEBUG", "$playWhenReady")
        }

        override fun onPlayerError(error: ExoPlaybackException?) {
            super.onPlayerError(error)
            d("DEBUG", "${error?.message}")
        }
    }
}