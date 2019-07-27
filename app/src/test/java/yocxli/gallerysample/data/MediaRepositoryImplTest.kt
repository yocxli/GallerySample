package yocxli.gallerysample.data

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock


@RunWith(AndroidJUnit4::class)
class MediaRepositoryImplTest {
    private val context: Context = ApplicationProvider.getApplicationContext()

    lateinit var repository: MediaRepositoryImpl

    @Mock
    lateinit var mockLocalDataStore: MediaDataStore

    @Mock
    lateinit var mockRemoteDataStore: MediaDataStore

    @Before
    fun setUp() {
        repository = MediaRepositoryImpl(mockLocalDataStore, mockRemoteDataStore)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun listAll() {
//        val cursor = object : RoboCursor() {
//            override fun registerContentObserver(observer: ContentObserver?) {
//                // no op
//            }
//
//            override fun unregisterContentObserver(observer: ContentObserver?) {
//                // no op
//            }
//
//            override fun registerDataSetObserver(observer: DataSetObserver?) {
//                // no op
//            }
//
//            override fun unregisterDataSetObserver(observer: DataSetObserver?) {
//                // no op
//            }
//
//            override fun isClosed(): Boolean {
//                return true
//            }
//        }
//        cursor.setColumnNames(
//            Arrays.asList(
//                MediaStore.Images.Media._ID,
//                MediaStore.Images.Media.BUCKET_DISPLAY_NAME
//            )
//        )
//        cursor.setResults(arrayOf(arrayOf(1L, "WhatsApp"), arrayOf(2L, "Photos"), arrayOf(3L, "WhatsApp")))
//        shadowOf(context.contentResolver)
//            .setCursor(MediaStore.Files.getContentUri("external"), cursor)
//        val result = repository.listAll()
//        assertThat(result.value?.size).isEqualTo(0)
    }

    @Test
    fun find() {
    }
}