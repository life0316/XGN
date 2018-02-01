package com.haoxi.xgn.adapter;

import android.bluetooth.BluetoothDevice;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haoxi.xgn.R;
import com.haoxi.xgn.bean.DeviceBean;
import com.haoxi.xgn.openBle.BleDevice;

import java.util.ArrayList;
import java.util.List;


public class MyShoesAdapter extends RecyclerView.Adapter<MyShoesAdapter.ShoesHolder> implements View.OnClickListener {

    private List<BleDevice> deviceBeen = new ArrayList<>();
    private RecyclerViewOnItemClickListener onItemClickListener;

    public MyShoesAdapter() {

    }

    public void addBleDevice(BleDevice bleDevice){
        deviceBeen.add(bleDevice);

        notifyDataSetChanged();
    }

    @Override
    public ShoesHolder onCreateViewHolder(ViewGroup parent, int position) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myshoes,null);
        view.setOnClickListener(this);

        return new ShoesHolder(view);
    }

    @Override
    public void onBindViewHolder(ShoesHolder shoesHolder, int position) {

        BluetoothDevice bluetoothDevice = deviceBeen.get(position).getDevice();
        shoesHolder.rootView.setTag(position);

        if (bluetoothDevice.getName() == null){
            shoesHolder.deviceIdTv.setText("无");
        }else {
            shoesHolder.deviceIdTv.setText(bluetoothDevice.getName());
        }

        Log.e("fafsf",bluetoothDevice.getAddress()+"-------4");
        shoesHolder.btmacTv.setText(bluetoothDevice.getAddress());
    }

    @Override
    public int getItemCount() {
        return deviceBeen.size();
    }

    @Override
    public void onClick(View view) {
        Log.e("dianji",view.getTag()+"-----tag");
        if (onItemClickListener != null) {
            onItemClickListener.onItemClickListener(view,(int)view.getTag(),deviceBeen.get((int)view.getTag()));
        }
    }

    //设置点击事件
    public void setRecyclerViewOnItemClickListener(RecyclerViewOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //接口回调设置点击事件
    public interface RecyclerViewOnItemClickListener {
        //点击事件
        void onItemClickListener(View view, int position,BleDevice deviceBean);
    }


    public class ShoesHolder extends RecyclerView.ViewHolder{

        TextView deviceIdTv;
        TextView btmacTv;

        View rootView;

        public ShoesHolder(View itemView) {
            super(itemView);
            this.rootView = itemView;
            deviceIdTv = itemView.findViewById(R.id.deviceid);
            btmacTv = itemView.findViewById(R.id.btmac);
        }
    }
}
