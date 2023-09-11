package com.indong.choirfinder.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.indong.choirfinder.R

class StartActivity : AppCompatActivity(){

    private val REQUEST_PERMISSIONS = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.startlayout)
        //Note 당분간은 권한에 따른 내용이 없기 때문에 주석처리 바로 실행
        checkPermission()
    }

    private fun checkPermission(){
        //Note 당분간은 권한에 따른 내용이 없기 때문에 주석처리 바로 실행
       /*
       val permission = mutableMapOf<String, String>()
        //permission["camera"]=Manifest.permission.CAMERA

        val denied = permission.count{ContextCompat.checkSelfPermission(this, it.value) == PackageManager.PERMISSION_DENIED}
        if(denied > 0) {
            requestPermissions(permission.values.toTypedArray(), REQUEST_PERMISSIONS)
        }
        else
        */
        run {
            initiateMainActivity()
        }
    }

    private fun initiateMainActivity()
    {
        val startIntent = Intent(this,MainActivity::class.java)
        startActivity(startIntent)
        finish()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSIONS) {
            /* 1. 권한 확인이 다 끝난 후 동의하지 않은것이 있다면 종료
            var count = grantResults.count { it == PackageManager.PERMISSION_DENIED } // 동의 안한 권한의 개수
            if(count != 0) {
                Toast.makeText(applicationContext, "서비스의 필요한 권한입니다.\n권한에 동의해주세요.", Toast.LENGTH_SHORT).show()
                finish()
            } */

            /* 2. 권한 요청을 거부했다면 안내 메시지 보여주며 앱 종료 */
            grantResults.forEach {
                if(it == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(applicationContext, getText(R.string.permission_request), Toast.LENGTH_SHORT).show()
                    finish()
                }
                else
                {
                    initiateMainActivity()
                }
            }
        }
    }
}