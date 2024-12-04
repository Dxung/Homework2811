package com.example.homework2811

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListViewModel : ViewModel() {
    private var studentDataList: MutableLiveData<List<StudentDataModel>> = MutableLiveData()
    private val selectedItems : MutableLiveData<List<Int>> = MutableLiveData()
    private var isOn : Boolean = false

    init {
        studentDataList.value = listOf(
            StudentDataModel("Nguyễn Văn An", "SV001"),
            StudentDataModel("Trần Thị Bảo", "SV002"),
            StudentDataModel("Lê Hoàng Cường", "SV003"),
            StudentDataModel("Phạm Thị Dung", "SV004"),
            StudentDataModel("Đỗ Minh Đức", "SV005"),
            StudentDataModel("Vũ Thị Hoa", "SV006"),
            StudentDataModel("Hoàng Văn Hải", "SV007"),
            StudentDataModel("Bùi Thị Hạnh", "SV008"),
            StudentDataModel("Đinh Văn Hùng", "SV009"),
            StudentDataModel("Nguyễn Thị Linh", "SV010"),
            StudentDataModel("Phạm Văn Long", "SV011"),
            StudentDataModel("Trần Thị Mai", "SV012"),
            StudentDataModel("Lê Thị Ngọc", "SV013"),
            StudentDataModel("Vũ Văn Nam", "SV014"),
            StudentDataModel("Hoàng Thị Phương", "SV015"),
            StudentDataModel("Đỗ Văn Quân", "SV016"),
            StudentDataModel("Nguyễn Thị Thu", "SV017"),
            StudentDataModel("Trần Văn Tài", "SV018"),
            StudentDataModel("Phạm Thị Tuyết", "SV019"),
            StudentDataModel("Lê Văn Vũ", "SV020")
        )

        selectedItems.value = mutableListOf()
    }

    /*--- Data List ---*/
    fun getStudentDataListData(): List<StudentDataModel> {
        return studentDataList.value ?: mutableListOf()
    }

    fun getStudentDataList(): LiveData<List<StudentDataModel>> {
        return studentDataList
    }

    fun addStudentData(newStudent: StudentDataModel) {
        val updatedList = studentDataList.value?.toMutableList() ?: mutableListOf()

        updatedList.add(newStudent)

        studentDataList.postValue(updatedList)
    }

    fun editStudentData(editedStudent: StudentDataModel, pos: Int){
        val editedList = studentDataList.value?.toMutableList() ?: mutableListOf()
        editedList[pos] = editedStudent

        studentDataList.postValue(editedList)
    }

    fun deleteStudentData(){
        val updatedList = studentDataList.value?.toMutableList() ?: mutableListOf()
        val sortedStudentPosList = selectedItems.value?.toMutableList()?.sortedDescending()

        sortedStudentPosList?.forEach { pos ->
            if (pos >=0 && pos < updatedList.size){
                updatedList.removeAt(pos)
            }else{
                Log.d("error", "Invalid pos: $pos")
            }

        }

        studentDataList.value = updatedList
    }

    /*--- Selected Student Position List ---*/
    fun getSelectedPosList(): LiveData<List<Int>>{
        return selectedItems
    }

    fun isSelectedListEmpty(): Boolean{
        return selectedItems.value?.isEmpty() ?: true
    }

    fun onlySingleSelected(): Boolean{
        return (selectedItems.value?.size ?: 0) == 1
    }

    fun clearAllSelected(){
        selectedItems.value = mutableListOf()
    }

    fun toggleDataPos(pos:Int){
        val currentList = selectedItems.value?.toMutableList() ?: mutableListOf()

        if(currentList.contains(pos)){
            currentList.remove(pos)

        }else{
            currentList.add(pos)
        }

        selectedItems.value = currentList
    }

    fun getEditStudentPos() : Int {
        val currentList = selectedItems.value?.toMutableList() ?: mutableListOf()
        return currentList.first()
    }

    fun getEditStudentData(pos : Int): StudentDataModel{

        if(studentDataList.value != null){
            return studentDataList.value?.get(pos) ?: StudentDataModel("error","error")
        }else{
            return StudentDataModel("error","error")
        }
    }

    /*--- Action Mode ---*/
    fun toggleStatus() {
        isOn = !isOn
    }

    fun isOnOrNot() : Boolean {
        return isOn
    }

}