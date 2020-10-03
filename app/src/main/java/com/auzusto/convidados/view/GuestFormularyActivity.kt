package com.auzusto.convidados.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.auzusto.convidados.viewModel.GuestFormularyViewModel
import com.auzusto.convidados.R
import com.auzusto.convidados.service.constants.GuestConstants
import kotlinx.android.synthetic.main.activity_guest_formulary.*

class GuestFormularyActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mViewModel: GuestFormularyViewModel
    private var mGuestId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_formulary)

        mViewModel = ViewModelProvider(this).get(GuestFormularyViewModel::class.java)

        setListeners()
        observe()
        loadData()

        radio_present.isChecked = true
    }

    private fun loadData() {
        val bundle = intent.extras
        if (bundle != null) {
            mGuestId = bundle.getInt(GuestConstants.GUEST_ID)
            mViewModel.load(mGuestId)
        }
    }

    override fun onClick(view: View) {
        val id = view.id
        if (id == R.id.button_save) {
            val name = edit_name.text.toString()
            val presence = radio_present.isChecked

            mViewModel.save(mGuestId, name, presence)
        }
    }

    private fun observe() {
        mViewModel.saveGuest.observe(this, Observer {
            if (it) {
                Toast.makeText(applicationContext, "Sucesso!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Falha!", Toast.LENGTH_SHORT).show()
            }
            finish()
        })

        mViewModel.guest.observe(this, Observer {
            edit_name.setText(it.name)
            if (it.presence) {
                radio_present.isChecked = true
            } else {
                radio_absent.isChecked = true
            }
        })
    }

    private fun setListeners() {
        button_save.setOnClickListener(this)
    }

}