package com.example.crest
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.DocumentReference

class MainActivity : AppCompatActivity() {

    // Initialize Firestore
    private lateinit var firestore: FirebaseFirestore
    private lateinit var likeButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance()

        // Reference to the like button in the layout
        likeButton = findViewById(R.id.likeButton)

        // Setup like button click listener
        setupLikeButton()
    }

    private fun setupLikeButton() {
        // Set initial button state (outline icon)
        likeButton.setImageResource(R.drawable.ic_heart_outline)

        likeButton.setOnClickListener {
            // Update icon to filled heart
            likeButton.setImageResource(R.drawable.ic_heart_filled)

            // Save like data to Firestore
            saveLikeToFirestore()
        }
    }

    private fun saveLikeToFirestore() {
        // Constant username to save to Firestore
        val username = "Hello"

        // Create a unique document ID for each like
        val likeDoc: DocumentReference = firestore.collection("likes").document()

        // Create like data to store
        val likeData = hashMapOf(
            "username" to username,
            "timestamp" to System.currentTimeMillis()
        )

        // Add data to Firestore
        likeDoc.set(likeData)
            .addOnSuccessListener {
                Log.d("Firestore", "Like saved successfully")
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Failed to save like", e)
            }
    }
}
