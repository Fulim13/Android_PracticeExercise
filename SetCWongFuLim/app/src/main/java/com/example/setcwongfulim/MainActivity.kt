package com.example.setcwongfulim

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.setcwongfulim.databinding.ActivityMainBinding

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
    }

    fun toast(s: String){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show()
    }

    fun reset(){
        binding.edtAge.text.clear()
        binding.spnLevel.setSelection(0)

        binding.txtOutput.text = ""

        binding.edtAge.requestFocus()
    }

    fun submit(){
        val age: Int = binding.edtAge.text.toString().toIntOrNull() ?: 0
        val level: String = binding.spnLevel.selectedItem.toString()

        if(age < 1){
            toast("Invaild Age")
            return
        }

        val duration: Int = when(level){
            "Foundation" -> 1
            "Diploma" -> 2
            "Bachelor" -> 3
            "Master" -> 2
            "PhD" -> 5
            else -> 0
        }

        val graduateAge = age + duration

        val withinAge = age >= 18
        val fulfillFDBGratuateAge = (level == "Foundation" || level == "Diploma" || level == "Bachelor") && graduateAge<=30
        val fulfillMPGratuateAge = (level == "Master" || level == "PhD") && graduateAge<=45
        val eligible: Boolean = withinAge && (fulfillFDBGratuateAge || fulfillMPGratuateAge)

        binding.txtOutput.text = """
            Age = $age
            Level = $level
            Duration = ${duration} year(s)
            Graduate Age = $graduateAge
            Eligible = ${if(eligible) "Yes" else "No"}
        """.trimIndent()
    }

    fun email(){
        val uri = Uri.parse("mailto:student_load@tarc.edu.my")
        val i = Intent(Intent.ACTION_SENDTO,uri)
        startActivity(i)
    }
}