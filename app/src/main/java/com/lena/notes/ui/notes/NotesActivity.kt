package com.lena.notes.ui.notes

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.lena.notes.adapter.NotesAdapter
import com.lena.notes.databinding.ActivityNotesBinding
import com.lena.notes.ui.notedetails.NoteDetailsActivity
import kotlinx.android.synthetic.main.activity_notes.*
import androidx.lifecycle.Observer
import com.lena.notes.R

class NotesActivity : AppCompatActivity(), PopupMenu.OnMenuItemClickListener, NotesAdapter.OnItemClickListener {

    lateinit var binding: ActivityNotesBinding
    lateinit var viewModel: NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_notes)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            binding.searchView.setQuery("", false)
            binding.searchView.clearFocus()
            startActivity(Intent(this, NoteDetailsActivity::class.java))
        }

        viewModel = ViewModelProviders.of(this).get(NotesViewModel::class.java)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        binding.recyclerView.adapter = NotesAdapter(viewModel.notes, this)

        viewModel.notesLiveData.observe(this, Observer {
            viewModel.updateData(it)
            binding.recyclerView.adapter!!.notifyDataSetChanged()
            binding.invalidateAll()
        })

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.updateData(newText!!)
                binding.recyclerView.adapter!!.notifyDataSetChanged()
                binding.invalidateAll()
                return true
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.mi_sorting -> {
                val popup = PopupMenu(this, findViewById(R.id.mi_sorting))
                popup.setOnMenuItemClickListener(this)
                popup.inflate(R.menu.menu_sorting)
                popup.show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {

        var bool = false

        viewModel.notesLiveData.removeObservers(this)

        when (item?.itemId) {
            R.id.mi_from_new_to_old -> {
                viewModel.getNotesOrderByDESC()
                bool = true
            }
            R.id.mi_from_old_to_new -> {
                viewModel.getNotesOrderBy()
                bool = true
            }
        }

        viewModel.notesLiveData.observe(this, Observer {
            viewModel.updateData(it)
            binding.recyclerView.adapter!!.notifyDataSetChanged()
        })

        return bool
    }

    override fun onItemClick(position: Int) {

        binding.searchView.setQuery("", false)
        binding.searchView.clearFocus()

        val intent = Intent(this, NoteDetailsActivity::class.java)
        intent.putExtra("id", viewModel.notes[position].id)
        intent.putExtra("text", viewModel.notes[position].text)
        intent.putExtra("date", viewModel.notes[position].date)
        startActivity(intent)
    }
}