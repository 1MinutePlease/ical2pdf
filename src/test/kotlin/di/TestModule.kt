package di

import data.repository.FakeICSRepository
import domain.repository.ICSRepository
import domain.use_cases.GetEvents
import org.koin.dsl.module

object TestModule {

    val testModule = module {
        single<ICSRepository> { FakeICSRepository() }
        single<GetEvents> { GetEvents(get()) }
    }
}