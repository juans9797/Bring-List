package com.example.sebastian.bringlist

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*

class home : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        val listView = findViewById<ListView>(R.id.homeListView)
        val listItems = arrayOfNulls<String>(3)
        listItems[0]="Carne"
        listItems[1]="Papa"
        listItems[2]="Gaseosa"
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,listItems)
        listView.adapter = adapter
        val Example = Intent(this, home::class.java)
        listView.setOnItemClickListener { _, _, _, _ -> finish();startActivity(Example) }


        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val newList = Intent(this,newList::class.java)
        val myList = Intent(this,myLists::class.java)
        val location = Intent(this,location1::class.java)
        when (item.itemId) {
            R.id.newList -> {
                finish()
                startActivity(newList)
            }
            R.id.myList -> {
                finish()
                startActivity(myList)

            }
            R.id.location -> {
                startActivity(location)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
