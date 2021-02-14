package com.example.testapp.presentation.ribs.root.find_flights.available_flights

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.domain.entity.flight.FlightOption
import com.example.domain.entity.flight.FlightOptions
import com.example.domain.mapper.Mapper
import com.example.testapp.R
import com.example.testapp.entity.FlightOptionVM
import com.example.testapp.mapper.FlightOptionToFlightOptionVMMapper
import com.example.testapp.presentation.ribs.root.find_flights.available_flights.flight_summary.FlightSummaryBuilder
import com.example.testapp.presentation.ribs.root.find_flights.available_flights.flight_summary.FlightSummaryInteractor
import com.uber.rib.core.InteractorBaseComponent
import com.uber.rib.core.ViewBuilder
import dagger.Binds
import dagger.BindsInstance
import dagger.Provides
import javax.inject.Scope

/**
 * Builder for the {@link AvailableFlightsScope}..
 */
class AvailableFlightsBuilder(dependency: ParentComponent) :
    ViewBuilder<AvailableFlightsView, AvailableFlightsRouter, AvailableFlightsBuilder.ParentComponent>(
        dependency
    ) {

    /**
     * Builds a new [AvailableFlightsRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [AvailableFlightsRouter].
     */
    fun build(parentViewGroup: ViewGroup, flightOptions: FlightOptions): AvailableFlightsRouter {
        val view = createView(parentViewGroup)
        val interactor = AvailableFlightsInteractor()
        val component = DaggerAvailableFlightsBuilder_Component.builder()
            .parentComponent(dependency)
            .view(view)
            .interactor(interactor)
            .flightOptions(flightOptions)
            .build()
        return component.availableflightsRouter()
    }

    override fun inflateView(
        inflater: LayoutInflater,
        parentViewGroup: ViewGroup,
    ): AvailableFlightsView {
        return inflater.inflate(
            R.layout.available_flights_rib,
            parentViewGroup,
            false
        ) as AvailableFlightsView
    }

    interface ParentComponent {
        fun availableFlightsListener(): AvailableFlightsInteractor.Listener
    }

    @dagger.Module
    abstract class Module {

        @AvailableFlightsScope
        @Binds
        internal abstract fun presenter(view: AvailableFlightsView): AvailableFlightsInteractor.AvailableFlightsPresenter

        @dagger.Module
        companion object {

            @AvailableFlightsScope
            @Provides
            @JvmStatic
            internal fun router(
                component: Component,
                view: AvailableFlightsView,
                interactor: AvailableFlightsInteractor,
            ): AvailableFlightsRouter = AvailableFlightsRouter(
                view,
                interactor,
                component,
                FlightSummaryBuilder(component)
            )

            @AvailableFlightsScope
            @Provides
            @JvmStatic
            internal fun flightOptionMapper(): Mapper<FlightOption, FlightOptionVM> =
                FlightOptionToFlightOptionVMMapper()

            @AvailableFlightsScope
            @Provides
            @JvmStatic
            internal fun flightSummaryListener(
                interactor: AvailableFlightsInteractor,
            ): FlightSummaryInteractor.Listener = interactor.FlightSummaryListener()
        }
    }

    @AvailableFlightsScope
    @dagger.Component(
        modules = [Module::class],
        dependencies = [ParentComponent::class]
    )
    interface Component : InteractorBaseComponent<AvailableFlightsInteractor>, BuilderComponent,
        FlightSummaryBuilder.ParentComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: AvailableFlightsInteractor): Builder

            @BindsInstance
            fun view(view: AvailableFlightsView): Builder

            @BindsInstance
            fun flightOptions(flightOptions: FlightOptions): Builder

            fun parentComponent(component: ParentComponent): Builder
            fun build(): Component
        }
    }

    interface BuilderComponent {
        fun availableflightsRouter(): AvailableFlightsRouter
    }

    @Scope
    @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
    internal annotation class AvailableFlightsScope
}
