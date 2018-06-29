package com.example.srain.teachernote.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

/**
 * Project: TeacherNote
 * Date: 2018/6/29
 *
 * @author srain
 */
public class DeleteItemDialogFragment extends DialogFragment {
    private String[] mItems;
    private boolean[] mItemStatus;

    private static LoginInputListener mListener;

    public static void setListener(LoginInputListener listener) {
        mListener = listener;
    }

    public static DeleteItemDialogFragment DeleteItemDialogCreator(String[] items, boolean[] itemStatus) {
        DeleteItemDialogFragment fragment = new DeleteItemDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArray("items", items);
        bundle.putBooleanArray("item_status", itemStatus);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mItems = bundle.getStringArray("items");
            mItemStatus = bundle.getBooleanArray("item_status");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMultiChoiceItems(mItems, mItemStatus, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                mItemStatus[which] = isChecked;
            }
        })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onLogInputComplete(mItemStatus);
                    }
                });

        return builder.create();
    }

    public interface LoginInputListener{
        void onLogInputComplete(boolean[] itemStatus);
    }
}
