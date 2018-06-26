package com.example.srain.teachernote.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.srain.teachernote.R;

/**
 * Project: TeacherNote
 * Date: 2018/5/16
 *
 * @author srain
 */
public class AddTeachClassDialogFragment extends DialogFragment {

    private EditText add_name;

    private EditText add_code;

    static AddTeachClassDialogFragment addTeachClassDialogFragment;

    private static LoginInputListener mListener;

    public static void setLoginInputListener(LoginInputListener listener) {
        mListener = listener;
    }

    public static AddTeachClassDialogFragment addDialogFragmentCreator() {
        if (addTeachClassDialogFragment == null) {
            addTeachClassDialogFragment = new AddTeachClassDialogFragment();
        }
        // 工厂方法传递参数的方法
        Bundle bundle = new Bundle();
        bundle.putString("name", "sss");
        addTeachClassDialogFragment.setArguments(bundle);
        return addTeachClassDialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String name = getArguments().getString("name");
        Log.d("add", name);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.teach_class_dialog_fragment_layout, null);
        add_name = view.findViewById(R.id.add_class_name);
        add_code = view.findViewById(R.id.add_class_code);

        builder.setView(view).setPositiveButton("添加", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String addClassName = add_name.getText().toString();
                String addClassCode = add_code.getText().toString();
                mListener.onLogInputComplete(addClassName, addClassCode);
            }
        });
        return builder.create();
    }

    public interface LoginInputListener{
        /**
         *
         * @param addClassName 要添加的课程姓名
         * @param addClassCode 要添加的课程编码
         */
        void onLogInputComplete(String addClassName, String addClassCode);
    }
}