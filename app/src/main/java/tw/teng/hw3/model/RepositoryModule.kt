package tw.teng.hw3.model

import org.koin.dsl.module
import tw.teng.hw3.repository.AppRepository

val repositoryModule = module {
    fun providerAppRepo(): AppRepository {
        return AppRepository()
    }
    single {
        providerAppRepo()
    }
}