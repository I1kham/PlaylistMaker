package com.alchemtech.playlistmaker

import android.os.Bundle
import android.util.TypedValue
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alchemtech.playlistmaker.track.Track
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.text.SimpleDateFormat
import java.util.Locale


class PlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        val a: Int = R.id.albumCover

        backButWorking()
        val track = intent.getSerializableExtra("track") as Track


        val trackTitle: TextView = findViewById(R.id.playerTrackName)
        trackTitle.text = track.trackName


        val trackArtist: TextView = findViewById(R.id.playerArtistName)
        trackArtist.text = track.artistName


        val trackTimeMillis: TextView = findViewById(R.id.trackTimeMillisText)
        trackTimeMillis.text =
            SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)

        val collectionName = findViewById<TextView>(R.id.collectionNameText)
        if (track.collectionName.isNullOrEmpty()) {
            collectionName.text = track.collectionName
        } else {
            collectionName.text = ""
        }

        val releaseDate = findViewById<TextView>(R.id.releaseDateText)
        releaseDate.text = track.releaseDate.substring(0 until 4) + " год"
        val primaryGenreName = findViewById<TextView>(R.id.primaryGenreNameText)
        primaryGenreName.text = track.primaryGenreName

        val country = findViewById<TextView>(R.id.trackCountryText)
        country.text = track.country

        val playTime = findViewById<TextView>(R.id.playTime)
        playTime.text = "0:30"



        val albumCover: ImageView = findViewById(R.id.playerAlbumCover)

        Glide.with(this)
                .load(track.getCoverArtwork())
                .placeholder(R.drawable.track_album_default_big)
                .centerCrop()
                .transform(
                    RoundedCorners(
                        getPxFromDp(8f)
                    )
                )
                .into(albumCover)

        val addTrack = findViewById<ImageView>(R.id.playerAddToListBut)
        addTrack.setOnClickListener {
//SearchHistory().addTrackToList(track)
        }




    }

  private  fun getPxFromDp(dp: Float): Int {

    return  TypedValue.applyDimension(
          TypedValue.COMPLEX_UNIT_DIP,
          dp,
          resources.displayMetrics).toInt()
    }

    private fun backButWorking() {
        val back = findViewById<Button>(R.id.playerPreview)
        back.setOnClickListener {
            finish()
        }
    }

}

