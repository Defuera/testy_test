package com.justd.rabo.issues

import android.arch.lifecycle.Observer
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import com.justd.rabo.R
import com.justd.rabo.app.model.Issue
import kotterknife.bindView
import org.koin.android.viewmodel.ext.android.viewModel
import ru.justd.duperadapter.ArrayListDuperAdapter
import ru.justd.lilwidgets.LilLoaderWidget

class MainActivity : AppCompatActivity() {

    private val recycler by bindView<RecyclerView>(R.id.recycler)
    private val loader by bindView<LilLoaderWidget>(R.id.loader)

    private val adapter = ArrayListDuperAdapter().apply {
        addViewType<Issue, IssueWidget>(Issue::class.java)
            .addViewCreator { viewGroup ->
                val widget = IssueWidget(viewGroup.context)
                widget.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                widget
            }
            .addViewBinder { widget, item -> widget.bind(item) }
            .commit()
    }
    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler.adapter = adapter
        recycler.addItemDecoration(VerticalSpaceItemDecoration(resources.getDimensionPixelOffset(R.dimen.padding_8)))

        viewModel.stateHolder.liveData.observe(this, Observer { state ->
            if (state != null) {
                onNewState(state)
            }
        })
    }

    //region ui state

    private fun onNewState(state: ViewState) {
        when (state) {
            is ViewState.Loading -> showLoading()
            is ViewState.Error -> showError()
            is ViewState.Empty -> showEmpty()
            is ViewState.Data -> showData(state.items)
        }
    }

    private fun showData(items: List<Issue>) {
        recycler.visibility = View.VISIBLE
        loader.hide()
        adapter.items.clear()
        adapter.addAll(items)
        adapter.notifyDataSetChanged()
    }

    private fun showEmpty() {
        recycler.visibility = View.INVISIBLE
        loader.showNoDataError()
    }

    private fun showError() {
        recycler.visibility = View.INVISIBLE
        loader.showNetworkError()

    }

    private fun showLoading() {
        recycler.visibility = View.INVISIBLE
        loader.showLoading()

    }

    //endregion

}

private class VerticalSpaceItemDecoration(private val mVerticalSpaceHeight: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.bottom = mVerticalSpaceHeight
    }
}