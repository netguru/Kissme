package com.netguru.multiplatform.sample

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView, AddItemDialogFragment.OnItemAddListener {

    private val adapter by lazy {
        MainAdapter()
    }
    private val presenter by lazy {
        MainPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.attachView(this)
        initRecyclerView()
        fabButton.setOnClickListener {
            AddItemDialogFragment.newInstance().show(supportFragmentManager, AddItemDialogFragment.TAG)
        }
        presenter.getToDoList()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun showElementAddedInfo() =
        Snackbar.make(findViewById(android.R.id.content), R.string.item_added, Snackbar.LENGTH_SHORT).show()

    override fun showToDoList(toDoList: List<String>) {
        adapter.itemsList = toDoList
    }

    override fun onItemAdded(item: String) {
        presenter.addNewToDoElement(item)
    }

    private fun initRecyclerView() = with(mainRecyclerView) {
        setHasFixedSize(true)
        addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        adapter = this@MainActivity.adapter
    }
}
