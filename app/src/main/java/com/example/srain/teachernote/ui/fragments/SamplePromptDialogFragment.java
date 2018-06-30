package com.example.srain.teachernote.ui.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

/**
 * Project: TeacherNote
 * Date: 2018/6/26
 *
 * @author srain
 */
public class SamplePromptDialogFragment extends DialogFragment {

    private String mAlertMsg;

    private static SamplePromptDialogFragment samplePromptDialogFragment;

    protected static SampleInputListener mListener;

    public static void setSampleInputListener(SampleInputListener listener) {
        mListener = listener;
    }

    public static SamplePromptDialogFragment samplePromptDialogCreator(String alertMassage) {
        if (samplePromptDialogFragment == null) {
            samplePromptDialogFragment = new SamplePromptDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putString("msg", alertMassage);
            samplePromptDialogFragment.setArguments(bundle);
        }
        return samplePromptDialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mAlertMsg = bundle.getString("msg");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("提示")
                .setMessage(mAlertMsg)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.sampleCellBack();
                    }
                });
        return builder.create();
    }

    public interface SampleInputListener{
        /**
         * 选择确定则执行该回调中的操作
         */
        void sampleCellBack();
    }
}
