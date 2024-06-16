package com.alchemtech.playlistmaker.presentation.ui.tracks

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import com.alchemtech.playlistmaker.creators.ListTrackRepositoryCreator
import com.alchemtech.playlistmaker.creators.SearchCreator
import com.alchemtech.playlistmaker.domain.api.TracksInteractor
import com.alchemtech.playlistmaker.domain.entity.Track


class TracksSearchPresenter(
    private val view: TracksView,
) {
    init {
        //getHistory()
    }

    private var searchText: String = ""

    private val tracksInteractor: TracksInteractor =
        SearchCreator.provideTracksInteractor()


    private val history =
        ListTrackRepositoryCreator.provideListTrackDb()

    internal fun clickOnTrack(track: Track) {
        addTrackToHistoryList(track)
        navigateToPlayer(track)
        println(track)
    }


//    private lateinit var clearButton: ImageView
//    private lateinit var upDateBut: Button
//    private lateinit var searchHistoryTitle: TextView
//    private lateinit var clearHistoryBut: TextView

//    private lateinit var inputEditText: EditText

//    private lateinit var trackRecyclerView: RecyclerView
//
//    private lateinit var progressBar: ProgressBar
//    private lateinit var noDataLinearLayout: LinearLayout
//    private lateinit var noConnectionLinearLayout: LinearLayout

    //   private var isClickAllowed: Boolean = true

    internal val tracksList = ArrayList<Track>()

    private val handler = Handler(Looper.getMainLooper())
    private val searchRunnable = Runnable { searchTrack() }

    private val tracksConsumer = object : TracksInteractor.TracksConsumer {
        override fun consume(foundedTracks: List<Track>?, errorMessage: String?) {

            handler.post {
                println(foundedTracks) // TODO:
                if (foundedTracks.isNullOrEmpty()) {

                    if (errorMessage.equals(" no connection")) {
                        view.showNoConnection(true)

                    } else {
                        view.showNoDataErr(true)
                    }
                } else {
                    tracksList.addAll(foundedTracks)
                    view.upDateRecycle()
                }

//
//                if (foundedTracks != null) {
////                    tracksList.clear()
//                    tracksList.addAll(foundedTracks)
//                }
//                if (errorMessage != null) {
////                    setNoConnectionErrLayoutVisible()
//                    view.showNoConnection(true)
//                } else if (foundedTracks.isNullOrEmpty()) {
////                    setNoDataErrLayoutVisible()
//                    view.showNoDataErr(true)
//                } else {
////                    setAllErrLayoutsGONE()
//                    view.showNoDataErr(false)
//                    view.showNoConnection(false)
//                }
                view.showProgressBar(false)
                println(tracksList)
                //setProgressBarGone() // TODO: реализацию логики проверки списка в репозиторий
            }
        }
    }


    fun onCreate() {
//        inputEditText = activity.findViewById(R.id.inputTextForSearching)
//
//        trackRecyclerView = activity.findViewById(R.id.trackCardsRecyclerView)
//
//        progressBar = activity.findViewById<ProgressBar>(R.id.progressBar)
//
//        noDataLinearLayout = activity.findViewById<LinearLayout>(R.id.noData)
//        noConnectionLinearLayout = activity.findViewById<LinearLayout>(R.id.noConnection)
//
//        clearButton = activity.findViewById<ImageView>(R.id.clearIcon)
//        upDateBut = activity.findViewById<Button>(R.id.updateButNoConnection)
//        searchHistoryTitle = activity.findViewById<TextView>(R.id.searchHistoryTitle)
//        clearHistoryBut = activity.findViewById<TextView>(R.id.clearHistoryBut)

        getHistory()

        // trackSearchAdapter = TrackSearchAdapter(tracksList)
//        trackSearchAdapter.onItemClick = { track: Track ->
//            addTrackToHistoryList(track)
//            navigateToPlayer(track)
//        }


//        trackRecyclerView.layoutManager =
//            LinearLayoutManager(
//                /* context = */ activity,
//                /* orientation = */ LinearLayoutManager.VERTICAL,
//                /* reverseLayout = */ false
//            )
//        trackRecyclerView.adapter = trackSearchAdapter


        //   inputEditTextWorking()

//        upDateButSearchWorking()

//        clearButLogic()
//
//        clearButSearchHistory()

//        enableHistoryList()

    }
//    fun addTextChangeListener(editText: EditText){
//           editText.addTextChangedListener(a())
//       }
//
//
//       private fun a():TextWatcher {
//           return object : TextWatcher {
//
//               override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
//               }
//
//               override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                   clearButVisibility(s)
//                   textChangeLogic(s)
//                   focusLogic()
//                   searchDebounce()
//               }
//
//               override fun afterTextChanged(s: Editable?) {
//                   focusLogic()
//                   textChangeLogic(s)
//               }
//           }
//
//
//       }

    fun onDestroy() {
        handler.removeCallbacks(searchRunnable)
    }

    fun onPause() {
        saveHistory()
    }

    private fun enableHistoryList() {
        val historyList = history.getTrackList()
        if (historyList.isNotEmpty()) {
            tracksList.clear()
            tracksList.addAll(historyList)
//            activity.runOnUiThread {
//                clearHistoryBut.visibility = View.VISIBLE
//                searchHistoryTitle.visibility = View.VISIBLE
//            }
//                 onItemClickToTrackCard.also { trackAdapter.onItemClick = it }
//                 recyclerView.adapter = trackAdapter
            view.showHistoryListButTitle(true)
        } else {
//            clearHistoryBut.visibility = View.GONE
//            searchHistoryTitle.visibility = View.GONE
            view.showHistoryListButTitle(false)
        }
    }


//    private fun disableHistoryList() {// TODO: changed and moved to Activity by showHistoryList fun
//        clearHistoryBut.visibility = View.GONE
//        searchHistoryTitle.visibility = View.GONE
//    }

//    @SuppressLint("NotifyDataSetChanged")
//    private fun upDateRecycle() {
//        activity.runOnUiThread {
//            trackRecyclerView.adapter?.notifyDataSetChanged()
//        }
//    }

    internal fun inputEditTextWorking(editText: EditText) {
        editText.setOnClickListener {
            // setAllErrLayoutsGONE()
            view.showNoDataErr(false)
            view.showNoConnection(false)
            editText.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    searchTrack() // выполнение задач

                    view.hideKeyBoard()
                }
                true
            }
        }
    }

//    private fun hideKeyBoard() {
//        val inputMethodManager =
//            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
//        inputMethodManager?.hideSoftInputFromWindow(inputEditText.windowToken, 0)
//    }
//
//    private fun setAllErrLayoutsGONE() {
//        noDataLinearLayout.visibility = View.GONE
//        noConnectionLinearLayout.visibility = View.GONE
//    }
//
//
//    private fun setNoDataErrLayoutVisible() {
//        noDataLinearLayout.visibility = View.VISIBLE
//        noConnectionLinearLayout.visibility = View.GONE
//    }
//
//    private fun setNoConnectionErrLayoutVisible() {
//        noDataLinearLayout.visibility = View.GONE
//        noConnectionLinearLayout.visibility = View.VISIBLE
//    }

    @SuppressLint("NotifyDataSetChanged")
    private fun searchTrack() {
        if (searchText.isNotEmpty()) {
            // setProgressBarVisible()
            view.showNoDataErr(false)
            view.showNoConnection(false)
            view.showProgressBar(true)
            tracksList.clear()
            view.hideKeyBoard()
            tracksInteractor.searchTracksInteractor(
                expression = searchText,
                consumer = tracksConsumer
            )
            // trackRecyclerView.adapter?.notifyDataSetChanged()
            //   view.showTrackRecycle(true)
            //view.upDateRecycle()
        }
    }

//    private fun setProgressBarGone() {
//        activity.runOnUiThread {
//            progressBar.visibility = View.GONE
//        }
//    }
//
//    private fun setProgressBarVisible() {
//        setAllErrLayoutsGONE()
//        progressBar.visibility = View.VISIBLE
//    }

    internal fun upDateButSearchWorking() {
        searchTrack()
    }

//    private fun clearButVisibility(s: CharSequence?) {
//        clearButton.isVisible = !(s.isNullOrEmpty())
//    }

    internal fun clearButLogic() {
//            inputEditText.setText("")
        view.clearInputText()
//            hideKeyBoard()
        view.hideKeyBoard()
        tracksList.clear()
//            setAllErrLayoutsGONE()
        view.showNoDataErr(false)
        view.showNoConnection(false)
//            trackRecyclerView.adapter?.notifyDataSetChanged()
        enableHistoryList()
        view.upDateRecycle()
    }

    internal fun clearButSearchHistory() {
        view.showHistoryListButTitle(true)
//            searchHistoryTitle.visibility = View.GONE
//            clearHistoryBut.visibility = View.GONE
        history.clearTracksList()
        tracksList.clear()
        view.upDateRecycle()
    }


    internal fun searchDebounce() {
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }

    private fun saveHistory() {// TODO: B_L
        history.writeTrackList()
    }

    private fun getHistory() {// TODO: B_L
        history.readTrackList()
        tracksList.addAll(history.getTrackList())
        println(history.getTrackList() + "8888888")
    }

    private fun addTrackToHistoryList(track: Track) { // TODO: B_L
        history.addTrack(track)
        tracksList.clear()
        tracksList.addAll(history.getTrackList())
        view.upDateRecycle()
    }


    private fun navigateToPlayer(track: Track) { // TODO: to del

        view.navigateTRackToPlayer(track)
    }

    internal fun textChangeLogic(text: CharSequence?) {
        if (text?.isNotEmpty() == true) {
            view.upDateRecycle()
//            disableHistoryList()
            view.showHistoryListButTitle(false)

        } else {
//            enableHistoryList()
//            setAllErrLayoutsGONE()
            view.showHistoryListButTitle(false)
            view.showNoDataErr(false)
            view.showNoConnection(false)
        }
    }

    internal fun focusLogic(editText: EditText) {
        editText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus && editText.text.isEmpty()) {
//                disableHistoryList()
                view.showHistoryListButTitle(true)
            }
        }
    }

    internal fun setSearchText(string: String) {
        searchText = string
    }

    internal fun backButLogic() {
        view.stopView()
    }

    private companion object {
        const val SEARCH_DEBOUNCE_DELAY = 2000L // TODO: to controller
//        const val CLICK_DEBOUNCE_DELAY = 1000L

    }
}