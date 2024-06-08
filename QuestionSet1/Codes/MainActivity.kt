package com.example.set0liawchunvoon

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.set0liawchunvoon.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        reset()
        binding.btnReset.setOnClickListener { reset() }
        binding.btnSubmit.setOnClickListener { submit() }
        binding.btnWebsite.setOnClickListener { website() }
    }

    fun toast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    fun reset() {
        binding.edtName.text.clear()
        binding.chkScholarship.isChecked = false
        binding.radFemale.isChecked = true
        binding.spnCampus.setSelection(0)
        binding.edtCGPA.text.clear()
        binding.txtOutput.text = ""

        binding.edtName.requestFocus()
    }

    fun submit() {
        // Inputs
        val name: String = binding.edtName.text.toString().trim()
        val scholarship: Boolean = binding.chkScholarship.isChecked
        val gender: String = if (binding.radFemale.isChecked) "Female" else "Male"
        val campus: String = binding.spnCampus.selectedItem.toString()
        val cgpa: Double = binding.edtCGPA.text.toString().toDoubleOrNull() ?: -1.0

        // Validations
        if (name == "") {
            toast("Name required")
            return
        }

        if (cgpa < 0.0000 || cgpa > 4.0000) {
            toast("Range 0.0000-4.0000")
            return
        }

        // Calculations
        val award: String
        val prize: Double

        when {
            cgpa >= 3.7500 -> { award = "Distinction"; prize = 100.00 }
            cgpa >= 2.7500 -> { award = "Merit"; prize = 50.00 }
            cgpa >= 2.0000 -> { award = "Pass"; prize = 0.00 }
            else -> { award = "Fail"; prize = 0.00 }
        }

        // Outputs
        binding.txtOutput.text = """
            Name = $name
            Scholarship = ${if (scholarship) "Yes" else "No"}
            Gender = $gender
            Campus = $campus
            CGPA = ${"%.4f".format(cgpa)}
            Award = $award
            Prize = RM ${"%.2f".format(prize)} %
        """.trimIndent()
    }

    fun website() {
        val uri = Uri.parse("https://www.tarc.edu.my")
        val i = Intent(Intent.ACTION_VIEW, uri)
        startActivity(i)
    }

}