package com.lena.notes.ui.notedetails

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.lena.notes.databinding.ActivityNoteDetailsBinding
import kotlinx.android.synthetic.main.activity_note_details.*
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.lena.notes.R

class NoteDetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityNoteDetailsBinding
    lateinit var viewModel : NoteDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_note_details)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        viewModel = ViewModelProviders.of(this).get(NoteDetailsViewModel::class.java)
        if (intent.hasExtra("id")) {
            viewModel.note.id = intent.getIntExtra("id", 0)
            viewModel.textOld = intent.getStringExtra("text")
            viewModel.note.text = intent.getStringExtra("text")
            viewModel.note.date = intent.getLongExtra("date", 0)
        }
        binding.viewModel = viewModel
        binding.executePendingBindings()

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_note_details, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            R.id.mi_delete -> {
                val builder = AlertDialog.Builder(this@NoteDetailsActivity)
                builder.setTitle(getString(R.string.ad_note_delete))
                builder.setMessage(getString(R.string.ad_note_delete_confirm))

                builder.setPositiveButton(getString(R.string.ad_yes)){ _, _ ->
                    viewModel.delete()
                    binding.invalidateAll()
                    Toast.makeText(this@NoteDetailsActivity, getString(R.string.toast_note_deleted), Toast.LENGTH_SHORT).show()
                    finish()
                }

                builder.setNegativeButton(getString(R.string.ad_no)){ _, _ -> }
                builder.setNeutralButton(getString(R.string.ad_cancel)){ _, _ -> }

                builder.create().show()
                true
            }
            R.id.mi_share -> {
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(Intent.EXTRA_TEXT, viewModel.note.text)
                sendIntent.type = "text/plain"
                startActivity(Intent.createChooser(sendIntent, getString(R.string.title_share)))
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}