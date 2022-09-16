package dev.katiebarnett.experiments.kotest

class FakeApi(): Api {

    override suspend fun getCyberImplants(): List<CyberImplant> {
        return listOf<CyberImplant>(CyberImplant("test1"))
    }

}