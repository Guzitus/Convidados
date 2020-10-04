package com.auzusto.convidados.service.repository

import android.content.Context
import com.auzusto.convidados.service.model.GuestModel

class GuestRepository(context: Context) {

    private val mDatabase = GuestDatabase.getDatabase(context).guestDAO()

    fun getAGuest(id: Int): GuestModel {
        return mDatabase.get(id)
    }

    fun getAllGuests(): List<GuestModel> {
        return mDatabase.getInvited()
    }

    fun getAllPresents(): List<GuestModel> {
        return mDatabase.getPresent()
    }

    fun getAllAbsents(): List<GuestModel> {
        return mDatabase.getAbsent()
    }

    fun save(guest: GuestModel): Boolean {
        return mDatabase.save(guest) > 0
    }

    fun update(guest: GuestModel): Boolean {
        return mDatabase.update(guest) > 0
    }

    fun delete(guest: GuestModel) {
        return mDatabase.delete(guest)
    }
}

