package com.nazmul.assessmentapphpl.viewmodel;

import static android.content.ContentValues.TAG;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.nazmul.assessmentapphpl.model.Registration;
import com.nazmul.assessmentapphpl.repository.RegistrationRepository;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RegistrationViewModel extends AndroidViewModel {

    private RegistrationRepository repository;
    private CompositeDisposable disposable;
    public RegistrationViewModel(@NonNull Application application) {
        super(application);
        repository = new RegistrationRepository();
        disposable = new CompositeDisposable();

    }


    public MutableLiveData<String> getRegistration(Registration registration){
        MutableLiveData<String> mlResponse = new MutableLiveData<>();

        repository.getRegistration(registration)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(String s) {
                        mlResponse.postValue(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError"+e.getMessage());

                    }
                });

        return mlResponse;


    }


}
