package com.nazmul.assessmentapphpl.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nazmul.assessmentapphpl.R;
import com.nazmul.assessmentapphpl.database.UserPreference;
import com.nazmul.assessmentapphpl.databinding.FragmentRegisterBinding;
import com.nazmul.assessmentapphpl.model.Registration;
import com.nazmul.assessmentapphpl.viewmodel.RegistrationViewModel;


public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private UserPreference preference;
    private RegistrationViewModel viewModel;

    public RegisterFragment() {
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
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);
        initView();
        setOnClickListeners();

    }

    private void setOnClickListeners() {

        binding.btnSignUp.setOnClickListener(v -> {
            registrationUIObserver();
        });
    }

    private void registrationUIObserver() {

        Registration registration = new Registration(binding.etUserName.getText().toString(),
                binding.etUserEmail.getText().toString(),
                binding.etUserPhoneNo.getText().toString());

        binding.progressBar.setVisibility(View.VISIBLE);
        viewModel.getRegistration(registration).observe(getViewLifecycleOwner(), response -> {
            if (response != null){
                Toast.makeText(requireContext(), response, Toast.LENGTH_SHORT).show();
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void initView() {
        preference = new UserPreference(requireContext());
        binding.etUserPhoneNo.setText("0"+preference.getLoginStatus());
    }
}