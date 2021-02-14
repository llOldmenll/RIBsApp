package com.example.testapp.presentation.ribs.root.find_flights

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.data.entity.flight.FlightsAvailabilityEntity
import com.example.data.network.NetworkServiceFactory
import com.example.data.network.mapper.FlightsAvailabilityEntityToFlightOptionsMapper
import com.example.data.network.service.FlightNetworkService
import com.example.data.repository.FlightRepositoryImpl
import com.example.domain.entity.flight.FlightOptions
import com.example.domain.entity.station.Stations
import com.example.domain.mapper.Mapper
import com.example.domain.repository.FlightRepository
import com.example.domain.use_case.GetFlightOptionsUseCase
import com.example.testapp.R
import com.example.testapp.presentation.ribs.root.find_flights.select_airport.SelectAirportBuilder
import com.example.testapp.presentation.ribs.root.find_flights.select_airport.SelectAirportInteractor
import com.example.testapp.presentation.ribs.root.find_flights.select_passengers.SelectPassengersBuilder
import com.example.testapp.presentation.ribs.root.find_flights.select_passengers.SelectPassengersInteractor
import com.uber.rib.core.InteractorBaseComponent
import com.uber.rib.core.ViewBuilder
import dagger.Binds
import dagger.BindsInstance
import dagger.Provides
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Scope

/**
 * Builder for the {@link FindFlightsScope}.
 */
class FindFlightsBuilder(dependency: ParentComponent) :
    ViewBuilder<FindFlightsView, FindFlightsRouter, FindFlightsBuilder.ParentComponent>(dependency) {

    /**
     * Builds a new [FindFlightsRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [FindFlightsRouter].
     */
    fun build(parentViewGroup: ViewGroup, stations: Stations): FindFlightsRouter {
        val view = createView(parentViewGroup)
        val interactor = FindFlightsInteractor()
        val component = DaggerFindFlightsBuilder_Component.builder()
            .parentComponent(dependency)
            .view(view)
            .interactor(interactor)
            .stations(stations)
            .build()
        return component.findflightsRouter()
    }

    override fun inflateView(
        inflater: LayoutInflater,
        parentViewGroup: ViewGroup,
    ): FindFlightsView {
        return inflater.inflate(
            R.layout.find_flights_rib,
            parentViewGroup,
            false
        ) as FindFlightsView
    }

    interface ParentComponent {
        @Named("prod")
        fun prodNetworkServiceFactory(): NetworkServiceFactory
    }

    @dagger.Module
    abstract class Module {

        @FindFlightsScope
        @Binds
        internal abstract fun presenter(view: FindFlightsView): FindFlightsInteractor.FindFlightsPresenter

        @dagger.Module
        companion object {

            @FindFlightsScope
            @Provides
            @JvmStatic
            internal fun router(
                component: Component,
                view: FindFlightsView,
                interactor: FindFlightsInteractor,
            ): FindFlightsRouter {
                return FindFlightsRouter(view,
                    interactor,
                    component,
                    SelectAirportBuilder(component),
                    SelectPassengersBuilder(component))
            }

            @FindFlightsScope
            @Provides
            @JvmStatic
            internal fun flightNetworkService(
                @Named("prod")
                networkServiceFactory: NetworkServiceFactory,
            ): FlightNetworkService = networkServiceFactory.create(FlightNetworkService::class.java)

            @FindFlightsScope
            @Provides
            @JvmStatic
            internal fun flightsAvailabilityMapper(): Mapper<FlightsAvailabilityEntity, FlightOptions> =
                FlightsAvailabilityEntityToFlightOptionsMapper()

            @FindFlightsScope
            @Provides
            @JvmStatic
            internal fun flightRepository(
                flightNetworkService: FlightNetworkService,
                flightsAvailabilityMapper: Mapper<FlightsAvailabilityEntity, FlightOptions>,
            ): FlightRepository =
                FlightRepositoryImpl(flightNetworkService, flightsAvailabilityMapper)

            @FindFlightsScope
            @Provides
            @JvmStatic
            internal fun getFlightOptionsUseCase(
                flightRepository: FlightRepository,
            ): GetFlightOptionsUseCase = GetFlightOptionsUseCase(flightRepository)

            @FindFlightsScope
            @Provides
            @JvmStatic
            internal fun selectAirportListener(
                interactor: FindFlightsInteractor,
            ): SelectAirportInteractor.Listener = interactor.SelectAirportListener()

            @FindFlightsScope
            @Provides
            @JvmStatic
            internal fun selectPassengersListener(
                interactor: FindFlightsInteractor,
            ): SelectPassengersInteractor.Listener = interactor.SelectPassengersListener()
        }
    }

    @FindFlightsScope
    @dagger.Component(
        modules = [Module::class],
        dependencies = [ParentComponent::class]
    )
    interface Component : InteractorBaseComponent<FindFlightsInteractor>, BuilderComponent,
        SelectAirportBuilder.ParentComponent, SelectPassengersBuilder.ParentComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: FindFlightsInteractor): Builder

            @BindsInstance
            fun view(view: FindFlightsView): Builder

            @BindsInstance
            fun stations(stations: Stations): Builder

            fun parentComponent(component: ParentComponent): Builder
            fun build(): Component
        }
    }

    interface BuilderComponent {
        fun findflightsRouter(): FindFlightsRouter
    }

    @Scope
    @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
    internal annotation class FindFlightsScope

    @Qualifier
    @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
    internal annotation class FindFlightsInternal
}
