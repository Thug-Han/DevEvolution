package com.thughan.kotlin.firstline

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.thughan.kotlin.KotlinConstants
import com.thughan.kotlin.R

@Route(path = KotlinConstants.ACTIVITY_FIRST_LINE_PATH)
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_kotlin)
    }
}