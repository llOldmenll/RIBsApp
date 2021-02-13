package com.example.testapp.presentation.root.find_flights.select_airport

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.domain.entity.station.Stations
import com.example.testapp.R
import com.example.testapp.presentation.root.find_flights.entities.AirPortType
import com.uber.rib.core.InteractorBaseComponent
import com.uber.rib.core.ViewBuilder
import dagger.Binds
import dagger.BindsInstance
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Scope

/**
 * Builder for the {@link SelectAirportScope}.
 */
class SelectAirportBuilder(dependency: ParentComponent) :
    ViewBuilder<SelectAirportView, SelectAirportRouter, SelectAirportBuilder.ParentComponent>(
        dependency
    ) {

    /**
     * Builds a new [SelectAirportRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [SelectAirportRouter].
     */
    fun build(parentViewGroup: ViewGroup, airPortType: AirPortType): SelectAirportRouter {
        val view = createView(parentViewGroup)
        val interactor = SelectAirportInteractor()
        val component = DaggerSelectAirportBuilder_Component.builder()
            .parentComponent(dependency)
            .view(view)
            .interactor(interactor)
            .airPortType(airPortType)
            .build()
        return component.selectairportRouter()
    }

    override fun inflateView(
        inflater: LayoutInflater,
        parentViewGroup: ViewGroup,
    ): SelectAirportView {
        return inflater.inflate(
            R.layout.select_airport_rib,
            parentViewGroup,
            false
        ) as SelectAirportView
    }

    interface ParentComponent {
        fun stations(): Stations
        fun selectAirPortListener(): SelectAirportInteractor.Listener
    }

    @dagger.Module
    abstract class Module {

        @SelectAirportScope
        @Binds
        internal abstract fun presenter(view: SelectAirportView): SelectAirportInteractor.SelectAirportPresenter

        @dagger.Module
        companion object {

            @SelectAirportScope
            @Provides
            @JvmStatic
            internal fun router(
                component: Component,
                view: SelectAirportView,
                interactor: SelectAirportInteractor,
            ): SelectAirportRouter = SelectAirportRouter(view, interactor, component)
        }
    }

    @SelectAirportScope
    @dagger.Component(
        modules = [Module::class],
        dependencies = [ParentComponent::class]
    )
    interface Component : InteractorBaseComponent<SelectAirportInteractor>, BuilderComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: SelectAirportInteractor): Builder

            @BindsInstance
            fun view(view: SelectAirportView): Builder

            @BindsInstance
            fun airPortType(airPortType: AirPortType): Builder

            fun parentComponent(component: ParentComponent): Builder
            fun build(): Component
        }
    }

    interface BuilderComponent {
        fun selectairportRouter(): SelectAirportRouter
    }

    @Scope
    @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
    internal annotation class SelectAirportScope

    @Qualifier
    @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
    internal annotation class SelectAirportInternal
}
