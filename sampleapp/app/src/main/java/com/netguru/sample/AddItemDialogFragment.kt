package com.netguru.sample

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.EditText

class AddItemDialogFragment : DialogFragment() {

    companion object {
        fun newInstance() = AddItemDialogFragment()

        internal const val TAG = "tag:AddItemDialogFragment"
    }

    private lateinit var onItemAddListener: OnItemAddListener

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            onItemAddListener = context as OnItemAddListener
        } catch (exception: ClassCastException) {
            throw ClassCastException(
                "Activity should implement ${OnItemAddListener::class.java.simpleName}"
            )
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_new_item, null)
        val editText = view.findViewById<EditText>(R.id.newItemEditText)

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.add_new_item)
            .setView(view)
            .setPositiveButton(R.string.action_ok) { _, _ -> onItemAddListener.onItemAdded(editText.text.toString()) }
            .create()
    }

    internal interface OnItemAddListener {
        fun onItemAdded(item: String)
    }
}
