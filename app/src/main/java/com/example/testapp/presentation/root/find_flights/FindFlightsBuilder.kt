package com.example.testapp.presentation.root.find_flights

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
 * Builder for the {@link FindFlightsScope}.
 *
 * TODO describe this scope's responsibility as a whole.
 */
class FindFlightsBuilder(dependency: ParentComponent) :
    ViewBuilder<FindFlightsView, FindFlightsRouter, FindFlightsBuilder.ParentComponent>(dependency) {

    /**
     * Builds a new [FindFlightsRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [FindFlightsRouter].
     */
    fun build(parentViewGroup: ViewGroup): FindFlightsRouter {
        val view = createView(parentViewGroup)
        val interactor = FindFlightsInteractor()
        val component = DaggerFindFlightsBuilder_Component.builder()
            .parentComponent(dependency)
            .view(view)
            .interactor(interactor)
            .build()
        return component.findflightsRouter()
    }

    override fun inflateView(
        inflater: LayoutInflater,
        parentViewGroup: ViewGroup
    ): FindFlightsView {
        return inflater.inflate(
            R.layout.find_flights_rib,
            parentViewGroup,
            false
        ) as FindFlightsView
    }

    interface ParentComponent {
        // TODO: Define dependencies required from your parent interactor here.
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
                interactor: FindFlightsInteractor
            ): FindFlightsRouter {
                return FindFlightsRouter(view, interactor, component)
            }
        }

        // TODO: Create provider methods for dependencies created by this Rib. These should be static.
    }

    @FindFlightsScope
    @dagger.Component(
        modules = [Module::class],
        dependencies = [ParentComponent::class]
    )
    interface Component : InteractorBaseComponent<FindFlightsInteractor>, BuilderComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: FindFlightsInteractor): Builder

            @BindsInstance
            fun view(view: FindFlightsView): Builder

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
