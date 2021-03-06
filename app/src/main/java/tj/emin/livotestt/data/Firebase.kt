package tj.emin.livotestt.data

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import tj.emin.livotestt.data.model.User
import tj.emin.testapp.R

object FirebaseRepository {
    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    val userCollection = FirebaseFirestore.getInstance().collection("USERS")

    private fun toastFailure(activity: Activity, task: Task<AuthResult>) =
        Toast.makeText(
            activity, task.exception?.localizedMessage,
            Toast.LENGTH_LONG
        ).show()

    fun createUser(
        activity: Activity,
        email: String,
        password: String,
        user: User,
        onSuccess: () -> Unit
    ) {
        if (email.isNotBlank() && password.isNotBlank())
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity) { task ->
                    if (task.isSuccessful) {
                        saveUser(user, onSuccess)
                    } else
                        toastFailure(activity, task)
                }
        else Toast.makeText(activity, R.string.field_empty, Toast.LENGTH_SHORT).show()
    }

    fun login(activity: Activity, email: String, password: String, onSuccess: () -> Unit) {
        if (email.isNotBlank() && password.isNotBlank()){
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity) { task ->
                    if (task.isSuccessful)
                        onSuccess()
                    else
                        toastFailure(activity, task)
                }
        }
        else Toast.makeText(activity, R.string.field_empty, Toast.LENGTH_SHORT).show()
    }

    fun signOut() {
        auth.signOut()
    }

    fun resetPassword(activity: Activity, email: String, onSuccess: () -> Unit) {
        if (email.isNotBlank())
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
        else Toast.makeText(activity, R.string.field_empty, Toast.LENGTH_SHORT).show()
    }

    private fun saveUser(user: User, onSuccess: () -> Unit) {

        val uid = auth.uid.toString()
        //if(user.)
        userCollection.document(uid).set(user).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess()
                Log.d("TAG", "saveUser: SUCCESS")
            } else {
                Log.d("TAG", "saveUser: ERROR")
            }
        }
    }
}

