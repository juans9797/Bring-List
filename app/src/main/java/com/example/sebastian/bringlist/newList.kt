package com.example.sebastian.bringlist


import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_new_list.*
import kotlinx.android.synthetic.main.app_bar_new_list.*
import kotlinx.android.synthetic.main.content_new_list.*
import kotlinx.android.synthetic.main.nav_header_new_list.*

class newList : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var mDatabase: DatabaseReference? = null
    private var mDatabase1: FirebaseDatabase? = null
    private var mDatabaseReference: DatabaseReference? = null
    private var mAuth: FirebaseAuth? = null
    private var name: String? = null
    private var email: String? = null
    private var consecutivo: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_list)
        setSupportActionBar(toolbar)

        mDatabase = FirebaseDatabase.getInstance().reference
        mAuth = FirebaseAuth.getInstance()
        val userId = mAuth!!.currentUser!!.uid
        mDatabase!!.child("Users").child(userId).child("name").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                name = dataSnapshot.getValue(String::class.java)
                tvNombre.text = name
            }
        })
        mDatabase!!.child("Users").child(userId).child("mail").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                email = dataSnapshot.getValue(String::class.java)
                tvCorreo.text = email
            }
        })
        mDatabase!!.child("Consec").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                consecutivo = dataSnapshot.getValue(String::class.java)
                tvIdList.text = consecutivo

            }
        })
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        val btn_click_me = findViewById<Button>(R.id.button6) as Button
        val vrName=findViewById<EditText>(R.id.etListName)
        val myList = Intent(this, myLists::class.java)
        mDatabase1 = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase1!!.reference.child("Lists")
        btn_click_me.setOnClickListener{
            val currentUserDb = mDatabaseReference!!.child(tvIdList.text.toString())
            currentUserDb.child("name").setValue(vrName.text.toString())
            var consec = tvIdList.text.toString()
            val newCon = consec.toInt() + 1
            mDatabaseReference = mDatabase1!!.reference
            val currentUserDb1 = mDatabaseReference
            currentUserDb1!!.child("Consec").setValue(newCon.toString())
            finish()
            startActivity(myList)


        }
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
        menuInflater.inflate(R.menu.new_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val myList = Intent(this, myLists::class.java)
        when (item.itemId) {
            R.id.myLists -> {
                finish()
                startActivity(myList)

            }
            }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
