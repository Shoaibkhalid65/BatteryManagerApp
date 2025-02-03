package com.example.voltmeter;

import static androidx.core.content.ContextCompat.registerReceiver;

import android.app.Dialog;
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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.Objects;

public class StatsFragment extends Fragment {
    Toolbar toolbar;
    ImageView ivBatteryImage;
    TextView tvBatteryLevel,tvBatteryTemp,tvBatteryVol,tvBatteryTech,tvBatteryHealth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.stats_acitvity,container,false);
        toolbar= view.findViewById(R.id.stats_top_bar);
        ivBatteryImage= view.findViewById(R.id.iv_battery_per);
        tvBatteryLevel=view.findViewById(R.id.tv_battery_per);
        tvBatteryTemp=view.findViewById(R.id.tv_temperature);
        tvBatteryVol=view.findViewById(R.id.tv_voltage);
        tvBatteryHealth=view.findViewById(R.id.tv_battery_health);
        tvBatteryTech=view.findViewById(R.id.tv_battery_tech);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                android.os.Process.killProcess(android.os.Process.myPid());
//                System.exit(0);
                createDialog();
            }
        });
        IntentFilter intentFilter=new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        ContextCompat.registerReceiver(requireContext(),receiver,intentFilter, ContextCompat.RECEIVER_VISIBLE_TO_INSTANT_APPS);
        return view;
    }
    BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level=intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
            int temp=intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0);
            int vol=intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,0);
            String tech=intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
            int health=intent.getIntExtra(BatteryManager.EXTRA_HEALTH,0);

//                logic to set the picture of battery according to the particular levels
            if(level==0){
                ivBatteryImage.setImageResource(R.drawable.battery_0);
            } else if (level>0&&level<=20) {
                ivBatteryImage.setImageResource(R.drawable.battery_1);
            }else if (level>20&&level<=40) {
                ivBatteryImage.setImageResource(R.drawable.battery_2);
            }else if (level>40&&level<=60) {
                ivBatteryImage.setImageResource(R.drawable.battery_3);
            }else if (level>60&&level<=70) {
                ivBatteryImage.setImageResource(R.drawable.battery_4);
            }else if (level>70&&level<=80) {
                ivBatteryImage.setImageResource(R.drawable.battery_5);
            }else {
                ivBatteryImage.setImageResource(R.drawable.battery_6);
            }
//                logic to set the battery level on textview
            tvBatteryLevel.setText(String.valueOf(level)+'%');
//                logic to set the temperature of battery on textview
            tvBatteryTemp.setText(String.valueOf(temp/10));
//                logic to set the voltage of battery on textview
//            because i want only first digit after the decimal point
            float batVol= (float) vol /1000;
            batVol=(float) (int)(batVol*10)/10;
            tvBatteryVol.setText(String.valueOf(batVol));
//                logic to set the technology of battery on textview
            tvBatteryTech.setText(tech);
//                logic to set the health of battery on textview
            if(health==BatteryManager.BATTERY_HEALTH_COLD){
                tvBatteryHealth.setText("Cold");
            } else if (health==BatteryManager.BATTERY_HEALTH_DEAD) {
                tvBatteryHealth.setText("Dead");
            } else if (health==BatteryManager.BATTERY_HEALTH_GOOD) {
                tvBatteryHealth.setText("Good");
            }else if (health==BatteryManager.BATTERY_HEALTH_OVERHEAT) {
                tvBatteryHealth.setText("Overheat");
            }else if (health==BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE) {
                tvBatteryHealth.setText("Over Voltage");
            }else if (health==BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE) {
                tvBatteryHealth.setText("Unspecified Failure");
            }else {
                tvBatteryHealth.setText("Unknown");
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
