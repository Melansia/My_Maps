package com.example.my_maps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.my_maps.models.Place
import com.example.my_maps.models.UserMap

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var rvMaps: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvMaps = findViewById(R.id.rvMaps)

        val userMaps = generateSampleData()
        // Set layout manager on recycler view
        rvMaps.layoutManager = LinearLayoutManager(this)
        // Set adapter on recycler view
        rvMaps.adapter = MapsAdapter(this, userMaps, object : MapsAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
                Log.i(TAG, "onItemClick $position")
                // When user taps on view in RV, navigate to new activity
                val intent = Intent(this@MainActivity, DisplayMapActivity::class.java)
                startActivity(intent)

            }
        })

    }

    private fun generateSampleData(): List<UserMap> {
        return listOf(
            UserMap(
                "Memories from Santorini",
                listOf(
                    Place(
                        "Adronis Arcadia",
                        "First Job on Santorini",
                        36.464076219644205,
                        25.375465626410556
                    ),
                    Place(
                        "Karterados",
                        "Many long nights in this basement",
                        36.41116165310547,
                        25.448128214999908
                    ),
                    Place(
                        "Kamari Beach",
                        "First bath in that season",
                        36.376184674614684,
                        25.485675710073167
                    )
                )
            ),
            UserMap(
                "January vacation planning!",
                listOf(
                    Place("Tokyo", "Overnight layover", 35.67, 139.65),
                    Place("Ranchi", "Family visit + wedding!", 23.34, 85.31),
                    Place("Singapore", "Inspired by \"Crazy Rich Asians\"", 1.35, 103.82)
                )
            ),
            UserMap(
                "Singapore travel itinerary",
                listOf(
                    Place("Gardens by the Bay", "Amazing urban nature park", 1.282, 103.864),
                    Place(
                        "Jurong Bird Park",
                        "Family-friendly park with many varieties of birds",
                        1.319,
                        103.706
                    ),
                    Place("Sentosa", "Island resort with panoramic views", 1.249, 103.830),
                    Place(
                        "Botanic Gardens",
                        "One of the world's greatest tropical gardens",
                        1.3138,
                        103.8159
                    )
                )
            ),
            UserMap(
                "My favorite places in the Midwest",
                listOf(
                    Place(
                        "Chicago",
                        "Urban center of the midwest, the \"Windy City\"",
                        41.878,
                        -87.630
                    ),
                    Place("Rochester, Michigan", "The best of Detroit suburbia", 42.681, -83.134),
                    Place(
                        "Mackinaw City",
                        "The entrance into the Upper Peninsula",
                        45.777,
                        -84.727
                    ),
                    Place("Michigan State University", "Home to the Spartans", 42.701, -84.482),
                    Place("University of Michigan", "Home to the Wolverines", 42.278, -83.738)
                )
            ),
            UserMap(
                "Restaurants to try",
                listOf(
                    Place("Champ's Diner", "Retro diner in Brooklyn", 40.709, -73.941),
                    Place("Althea", "Chicago upscale dining with an amazing view", 41.895, -87.625),
                    Place("Shizen", "Elegant sushi in San Francisco", 37.768, -122.422),
                    Place(
                        "Citizen Eatery",
                        "Bright cafe in Austin with a pink rabbit",
                        30.322,
                        -97.739
                    ),
                    Place(
                        "Kati Thai",
                        "Authentic Portland Thai food, served with love",
                        45.505,
                        -122.635
                    )
                )
            )
        )
    }
}
