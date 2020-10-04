package com.auzusto.convidados.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.auzusto.convidados.service.constants.DatabaseConstants
import com.auzusto.convidados.service.constants.GuestConstants
import com.auzusto.convidados.service.model.GuestModel
import com.auzusto.convidados.service.repository.GuestRepository

class GuestsViewModel(application: Application) : AndroidViewModel(application) {

    private val mGuestRepository = GuestRepository(application.applicationContext)

    private val mGuestList = MutableLiveData<List<GuestModel>>()
    val guestList: LiveData<List<GuestModel>> = mGuestList

    fun load(filter: Int) {

        if (filter == GuestConstants.FILTER.EMPTY) {
            mGuestList.value = mGuestRepository.getAllGuests()
        } else if (filter == GuestConstants.FILTER.PRESENT) {
            mGuestList.value = mGuestRepository.getAllPresents()
        } else {
            mGuestList.value = mGuestRepository.getAllAbsents()
        }

    }

    fun delete(id: Int) {
        val guest = mGuestRepository.getAGuest(id)
        mGuestRepository.delete(guest)
    }

}