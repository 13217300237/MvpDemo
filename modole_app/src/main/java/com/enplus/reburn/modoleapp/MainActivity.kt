package com.enplus.reburn.modoleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * 外壳模块，只用来 装载 业务功能模块
 * 本身没有功能，只是容器
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
