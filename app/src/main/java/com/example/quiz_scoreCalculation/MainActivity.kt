package com.example.quiz_scoreCalculation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var keys = ArrayList<String>()
    private var values = ArrayList<ArrayList<String>>()
    private var correctKeyValues = HashMap<String, String>()

    private var index = 0
    private lateinit var selection: String
    var selectedanswers = ArrayList<String>()

    private fun arrayLists() {

        //Adding all questions to keys
        keys.add("What color is the Android Mascot?")
        keys.add("How many days are there in a year?")
        keys.add("Which is the largest country?")
        keys.add("What is the highest peak called?")
        keys.add("What is the molecular formula for Water?")
        keys.add("In which continent does Nepal reside?")

        //Adding all options to values
        values.add(arrayListOf("Blue", "Red", "Green", "Yellow"))
        values.add(arrayListOf("362", "365", "369", "366"))
        values.add(arrayListOf("Japan", "China", "Australia", "Russia"))
        values.add(
            arrayListOf(
                "Mt. Everest",
                "Mt. Annapurna",
                "Mt. Kanchenjunga",
                "Mt. Dhaulagiri"
            )
        )
        values.add(arrayListOf("HCl", "H20", "HI", "NaOH"))
        values.add(arrayListOf("Africa", "North America", "Asia", "Europe"))

        //Adding correct key values to a hash map
        correctKeyValues = hashMapOf(
            keys[0] to values[0][2],
            keys[1] to values[1][1],
            keys[2] to values[2][3],
            keys[3] to values[3][0],
            keys[4] to values[4][1],
            keys[5] to values[5][2]
        )

        //Loading the first question at the start of the app
        changequestions(0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        finalScore.isVisible = false

        //Loading the app with arraylists
        arrayLists()

        option1.setOnClickListener {
            option2.isChecked = false
            option3.isChecked = false
            option4.isChecked = false
        }
        option2.setOnClickListener {
            option1.isChecked = false
            option3.isChecked = false
            option4.isChecked = false
        }
        option3.setOnClickListener {
            option2.isChecked = false
            option1.isChecked = false
            option4.isChecked = false
        }
        option4.setOnClickListener {
            option2.isChecked = false
            option3.isChecked = false
            option1.isChecked = false
        }

        submit.setOnClickListener {
            val answer = checkanswers()

            if (answer == "") {
                Toast.makeText(this, "Select one answer to proceed", Toast.LENGTH_SHORT)
                    .show()
            } else {
                if (answer == correctKeyValues[keys[index]]) {
                    Toast.makeText(this, "Correct Answer ✅", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(
                        this,
                        "Incorrect Answer ❌ : ${correctKeyValues[keys[index]]} was the correct answer",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                index += 1
                changequestions(index)
                option1.isChecked = false
                option2.isChecked = false
                option3.isChecked = false
                option4.isChecked = false
            }
        }
    }

    private fun checkanswers(): String {
        selection = if (option1.isChecked) {
            (option1.text).toString()
        } else if (option2.isChecked) {
            (option2.text).toString()
        } else if (option3.isChecked) {
            (option3.text).toString()
        } else if (option4.isChecked) {
            (option4.text).toString()
        } else
            ""
        if (selection != "") {
            selectedanswers.add(selection)
        }
        return selection
    }

    private fun calculateScore(): Int {
        var score = 0
        for (each in selectedanswers) {
            if (each in correctKeyValues.values) {
                score += 10
            }
        }
        return score
    }

    private fun changequestions(index: Int) {
        score.text = "Score: ${calculateScore()}🎭"
        try {
            if (index <= values.size) {
                question_field.text = keys[index]
                option1.text = values[index][0]
                option2.text = values[index][1]
                option3.text = values[index][2]
                option4.text = values[index][3]
            }
        } catch (e: Exception) {
            question_field.text = "You have come to the end of the quiz"
            option1.isVisible = false
            option2.isVisible = false
            option3.isVisible = false
            option4.isVisible = false
            submit.isVisible = false
            question_field.textSize = 24F
            score.textSize = 24F
            Toast.makeText(this, "Your Score is ${calculateScore()}✨", Toast.LENGTH_LONG).show()
            finalScore.text = "Your final Score is: ${calculateScore()}✨"
            finalScore.isVisible = true
            score.isVisible = false
        }
    }
}