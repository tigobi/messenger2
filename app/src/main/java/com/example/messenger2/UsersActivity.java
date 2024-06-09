package com.example.messenger2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UsersActivity extends AppCompatActivity {
    private UserViewModel viewModel;
    private RecyclerView recyclerViewUsers;
    private UsersAdapter usersAdapter;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        for (int i = 0; i < 10; i++) {
            User user = new User("id" + i, "name" + i, "lastName" + i, i, false);
            databaseReference.push().setValue(user);
        }
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User value = dataSnapshot.getValue(User.class);
                    Log.d("UsersActivity", value.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        initViews();
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        observeViewModel();
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, UsersActivity.class);
    }

    private void observeViewModel() {
        viewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser == null) {
                    Intent intent = LoginActivity.newIntent(UsersActivity.this);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void initViews() {
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
        usersAdapter = new UsersAdapter();
        recyclerViewUsers.setAdapter(usersAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_logout) {
            viewModel.logout();
        }
        return super.onOptionsItemSelected(item);
    }
}