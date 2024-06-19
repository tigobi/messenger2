package com.example.messenger2;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ChatViewModel extends ViewModel {
    private MutableLiveData<List<Message>> messages = new MutableLiveData<>();
    private MutableLiveData<List<Message>> otherUser = new MutableLiveData<>();
    private MutableLiveData<List<Message>> messageSent = new MutableLiveData<>();
    private MutableLiveData<List<Message>> error = new MutableLiveData<>();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference referenceUsers = firebaseDatabase.getReference("Users");
    private String currentUserId;
    private String otherUserId;

    public ChatViewModel(String currentUserId, String otherUserId) {
        this.currentUserId = currentUserId;
        this.otherUserId = otherUserId;
        referenceUsers.child(otherUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                otherUser.setValue(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public LiveData<List<Message>> getMessages() {
        return messages;
    }

    public LiveData<List<Message>> getOtherUser() {
        return otherUser;
    }

    public LiveData<List<Message>> getMessageSent() {
        return messageSent;
    }

    public LiveData<List<Message>> getError() {
        return error;
    }

    public void sendMessage(Message message) {

    }
}
