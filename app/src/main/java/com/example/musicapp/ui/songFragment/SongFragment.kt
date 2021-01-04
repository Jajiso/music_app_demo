package com.example.musicapp.ui.songFragment

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.musicapp.R
import com.example.musicapp.data.local.AppDatabase
import com.example.musicapp.data.local.LocalDataSource
import com.example.musicapp.data.remote.NetworkDataSource
import com.example.musicapp.databinding.FragmentSongBinding
import com.example.musicapp.domain.Repository
import com.example.musicapp.viewmodel.SongViewModel
import com.example.musicapp.viewmodel.factory.VMFactory
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

class SongFragment : Fragment() {

    private val mediaPlayer = MediaPlayer()
    private val viewModel by activityViewModels<SongViewModel> {
        VMFactory(
            Repository(
                NetworkDataSource(),
                LocalDataSource(
                    AppDatabase.getDatabase( requireContext() )
                )
            )
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_song, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentSongBinding.bind(view)

        setupObservers(binding)
    }

    override fun onPause() {
        mediaPlayer.stop()
        mediaPlayer.reset()
        super.onPause()
    }

    private fun setupObservers(binding: FragmentSongBinding) {
        viewModel.isLiked.observe(viewLifecycleOwner, Observer { isLiked ->
            if (isLiked) {
                binding.imageLike.setImageResource(R.drawable.ic_like_yes)
            }else {
                binding.imageLike.setImageResource(R.drawable.ic_like_no)
            }
        })

        viewModel.song.observe(viewLifecycleOwner, Observer { song ->
            if (song == null) {
                findNavController().navigate(R.id.action_songFragment_to_songListFragment)
            } else {
                viewModel.setIsLiked(song)
                binding.imageLike.setOnClickListener {
                    viewModel.deleteOrInsertLikedSong(song)
                }
                binding.tvTitleSongName.text = song.name

                mediaPlayer.setDataSource(song.previewUrl)

                setupMediaPlayer(binding)
                setupMediaPlayerComponents(binding)
            }
        })
    }

    private fun setupMediaPlayer(binding: FragmentSongBinding) {
        mediaPlayer.prepareAsync()

        mediaPlayer.setOnPreparedListener { mp ->
            binding.seekBarPlayer.max = mediaPlayer.duration
            binding.tvMaximumDuration.text = convertFormat(mediaPlayer.duration)
            mediaPlayer.start()
            binding.imagePlayPause.setImageResource(R.drawable.ic_pause)
            updateSeekBar(binding.seekBarPlayer, mp)
        }

        mediaPlayer.setOnBufferingUpdateListener { mp, percent ->
            binding.seekBarPlayer.secondaryProgress = (mp.duration.times((percent / 100)))
        }

        mediaPlayer.setOnCompletionListener { mp ->
            binding.imagePlayPause.setImageResource(R.drawable.ic_play)
            mp.seekTo(0)
            binding.seekBarPlayer.setProgress(0, true)
        }

    }

    private fun setupMediaPlayerComponents(binding: FragmentSongBinding) {
        binding.seekBarPlayer.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged( seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser) {
                    mediaPlayer.seekTo(progress)
                    seekBar?.progress = progress
                }
                binding.tvCurrentTime.text = convertFormat(mediaPlayer.currentPosition)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.imagePlayPause.setOnClickListener {
            if (mediaPlayer.isPlaying ) {
                mediaPlayer.pause()
                binding.imagePlayPause.setImageResource(R.drawable.ic_play)
            } else {
                mediaPlayer.start()
                binding.imagePlayPause.setImageResource(R.drawable.ic_pause)
                updateSeekBar(binding.seekBarPlayer, mediaPlayer)
            }
        }
    }

    private fun convertFormat(duration: Int): String {
        return String.format("%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(duration.toLong()),
            TimeUnit.MILLISECONDS.toSeconds(duration.toLong()) -
            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration.toLong()))
        )
    }

    private fun updateSeekBar(seekBar: SeekBar, mediaPlayer: MediaPlayer) {
        GlobalScope.launch {
            while (mediaPlayer.isPlaying ) {
                seekBar.progress = mediaPlayer.currentPosition
            }
        }
    }

}