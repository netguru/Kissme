package com.netguru.sample
import com.netguru.multiplatformstorage.MultiPlatformStorage

class MainPresenter {

    private var view: MainView? = null

    private val toDoList = mutableListOf<String>()

    private val storage by lazy {
        MultiPlatformStorage()
    }

    fun attachView(view: MainView) {
        println("attachView")
        this.view = view
        getToDoList()
    }

    fun detachView() {
        println("dettachView")
        this.view = null
    }

    fun addNewToDoElement(item: String) {
        toDoList.add(item)
        storage.putString(KEY_LIST, toDoList.joinToString(separator = STRING_LIST_SEPARATOR))
        storage.getAll()

        with(view!!) {
            showElementAddedInfo()
            showToDoList(toDoList)
        }
    }

    private fun getToDoList() {
        println("getToDoList")

        if (storage.contains(KEY_LIST)) {
            val elements = storage.getString(KEY_LIST, "")!!.split(STRING_LIST_SEPARATOR)
            println("fetched ${elements.size} data records")
            toDoList.addAll(elements)
        }
        println("calling view!!.showToDoList(toDoList). view == null = ${view == null}")
        view!!.showToDoList(toDoList)
    }

    companion object {
        private const val KEY_LIST = "key:list"
        private const val STRING_LIST_SEPARATOR = ","
    }
}
