package com.auzusto.convidados.service.repository

import android.content.ContentValues
import android.content.Context
import android.provider.ContactsContract
import com.auzusto.convidados.service.constants.DatabaseConstants
import com.auzusto.convidados.service.model.GuestModel

class GuestRepository private constructor(context: Context) {

    private var mGuestDatabaseHelper: GuestDatabaseHelper = GuestDatabaseHelper(context)

    companion object {
        private lateinit var repository: GuestRepository

        fun getInstance(context: Context): GuestRepository {
            if (!::repository.isInitialized) {
                repository = GuestRepository(context)
            }
            return repository
        }
    }

    fun getAGuest(id: Int): GuestModel? {

        var guest: GuestModel? = null

        return try {
            val db = mGuestDatabaseHelper.readableDatabase

            val columns = arrayOf(
                DatabaseConstants.GUEST.COLUMNS.NAME,
                DatabaseConstants.GUEST.COLUMNS.PRESENCE
            )

            val selection = DatabaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            val cursor = db.query(
                DatabaseConstants.GUEST.TABLE_NAME,
                columns,
                selection,
                args,
                null,
                null,
                null

            )

            if (cursor != null && cursor.count > 0) {
                cursor.moveToFirst()

                val name = cursor.getString(
                    cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.NAME)
                )
                val presence = (cursor.getInt(
                    cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.PRESENCE)
                ) == 1)

                guest = GuestModel(id, name, presence)
            }

            cursor.close()

            guest
        } catch (e: Exception) {

            guest
        }
    }

    fun getAllGuests(): List<GuestModel> {

        val list: MutableList<GuestModel> = ArrayList()

        return try {
            val db = mGuestDatabaseHelper.readableDatabase

            val columns = arrayOf(
                DatabaseConstants.GUEST.COLUMNS.ID,
                DatabaseConstants.GUEST.COLUMNS.NAME,
                DatabaseConstants.GUEST.COLUMNS.PRESENCE
            )

            val cursor = db.query(
                DatabaseConstants.GUEST.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null

            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {

                    val id = cursor.getInt(
                        cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.ID)
                    )
                    val name = cursor.getString(
                        cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.NAME)
                    )
                    val presence = (cursor.getInt(
                        cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.PRESENCE)
                    ) == 1)

                    val guest = GuestModel(id, name, presence)
                    list.add(guest)
                }
            }

            cursor?.close()
            list
        } catch (e: Exception) {
            list
        }

    }

    fun getAllPresents(): List<GuestModel> {
        val list: MutableList<GuestModel> = ArrayList()

        return try {

            val db = mGuestDatabaseHelper.readableDatabase

            val cursor = db.rawQuery(
                "SELECT id, name, presence FROM Guest WHERE presence = 1",
                null
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {

                    val id = cursor.getInt(
                        cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.ID)
                    )
                    val name = cursor.getString(
                        cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.NAME)
                    )
                    val presence = (cursor.getInt(
                        cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.PRESENCE)
                    ) == 1)

                    val guest = GuestModel(id, name, presence)
                    list.add(guest)
                }
            }

            cursor?.close()
            list
        } catch (e: Exception) {
            list
        }
    }

    fun getAllAbsents(): List<GuestModel> {
        val list: MutableList<GuestModel> = ArrayList()

        return try {

            val db = mGuestDatabaseHelper.readableDatabase

            val cursor = db.rawQuery(
                "SELECT id, name, presence FROM Guest WHERE presence = 0",
                null
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {

                    val id = cursor.getInt(
                        cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.ID)
                    )
                    val name = cursor.getString(
                        cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.NAME)
                    )
                    val presence = (cursor.getInt(
                        cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.PRESENCE)
                    ) == 1)

                    val guest = GuestModel(id, name, presence)
                    list.add(guest)
                }
            }

            cursor?.close()
            list
        } catch (e: Exception) {
            list
        }
    }

    fun save(guest: GuestModel): Boolean {

        return try {

            val db = mGuestDatabaseHelper.writableDatabase

            val contentValues = ContentValues()
            contentValues.put(DatabaseConstants.GUEST.COLUMNS.NAME, guest.name)
            contentValues.put(DatabaseConstants.GUEST.COLUMNS.PRESENCE, guest.presence)

            db.insert(DatabaseConstants.GUEST.TABLE_NAME, null, contentValues)

            true
        } catch (e: Exception) {

            false
        }
    }

    fun update(guest: GuestModel): Boolean {

        return try {

            val db = mGuestDatabaseHelper.writableDatabase

            val contentValues = ContentValues()
            contentValues.put(DatabaseConstants.GUEST.COLUMNS.NAME, guest.name)
            contentValues.put(DatabaseConstants.GUEST.COLUMNS.PRESENCE, guest.presence)

            val selection = DatabaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(guest.id.toString())
            db.update(DatabaseConstants.GUEST.TABLE_NAME, contentValues, selection, args)

            true
        } catch (e: Exception) {
            false
        }

    }

    fun delete(id: Int): Boolean {

        return try {

            val db = mGuestDatabaseHelper.readableDatabase

            val selection = DatabaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())
            db.delete(DatabaseConstants.GUEST.TABLE_NAME, selection, args)

            true
        } catch (e: Exception) {
            false
        }

    }

}