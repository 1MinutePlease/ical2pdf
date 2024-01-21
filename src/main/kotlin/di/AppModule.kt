package di

import data.repository.ICSRepositoryImpl
import domain.repository.ICSRepository
import domain.use_cases.GeneratePdf
import domain.use_cases.GetEvents
import domain.use_cases.GroupEvents
import org.koin.dsl.module
import presentation.common.CalendarViewModel

object AppModule {
    val appModule = module {
        single<ICSRepository> { ICSRepositoryImpl() }
        factory {
            CalendarViewModel(
                getEvents = GetEvents(get()),
                groupEvents = GroupEvents(),
                generatePdf = GeneratePdf()
            )
        }
    }
}