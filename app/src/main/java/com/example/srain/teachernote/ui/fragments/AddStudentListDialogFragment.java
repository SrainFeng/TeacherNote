package com.example.srain.teachernote.ui.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.srain.teachernote.R;

/**
 * Project: TeacherNote
 * Date: 2018/6/26
 *
 * @author srain
 */
public class AddStudentListDialogFragment extends DialogFragment {
    private EditText addName;
    private EditText addNumber;
    private EditText addScore;

    static AddStudentListDialogFragment addStudentListDialogFragment;

    private static LoginInputListener mListener;

    public static void setLoginInputListener(LoginInputListener listener) {
        mListener = listener;
    }

    public static AddStudentListDialogFragment addDialogFragmentCreator(){
        if (addStudentListDialogFragment == null) {
            addStudentListDialogFragment = new AddStudentListDialogFragment();
        }
        return addStudentListDialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.student_list_dialog_fragment_layout, null);
        addName = view.findViewById(R.id.add_student_name);
        addNumber = view.findViewById(R.id.add_student_number);
        addScore = view.findViewById(R.id.add_student_score);

        builder.setView(view).setPositiveButton("添加", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String addStudentName = addName.getText().toString();
                String addStudentNumber = addNumber.getText().toString();
                String addStudentScore = addScore.getText().toString();
                mListener.onLogInputComplete(addStudentName, addStudentNumber, addStudentScore);
            }
        });
        return builder.create();

    }

    public interface LoginInputListener{
        void onLogInputComplete(String addName, String addNumber, String addScore);
    }
}
