package com.msl.mapp;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE = 0;
    private static final int REQUEST_DISCOVER = 1;

    private TextView mStatus, mPair;
    private ImageView mStatusIcon;
    private Button mTurnOn, mTurnOff, mDiscover, mPaired;
    private BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStatus = findViewById(R.id.blstatus);
        mPair = findViewById(R.id.pair);

        mStatusIcon = findViewById(R.id.blicon);

        mTurnOn = findViewById(R.id.switch_on);
        mTurnOff = findViewById(R.id.switch_off);
        mDiscover = findViewById(R.id.blfind);
        mPaired = findViewById(R.id.blpair);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        checkBluetoothAdapterIsNull(bluetoothAdapter);

        enableBluetooth(mTurnOn);

        checkBluetoothAdapterIsEnabled(bluetoothAdapter);

    }

    private void checkBluetoothAdapterIsNull(BluetoothAdapter adapter) {

        if (adapter == null) {
            mStatus.setText(R.string.n_available);
        }
        else mStatus.setText(R.string.is_available);
    }

    private void checkBluetoothAdapterIsEnabled(BluetoothAdapter adapter) {

        if (adapter.isEnabled()) {
            mStatusIcon.setImageResource(R.drawable.ic_baseline_bluetooth_128);
        }
        else mStatusIcon.setImageResource(R.drawable.ic_baseline_bluetooth_disabled_128);
    }

    private void showMessage(String msg) {

        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void enableBluetooth(Button button) {

        button.setOnClickListener(view -> {

            if (!bluetoothAdapter.isEnabled()) {

                showMessage("Turning on bluetooth");

                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent, REQUEST_ENABLE);
            }
            else { showMessage("Bluetooth is already on"); }
        });
    }


}