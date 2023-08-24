package com.nazmul.assessmentapphpl.ui.fragment;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.nazmul.assessmentapphpl.R;
import com.nazmul.assessmentapphpl.databinding.FragmentOtpSentBinding;

import java.util.concurrent.TimeUnit;


public class OtpSentFragment extends Fragment {

    private FragmentOtpSentBinding binding;
    private NavController navController;
    public OtpSentFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOtpSentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        setOnClickListeners();
    }

    private void setOnClickListeners() {

        binding.buttonGetOTP.setOnClickListener(view -> {
            if (binding.inputMobile.getText().toString().trim().isEmpty()){
                Toast.makeText(requireContext(), "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                return;
            }
            else {
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.buttonGetOTP.setVisibility(View.INVISIBLE);

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+880" + binding.inputMobile.getText().toString(),
                        60,
                        TimeUnit.SECONDS,
                        requireActivity(),
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                binding.progressBar.setVisibility(View.GONE);
                                binding.buttonGetOTP.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                binding.progressBar.setVisibility(View.GONE);
                                binding.buttonGetOTP.setVisibility(View.VISIBLE);
                                Log.e(TAG, "onVerificationFailed: "+e.getLocalizedMessage());
                                Toast.makeText(requireContext(), "Try To Another Phone Number", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                binding.progressBar.setVisibility(View.GONE);
                                binding.buttonGetOTP.setVisibility(View.VISIBLE);

                                OtpSentFragmentDirections.ActionOtpSentFragmentToOtpVerificationFragment action = OtpSentFragmentDirections.actionOtpSentFragmentToOtpVerificationFragment();
                                action.setVerificationId(s);
                                action.setPhone(binding.inputMobile.getText().toString());
                                navController.navigate(action);
                            }
                        }
                );

            }
        });
    }


}