package di

import data.repository.ICSRepositoryImpl
import domain.repository.ICSRepository
import org.koin.dsl.module
import presentation.CalendarViewModel

object AppModule {
    val appModule = module {
        single<ICSRepository> { ICSRepositoryImpl() }
        factory {
            CalendarViewModel()
        }
    }
}