package tw.teng.hw3

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import tw.teng.hw3.model.repositoryModule
import tw.teng.hw3.model.viewModelModule


class HW3Application : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            // Koin Android logger
            androidLogger()
            // inject Android context
            androidContext(this@HW3Application)
            // use modules
            modules(
                repositoryModule,
                viewModelModule
            )
        }
    }
}