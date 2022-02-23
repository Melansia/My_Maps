package com.example.my_maps

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.my_maps.models.Place
import com.example.my_maps.models.UserMap
import com.google.android.material.floatingactionbutton.FloatingActionButton

const val EXTRA_USER_MAP = "EXTRA_USER_MAP"
const val EXTRA_MAP_TITLE = "EXTRA_MAP_TITLE"
const val REQUEST_CODE = 1234
private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var rvMaps: RecyclerView
    private lateinit var fabCreateMap: FloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvMaps = findViewById(R.id.rvMaps)
        fabCreateMap = findViewById(R.id.fabCreateMap)

        val userMaps = generateSampleData()
        // Set layout manager on recycler view
        rvMaps.layoutManager = LinearLayoutManager(this)
        // Set adapter on recycler view
        rvMaps.adapter = MapsAdapter(this, userMaps, object : MapsAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
                Log.i(TAG, "onItemClick $position")
                // When user taps on view in RV, navigate to new activity
                val intent = Intent(this@MainActivity, DisplayMapActivity::class.java)
                intent.putExtra(EXTRA_USER_MAP, userMaps[position])
                startActivity(intent)
            }
        })

        fabCreateMap.setOnClickListener {
            Log.i(TAG, "Tap on FAB")
            val intent = Intent(this@MainActivity, CreateMapActivity::class.java)
            intent.putExtra(EXTRA_MAP_TITLE, "new map name")
            startActivityForResult(intent, REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Get New map data from the data
            val userMap = data?.getSerializableExtra(EXTRA_USER_MAP) as UserMap
            Log.i(TAG, "onActivityResult with new map title ${userMap.title}")
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun generateSampleData(): List<UserMap> {
        return listOf(
            UserMap(
                "Memories from Santorini",
                listOf(
                    Place(
                        "Andronis Arcadia",
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
                "Restaurants and CoffeeShops to try",
                listOf(
                    Place(
                        "Couleur Locale",
                        "Retro diner in Brooklyn",
                        37.97668477967003,
                        23.724492816257083
                    ),
                    Place(
                        "Ρίζα Ρίζα",
                        "A Cocktail bar with amazing burgers",
                        37.96562225041097,
                        23.725594087377033
                    ),
                    Place(
                        "ShirakiAthens",
                        "Traditional Japanese in Athens",
                        37.97471847120148,
                        23.7332011849714
                    ),
                    Place(
                        "Virtuoso_enjoyment",
                        "Pete's favorite Pastry shop",
                        37.90012382756551, 23.75016590748628
                    ),
                    Place(
                        "Romatella - Pizza al Taglio",
                        "Authentic Italian Pizza",
                        37.966951112258805, 23.728708839924245
                    )
                )
            )
        )
    }
}

