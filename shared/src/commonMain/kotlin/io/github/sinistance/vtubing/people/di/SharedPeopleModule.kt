package io.github.sinistance.vtubing.people.di

import io.github.sinistance.vtubing.people.data.PeopleRepositoryImpl
import io.github.sinistance.vtubing.people.data.mapper.PeopleDataMapper
import io.github.sinistance.vtubing.people.data.mapper.PeopleDataMapperImpl
import io.github.sinistance.vtubing.people.data.remote.PeopleService
import io.github.sinistance.vtubing.people.data.remote.PeopleServiceImpl
import io.github.sinistance.vtubing.people.domain.repository.PeopleRepository
import io.github.sinistance.vtubing.people.domain.usecase.PeopleUseCase
import io.github.sinistance.vtubing.people.domain.usecase.PeopleUseCaseImpl
import org.koin.dsl.module

val sharedPeopleModule = module {
    single<PeopleDataMapper> { PeopleDataMapperImpl() }
    single<PeopleService> { PeopleServiceImpl(get()) }
    single<PeopleRepository> { PeopleRepositoryImpl(get(), get()) }
    single<PeopleUseCase> { PeopleUseCaseImpl(get()) }
}