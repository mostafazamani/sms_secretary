package com.op.smss;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.op.smss.background.ServiceCommunicator;
import com.op.smss.databinding.ActivityMainBinding;
import com.op.smss.model.Items;
import com.op.smss.view.AddItem;
import com.op.smss.view.ItemListAdapter;
import com.op.smss.viewmodel.SmsViewModel;

import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private static final int PERMISSION_REQUEST_CODE = 1;

    private SmsViewModel msmsViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final ItemListAdapter adapter = new ItemListAdapter(new ItemListAdapter.WordDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        msmsViewModel = new ViewModelProvider(this).get(SmsViewModel.class);

        msmsViewModel.getAllWords().observe(this, item -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(item);
        });



        Intent intent = new Intent(this, ServiceCommunicator.class);
        startService(intent);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            if (checkSelfPermission(android.Manifest.permission.SEND_SMS)
                    == PackageManager.PERMISSION_DENIED ||checkSelfPermission(Manifest.permission.RECEIVE_SMS)
                    == PackageManager.PERMISSION_DENIED ) {

                Log.d("permission", "permission denied to SEND_SMS - requesting it");
                String[] permissions = {android.Manifest.permission.SEND_SMS,Manifest.permission.RECEIVE_SMS};

                requestPermissions(permissions, PERMISSION_REQUEST_CODE);

            }
        }

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddItem.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Items sms = new Items(data.getStringExtra("key"),data.getStringExtra("value"));
            msmsViewModel.insert(sms);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "not save !",
                    Toast.LENGTH_LONG).show();
        }
    }


//    @Override
//    protected void onPause() {
//        super.onPause();
//        ActivityManager activityManager = (ActivityManager) getApplicationContext()
//                .getSystemService(Context.ACTIVITY_SERVICE);
//        activityManager.moveTaskToFront(getTaskId(), 0);
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_HOME)
        {
            Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
            return true;
        }
        return true;
    }
//    @Override
//    protected void onUserLeaveHint() {
//        Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
//
//    }
//@Override
//public void onAttachedToWindow() {
//    super.onAttachedToWindow();
//    this.getWindow().setType(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
//}

}