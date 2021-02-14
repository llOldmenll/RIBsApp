package com.example.testapp.presentation.ribs.root.find_flights.available_flights

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.testapp.R
import com.uber.rib.core.InteractorBaseComponent
import com.uber.rib.core.ViewBuilder
import dagger.Binds
import dagger.BindsInstance
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Scope

/**
 * Builder for the {@link AvailableFlightsScope}.
 *
 * TODO describe this scope's responsibility as a whole.
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
    fun build(parentViewGroup: ViewGroup): AvailableFlightsRouter {
        val view = createView(parentViewGroup)
        val interactor = AvailableFlightsInteractor()
        val component = DaggerAvailableFlightsBuilder_Component.builder()
            .parentComponent(dependency)
            .view(view)
            .interactor(interactor)
            .build()
        return component.availableflightsRouter()
    }

    override fun inflateView(
        inflater: LayoutInflater,
        parentViewGroup: ViewGroup
    ): AvailableFlightsView {
        return inflater.inflate(
            R.layout.available_flights_rib,
            parentViewGroup,
            false
        ) as AvailableFlightsView
    }

    interface ParentComponent {
        // TODO: Define dependencies required from your parent interactor here.
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
                interactor: AvailableFlightsInteractor
            ): AvailableFlightsRouter {
                return AvailableFlightsRouter(view, interactor, component)
            }
        }

        // TODO: Create provider methods for dependencies created by this Rib. These should be static.
    }

    @AvailableFlightsScope
    @dagger.Component(
        modules = [Module::class],
        dependencies = [ParentComponent::class]
    )
    interface Component : InteractorBaseComponent<AvailableFlightsInteractor>, BuilderComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: AvailableFlightsInteractor): Builder

            @BindsInstance
            fun view(view: AvailableFlightsView): Builder

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

    @Qualifier
    @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
    internal annotation class AvailableFlightsInternal
}
