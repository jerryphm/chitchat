package com.example.chitchat.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.chitchat.model.Channel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val firebaseDatabase = Firebase.database("https://chitchat-a0de6-default-rtdb.asia-southeast1.firebasedatabase.app")
    private val channelObj = firebaseDatabase.getReference("channel")

    private val _channels: MutableStateFlow<List<Channel>> = MutableStateFlow(listOf(Channel(name = "")))
    val channels: StateFlow<List<Channel>> = _channels.asStateFlow()

    init {
        getChannels()
    }

    private fun getChannels() {
        channelObj.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val list = mutableListOf<Channel>()
                dataSnapshot.children.forEach { data ->
                    val channel = Channel(data.key!!, data.value.toString())
                    list.add(channel)
                }
                _channels.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("chitchat home", "Failed to read value.", error.toException())
            }
        })

    }

    fun addChannel(channelName: String) {
        channelObj.child(channelName).setValue("")
            .addOnSuccessListener {
                Log.d("chitchat home", "Channel added successfully: $channelName")
            }
            .addOnFailureListener { exception ->
                Log.e("chitchat home", "Failed to add channel", exception)
            }
    }
}