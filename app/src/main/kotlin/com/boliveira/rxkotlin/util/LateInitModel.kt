package com.boliveira.rxkotlin.util

// We are using lateinitmodel to have loadable view models in fragments
// Android System when retrieve the Fragment (for example) from the stack
// Will always reinit all lateinit variables with this interface we are trying
// to workaround that issue
interface LateInitModel<T> {

    // This is the optional property that you need to initialize in your views
    var lateinitModel: T?

    // This is the forced unwrapped model
    var model: T
        get() {
            //ATTENTION: -> This will throw if your model was not inited just like lateinit variables
            if (!modelIsInited) {
                throw java.lang.Error("Model needs to be inited before propper use")
            }
            return lateinitModel!!
        }
        set(value) {
            lateinitModel = value
        }

    // Simple helper to set the model if the given parameter is notnull
    fun modelReplaceNonNull(viewModel: T?) {
        viewModel?.let {
            model = it
        }
    }

    val modelIsInited: Boolean
        get() = lateinitModel != null
}
