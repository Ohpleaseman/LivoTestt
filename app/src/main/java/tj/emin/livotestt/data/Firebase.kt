package tj.emin.livotestt.data

import android.app.Activity
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import tj.emin.testapp.R

object FirebaseRepository {
    var auth: FirebaseAuth = FirebaseAuth.getInstance()

    private fun toastFailure(activity: Activity, task: Task<AuthResult>) =
        Toast.makeText(
            activity, task.exception?.localizedMessage,
            Toast.LENGTH_LONG
        ).show()

    fun createUser(activity: Activity, email: String, password: String, onSuccess: () -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful)
                    onSuccess()
                else
                    toastFailure(activity, task)
            }
    }

    fun login(activity: Activity, email: String, password: String, onSuccess: () -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful)
                    onSuccess()
                else
                    toastFailure(activity, task)
            }
    }

    fun logOut() {
        auth.signOut()
    }

    fun resetPassword(activity: Activity, email: String, onSuccess: () -> Unit) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    onSuccess()
                    Toast.makeText(
                        activity, activity.getString(R.string.reset_password_request_sent),
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        activity, task.exception?.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
}

