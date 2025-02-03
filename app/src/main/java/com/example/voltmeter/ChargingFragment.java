package com.example.voltmeter;

import android.app.Dialog;

import androidx.fragment.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;


public class ChargingFragment extends Fragment {
    Toolbar toolbar;
    Button btnPowerUsage;
    TextView tvLevel,tvChargingStatus,tvPlugged,tvChargeTimeRemaining,tvBatteryLow,tvChargeScale,tvChargeStatus;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.charging_activity,container,false);
        btnPowerUsage=view.findViewById(R.id.btn_power_usage);
        toolbar=view.findViewById(R.id.charging_top_bar);
        tvLevel=view.findViewById(R.id.progressText);
        tvChargingStatus=view.findViewById(R.id.tv_charging_status);
        tvPlugged=view.findViewById(R.id.tv_plugged);
        tvChargeTimeRemaining=view.findViewById(R.id.tv_charge_time_remaining);
        tvBatteryLow=view.findViewById(R.id.tv_battery_low);
        tvChargeScale=view.findViewById(R.id.tv_charge_scale);
        tvChargeStatus=view.findViewById(R.id.tv_charge_status);
        btnPowerUsage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_POWER_USAGE_SUMMARY);
                startActivity(intent);
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               createDialog();
            }
        });

        ContextCompat.registerReceiver(requireContext(),receiver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED),ContextCompat.RECEIVER_VISIBLE_TO_INSTANT_APPS);
        return view;
    }
    BroadcastReceiver receiver=new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
//            logic to set the present level of the battery
            int level=intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
            tvLevel.setText(level+"%");
//            logic to find that is charger is plugged or not if plugged then plugged will be show on the textview otherwise Disconnected will be shown
            int isPlugged=intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,0);
            if(isPlugged==0){
                tvChargingStatus.setText("Disconnected");
            }else{
                tvChargingStatus.setText("Plugged");
            }
//            logic to find ,is battery charger is plugged or not or if plugged so at which source ?
            int plugged=intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,0);
            if(plugged==BatteryManager.BATTERY_PLUGGED_AC){
                tvPlugged.setText("AC");
            } else if (plugged==BatteryManager.BATTERY_PLUGGED_USB) {
                tvPlugged.setText("USB");
            } else if (plugged==BatteryManager.BATTERY_PLUGGED_DOCK) {
                tvPlugged.setText("Dock");
            } else if (plugged==BatteryManager.BATTERY_PLUGGED_WIRELESS) {
                tvPlugged.setText("Wireless");
            }else{
                tvPlugged.setText("fasle");
            }
//             logic to set the remaining charging time if charging ,if not charging then set N/A
            BatteryManager batteryManager = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);
            int chargeCurrent = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);
            int batteryCapacity = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER);
            if (isPlugged!=0&&chargeCurrent > 0) {
                long estimatedTime = (batteryCapacity / chargeCurrent) * 60; // Estimated time in minutes
                tvChargeTimeRemaining.setText(estimatedTime + " min");
            }else{
                tvChargeTimeRemaining.setText("N/A");
            }
//            logic to set that battery is low or not
            if(level>15){
                tvBatteryLow.setText("false");
            }else{
                tvBatteryLow.setText("true");
            }
//            logic to set the charging scale of battery
            int scale=intent.getIntExtra(BatteryManager.EXTRA_SCALE,0);
            tvChargeScale.setText(String.valueOf(scale));
//            logic to set the charging status of the battery
            int charging=intent.getIntExtra(BatteryManager.EXTRA_STATUS,0);
            if(charging==BatteryManager.BATTERY_STATUS_CHARGING){
                tvChargeStatus.setText("Charging");
            } else if (charging==BatteryManager.BATTERY_STATUS_NOT_CHARGING) {
                tvChargeStatus.setText("Not Charging");
            } else if (charging==BatteryManager.BATTERY_STATUS_DISCHARGING){
                tvChargeStatus.setText("Discharging");
            }else {
                tvChargeStatus.setText("Full");
            }
        }
    };
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        requireContext().unregisterReceiver(receiver);
    }
}
