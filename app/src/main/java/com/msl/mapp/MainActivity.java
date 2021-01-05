package com.msl.mapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVER_BT = 1;

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
        checkBluetoothAdapterIsEnabled(bluetoothAdapter);

        enableBt(mTurnOn);
        disableBt(mTurnOff);
        discoverBt(mDiscover);
        pairedBt(mPaired);
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

    private void enableBt(Button button) {

        button.setOnClickListener(view -> {

            if (!bluetoothAdapter.isEnabled()) {

                showMessage("Turning on bluetooth");

                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent, REQUEST_ENABLE_BT);
            }
            else { showMessage("Bluetooth is already on"); }
        });
    }

    private void discoverBt(Button button) {

        button.setOnClickListener(v -> {
            if (!bluetoothAdapter.isDiscovering()) {

                showMessage("Making Your Device Discoverable");
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                startActivityForResult(intent, REQUEST_DISCOVER_BT);
            }
        });
    }

    private void disableBt(Button button) {

        button.setOnClickListener(v -> {
            if (bluetoothAdapter.isEnabled()) {

                bluetoothAdapter.disable();
                showMessage("Turning  Bluetooth off");
                mStatusIcon.setImageResource(R.drawable.ic_baseline_bluetooth_disabled_128);
            } else { showMessage("Bluetooth is already off"); }
        });
    }

    private void pairedBt(Button button) {

       button.setOnClickListener(v -> {

           if (bluetoothAdapter.isEnabled()) {

               mPair.setText(R.string.paired_dev);
               Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();

               for (BluetoothDevice device : devices) {
                   mPair.append("\n Device : " + device.getName() + " , " + device);
               }
           } else { showMessage("Turn On bluetooth to get paired devices"); }
       });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {

                mStatusIcon.setImageResource(R.drawable.ic_baseline_bluetooth_128);
                showMessage("Bluetooth is On");
            } else { showMessage("Bluetooth is Off"); }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}