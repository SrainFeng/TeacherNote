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
 * Date: 2018/6/29
 *
 * @author srain
 */
public class ReviseScoreDialogFragment extends DialogFragment {
    private EditText mEditText;

    private static LoginInputListener mListener;

    public static ReviseScoreDialogFragment FragmentCreator() {
        ReviseScoreDialogFragment fragment = new ReviseScoreDialogFragment();
        return fragment;
    }

    public static void setListener(LoginInputListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.score_dialog, null);

        mEditText = view.findViewById(R.id.revise_score);

        builder.setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newScore = mEditText.getText().toString();
                mListener.onLogInputComplete(newScore);
            }
        });

        return builder.create();
    }

    public interface LoginInputListener{
        void onLogInputComplete(String newScore);
    }
}
