package com.example.voltmeter;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class PropertiesFragment extends Fragment {
    Toolbar toolbar;
    TextView tvRemainingCapacity,tvChargeCounter,tvCurrentAverage,tvEnergyCounter,tvCurrentNow;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_properties,container,false);
        toolbar=view.findViewById(R.id.properties_top_bar);
        tvRemainingCapacity=view.findViewById(R.id.tv_capacity_val);
        tvChargeCounter=view.findViewById(R.id.tv_charge_counter_val);
        tvCurrentAverage=view.findViewById(R.id.tv_current_average_val);
        tvEnergyCounter=view.findViewById(R.id.tv_energy_counter_val);
        tvCurrentNow=view.findViewById(R.id.tv_current_now_val);
        ContextCompat.registerReceiver(requireContext(),receiver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED),ContextCompat.RECEIVER_VISIBLE_TO_INSTANT_APPS);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog();
            }
        });
        return view;
    }
    BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            BatteryManager batteryManager=(BatteryManager) ContextCompat.getSystemService(requireContext(), BatteryManager.class);
            int remainingCapacity=batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
            tvRemainingCapacity.setText(String.valueOf(remainingCapacity));
            long chargeCounter=batteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER);
            tvChargeCounter.setText(String.valueOf(chargeCounter));
            long currentAverage=batteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_AVERAGE);
            tvCurrentAverage.setText(String.valueOf(currentAverage));
            long energyCounter=batteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_ENERGY_COUNTER);
            tvEnergyCounter.setText(String.valueOf(energyCounter/1000000000));
            long currentNow=batteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);
            tvCurrentNow.setText(String.valueOf(currentNow));
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        requireContext().unregisterReceiver(receiver);
    }
    public void createDialog(){
        Dialog dialog=new Dialog(requireContext());
        View view=LayoutInflater.from(getContext()).inflate(R.layout.exit_dialog,null);
        Button btnYes=view.findViewById(R.id.btn_yes);
        Button btnNo=view.findViewById(R.id.btn_no);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        dialog.getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        dialog.show();
    }
}
