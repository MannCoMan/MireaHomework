package ru.mirea.kryazhin.firebaseauth

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val TAG = MainActivity::class.java.simpleName
    private var mStatusTextView: TextView? = null
    private var mDetailTextView: TextView? = null
    private var mEmailField: EditText? = null
    private var mPasswordField: EditText? = null
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mStatusTextView = findViewById(R.id.status);
        mDetailTextView = findViewById(R.id.detail);
        mEmailField = findViewById(R.id.fieldEmail);
        mPasswordField = findViewById(R.id.fieldPassword);
        // Buttons
        findViewById<Button>(R.id.emailSignInButton).setOnClickListener(this);
        findViewById<Button>(R.id.emailCreateAccountButton).setOnClickListener(this);
        findViewById<Button>(R.id.signOutButton).setOnClickListener(this);
        findViewById<Button>(R.id.verifyEmailButton).setOnClickListener(this);
        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser: FirebaseUser? = mAuth?.getCurrentUser()
        updateUI(currentUser)
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            mStatusTextView!!.text = getString(
                R.string.emailpassword_status_fmt,
                user.email, user.isEmailVerified
            )
            mDetailTextView!!.text = getString(R.string.firebase_status_fmt, user.uid)
            findViewById<View>(R.id.emailPasswordButtons).visibility = View.GONE
            findViewById<View>(R.id.emailPasswordFields).visibility = View.GONE
            findViewById<View>(R.id.signedInButtons).visibility = View.VISIBLE
            findViewById<View>(R.id.verifyEmailButton).isEnabled = !user.isEmailVerified
        } else {
            mStatusTextView?.setText(R.string.signed_out)
            mDetailTextView?.setText(null)
            findViewById<View>(R.id.emailPasswordButtons).visibility = View.VISIBLE
            findViewById<View>(R.id.emailPasswordFields).visibility = View.VISIBLE
            findViewById<View>(R.id.signedInButtons).visibility = View.GONE
        }
    }

    private fun validateForm(): Boolean {
        var valid = true
        val email = mEmailField!!.text.toString()
        if (TextUtils.isEmpty(email)) {
            mEmailField!!.error = "Required."
            valid = false
        } else {
            mEmailField!!.error = null
        }
        val password = mPasswordField!!.text.toString()
        if (TextUtils.isEmpty(password)) {
            mPasswordField!!.error = "Required."
            valid = false
        } else {
            mPasswordField!!.error = null
        }
        return valid
    }

    private fun createAccount(email: String, password: String) {
        Log.d(TAG, "createAccount:$email")
        if (!validateForm()) {
            return
        }
        // [START create_user_with_email]
        mAuth?.createUserWithEmailAndPassword(email, password)
            ?.addOnCompleteListener(this,
                OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user: FirebaseUser? = mAuth?.getCurrentUser()
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            this@MainActivity, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        updateUI(null)
                    }
                })
        // [END create_user_with_email]
    }

    private fun signIn(email: String, password: String) {
        Log.d(TAG, "signIn:$email")
        if (!validateForm()) {
            return
        }
        // [START sign_in_with_email]
        mAuth?.signInWithEmailAndPassword(email, password)
            ?.addOnCompleteListener(this,
                OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user: FirebaseUser? = mAuth?.getCurrentUser()
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            this@MainActivity, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        updateUI(null)
                    }
                    // [START_EXCLUDE]
                    if (!task.isSuccessful) {
                        mStatusTextView?.setText(R.string.auth_failed)
                    }
                    // [END_EXCLUDE]
                })
        // [END sign_in_with_email]
    }

    private fun signOut() {
        mAuth?.signOut()
        updateUI(null)
    }

    override fun onClick(v: View) {
        val i = v.id
        if (i == R.id.emailCreateAccountButton) {
            createAccount(mEmailField!!.text.toString(), mPasswordField!!.text.toString())
        } else if (i == R.id.emailSignInButton) {
            signIn(mEmailField!!.text.toString(), mPasswordField!!.text.toString())
        } else if (i == R.id.signOutButton) {
            signOut()
        } else if (i == R.id.verifyEmailButton) {
            val user: FirebaseUser = mAuth?.getCurrentUser()!!
            user.sendEmailVerification()
        }
    }
}