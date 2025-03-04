package com.eminokumus.bookvox.audioplayer

import android.content.Context
import android.media.AudioManager
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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.eminokumus.bookvox.Constants
import com.eminokumus.bookvox.R
import com.eminokumus.bookvox.databinding.FragmentAudioPlayerBinding


class AudioPlayerFragment : Fragment() {
    private lateinit var binding: FragmentAudioPlayerBinding
    private val viewModel: AudioPlayerViewModel by viewModels()

    private val args: AudioPlayerFragmentArgs by navArgs()

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

        addListenerToAudioProgressSeekBar()
        changeVolumeWithVolumeSeekBar()

    }

    private fun setOnClickListeners() {
        setVolumeButtonOnClickListener()
        setPlayButtonOnClickListener()
        setNextButtonOnClickListener()
        setPreviousButtonOnClickListener()
        setBackButtonOnClickListener()
    }

    private fun setVolumeButtonOnClickListener() {
        binding.volumeImageButton.setOnClickListener {
            if (binding.volumeSeekBar.visibility == View.INVISIBLE) {
                binding.volumeSeekBar.visibility = View.VISIBLE


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
    private fun setNextButtonOnClickListener() {
        binding.nextImageButton.setOnClickListener {

            val newBookIndex = viewModel.book.value?.id?.plus(1) ?: -1
            if(newBookIndex in 0 until Constants.bookList.size){
                val newBook = Constants.bookList[newBookIndex]
                viewModel.setBook(newBook)
            }
        }


    }

    private fun setPreviousButtonOnClickListener() {
        binding.previousImageButton.setOnClickListener {
            val newBookIndex = viewModel.book.value?.id?.plus(-1) ?: -1
            if(newBookIndex in 0 until Constants.bookList.size){
                val newBook = Constants.bookList[newBookIndex]
                viewModel.setBook(newBook)
            }
        }
    }

    private fun setBackButtonOnClickListener(){
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
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


    private fun addListenerToAudioProgressSeekBar() {
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

    private fun changeVolumeWithVolumeSeekBar() {
        val audioManager = requireContext().getSystemService(Context.AUDIO_SERVICE) as AudioManager

        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        binding.volumeSeekBar.max = maxVolume

        val currVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        binding.volumeSeekBar.progress = currVolume

        binding.volumeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                binding.volumeSeekBar.visibility = View.INVISIBLE
            }
        })
    }
}



