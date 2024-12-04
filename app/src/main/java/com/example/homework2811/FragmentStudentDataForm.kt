package com.example.homework2811

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

class FragmentStudentDataForm: Fragment() {
    private lateinit var viewModel: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_student_data_form, container, false)
        initProps()

        val addStudentNameEditText = view.findViewById<EditText>(R.id.add_student_name)
        val addStudentIdEditText   = view.findViewById<EditText>(R.id.add_student_id)

        view.findViewById<Button>(R.id.button_add_new).setOnClickListener {
            val addStudentNameValue = addStudentNameEditText.text.toString()
            val addStudentIdValue = addStudentIdEditText.text.toString()

            if(addStudentNameValue.isNotEmpty() && addStudentIdValue.isNotEmpty()){
                //Thêm dữ liệu mới vào viewmodel
                viewModel.addStudentData(StudentDataModel(addStudentNameValue, addStudentIdValue))

                //back
                findNavController().navigateUp()
            }else{
                Toast.makeText(context, "Tên hoặc Id của sinh viên không hợp lệ", Toast.LENGTH_SHORT).show()
            }
        }

        view.findViewById<Button>(R.id.button_cancel).setOnClickListener {
                findNavController().navigateUp()
                Toast.makeText(context, "Dừng thêm sinh viên", Toast.LENGTH_SHORT).show()
        }


        return view
    }

    private fun initProps(){
        //Gắn vào mainactivity thay vì "this" -> chỉ vòng đời của các class
        viewModel = ViewModelProvider(requireActivity())[ListViewModel::class.java]
    }

}