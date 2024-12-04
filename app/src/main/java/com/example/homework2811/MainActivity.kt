package com.example.homework2811

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment


class MainActivity : AppCompatActivity(), ItemClickListener {
    private lateinit var viewModel: ListViewModel
    private lateinit var navController: NavController


    var actionMode: ActionMode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initProps()
    }

    private fun initProps(){
        //view model
        viewModel = ViewModelProvider(this)[ListViewModel::class.java]

        //nav
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController


        val toolbar: Toolbar = findViewById(R.id.main_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Student Manager"
    }

        private val callback = object : ActionMode.Callback {
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                menuInflater.inflate(R.menu.contextmenu, menu)
                return true
            }

            //khi actionMode cập nhật lại menu
            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                menu?.findItem(R.id.action_edit)?.isVisible = viewModel.onlySingleSelected()
                return false
            }

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                when (item?.itemId) {
                    R.id.action_edit -> {
                        startEditFragment()
                        actionMode?.finish()
                    }
                    R.id.action_delete -> {
                        viewModel.deleteStudentData()
                        actionMode?.finish()
                    }
                }
                return true
            }

            override fun onDestroyActionMode(mode: ActionMode?) {

                //xoá selected
                viewModel.clearAllSelected()

                //tắt action mode
                actionMode = null

                //thông báo đã tắt
                viewModel.toggleStatus()


            }
        }


    override fun onCAMItemClicked() {
        if(viewModel.isSelectedListEmpty()){
            actionMode?.finish()
        }

        //update
        actionMode?.invalidate()
    }



    override fun onLongPressStartCAM() {
        if (actionMode == null){
            actionMode = startSupportActionMode(callback)
            viewModel.toggleStatus()
        }
    }

    private fun startEditFragment(){
        val action = FragmentListDirections.actionFragmentListToFragmentStudentData(viewModel.getEditStudentPos())
        navController.navigate(action)
    }




}