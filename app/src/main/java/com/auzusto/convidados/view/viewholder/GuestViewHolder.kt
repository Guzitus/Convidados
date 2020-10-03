package com.auzusto.convidados.view.viewholder

import android.app.AlertDialog
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.auzusto.convidados.R
import com.auzusto.convidados.service.model.GuestModel
import com.auzusto.convidados.view.listener.GuestListener

class GuestViewHolder(itemView: View, private val listener: GuestListener): RecyclerView.ViewHolder(itemView) {

    fun bind(guest: GuestModel) {
        val textName = itemView.findViewById<TextView>(R.id.text_name)
        textName.text = guest.name

        textName.setOnClickListener{
            listener.onClick(guest.id)
        }

        textName.setOnLongClickListener {

            AlertDialog.Builder(itemView.context)
                .setTitle(R.string.remocao_convidado)
                .setMessage(R.string.deseja_remover_convidado)
                .setPositiveButton(R.string.remove) { _, _ ->
                    listener.onDelete(guest.id)
                }
                .setNeutralButton(R.string.cancel, null)
                .show()

            true
        }
    }

}