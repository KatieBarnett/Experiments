package dev.katiebarnett.experiments.kotest

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import androidx.arch.core.executor.testing.InstantTaskExecutorRule

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class CyberImplantListViewModelTests {
    
    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesDispatcherRule = CoroutineDispatcherRule()

    val api = FakeApi() // FakeApi with canned responses
    val viewModel = CyberImplantListViewModel(api)

    @Test
    fun `when loadData() is called, should load data`() {
        viewModel.loadData()
        assertTrue(viewModel.data.value is List<CyberImplant>)
        assertTrue(viewModel.data.value?.size == 1)
        assertEquals(CyberImplant("test1"), viewModel.data.value?.get(0))
    }
}