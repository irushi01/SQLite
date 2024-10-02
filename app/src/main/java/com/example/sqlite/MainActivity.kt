package com.example.sqlite

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var addName: Button
    private lateinit var printName: Button
    private lateinit var enterName: EditText
    private lateinit var enterAge: EditText
    private lateinit var nameTextView: TextView
    private lateinit var ageTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Initialize views
        addName = findViewById(R.id.addName)
        printName = findViewById(R.id.printName)
        enterName = findViewById(R.id.enterName)
        enterAge = findViewById(R.id.enterAge)
        nameTextView = findViewById(R.id.Name)
        ageTextView = findViewById(R.id.Age)

        // Add onClickListener for addName button
        addName.setOnClickListener {
            val db = DBHelper(this, null)
            val name = enterName.text.toString()
            val age = enterAge.text.toString()

            if (name.isNotBlank() && age.isNotBlank()) {
                db.addName(name, age)
                Toast.makeText(this, "$name added to database", Toast.LENGTH_LONG).show()
                enterName.text.clear()
                enterAge.text.clear()
            } else {
                Toast.makeText(this, "Please enter both name and age", Toast.LENGTH_SHORT).show()
            }
        }

        // Add onClickListener for printName button
        printName.setOnClickListener {
            val db = DBHelper(this, null)
            val cursor = db.getName()

            // Clear previous text before appending new data
            nameTextView.text = ""
            ageTextView.text = ""

            cursor?.let {
                if (it.moveToFirst()) {
                    do {
                        nameTextView.append(it.getString(it.getColumnIndex(DBHelper.NAME_COL)) + "\n")
                        ageTextView.append(it.getString(it.getColumnIndex(DBHelper.AGE_COL)) + "\n")
                    } while (it.moveToNext())
                }
                it.close()
            } ?: run {
                Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show()
            }
        }
    }
}