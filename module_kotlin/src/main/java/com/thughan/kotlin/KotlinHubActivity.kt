package com.thughan.kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.thughan.kotlin.firstline.MainActivity
import com.thughan.kotlin.jetpack.compose.ComposeActivity

@Route(path = KotlinConstants.ACTIVITY_HUB_PATH)
class KotlinHubActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hub)

        val btnCompose = findViewById<Button>(R.id.btn_compose)
        val btnFirstline = findViewById<Button>(R.id.btn_firstline)

        btnCompose.setOnClickListener {
            startActivity(Intent(this, ComposeActivity::class.java))
        }
        btnFirstline.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
