package com.example.homework2811

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.selects.select

class FragmentList: Fragment() {

    private lateinit var listView: ListView
    private lateinit var customAdapter: customAdaptor
    private lateinit var viewModel: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        listView = view.findViewById(R.id.list_view)

        initProps()
        firstInitAdapter()
        makeOptionMenu()

        listView.setOnItemLongClickListener { parent, view, position, id ->

            if (activity is ItemClickListener) {

                //xử lý dữ liệu trước đã
                viewModel.toggleDataPos(position)

                //Khởi động action mode
                (activity as ItemClickListener).onLongPressStartCAM()

            }

            true
        }

        listView.setOnItemClickListener { parent, view, position, id ->
            if(viewModel.isOnOrNot()){
                if (activity is ItemClickListener) {

                    //xử lý dữ liệu trước đã
                    viewModel.toggleDataPos(position)

                    (activity as ItemClickListener).onCAMItemClicked()


                }
            }
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        observeStudentData()
        observeSelectedPos()
    }

    private fun initProps(){

        //Gắn vào mainactivity thay vì "this" -> chỉ vòng đời của các class
        viewModel = ViewModelProvider(requireActivity())[ListViewModel::class.java]

    }

    private fun firstInitAdapter (){
        customAdapter = customAdaptor(requireContext(), viewModel.getStudentDataListData())
        listView.adapter = customAdapter
    }

    private fun observeStudentData(){
        viewModel.getStudentDataList().observe(viewLifecycleOwner) {
            customAdapter.updateData(viewModel.getStudentDataListData())
        }
    }

    private fun observeSelectedPos(){
        viewModel.getSelectedPosList().observe(viewLifecycleOwner){ selectedList ->
            Log.d("checkList",selectedList.toString())
            for (i in 0 until listView.childCount) {
                val itemView = listView.getChildAt(i)
                if (itemView != null) {
                    if (selectedList.contains(i)) {
                        itemView.setBackgroundColor(Color.parseColor("#808080"))
                    } else {
                        itemView.setBackgroundColor(Color.TRANSPARENT)
                    }
                }
            }

        }
    }

    private fun makeOptionMenu(){
        val menuHost : MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.optionmenu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.addNew -> {
                        findNavController().navigate(R.id.action_fragmentList_to_fragmentStudentDataForm)
                        return true
                    }
                    else -> {
                        return false
                    }
                }
            }
        },viewLifecycleOwner, Lifecycle.State.RESUMED)
    }



}