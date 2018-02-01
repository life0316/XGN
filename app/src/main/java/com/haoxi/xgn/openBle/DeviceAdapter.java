package com.haoxi.xgn.openBle;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.haoxi.xgn.R;
import java.util.List;

public class DeviceAdapter extends BaseAdapter {

    Context context;
    List<BleDevice> bleDeviceList;
    public DeviceAdapter(Context context, List<BleDevice> bleDeviceList){
        this.context = context;
        this.bleDeviceList = bleDeviceList;
    }

    @Override
    public int getCount() {
        return bleDeviceList.size()>5?5:bleDeviceList.size();
    }

    @Override
    public Object getItem(int position) {
        return bleDeviceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.device_list, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        BleDevice device = bleDeviceList.get(position);
        viewHolder.listRiss.setText("RISS：" + device.getRiss());
        viewHolder.listAddress.setText("Name:" + device.getDevice().getName() + "\nMAC:" + device.getDevice().getAddress());
        return convertView;
    }
    class ViewHolder {
        TextView listRiss;
        TextView listAddress;
        ViewHolder(View view) {
            listAddress = view.findViewById(R.id.list_name);
            listRiss = view.findViewById(R.id.list_riss);
        }
    }
}
