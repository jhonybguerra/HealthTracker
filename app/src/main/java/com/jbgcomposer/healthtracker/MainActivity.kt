package com.jbgcomposer.healthtracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.jbgcomposer.healthtracker.databinding.ActivityMainBinding
import com.jbgcomposer.healthtracker.model.Calc

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainItems: MutableList<MainItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupTvAutoComplete()

        mainItems = createMainItems()
        binding.rvMainActivity.adapter = setupAdapter()

    }

    private fun setupTvAutoComplete() {
        val itemsTvAutoComplete = resources.getStringArray(R.array.tmb_lifestyle)
        val adapterTvAutocomplete =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, itemsTvAutoComplete)
        binding.tvAutoComplete.apply {
            setAdapter(adapterTvAutocomplete)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_imc -> {
                Thread{
                    if(hasDataOnDatabase("imc"))
                        openImcResult() else
                            runOnUiThread {
                                showToastValidateMenu()
                            }
                }.start()

            }
            R.id.menu_tmb -> {
                Thread {
                    if(hasDataOnDatabase("tmb"))
                        openTmbResult() else
                            runOnUiThread {
                        showToastValidateMenu()
                    }
                }.start()

            }
        }
        return true
    }


    private fun setupAdapter(): MainAdapter {

        val adapter = MainAdapter(mainItems) { id ->
            when (id) {
                1 -> {
                    if (!validateImc()) {
                        Toast.makeText(this, R.string.fields_imc_message, Toast.LENGTH_SHORT).show()
                    } else {
                        calculateIMC()
                    }

                }
                2 -> {
                    if (!validateTmb()) {
                        Toast.makeText(this, R.string.fields_tmb_message, Toast.LENGTH_SHORT).show()
                    } else {
                        calculateTMB()
                    }

                }
                3 -> {
                    if (!validateWater()) {
                        Toast.makeText(this, R.string.fields_water_messages, Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        calculateWaterIntake()
                    }

                }
            }
        }
        return adapter
    }


    private fun calculateIMC() {
        val weight = binding.edtWeight.text.toString().toDouble()
        val height = binding.edtHeight.text.toString().toDouble().div(100)

        val imcResult = weight / (height * height)

        AlertDialog.Builder(this).setTitle(getString(R.string.imc_response, imcResult))
            .setMessage(imcResponse(imcResult)).setPositiveButton(android.R.string.ok) { _, _ -> }
            .setNegativeButton(R.string.save) { _, _ ->
                Thread {
                    val app = application as App
                    val dao = app.db.calcDao()

                    val updateId = intent.extras?.getInt("update")
                    if (updateId != null) {
                        dao.update(Calc(id = updateId, type = "imc", res = imcResult))
                    } else {
                        dao.insert(Calc(type = "imc", res = imcResult))
                    }
                    runOnUiThread {
                        openImcResult()
                    }
                }.start()
            }.create().show()

    }

    private fun openImcResult() {
        val intent = Intent(this@MainActivity, ResultActivity::class.java)
        intent.putExtra("type", "imc")
        startActivity(intent)
    }

    private fun calculateTMB() {
        val weight = binding.edtWeight.text.toString().toDouble()
        val height = binding.edtHeight.text.toString().toDouble()
        val age = binding.edtAge.text.toString().toDouble()

        val tmbResult = 66 + (13.8 * weight) + (5 * height) - (6.8 * age)
        val tmbResponse = tmbRequest(tmbResult)

        AlertDialog.Builder(this).setTitle(getString(R.string.tmb_response, tmbResponse))
            .setMessage(R.string.tmb_message).setPositiveButton(android.R.string.ok) { _, _ -> }
            .setNegativeButton(R.string.save) { _, _ ->
                Thread {
                    val app = application as App
                    val dao = app.db.calcDao()

                    val updateId = intent.extras?.getInt("update")
                    if (updateId != null) {
                        dao.update(Calc(id = updateId, type = "tmb", res = tmbResponse))
                    } else {
                        dao.insert(Calc(type = "tmb", res = tmbResponse))
                    }

                    runOnUiThread {
                        openTmbResult()

                    }
                }.start()


            }.create().show()

        val service = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        service.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

    }

    private fun openTmbResult() {
        val intent = Intent(this@MainActivity, ResultActivity::class.java)
        intent.putExtra("type", "tmb")
        startActivity(intent)
    }

    private fun calculateWaterIntake() {
        val weight = binding.edtWeight.text.toString().toDouble()

        val waterIntake = weight * 35
        Toast.makeText(
            this, "Sua quantidade de água recomendada é: $waterIntake ml por dia", Toast.LENGTH_LONG
        ).show()

    }

    private fun createMainItems(): MutableList<MainItem> {
        val items = mutableListOf<MainItem>()
        items.add(
            MainItem(
                id = 1, drawableRes = R.drawable.ic_imc, textStringId = R.string.label_imc
            )
        )
        items.add(
            MainItem(
                id = 2, drawableRes = R.drawable.ic_tmb, textStringId = R.string.label_tmb
            )
        )
        items.add(
            MainItem(
                id = 3, drawableRes = R.drawable.ic_water, textStringId = R.string.label_water
            )
        )
        return items
    }

    private fun validateImc(): Boolean {
        return (binding.edtWeight.text.toString().isNotEmpty() && binding.edtHeight.text.toString()
            .isNotEmpty() && !binding.edtWeight.text.toString()
            .startsWith("0") && !binding.edtHeight.text.toString().startsWith("0"))
    }

    private fun validateTmb(): Boolean {
        return (binding.edtWeight.text.toString().isNotEmpty() && binding.edtHeight.text.toString()
            .isNotEmpty() && binding.edtAge.text.toString()
            .isNotEmpty() && !binding.edtWeight.text.toString()
            .startsWith("0") && !binding.edtHeight.text.toString()
            .startsWith("0") && !binding.edtAge.text.toString().startsWith("0"))
    }

    private fun validateWater(): Boolean {
        return (binding.edtWeight.text.toString().isNotEmpty() && !binding.edtWeight.text.toString()
            .startsWith("0"))
    }

    @StringRes
    private fun imcResponse(imc: Double): Int {
        return when {
            imc < 15.0 -> R.string.imc_severely_low_weight
            imc < 16.0 -> R.string.imc_very_low_weight
            imc < 18.5 -> R.string.imc_low_weight
            imc < 25.0 -> R.string.normal
            imc < 30.0 -> R.string.imc_high_weight
            imc < 35.0 -> R.string.imc_so_high_weight
            imc < 40.0 -> R.string.imc_severely_high_weight
            else -> R.string.imc_extreme_weight
        }
    }

    private fun tmbRequest(tmb: Double): Double {
        val items = resources.getStringArray(R.array.tmb_lifestyle)
        return when (binding.tvAutoComplete.text.toString()) {
            items[0] -> tmb * 1.2
            items[1] -> tmb * 1.375
            items[2] -> tmb * 1.55
            items[3] -> tmb * 1.725
            items[4] -> tmb * 1.9
            else -> 0.0
        }
    }

    private fun hasDataOnDatabase(type: String): Boolean {
        val app = application as App
        val dao = app.db.calcDao()
        val response = dao.getRegisterByType(type)
        return response.isNotEmpty()
    }

    private fun showToastValidateMenu() {
        Toast.makeText(this, getString(R.string.validation_history), Toast.LENGTH_LONG).show()
    }

}