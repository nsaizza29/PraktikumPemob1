package com.unsoed.ifunsoedmobile

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.unsoed.ifunsoedmobile.data.model.BookDoc
import com.unsoed.ifunsoedmobile.databinding.ActivityDaftarBukuBinding
import com.unsoed.ifunsoedmobile.ui.adapter.BookAdapter
import com.unsoed.ifunsoedmobile.viewmodel.MainViewModel
import com.unsoed.ifunsoedmobile.ui.adapter.OnBookClickListener
import com.unsoed.ifunsoedmobile.ui.fragment.BookDetailFragment

class DaftarBukuActivity : AppCompatActivity(), OnBookClickListener {

    private lateinit var binding: ActivityDaftarBukuBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaftarBukuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = BookAdapter(emptyList(), this)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        viewModel.books.observe(this) {
            adapter.setData(it)
        }

        viewModel.fetchBooks("kotlin programming")
    }

    override fun onBookClick(book: BookDoc) {
        book.let {b->
            BookDetailFragment(
                b.title ?: "-",
                b.authorName?.joinToString(", ") ?: "Unknown Author",
                "${b.firstPublishYear}",
                b.coverId ?: 0
            ).show(supportFragmentManager, BookDetailFragment::class.java.simpleName)

        }
    }
}