package dev.katiebarnett.experiments.kotest

import io.kotest.core.spec.style.FunSpec
import io.kotest.core.test.TestCase
import io.kotest.matchers.shouldBe

class CyberImplantListViewModelTestsKotest : FunSpec() {

    lateinit var viewModel: CyberImplantListViewModel
//
//    override suspend fun beforeTest(testCase: TestCase) {
//        super.beforeTest(testCase)
////        viewModel = CyberImplantListViewModel(FakeApi())
//    }
//    
//    

    init {
        listeners(MainCoroutineListener())
        test("my first test").config(coroutineTestScope = true) {

            viewModel = CyberImplantListViewModel(FakeApi())
            viewModel.loadData()
            viewModel.staticData shouldBe listOf(CyberImplant("test1"))
//            viewModel.data.value shouldBe listOf(CyberImplant("test1"))
        }
    }
}