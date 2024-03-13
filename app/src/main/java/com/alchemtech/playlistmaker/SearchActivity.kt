package com.alchemtech.playlistmaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val back = findViewById<Button>(R.id.pageSearchPreview)
        back.setOnClickListener {
            finish()
        }

        val inputEditText = findViewById<EditText>(R.id.inputEditText)
        val clearButton = findViewById<ImageView>(R.id.clearIcon)

        clearButton.setOnClickListener {
            inputEditText.setText("")
        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearButton.visibility = clearButtonVisibility(s)
            }

            override fun afterTextChanged(s: Editable?) {
                // empty
            }
        }
        inputEditText.addTextChangedListener(simpleTextWatcher)


        // Значение savedInstanceState равно null в том случае, если activity нечего восстанавливать.
        // То есть не произошло ситуации, при которой необходимо сохранить состояние
        if (savedInstanceState != null) {
            this.inputEditText = savedInstanceState.getString(STRING_AMOUNT, AMOUNT_DEF)
        }


    }

    private var inputEditText: String = AMOUNT_DEF

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STRING_AMOUNT, inputEditText)
    }

    // В Kotlin для создания константной переменной мы используем companion object.
// Ключ должен быть константным, чтобы мы точно знали, что он не изменится
    companion object {
        const val STRING_AMOUNT = "STRING_AMOUNT"
        const val AMOUNT_DEF = ""

    }

}

private fun clearButtonVisibility(s: CharSequence?): Int {
    return if (s.isNullOrEmpty()) {
        View.GONE
    } else {
        View.VISIBLE
    }
}


