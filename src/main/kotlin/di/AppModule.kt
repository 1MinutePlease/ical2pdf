package di

import data.repository.ICSRepositoryImpl
import domain.repository.ICSRepository
import domain.use_cases.GetEvents
import domain.use_cases.GroupEvents
import org.koin.dsl.module
import presentation.CalendarViewModel

object AppModule {
    val appModule = module {
        single<ICSRepository> { ICSRepositoryImpl() }
        factory {
            CalendarViewModel(
                getEvents = GetEvents(get()),
                groupEvents = GroupEvents()
            )
        }
    }
}