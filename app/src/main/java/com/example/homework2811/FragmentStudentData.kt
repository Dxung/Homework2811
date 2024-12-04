package com.example.homework2811

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

class FragmentStudentData : Fragment() {
    private lateinit var viewModel: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_student_data, container, false)
        initProps()

        //Lấy Name & ID EditTextView
        val addStudentNameEditText = view.findViewById<EditText>(R.id.edit_student_name)
        val addStudentIdEditText   = view.findViewById<EditText>(R.id.edit_student_id)

        //Lấy pos của student đã được gửi qua safe args
        val args = FragmentStudentDataArgs.fromBundle(requireArguments())
        val pos = args.deletePos

        //Lấy dữ liệu của student từ viewModel
        val targetStudentData = viewModel.getEditStudentData(pos)

        //Hiển thị dữ liệu
        addStudentNameEditText.setText(targetStudentData.studentName)
        addStudentIdEditText.setText(targetStudentData.studentId)


        //Sau khi đã thay đổi xong và ấn Add:
        view.findViewById<Button>(R.id.button_confirm_editing).setOnClickListener {
            val editStudentNameValue = addStudentNameEditText.text.toString()
            val editStudentIdValue = addStudentIdEditText.text.toString()

            if(editStudentNameValue.isNotEmpty() && editStudentIdValue.isNotEmpty()){
                //Thay dữ liệu ở viewmodel
                viewModel.editStudentData(StudentDataModel(editStudentNameValue, editStudentIdValue), pos)

                //back
                findNavController().navigateUp()
            }else{
                Toast.makeText(context, "Tên hoặc Id của sinh viên không hợp lệ", Toast.LENGTH_SHORT).show()
            }
        }

        view.findViewById<Button>(R.id.button_cancel_editing).setOnClickListener {
                findNavController().navigateUp()
                Toast.makeText(context, "Huỷ thay đổi dữ liệu sinh viên", Toast.LENGTH_SHORT).show()

        }


        return view
    }

    private fun initProps(){

        //Gắn vào mainactivity thay vì "this" -> chỉ vòng đời của các class
        viewModel = ViewModelProvider(requireActivity())[ListViewModel::class.java]

    }
}