package ru.mirea.kryazhin.mireaproject.ui.firebase

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import ru.mirea.kryazhin.mireaproject.MainActivity
import ru.mirea.kryazhin.mireaproject.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FirebaseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FirebaseFragment : AppCompatActivity(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val TAG = MainActivity::class.java.simpleName
    private var mStatusTextView: TextView? = null
    private var mDetailTextView: TextView? = null
    private var mEmailField: EditText? = null
    private var mPasswordField: EditText? = null
    private var enterButton: Button? = null

    private var mAuth: FirebaseAuth? = null

    override fun onCreate (savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_firebase)
        enterButton = findViewById<Button>(R.id.enterButton)
        mStatusTextView = findViewById<TextView>(R.id.status)
        mDetailTextView = findViewById<TextView>(R.id.detail)
        mEmailField = findViewById<EditText>(R.id.fieldEmail)
        mPasswordField = findViewById<EditText>(R.id.fieldPassword)
        findViewById<View>(R.id.emailSignInButton).setOnClickListener(this)
        findViewById<View>(R.id.emailCreateAccountButton).setOnClickListener(this)
        findViewById<View>(R.id.signOutButton).setOnClickListener(this)
        findViewById<View>(R.id.verifyEmailButton).setOnClickListener(this)
        mAuth = FirebaseAuth.getInstance()
        signOut()
        enterButton!!.setOnClickListener { view ->
            val intent = Intent(view.context, MainActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onStart() {
        super.onStart()
        val currentUser = mAuth!!.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            mStatusTextView!!.text = getString(
                R.string.emailpassword_status_fmt,
                user.email, user.isEmailVerified
            )
            mDetailTextView!!.text = getString(R.string.firebase_status_fmt, user.uid)
            findViewById<View>(R.id.emailPasswordButtons).setVisibility(View.GONE)
            findViewById<View>(R.id.emailPasswordFields).setVisibility(View.GONE)
            findViewById<View>(R.id.signedInButtons).setVisibility(View.VISIBLE)
            findViewById<View>(R.id.verifyEmailButton).setEnabled(!user.isEmailVerified)
        } else {
            mStatusTextView!!.setText(R.string.signed_out)
            findViewById<View>(R.id.emailPasswordButtons).setVisibility(View.VISIBLE)
            findViewById<View>(R.id.emailPasswordFields).setVisibility(View.VISIBLE)
            findViewById<View>(R.id.signedInButtons).setVisibility(View.GONE)
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
                            this@FirebaseFragment, "Authentication failed.",
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
        mAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this,
                OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithEmail:success")
                        enterButton!!.visibility = View.VISIBLE
                        val user = mAuth!!.currentUser
                        updateUI(user)
                    } else {
                        Log.w(
                            TAG,
                            "signInWithEmail:failure",
                            task.exception
                        )
                        Toast.makeText(
                            this@FirebaseFragment, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        updateUI(null)
                    }
                    if (!task.isSuccessful) {
                        mStatusTextView!!.setText(R.string.auth_failed)
                    }
                })
    }

    private fun signOut() {
        enterButton!!.visibility = View.INVISIBLE
        mAuth!!.signOut()
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
            val user = mAuth!!.currentUser!!
            user.sendEmailVerification()
        }
    }
}