package com.alchemtech.playlistmaker

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.core.view.isVisible

class SearchActivity : AppCompatActivity() {

   private var inputString: String = AMOUNT_DEF

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)



        val clearButton = findViewById<ImageView>(R.id.clearIcon)
        val back = findViewById<Button>(R.id.pageSearchPreview)
        val inputEditText  = findViewById<EditText>(R.id.inputEditText)
        back.setOnClickListener {
            finish()
        }

        clearButton.setOnClickListener {
            inputEditText.setText("")

            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(inputEditText.windowToken, 0)
        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearButton.isVisible = !(s.isNullOrEmpty())

            }

            override fun afterTextChanged(s: Editable?) {
                if (savedInstanceState != null) {
                    onSaveInstanceState(savedInstanceState)
                    inputString= s.toString()
                }
            }
        }
        inputEditText.addTextChangedListener(simpleTextWatcher)



    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(/* key = */ STRING_AMOUNT, /* value = */ inputString)
    }
    // В Kotlin для создания константной переменной мы используем companion object.
// Ключ должен быть константным, чтобы мы точно знали, что он не изменится

    private companion object {
        const val STRING_AMOUNT = "STRING_AMOUNT"
        const val AMOUNT_DEF = ""
    }
}



