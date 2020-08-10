package com.enplus.reburn.modoleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 外壳模块，只用来 装载 业务功能模块
 * 本身没有功能，只是容器
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn1.setOnClickListener {
            ARouter.getInstance().build("/business_mode/main").navigation()
        }

        btn2.setOnClickListener {
            ARouter.getInstance().build("/user/main").navigation()
        }
    }
}
