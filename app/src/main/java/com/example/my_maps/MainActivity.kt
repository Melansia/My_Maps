package com.example.my_maps

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.my_maps.models.Place
import com.example.my_maps.models.UserMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.*

const val EXTRA_USER_MAP = "EXTRA_USER_MAP"
const val EXTRA_MAP_TITLE = "EXTRA_MAP_TITLE"
const val FILENAME = "UserMap.data"
const val REQUEST_CODE = 1234
private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var rvMaps: RecyclerView
    private lateinit var fabCreateMap: FloatingActionButton

    private lateinit var userMaps: MutableList<UserMap>
    private lateinit var mapAdapter: MapsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvMaps = findViewById(R.id.rvMaps)
        fabCreateMap = findViewById(R.id.fabCreateMap)

        userMaps = deserializeUserMaps(this).toMutableList()
        // Set layout manager on recycler view
        rvMaps.layoutManager = LinearLayoutManager(this)
        // Set adapter on recycler view
        mapAdapter = MapsAdapter(this, userMaps, object : MapsAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
                Log.i(TAG, "onItemClick $position")
                // When user taps on view in RV, navigate to new activity
                val intent = Intent(this@MainActivity, DisplayMapActivity::class.java)
                intent.putExtra(EXTRA_USER_MAP, userMaps[position])
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        })
        rvMaps.adapter = mapAdapter

        fabCreateMap.setOnClickListener {
            Log.i(TAG, "Tap on FAB")
            showAlertDialog()
        }
    }

    private fun showAlertDialog() {
        val mapFormView = LayoutInflater.from(this).inflate(R.layout.dialog_create_map, null)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Map Title")
            .setView(mapFormView)
            .setNegativeButton("Cancel", null)
            .setPositiveButton("OK", null)
            .show()

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
            val title = mapFormView.findViewById<EditText>(R.id.etTitleMap).text.toString()
            if (title.trim().isEmpty()) {
                Toast.makeText(
                    this,
                    "Map must have a non-empty title",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }
            // Navigate to create map activity
            val intent = Intent(this@MainActivity, CreateMapActivity::class.java)
            intent.putExtra(EXTRA_MAP_TITLE, title)
            startActivityForResult(intent, REQUEST_CODE)
            dialog.dismiss()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Get New map data from the data
            val userMap = data?.getSerializableExtra(EXTRA_USER_MAP) as UserMap
            Log.i(TAG, "onActivityResult with new map title ${userMap.title}")
            userMaps.add(userMap)
            mapAdapter.notifyItemInserted(userMaps.size - 1)
            serializeUserMaps(this, userMaps)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun serializeUserMaps(context: Context, userMap: List<UserMap>) {
        Log.i(TAG, "serializeUserMaps")
        ObjectOutputStream(FileOutputStream(getDataFile(context))).use { it.writeObject(userMaps) }
    }

    private fun deserializeUserMaps(context: Context): List<UserMap> {
        Log.i(TAG, "deserializeUserMaps")
        val dataFile = getDataFile(context)
        if (!dataFile.exists()) {
            Log.i(TAG, "Data file does not exit yet")
            return emptyList()
        }
        ObjectInputStream(FileInputStream(dataFile)).use { return it.readObject() as List<UserMap> }
    }

    private fun getDataFile(context: Context): File {
        Log.i(TAG, "Getting file from directory ${context.filesDir}")
        return File(context.filesDir, FILENAME)
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
                "Once upon a time '2017' ",
                listOf(
                    Place("Inbi", "Great Moldovian meeting", 36.99647441097358, 21.648494371866093),
                    Place(
                        "Navarino Complex",
                        "Great Moldovians shelters",
                        36.99315005339711, 21.65695266441799
                    ),
                    Place(
                        "The American Diner",
                        "Second season job",
                        36.994327238315655,
                        21.652254775310546
                    ),
                    Place(
                        "Kookoonari Beach Bar",
                        "One of the season's greatest beach bar",
                        36.986371513078446, 21.652993723928486
                    ),
                    Place(
                        "Cafe Plateia",
                        "Cheapest after work food around",
                        36.999818612722954, 21.6814166
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
                "Restaurants and CoffeeShops in Athens to try",
                listOf(
                    Place(
                        "Couleur Locale",
                        "Retro diner in Brooklyn",
                        37.97668477967003,
                        23.724492816257083
                    ),
                    Place(
                        "???????? ????????",
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

