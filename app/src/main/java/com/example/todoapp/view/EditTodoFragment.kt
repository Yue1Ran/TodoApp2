package com.example.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentEditTodoBinding
import com.example.todoapp.model.Todo
import com.example.todoapp.viewmodel.DetailTodoViewModel


class EditTodoFragment : Fragment() {
    private lateinit var viewModel: DetailTodoViewModel
    private lateinit var dataBinding:FragmentEditTodoBinding
    private var todo: Todo? =null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dataBinding = FragmentEditTodoBinding.inflate(inflater,container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)

        val uuid = EditTodoFragmentArgs.fromBundle(requireArguments()).uuid  //baca argumen
        viewModel.fetch(uuid)

        observerViewModel()

        val caption = view.findViewById<TextView>(R.id.txtTitleCaption)
        val btnAdd = view.findViewById<Button>(R.id.btnAdd)

        caption.text = "Edit Todo"
        btnAdd.text = "Save Changes"

        btnAdd.setOnClickListener {
            val titile = view.findViewById<TextView>(R.id.txtTitle)
            todo?.title = titile.text.toString()
            viewModel.update(todo!!)
        }
    }

    fun observerViewModel(){
        viewModel.todoLD.observe(viewLifecycleOwner, Observer {
            dataBinding.todo = it
//            todo = it
//            val title = view?.findViewById<EditText>(R.id.txtTitle)
//            val desc = view?.findViewById<EditText>(R.id.txtNotes)
//            title?.setText(it.title)
//            desc?.setText(it.notes)
//
//            val radioLow = view?.findViewById<RadioButton>(R.id.radioLow)
//            val radioMed = view?.findViewById<RadioButton>(R.id.radioMedium)
//            val radioHi = view?.findViewById<RadioButton>(R.id.radioHigh)
//
//
//            when (it.priority) {
//                1 -> radioLow?.isChecked = true
//                2 -> radioMed?.isChecked = true
//                else -> radioHi?.isChecked = true
//            }

        })
    }
}