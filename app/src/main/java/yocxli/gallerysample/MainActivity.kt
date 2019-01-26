package yocxli.gallerysample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
