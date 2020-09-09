package com.bigtarakan.dynamicdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bigtarakan.dynamicdemo.view.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(findViewById(R.id.toolbar))
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.container,
                MainFragment/*.newInstance*/()
            ).commitNow()
        }
    }
}