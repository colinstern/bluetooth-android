package sensors.myjankyapp;

import android.app.DialogFragment;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.Set;

/**
 * Created by cost on 7/18/16.
 */
public class BluetoothInit extends Fragment {

    /*
bluetooth comes in here
*/
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null) { //setup
            //Device does not support Bluetooth
            showDialog(); //TODO why won't my dialog display?
        }

        if (!mBluetoothAdapter.isEnabled()) { //finding devices
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, -1);
            /*TODO actually give the user a choice rather than forcing bluetooth to turn on*/
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        //If there are paired devices
        if (pairedDevices.size() > 0) {
            //Loop thru paired devices
            ArrayAdapter mArrayAdapter = new ArrayAdapter(getActivity(), 0);
            for (BluetoothDevice device : pairedDevices) {
                //Add name and address to an array adapter to show in listView
                mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        }
        //Create a broadcast receiver for ACTION_FOUND
        final BroadcastReceiver mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                //When discovery finds a device
                ArrayAdapter mArrayAdapter = new ArrayAdapter(getActivity(), 0);
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    //Get the BluetoothDevice object from the intent
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    // Add name and address to an arrat adapter to show in a list view
                    mArrayAdapter.add(device.getName() + "\n" + device.getAddress());

                }
            }
        };
        //register the BroadcastReceiver
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
//        registerReceiver(mReceiver, filter); //Don't forget to unregister during onDestroy

        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);


    }

    public void showDialog() {
        //Show the dialog fragment
        DialogFragment dialog = new BluetoothRadioNotFound();
    }
}