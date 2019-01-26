package yocxli.gallerysample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import yocxli.gallerysample.ui.list.MediaFileFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.content, MediaFileFragment.newInstance(4))
            .commit()
    }
}
