package com.project.myrecyclerview

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var rvHeroes: RecyclerView
    private val list = ArrayList<Video>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvHeroes = findViewById(R.id.rv_heroes)
        rvHeroes.setHasFixedSize(true)

        list.addAll(getListHeroes())
        showRecyclerList()

        val outButton: Button = findViewById(R.id.out)
        outButton.setOnClickListener {
            goToLoginActivity()
        }
    }

    private fun goToLoginActivity() {
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
    }
    private fun isLoginActivity(): Boolean {
        return Login::class.java.isAssignableFrom(this::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (!isLoginActivity()) {
            menuInflater.inflate(R.menu.menu_main, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_list -> {
                rvHeroes.layoutManager = LinearLayoutManager(this)
            }
            R.id.action_grid -> {
                rvHeroes.layoutManager = GridLayoutManager(this, 2)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getListHeroes(): ArrayList<Video> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val videoId=resources.obtainTypedArray(R.array.video_id)
        val listVideos = ArrayList<Video>()
        for (i in dataName.indices) {
            val video = Video(dataPhoto.getResourceId(i, -1),dataName[i], dataDescription[i], videoId.getResourceId(i,-1))
            listVideos.add(video)
        }
        return listVideos
    }

    private fun showRecyclerList() {
        rvHeroes.layoutManager = LinearLayoutManager(this)
        val listVideoAdapter = ListVideoAdapter(list)
        rvHeroes.adapter = listVideoAdapter

        listVideoAdapter.setOnItemClickCallback(object : ListVideoAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Video){
                val intent = Intent(this@MainActivity, DetailActivity::class.java)

                intent.putExtra("HERO_DATA", data)

                startActivity(intent)



            }

        })
    }

    private fun showSelectedHero(video: Video) {
        Toast.makeText(this, "Kamu memilih " + video.name, Toast.LENGTH_SHORT).show()
    }
}