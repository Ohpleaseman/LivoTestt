package tj.emin.livotestt


import android.app.Activity
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import tj.emin.testapp.R

object BiometricPromptUtils {

    fun createBiometricPrompt(
        activity: FragmentActivity,
        processSuccess: (BiometricPrompt.AuthenticationResult) -> Unit
    ): BiometricPrompt {
        val executor = ContextCompat.getMainExecutor(activity)

        fun toast(message: String) =
            Toast.makeText(
                activity, message,
                Toast.LENGTH_LONG
            ).show()

        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                toast(activity.getString(R.string.auth_succeeded))
                processSuccess(result)
            }

            override fun onAuthenticationError(errCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errCode, errString)
                toast("${activity.getString(R.string.auth_error)}: $errCode")
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                toast(activity.getString(R.string.auth_failed))
            }
        }
        return BiometricPrompt(activity, executor, callback)
    }

    fun createPromptInfo(activity: Activity): BiometricPrompt.PromptInfo =
        BiometricPrompt.PromptInfo.Builder().apply {
            setTitle(activity.getString(R.string.prompt_info_title))
            setConfirmationRequired(false)
            setNegativeButtonText(activity.getString(R.string.cancel))
        }.build()
}
