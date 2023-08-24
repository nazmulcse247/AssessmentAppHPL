package com.nazmul.assessmentapphpl.ui.fragment;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.nazmul.assessmentapphpl.R;
import com.nazmul.assessmentapphpl.database.UserPreference;
import com.nazmul.assessmentapphpl.databinding.FragmentOtpVerificationBinding;

import java.util.concurrent.TimeUnit;


public class OtpVerificationFragment extends Fragment {

    private FragmentOtpVerificationBinding binding;
    private String verificationId;
    private String phoneNumber;
    private UserPreference preference;
    private NavController navController;
    public OtpVerificationFragment() {
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
        binding = FragmentOtpVerificationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        initView();
        setOnClickListeners();
        setOTPInputs();

    }

    private void setOnClickListeners() {

        binding.buttonVerify.setOnClickListener(view -> {
            if (binding.inputCode1.getText().toString().trim().isEmpty()
                    || binding.inputCode2.getText().toString().trim().isEmpty()
                    || binding.inputCode3.getText().toString().trim().isEmpty()
                    || binding.inputCode4.getText().toString().trim().isEmpty()
                    || binding.inputCode5.getText().toString().trim().isEmpty()
                    || binding.inputCode6.getText().toString().trim().isEmpty()) {
                Toast.makeText(requireContext(), "Please Enter valid code", Toast.LENGTH_SHORT).show();
                return;
            }

            String code = binding.inputCode1.getText().toString() +
                    binding.inputCode2.getText().toString() +
                    binding.inputCode3.getText().toString() +
                    binding.inputCode4.getText().toString() +
                    binding.inputCode5.getText().toString() +
                    binding.inputCode6.getText().toString();

            if (verificationId != null) {
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.buttonVerify.setVisibility(View.INVISIBLE);

                PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                        verificationId,
                        code
                );
                FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                binding.progressBar.setVisibility(View.GONE);
                                binding.buttonVerify.setVisibility(View.VISIBLE);

                                if (task.isSuccessful()) {
                                    //save user info
                                    preference.setLoginStatus(phoneNumber);
                                    Toast.makeText(requireContext(), "Verify Successful", Toast.LENGTH_SHORT).show();
                                    navController.navigate(R.id.action_otpVerificationFragment_to_registerFragment);

                                } else {
                                    Toast.makeText(requireContext(), "The Verification code was entered invalid", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });
        binding.textResendOTP.setOnClickListener(view -> {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    "+880" + phoneNumber,
                    60,
                    TimeUnit.SECONDS,
                    requireActivity(),
                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                        @Override
                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                        }

                        @Override
                        public void onVerificationFailed(@NonNull FirebaseException e) {
                            Toast.makeText(requireContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                            verificationId = s;
                            Toast.makeText(requireContext(), "OTP sent", Toast.LENGTH_SHORT).show();
                        }
                    }
            );

        });
    }

    private void initView() {
        if (getArguments() != null){
            OtpVerificationFragmentArgs args = OtpVerificationFragmentArgs.fromBundle(getArguments());
            phoneNumber = args.getPhone();
            verificationId = args.getVerificationId();
            preference = new UserPreference(requireContext());
            Log.d(TAG, "initView" + phoneNumber + " " + verificationId);
            binding.textMobile.setText(String.format("+880-%s",args.getPhone()));
        }
    }

    private void setOTPInputs(){
        binding.inputCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty()){
                    binding.inputCode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.inputCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty()){
                    binding.inputCode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.inputCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty()){
                    binding.inputCode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.inputCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty()){
                    binding.inputCode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.inputCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty()){
                    binding.inputCode6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}