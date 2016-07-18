package sensors.myjankyapp;

import android.app.DialogFragment;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
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
            for (BluetoothDevice device : pairedDevices) {
                //Add name and address to an array adapter to show in listView
                ArrayAdapter mArrayAdapter = new ArrayAdapter(getActivity(), 0);
                mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        }
        //TODO start here from Create a BroadcastReceiver (discovering devices)
    }

    public void showDialog() {
        //Show the dialog fragment
        DialogFragment dialog = new BluetoothRadioNotFound();
    }
}