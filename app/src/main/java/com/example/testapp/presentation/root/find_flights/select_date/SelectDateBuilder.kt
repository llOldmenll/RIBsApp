package com.example.testapp.presentation.root.find_flights.select_date

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
 * Builder for the {@link SelectDateScope}.
 *
 * TODO describe this scope's responsibility as a whole.
 */
class SelectDateBuilder(dependency: ParentComponent) :
    ViewBuilder<SelectDateView, SelectDateRouter, SelectDateBuilder.ParentComponent>(dependency) {

    /**
     * Builds a new [SelectDateRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [SelectDateRouter].
     */
    fun build(parentViewGroup: ViewGroup): SelectDateRouter {
        val view = createView(parentViewGroup)
        val interactor = SelectDateInteractor()
        val component = DaggerSelectDateBuilder_Component.builder()
            .parentComponent(dependency)
            .view(view)
            .interactor(interactor)
            .build()
        return component.selectdateRouter()
    }

    override fun inflateView(
        inflater: LayoutInflater,
        parentViewGroup: ViewGroup
    ): SelectDateView {
        return inflater.inflate(
            R.layout.select_date_rib,
            parentViewGroup,
            false
        ) as SelectDateView
    }

    interface ParentComponent {
        // TODO: Define dependencies required from your parent interactor here.
    }

    @dagger.Module
    abstract class Module {

        @SelectDateScope
        @Binds
        internal abstract fun presenter(view: SelectDateView): SelectDateInteractor.SelectDatePresenter

        @dagger.Module
        companion object {

            @SelectDateScope
            @Provides
            @JvmStatic
            internal fun router(
                component: Component,
                view: SelectDateView,
                interactor: SelectDateInteractor
            ): SelectDateRouter {
                return SelectDateRouter(view, interactor, component)
            }
        }

        // TODO: Create provider methods for dependencies created by this Rib. These should be static.
    }

    @SelectDateScope
    @dagger.Component(
        modules = [Module::class],
        dependencies = [ParentComponent::class]
    )
    interface Component : InteractorBaseComponent<SelectDateInteractor>, BuilderComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: SelectDateInteractor): Builder

            @BindsInstance
            fun view(view: SelectDateView): Builder

            fun parentComponent(component: ParentComponent): Builder
            fun build(): Component
        }
    }

    interface BuilderComponent {
        fun selectdateRouter(): SelectDateRouter
    }

    @Scope
    @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
    internal annotation class SelectDateScope

    @Qualifier
    @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
    internal annotation class SelectDateInternal
}
