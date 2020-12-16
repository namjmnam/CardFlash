package com.lemol.cardflash

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class WordList : AppCompatActivity() {

    private lateinit var savedVocabs : SharedPreferences
    //Initiate SharedPreferences
    private val wordPair = testList()
    private val adapter = WordListAdapter(wordPair)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_list)

        savedVocabs = getSharedPreferences("DictList", Context.MODE_PRIVATE)
        //This line saves the savedVocabs as DictList.xml?

        //reinitialize. is there a way to simplify?
        //val wordPair = generateListFromSP()
        //val adapter = WordListAdapter(wordPair)

        findViewById<RecyclerView>(R.id.recycler_view).adapter = adapter
        findViewById<RecyclerView>(R.id.recycler_view).layoutManager = LinearLayoutManager(this)
        //line above won't work if databinding is not enabled on gradle
        findViewById<RecyclerView>(R.id.recycler_view).setHasFixedSize(true)
    }

    private fun addToSharedPreferences(word : String, answer : String) {
        val editor = savedVocabs.edit()
        editor.putString(findViewById<EditText>(R.id.addQuestion).text.toString(),
            findViewById<EditText>(R.id.addAnswer).text.toString())
        editor.apply()
    }

    fun insertItem(view : View) {
        val word = findViewById<EditText>(R.id.addQuestion).text.toString()
        val answer = findViewById<EditText>(R.id.addAnswer).text.toString()
        val newItem = WordPair(word, answer)
        addToSharedPreferences(word, answer)
        wordPair.add(1,newItem)
        //adapter.notifyItemInserted(1)
        //Toast.makeText(this, "Added $word / $answer", Toast.LENGTH_SHORT).show()
        adapter.notifyDataSetChanged()
        //why doesn't it refresh?
        findViewById<EditText>(R.id.addAnswer).onEditorAction(EditorInfo.IME_ACTION_DONE)
        findViewById<EditText>(R.id.addQuestion).onEditorAction(EditorInfo.IME_ACTION_DONE)
    }

    fun removeItem(view : View) {
        wordPair.removeAt(1)
        adapter.notifyItemRemoved(1)
    }

    private fun testList(size: Int = 300): ArrayList<WordPair> {
        //test list
        val list = ArrayList<WordPair>()
        list += WordPair("","")
        for (i in 0 until size) {
            val item = WordPair("word $i", "answer $i")
            list += item
        }
        return list
    }

    private fun dummyList(): ArrayList<WordPair> {
        //a dummy function to generate dummy pairs
        val list = ArrayList<WordPair>()
        list += WordPair("","")
        return list
    }

    private fun generateListFromSP(): ArrayList<WordPair> {
        //why doesn't it work without reinitializing?
        val vocabWords = savedVocabs.all.keys.toMutableList()
        val list = ArrayList<WordPair>()
        //list += WordPair("0","0")
        for (i in vocabWords) {
            val item = WordPair(i, savedVocabs.getString(i,"")?:"")
            list += item
        }
        return list
    }
}