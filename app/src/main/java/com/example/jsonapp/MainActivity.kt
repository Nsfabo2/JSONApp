package com.example.jsonapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*
Create a Currency converter app using the following API: https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/eur.json
The API uses EURO as the base currency
Make sure to allow users to convert any amount of EURO to the target currency

Bonus:
Allow users to convert a target currency back to EURO (including custom amounts)
Improve the look of the application
 */

class MainActivity : AppCompatActivity() {

    private var CurencyDetails: BookDetails? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //UI intializing
        val UserInput = findViewById<View>(R.id.UserInput) as EditText
        val Convertbtn = findViewById<View>(R.id.Convertbtn) as Button
        val Spriner = findViewById<View>(R.id.spriner0) as Spinner
        val appLO = findViewById<View>(R.id.appLO) as LinearLayout
        val Curenciess = arrayListOf("kwd", "sar", "egp", "aed", "bhd", "qar")
        var SelectedCurency: Int = 0

        //spinner stuff
        if (Spriner != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, Curenciess)
            Spriner.adapter = adapter
            Spriner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    SelectedCurency = position
                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                    Snackbar.make(appLO, "Default currency select: inr!!", Snackbar.LENGTH_LONG).show()
                }
            }
        }
        Convertbtn.setOnClickListener {

            var Choice = UserInput.text.toString()
            var Currency: Double = Choice.toDouble()

            CurrencyConverter(onResult = {
                CurencyDetails = it

                when (SelectedCurency) {
                    0 -> DisplayResult(Calculate(CurencyDetails?.eur?.kwd?.toDouble(), Currency));
                    1 -> DisplayResult(Calculate(CurencyDetails?.eur?.sar?.toDouble(), Currency));
                    2 -> DisplayResult(Calculate(CurencyDetails?.eur?.egp?.toDouble(), Currency));
                    3 -> DisplayResult(Calculate(CurencyDetails?.eur?.aed?.toDouble(), Currency));
                    4 -> DisplayResult(Calculate(CurencyDetails?.eur?.bhd?.toDouble(), Currency));
                    5 -> DisplayResult(Calculate(CurencyDetails?.eur?.qar?.toDouble(), Currency));
                }
            })
        }

    }

    private fun DisplayResult(Calculate: Double) {

        val responseText = findViewById<View>(R.id.ResualtTV) as TextView
        responseText.text = "result " + Calculate
    }

    private fun Calculate(i: Double?, Choice: Double): Double {
        var Doubled = 0.0
        if (i != null) {
            Doubled = (i * Choice)
        }
        return Doubled
    }

    fun CurrencyConverter(onResult: (BookDetails?) -> Unit) {

        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

        if (apiInterface != null) {
            apiInterface.CurrencyConverter()?.enqueue(object : Callback<BookDetails> {
                override fun onResponse(
                    call: Call<BookDetails>,
                    response: Response<BookDetails>
                ) {
                    onResult(response.body())

                }

                override fun onFailure(call: Call<BookDetails>, t: Throwable) {
                    onResult(null)
                    call.cancel()
                }

            })
        }
    }


}//end class