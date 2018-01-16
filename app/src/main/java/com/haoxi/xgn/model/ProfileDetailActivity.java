package com.haoxi.xgn.model;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.haoxi.xgn.R;
import com.haoxi.xgn.base.BaseActivity;
import com.haoxi.xgn.utils.ActivityFragmentInject;
import com.haoxi.xgn.utils.ApiUtils;
import com.haoxi.xgn.widget.WheelView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

@ActivityFragmentInject(contentViewId = R.layout.act_prodetail,menuId = 1,toolbarTitle = R.string.siger)
public class ProfileDetailActivity extends BaseActivity implements View.OnClickListener{

    private static final List<String> PLANETS = new ArrayList<>();
    private static final List<String> ageLists = new ArrayList<>();
    private static final List<String> highLists = new ArrayList<>();
    private static final List<String> weightLists = new ArrayList<>();


    @BindView(R.id.profile_civ)
    CircleImageView mHeadIv;
    @BindView(R.id.update_pwd)
    RelativeLayout mUpdatePwd;
    @BindView(R.id.sexRl)
    RelativeLayout mSexRl;
    @BindView(R.id.ageRl)
    RelativeLayout mAgeRl;
    @BindView(R.id.highRl)
    RelativeLayout mHighRl;
    @BindView(R.id.weightRl)
    RelativeLayout mWeightRl;
    @BindView(R.id.sexTv)
    TextView mSexTv;
    @BindView(R.id.ageTv)
    TextView mAgeTv;
    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA
    };
    private String changeStr;

    @Override
    protected void init() {
        mHeadIv.setOnClickListener(this);
        mUpdatePwd.setOnClickListener(this);
        mSexRl.setOnClickListener(this);
        mAgeRl.setOnClickListener(this);
        mHighRl.setOnClickListener(this);
        mWeightRl.setOnClickListener(this);

        for (int i = 1; i < 200; i++) {
            if (i<=130){
                ageLists.add(String.valueOf(i));
            }
            highLists.add(String.valueOf(i));
            weightLists.add(String.valueOf(i));
        }

        PLANETS.add("男");
        PLANETS.add("女");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.profile_civ:
                showHeadDialog();
                break;
            case R.id.sexRl:
                showSexDialog(false,"sex","男",PLANETS,"");
                break;
            case R.id.ageRl:
                showSexDialog(true,"age","27",ageLists,"岁");
                break;
            case R.id.highRl:
                showSexDialog(true,"high","168",highLists,"cm");
                break;
            case R.id.weightRl:
                showSexDialog(true,"weight","63",weightLists,"公斤");
                break;
            case R.id.update_pwd:
                Intent intent = new Intent(ProfileDetailActivity.this,UpdateActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void showHeadDialog(){
        final Dialog mDialog = new Dialog(this, R.style.DialogTheme);
        mDialog.setCancelable(false);
        View view = getLayoutInflater().inflate(R.layout.personal_headciv_dialog,null);
        mDialog.setContentView(view,new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView mTakePhoto = view.findViewById(R.id.headciv_dialog_takephoto);
        TextView mFromPhoto = view.findViewById(R.id.headciv_dialog_fromphoto);
        TextView mCancle = view.findViewById(R.id.headciv_dialog_cancle);
        mCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (isNeedCheck){
//                    if (!ApiUtils.checkPermissions(ProfileDetailActivity.this, ConstantUtils.PERMISSION_REQUESTCODE_1,needPermissions)){
//                        takePhoto();
//                    }
//                }else {
//                    takePhoto();
//                }
                mDialog.dismiss();
            }
        });
        mFromPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (isNeedCheck){
//                    if (!ApiUtils.checkPermissions(ProfileDetailActivity.this, ConstantUtils.PERMISSION_REQUESTCODE_2,needPermissions)){
//                        choosePhoto();
//                    }
//                }else {
//                    choosePhoto();
//                }
                mDialog.dismiss();
            }
        });
        ApiUtils.setDialogWindow(mDialog);
        mDialog.show();
    }

    private void showSexDialog(boolean showDw,String type,String curStr,List PLANETS,String danwei){
        changeStr = "";
        final Dialog mDialog = new Dialog(this, R.style.DialogTheme);
        mDialog.setCancelable(false);
        View view = getLayoutInflater().inflate(R.layout.sex_dialog,null);
        mDialog.setContentView(view,new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView mCancle = view.findViewById(R.id.dialog_cancle);
        TextView mConform = view.findViewById(R.id.dialog_conform);
        TextView mDanwei = view.findViewById(R.id.dialog_danwei);
        WheelView wheelView = view.findViewById(R.id.dialog_wheelview);

        if (showDw){
            mDanwei.setVisibility(View.VISIBLE);
            mDanwei.setText(danwei);
        }

        int index = PLANETS.indexOf(curStr);

        wheelView.setOffset(1);
        wheelView.setItems(PLANETS);
        wheelView.setSeletion(index);
        wheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                changeStr = item;
                mDialog.dismiss();
            }
        });

        mCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        mConform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });

        ApiUtils.setDialogWindow(mDialog);
        mDialog.show();
    }
}
