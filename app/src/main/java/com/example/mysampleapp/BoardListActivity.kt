package com.example.mysampleapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.databinding.DataBindingUtil
import com.example.mysampleapp.databinding.ActivityBoardListBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class BoardListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBoardListBinding
    val list= mutableListOf<Model>()
    lateinit var LVAdapter : ListViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_list)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_list)
        val writeBtn=binding.writeBtn
        writeBtn.setOnClickListener{
            val intent= Intent(this,BoardWriteActivity::class.java)
            startActivity(intent)
        }


        LVAdapter = ListViewAdapter(list)
        val lv=findViewById<ListView>(R.id.lv)
        lv.adapter=LVAdapter

        getData()
    }
    fun getData(){
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("board")
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for(dataModel in dataSnapshot.children){

                    val item=dataModel.getValue(Model::class.java)
                    Log.d("BoardListActivity",item.toString())
                    list.add(item!!)
                }
                LVAdapter.notifyDataSetChanged()

                Log.d("getItem", LVAdapter.getItem(1).toString())
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        myRef.addValueEventListener(postListener)
    }
}