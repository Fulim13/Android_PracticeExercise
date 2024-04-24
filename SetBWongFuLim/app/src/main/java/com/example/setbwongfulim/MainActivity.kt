package com.example.setbwongfulim

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.setbwongfulim.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        reset()
        binding.btnReset.setOnClickListener { reset() }
        binding.btnSubmit.setOnClickListener { submit() }
        binding.btnEmail.setOnClickListener { email() }
        binding.btnMap.setOnClickListener { map() }
    }

    fun toast(s: String){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show()
    }

    fun reset(){
        binding.spnProgram.setSelection(0)
        binding.edtYear.text.clear()
        binding.edtCGPA.text.clear()

        binding.txtOutput.text = ""

        binding.edtYear.requestFocus()
    }

    fun submit(){
        val program: String = binding.spnProgram.selectedItem.toString()
        val year: Int = binding.edtYear.text.toString().toIntOrNull() ?: 0
        val cgpa: Double = binding.edtCGPA.text.toString().toDoubleOrNull() ?: -1.0

        if(year < 1 || year > 3){
            toast("Invalid Year")
            return
        }

        if(cgpa < 0.0 || cgpa > 4.0){
            toast("Invalid CGPA")
            return
        }

        val fee: Double = when (program) {
            "RIS" -> when (year) {
                1 -> 1100.00
                2 -> 1200.00
                3 -> 1300.00
                else -> 0.00
            }
            "RIT" -> when (year) {
                1 -> 1400.00
                2 -> 1500.00
                3 -> 1600.00
                else -> 0.00
            }
            "RSD","RSW" -> when (year) {
                1 -> 1700.00
                2 -> 1800.00
                3 -> 1800.00
                else -> 0.00
            }
            else -> 0.00
        }

        val rate: Double = when {
            cgpa >= 3.75 -> 1.00
            cgpa >= 3.50 -> 0.75
            cgpa >= 3.25 -> 0.50
            cgpa >= 3.00 -> 0.25
            else-> 0.00
        }

        val discount: Double = fee * rate
        val payable: Double = fee - discount


        binding.txtOutput.text = """
            Program = $program
            Year = $year
            CGPA = ${"%.4f".format(cgpa)}
            Fee = RM ${"%.2f".format(fee)}
            Rate = ${"%.0f".format(rate * 100)} %
            Discount = RM ${"%.2f".format(discount)}
            Payable = RM ${"%.2f".format(payable)}
        """.trimIndent()
    }

    fun email(){
        val uri = Uri.parse("mailto:someone@example.com")
        val i = Intent(Intent.ACTION_SENDTO,uri)
        startActivity(i)
    }

    fun map(){
        val uri = Uri.parse("geo:4.215515,101.728186")
        val i = Intent(Intent.ACTION_VIEW,uri)
        startActivity(i)
    }
}