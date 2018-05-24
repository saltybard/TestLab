package edu.tacoma.uw.renzc.testinglab;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link onRegisterListener} interface
 * to handle interaction events.
 */
public class RegisterFragment extends Fragment {

    private onRegisterListener mRegisterListener;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_register, container, false);

        final EditText emailText = (EditText)v.findViewById(R.id.edit_email);
        final EditText pwdText = (EditText)v.findViewById(R.id.edit_pwd);
        Button registerButton = (Button) v.findViewById(R.id.btn_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailText.getText().toString();
                String pwd = pwdText.getText().toString();

                try {
                    Account account = new Account(email, pwd);
                    mRegisterListener.register(account);
                } catch (Exception e) {
                    Toast.makeText(v.getContext(),
                            "Unable to register:" + e.getMessage(),
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

        TextView logInLink = (TextView) v.findViewById(R.id.tv_login);
        logInLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new LoginFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
        return v;
    }

    /*// TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onRegisterListener) {
            mRegisterListener = (onRegisterListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement onRegisterListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mRegisterListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface onRegisterListener {
        void register(Account account);
        void launchLoginFragment();
    }


}
