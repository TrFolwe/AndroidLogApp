package com.example.performans;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.time.*;
import java.util.ListIterator;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private double moneyTotal = 0;
    private EditText moneyInput;
    private ListView logView;

    List<String> dataList = new ArrayList<String>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
        moneyInput = findViewById(R.id.moneyInput);
        logView = findViewById(R.id.listView);

        //Element Listeners
        findViewById(R.id.sendBtn).setOnClickListener(this::addMoney);
        findViewById(R.id.rejectBtn).setOnClickListener(this::removeMoney);
        logView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(MainActivity.this).setTitle("ID: " + id).setMessage(dataList.get((int) id)).show();
            }
        });
    }

    @SuppressLint({"SetTextI18n", "ResourceType", "SimpleDateFormat"})
    public void addMoney(View v) {
        double moneyAmount = Double.parseDouble(moneyInput.getText().toString());
        new AlertDialog.Builder(MainActivity.this).setTitle("Para arttırıldı").setMessage("Miktar: "+moneyAmount).show();
        moneyTotal += moneyAmount;
        ((TextView)findViewById(R.id.moneyInfoTxt)).setText(moneyTotal+" TL");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dataList.add(moneyAmount+" TL Para Aktarımı ("+new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date()) +")");
            logView.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, dataList));
        }
    }

    @SuppressLint({"SetTextI18n", "ResourceType", "SimpleDateFormat"})
    public void removeMoney(View v) {
        double moneyAmount = Double.parseDouble(moneyInput.getText().toString());
        if((moneyTotal - moneyAmount) < 0) {
            new AlertDialog.Builder(MainActivity.this).setTitle("Hata").setMessage("Yazdığın değer bütçenden fazla olamaz!").show();
            return;
        }
        new AlertDialog.Builder(MainActivity.this).setTitle("Para azaltıldı").setMessage("Miktar: "+moneyAmount).show();
        moneyTotal -= moneyAmount;
        ((TextView)findViewById(R.id.moneyInfoTxt)).setText(moneyTotal+" TL");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dataList.add("-"+moneyAmount+" TL Para Gönderimi ("+new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date())+")");
            logView.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, dataList));
        }
    }
}