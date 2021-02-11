package com.example.testapp.presentation.root.find_flights.select_passengers

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
 * Builder for the {@link SelectPassengersScope}.
 *
 * TODO describe this scope's responsibility as a whole.
 */
class SelectPassengersBuilder(dependency: ParentComponent) :
    ViewBuilder<SelectPassengersView, SelectPassengersRouter, SelectPassengersBuilder.ParentComponent>(
        dependency
    ) {

    /**
     * Builds a new [SelectPassengersRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [SelectPassengersRouter].
     */
    fun build(parentViewGroup: ViewGroup): SelectPassengersRouter {
        val view = createView(parentViewGroup)
        val interactor = SelectPassengersInteractor()
        val component = DaggerSelectPassengersBuilder_Component.builder()
            .parentComponent(dependency)
            .view(view)
            .interactor(interactor)
            .build()
        return component.selectpassengersRouter()
    }

    override fun inflateView(
        inflater: LayoutInflater,
        parentViewGroup: ViewGroup
    ): SelectPassengersView {
        return inflater.inflate(
            R.layout.select_passengers_rib,
            parentViewGroup,
            false
        ) as SelectPassengersView
    }

    interface ParentComponent {
        // TODO: Define dependencies required from your parent interactor here.
    }

    @dagger.Module
    abstract class Module {

        @SelectPassengersScope
        @Binds
        internal abstract fun presenter(view: SelectPassengersView): SelectPassengersInteractor.SelectPassengersPresenter

        @dagger.Module
        companion object {

            @SelectPassengersScope
            @Provides
            @JvmStatic
            internal fun router(
                component: Component,
                view: SelectPassengersView,
                interactor: SelectPassengersInteractor
            ): SelectPassengersRouter {
                return SelectPassengersRouter(view, interactor, component)
            }
        }

        // TODO: Create provider methods for dependencies created by this Rib. These should be static.
    }

    @SelectPassengersScope
    @dagger.Component(modules = [Module::class], dependencies = [ParentComponent::class])
    interface Component : InteractorBaseComponent<SelectPassengersInteractor>, BuilderComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: SelectPassengersInteractor): Builder

            @BindsInstance
            fun view(view: SelectPassengersView): Builder

            fun parentComponent(component: ParentComponent): Builder
            fun build(): Component
        }
    }

    interface BuilderComponent {
        fun selectpassengersRouter(): SelectPassengersRouter
    }

    @Scope
    @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
    internal annotation class SelectPassengersScope

    @Qualifier
    @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
    internal annotation class SelectPassengersInternal
}
