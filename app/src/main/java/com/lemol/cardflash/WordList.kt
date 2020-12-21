package com.lemol.cardflash

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class WordList : AppCompatActivity(), WordListAdapter.OnItemClickListener {

    //private lateinit var savedVocabs : SharedPreferences
    //Initiate SharedPreferences
    //private val wordPair = generateListFromSP()
    //private val adapter = WordListAdapter(wordPair)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_list)

        //savedVocabs = getSharedPreferences("DictList", Context.MODE_PRIVATE)
        //This line saves the savedVocabs as DictList.xml?

        //reinitialize. is there a way to simplify?
        val wordPair = generateListFromSP()
        val adapter = WordListAdapter(wordPair,  this)

        findViewById<RecyclerView>(R.id.recycler_view).adapter = adapter
        findViewById<RecyclerView>(R.id.recycler_view).layoutManager = LinearLayoutManager(this)
        //line above won't work if databinding is not enabled on gradle
        findViewById<RecyclerView>(R.id.recycler_view).setHasFixedSize(true)
    }

    private fun addToSharedPreferences(word : String, answer : String) {
        //val editor = savedVocabs.edit()
        val shared = getSharedPreferences("DictList", Context.MODE_PRIVATE)
        val editor = shared.edit()
        editor.putString(findViewById<EditText>(R.id.addQuestion).text.toString(),
                findViewById<EditText>(R.id.addAnswer).text.toString())
        editor.apply()
    }

    fun insertItem(view : View) {
        val word = findViewById<EditText>(R.id.addQuestion).text.toString()
        val answer = findViewById<EditText>(R.id.addAnswer).text.toString()
        val newItem = WordPair(word, answer)
        addToSharedPreferences(word, answer)

        //do i have to do this all the time?
        val wordPair = generateListFromSP()
        val adapter = WordListAdapter(wordPair, this)
        findViewById<RecyclerView>(R.id.recycler_view).adapter = adapter
        findViewById<RecyclerView>(R.id.recycler_view).layoutManager = LinearLayoutManager(this)
        findViewById<RecyclerView>(R.id.recycler_view).setHasFixedSize(true)

        findViewById<EditText>(R.id.addQuestion).setText("")
        findViewById<EditText>(R.id.addAnswer).setText("")

        wordPair.add(1,newItem)
        //adapter.notifyItemInserted(1)
        refreshItems()
        Toast.makeText(this, "Added $word", Toast.LENGTH_SHORT).show()
    }

    fun sortItems(view : View) {
        //do i have to do this all the time?
        /*
        val wordPair = generateSortedList()
        val adapter = WordListAdapter(wordPair, this)
        findViewById<RecyclerView>(R.id.recycler_view).adapter = adapter
        findViewById<RecyclerView>(R.id.recycler_view).layoutManager = LinearLayoutManager(this)
        findViewById<RecyclerView>(R.id.recycler_view).setHasFixedSize(true)

        adapter.notifyDataSetChanged()
         */
    } //unused

    fun shuffleItems(view : View) {
        //unused
        //do i have to do this all the time?
        /*
        val wordPair = generateShuffledList()
        val adapter = WordListAdapter(wordPair, this)
        findViewById<RecyclerView>(R.id.recycler_view).adapter = adapter
        findViewById<RecyclerView>(R.id.recycler_view).layoutManager = LinearLayoutManager(this)
        findViewById<RecyclerView>(R.id.recycler_view).setHasFixedSize(true)

        adapter.notifyDataSetChanged()
         */
    } //unused

    private fun refreshItems() {
        //unused
        //do i have to do this all the time?
        val wordPair = generateListFromSP()
        val adapter = WordListAdapter(wordPair, this)
        findViewById<RecyclerView>(R.id.recycler_view).adapter = adapter
        findViewById<RecyclerView>(R.id.recycler_view).layoutManager = LinearLayoutManager(this)
        findViewById<RecyclerView>(R.id.recycler_view).setHasFixedSize(true)

        adapter.notifyDataSetChanged()
    }

    override fun onItemClick(position: Int) {
        //do i have to do this all the time?
        val wordPair = generateListFromSP()
        //val adapter = WordListAdapter(wordPair, this)
        //findViewById<RecyclerView>(R.id.recycler_view).adapter = adapter
        //findViewById<RecyclerView>(R.id.recycler_view).layoutManager = LinearLayoutManager(this)
        //findViewById<RecyclerView>(R.id.recycler_view).setHasFixedSize(true)

        val clickedItem : WordPair = wordPair[position]
        Toast.makeText(this, "Removed ${clickedItem.word}", Toast.LENGTH_SHORT).show()

        val shared = getSharedPreferences("DictList", Context.MODE_PRIVATE)
        val editor = shared.edit()
        editor.remove(clickedItem.word)
        editor.apply()
        refreshItems()
    }

    /*
    private fun testList(size: Int = 100): ArrayList<WordPair> {
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
     */

    private fun generateListFromSP(): ArrayList<WordPair> {
        //why doesn't it work without reinitializing?
        val shared = getSharedPreferences("DictList", Context.MODE_PRIVATE)
        val vocabWords = shared.all.keys.toMutableList()
        val list = ArrayList<WordPair>()
        //list += WordPair("0","0")
        for (i in vocabWords) {
            val item = WordPair(i, shared.getString(i,"")?:"")
            list += item
        }
        return list
    }

    /*
    private fun generateSortedList(): ArrayList<WordPair> {
        //why doesn't it work without reinitializing?
        val shared = getSharedPreferences("DictList", Context.MODE_PRIVATE)
        val vocabWords = shared.all.keys.toMutableList()
        val list = ArrayList<WordPair>()
        //list += WordPair("0","0")
        for (i in vocabWords) {
            val item = WordPair(i, shared.getString(i,"")?:"")
            list += item
        }
        return list
    }

    private fun generateShuffledList(): ArrayList<WordPair> {
        //why doesn't it work without reinitializing?
        val shared = getSharedPreferences("DictList", Context.MODE_PRIVATE)
        val vocabWords = shared.all.keys.toMutableList()
        val list = ArrayList<WordPair>()
        //list += WordPair("0","0")
        for (i in vocabWords) {
            val item = WordPair(i, shared.getString(i,"")?:"")
            list += item
        }
        return list
    }
     */
}