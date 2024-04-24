package com.example.setawongfulim

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.setawongfulim.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        reset()
        binding.btnReset.setOnClickListener { reset() }
        binding.btnSubmit.setOnClickListener { submit() }
        binding.btnPhone.setOnClickListener { phone() }
        binding.btnSMS.setOnClickListener { sms() }
    }

    fun toast(s: String){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show()
    }

    fun reset(){
        binding.edtWeight.text.clear()
        binding.edtHeight.text.clear()
        binding.radHealthy.isChecked = true
        binding.chkFirstTimeDonor.isChecked = false
        binding.edtAge.text.clear()
        binding.spnState.setSelection(0)

        binding.txtOutput.text = ""

        binding.edtWeight.requestFocus()
    }

    fun submit(){
        val weight: Double = binding.edtWeight.text.toString().toDoubleOrNull() ?: 0.0
        val height: Double = binding.edtHeight.text.toString().toDoubleOrNull() ?: 0.0
        val condition: String = if(binding.radHealthy.isChecked) "Healthy" else "Sick"
        val firstTimeDonor: Boolean = binding.chkFirstTimeDonor.isChecked
        val age: Int = binding.edtAge.text.toString().toIntOrNull() ?: 0
        val region: String = binding.spnState.selectedItem.toString()

        if(weight < 0.01){
            toast("Invalid Weight")
            return
        }

        if(height < 0.01){
            toast("Invalid Height")
            return
        }

        if(age < 1){
            toast("Invalid Age")
            return
        }

        val bmi: Double = weight / (height * height)


        val status: String = when {
            bmi < 18.5 -> "Underweight"
            bmi < 25.0 -> "Normal"
            bmi < 30.0 -> "Overweight"
            else -> "Obese"
        }

//        val eligible: Boolean
//        eligible = if(!(status == "Normal" && condition == "Healthy")){
//            false
//        } else {
//            if(firstTimeDonor && age>=18 && age <=60){
//                true
//            } else if (!firstTimeDonor && age>=18 && age<=65){
//                true
//            } else {
//                false
//            }
//        }
        val isNormalHealthy = status == "Normal" && condition == "Healthy"
        val isAgeInRange = if(firstTimeDonor) age in 18..60 else age in 18..65
        val eligible: Boolean = isNormalHealthy && isAgeInRange

        binding.txtOutput.text = """
            Weight = ${"%.2f".format(weight)} kg
            Height = ${"%.2f".format(height)} m
            Condition = $condition
            First Time Donor =  ${if(firstTimeDonor) "Yes" else "No"}
            Age = $age
            Region = $region
            BMI = ${"%.1f".format(bmi)}
            Status = $status
            Eligible = ${if(eligible) "Yes" else "No"}
        """.trimIndent()

    }

    fun phone(){
        val uri = Uri.parse("tel:+60123456789")
        val i = Intent(Intent.ACTION_DIAL,uri)
        startActivity(i)
    }

    fun sms(){
        val uri = Uri.parse("sms:+60123456789")
        val i = Intent(Intent.ACTION_SENDTO,uri)
        startActivity(i)
    }
}