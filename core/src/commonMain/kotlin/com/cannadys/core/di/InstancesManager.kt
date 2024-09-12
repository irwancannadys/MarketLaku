package com.cannadys.core.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewmodel.viewModelFactory
import com.cannadys.core.WeakReferenceCommon
import org.example.core.result.SharedResultManager

/**
 * Create the own Dependency Injection using WeakReference concept in Kotlin Native,
 * This is minimalistic approach of Koin
 * */
object InstancesManager {

    /**
     * Reference map for instance factory
     * */
    val referencesFactory = mutableMapOf<String, (InstancesManager) -> Any>()

    /**
     * Reference map for soft reference using WeakReference
     * */
    val instancesReference = mutableMapOf<String, WeakReferenceCommon<Any>>()

    /**
     * Same with [instancesReference], but this is for ViewModel instance using WeakReference
     * */
    val viewModelsReference = mutableMapOf<String, WeakReferenceCommon<ViewModel>>()

    val viewModelStore = ViewModelStore()

    /**
     * Save factory to [referencesFactory] as instance factory references using KClass identifier
     * */
    inline fun <reified T : Any> install(key: String = "", noinline block: (InstancesManager) -> T) {
        val name = T::class.simpleName.orEmpty() + key
        referencesFactory[name] = block
    }

    /**
     * Getter for check available soft reference using WeekReference, refer to [instancesReference].
     * if reference is available, get from WeakReference,
     * is reference is not available, create instance from factory and create it as WeakReference
     * and put into references map
     * */
    inline fun <reified T : Any> get(key: String = ""): T {
        val name = T::class.simpleName + key
        val factory = referencesFactory[name]
            ?: throw IllegalArgumentException("Factory of $name is not found")

        val weakReference =
            instancesReference[name] ?: WeakReferenceCommon(factory.invoke(this) as T).also {
                instancesReference[name] = it as WeakReferenceCommon<Any>
            }

        val instance = weakReference.get() ?: factory.invoke(this).also {
            instancesReference[name] = WeakReferenceCommon(it)
        }

        return requireNotNull(instance as T) { "Get instance $name failed!" }
    }

    /**
     * Same with [get], but refer to [viewModelsReference] and for factory using ViewModelStore
     * */
    inline fun <reified T : ViewModel> getViewModel(): T {
        val name = T::class.simpleName.orEmpty()
        val factory = referencesFactory[name]
            ?: throw IllegalArgumentException("Factory of $name is not found")

        val factoryVm = {
            ViewModelProvider.create(viewModelStore, viewModelFactory {
                addInitializer(T::class) {
                    factory.invoke(this@InstancesManager) as T
                }
            })[T::class]
        }

        val weakReference = viewModelsReference[name] ?: WeakReferenceCommon(factoryVm.invoke()).also {
                viewModelsReference[name] = it as WeakReferenceCommon<ViewModel>
            }

        val instance = weakReference.get() ?: factoryVm.invoke().also {
            viewModelsReference[name] = WeakReferenceCommon(it)
        }

        return requireNotNull(instance as T) { "Get viewmodel $name failed!" }
    }
}

/**
 * Getter viewmodel with [Lazy] type, avoid create viewmodel instance using
 * factory when class created.
 * */
@Composable
inline fun <reified T : ViewModel> viewModel(): Lazy<T> {
    return remember { lazy { InstancesManager.getViewModel() } }
}

@Composable
fun sharedResultManager(): Lazy<SharedResultManager> {
    return remember { lazy { InstancesManager.get() } }
}