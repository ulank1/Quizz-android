package kg.mvvmdordoi.ui.auth.confirm_code

import android.app.Activity
import android.arch.lifecycle.MutableLiveData
import android.content.Intent
import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kg.mvvmdordoi.App
import kg.mvvmdordoi.App.Companion.activity
import kg.mvvmdordoi.base.BaseViewModel
import kg.mvvmdordoi.network.PostApi
import kg.mvvmdordoi.ui.auth.confirm_code.new_password.NewPasswordActivity
import kg.mvvmdordoi.utils.extension.toast
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ConfirmCodeViewModel : BaseViewModel() {
    @Inject
    lateinit var postApi: PostApi
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    lateinit var phone: String
    var isRegister: Int = 0
    private val TAG = this::class.java.simpleName
    private var storedVerificationId: String? = ""
    private var auth = FirebaseAuth.getInstance()
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    private var subscription: CompositeDisposable = CompositeDisposable()

    private var mCallBacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


        override fun onVerificationCompleted(credential: PhoneAuthCredential) {

            Log.e(TAG, "onVerificationCompleted:$credential")

            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {

            Log.e(TAG, "onVerificationFailed", e)
            if (e is FirebaseAuthInvalidCredentialsException) {
                activity?.toast("Формат телефонного номера введен неправильно.")
            } else if (e is FirebaseTooManyRequestsException) {

            }

        }

        override fun onCodeSent(
            verificationId: String?,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            Log.e(TAG, "onCodeSent")
            storedVerificationId = verificationId
            resendToken = token
        }
    }

    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {


        val a = auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.e(TAG, "signed in successfully")
                    Log.e("ISreg",isRegister.toString())
                    onVerificationComplete()
                } else {
                        Log.e("ERROR",task.exception.toString())
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Log.e("LOH","LOH")
                    }
                }
            }
    }

    private fun startPhoneNumberVerification(activity: Activity, phoneNumber: String) {


        Log.e(TAG, phoneNumber)
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber,
            60,
            TimeUnit.SECONDS,
            activity,
            mCallBacks
        )

    }

    fun getSmsCode(activity: Activity, phone: String, isRegister: Int) {

        this.isRegister = isRegister
        this.phone = phone
        startPhoneNumberVerification(activity, phone)
    }

    private fun verifyPhoneNumberWithCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(storedVerificationId!!, code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun resendVerificationCode(
        phoneNumber: String,
        activity: Activity
    ) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber, // Phone number to verify
            60, // Timeout duration
            TimeUnit.SECONDS, // Unit of timeout
            activity, // Activity (for callback binding)
            mCallBacks, // OnVerificationStateChangedCallbacks
            resendToken
        ) // ForceResendingToken from callbacks
    }

    override fun onCleared() {
        super.onCleared()
        subscription = CompositeDisposable()
    }

    private fun onRetrievePostListError() {

    }

    fun sendCode(code: String) {
        if (code.isNotEmpty()) {
            verifyPhoneNumberWithCode(code)
        } else {
            activity?.toast("Наберите Код")
        }
    }

    private fun onVerificationComplete() {
          if (isRegister==0) {
          } else if (isRegister==1){
                App.activity?.startActivity(Intent(App.activity!!, NewPasswordActivity::class.java).putExtra("phone",phone))
              App.activity?.finish()
          }else{

          }
    }



}