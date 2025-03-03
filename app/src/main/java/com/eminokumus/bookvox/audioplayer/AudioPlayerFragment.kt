package com.eminokumus.bookvox.audioplayer

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.SeekBar
import androidx.fragment.app.viewModels
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.fragment.navArgs
import com.eminokumus.bookvox.R
import com.eminokumus.bookvox.databinding.FragmentAudioPlayerBinding
import java.util.Timer
import java.util.TimerTask


class AudioPlayerFragment : Fragment() {
    private lateinit var binding: FragmentAudioPlayerBinding
    private val viewModel: AudioPlayerViewModel by viewModels()

    private val args: AudioPlayerFragmentArgs by navArgs()

    private val timer = Timer()

    private lateinit var audioPlayer: ExoPlayer

    private val handler = Handler(Looper.getMainLooper())
    private val updateRunnable = object : Runnable {
        override fun run() {
            if (audioPlayer.isPlaying) {
                viewModel.updateCurrentTime(audioPlayer.currentPosition)
                binding.audioProgressSeekBar.progress =
                    viewModel.millisToSeconds(audioPlayer.currentPosition).toInt()
                handler.postDelayed(this, 1000)
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAudioPlayerBinding.inflate(layoutInflater, container, false)
        viewModel.setBook(args.book)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        audioPlayer = ExoPlayer.Builder(requireContext()).build()

        binding.audioPlayer.player = audioPlayer

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
        observeViewModel()

        binding.audioProgressSeekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val progressInMillis = viewModel.secondsToMillis(progress)
                viewModel.updateCurrentTime(progressInMillis)

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                audioPlayer.pause()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                audioPlayer.play()
                viewModel.currentTimeInMillis.value?.let { audioPlayer.seekTo(it) }
            }

        })
    }

    private fun setOnClickListeners() {
        setVolumeButtonOnClickListener()
        setPlayButtonOnClickListener()
        setSkipForwardButtonOnClickListener()
        setRewindButtonOnClickListener()
    }

    private fun setVolumeButtonOnClickListener() {
        binding.volumeImageButton.setOnClickListener {
            if (binding.volumeSeekBar.visibility == View.INVISIBLE) {
                binding.volumeSeekBar.visibility = View.VISIBLE

                timer.schedule(object : TimerTask() {
                    override fun run() {
                        binding.volumeSeekBar.visibility = View.INVISIBLE
                    }
                }, 3000)
            } else {
                binding.volumeSeekBar.visibility = View.INVISIBLE
            }
        }
    }

    private fun setPlayButtonOnClickListener() {
        binding.playImageButton.setOnClickListener {
            if (audioPlayer.isPlaying) {
                audioPlayer.pause()
                (it as ImageButton).setImageResource(R.drawable.ic_play_primary_purple)

            } else {
                audioPlayer.playWhenReady = true
                audioPlayer.play()
                (it as ImageButton).setImageResource(R.drawable.ic_pause_primary_purple)


            }
        }
    }

    private fun setSkipForwardButtonOnClickListener() {
        binding.skipForwardImageButton.setOnClickListener {
            val newCurrentTime = audioPlayer.currentPosition + 15000
            if (newCurrentTime < audioPlayer.duration) {
                audioPlayer.seekTo(newCurrentTime)
                setAudioSeekBarProgress(newCurrentTime)
            } else {
                audioPlayer.seekTo(audioPlayer.duration)
                audioPlayer.stop()
                setAudioSeekBarProgress(audioPlayer.duration)
            }
        }
    }

    private fun setRewindButtonOnClickListener() {
        binding.rewindImageButton.setOnClickListener {
            val newCurrentTime = audioPlayer.currentPosition - 15000
            if (newCurrentTime > 0) {
                audioPlayer.seekTo(newCurrentTime)
                setAudioSeekBarProgress(newCurrentTime)
            } else {
                audioPlayer.seekTo(0)
                setAudioSeekBarProgress(0)


            }

        }
    }

    private fun observeViewModel() {
        observeBook()
    }

    private fun observeBook() {
        viewModel.book.observe(viewLifecycleOwner) { book ->
            if (book != null) {
                if (book.audioUrl != null) {
                    audioPlayer.setMediaItem(MediaItem.fromUri(book.audioUrl))
                    audioPlayer.prepare()
                    addListenerToAudioPlayer()

                }
            }
        }
    }

    private fun addListenerToAudioPlayer() {
        audioPlayer.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == Player.STATE_READY) {
                    viewModel.setAudioDuration(audioPlayer.duration)
                    setAudioSeekBarMaxWithSeconds(viewModel.millisToSeconds(audioPlayer.duration))
                    setAudioSeekBarProgress(audioPlayer.currentPosition)
                    viewModel.updateCurrentTime(audioPlayer.currentPosition)
                }
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                if (isPlaying) {
                    handler.post(updateRunnable)
                } else {
                    handler.removeCallbacks(updateRunnable)
                }
            }
        })
    }

    private fun setAudioSeekBarProgress(currentTimeInMillis: Long) {
        binding.audioProgressSeekBar.progress =
            viewModel.millisToSeconds(currentTimeInMillis).toInt()
    }

    private fun setAudioSeekBarMaxWithSeconds(seconds: Long) {
        binding.audioProgressSeekBar.max =
            seconds.toInt()
    }
}



