package com.example.myapplication.fragment

//import com.example.myapplication.BuildConfig.SERVER_CLIENT_ID

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.GetCredentialRequest
import androidx.fragment.app.Fragment
import com.example.myapplication.BuildConfig
import com.example.myapplication.activity.ShareDataActivity
import com.example.myapplication.databinding.FragmentLoginBinding
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import java.net.URL
import java.security.MessageDigest
import java.util.UUID


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.button.setOnClickListener {
            val intent: Intent = Intent(context, ShareDataActivity::class.java)
            startActivity(intent)
        }

//        Picasso.get()
//            .load("http://localhost/devifood/images/kfc1.png")
//            .into(binding.loginImage)

        return binding.root
    }


    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    fun GoogleSignIn() {

        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()

        val credentialManager = context.let { it1 -> androidx.credentials.CredentialManager.create(it1) }

        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        val hashedNonce = digest.fold("") { str, it -> str + "%02x".format(it) }

        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(true)
            .setServerClientId(BuildConfig.SERVER_CLIENT_ID)
            .setNonce(hashedNonce)
            .build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        coroutineScope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = context,
                )
                val credential = result.credential

                val googleIdTokenCredential = GoogleIdTokenCredential
                    .createFrom(credential.data)

                val googleIdToken = googleIdTokenCredential.idToken

                Log.i("ID_TOKEN", googleIdToken)
                Toast.makeText(context, "You are signed in!", Toast.LENGTH_SHORT).show()
            } catch(e: androidx.credentials.exceptions.GetCredentialException) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            } catch (e: GoogleIdTokenParsingException) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

