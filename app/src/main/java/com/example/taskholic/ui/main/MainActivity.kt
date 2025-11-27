package com.example.taskholic.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.taskholic.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // этот лэйаут уже есть
    }
}
