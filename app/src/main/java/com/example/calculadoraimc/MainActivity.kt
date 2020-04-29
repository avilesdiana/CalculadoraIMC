package com.example.calculadoraimc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    //DECLARACIONES
    lateinit var radioGroup: RadioGroup

    lateinit var editText: EditText
    lateinit var editText1: EditText
    lateinit var editText2: EditText

    lateinit var button: Button
    lateinit var button1: Button

    lateinit var textView2: TextView
    lateinit var textViewIMC: TextView
    lateinit var textViewIMCPrime: TextView
    lateinit var textViewCategoria: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         //Declaración donde se guarda el arreglo
        var array = ArrayList<String>()

        //Intancias de los elementos puestos.
        radioGroup = findViewById(R.id.radioGroup1)

        editText = findViewById<EditText>(R.id.peso1)
        editText1= findViewById<EditText>(R.id.esta3)
        editText2 = findViewById<EditText>(R.id.esta2)

        button = findViewById(R.id.buttonCalcular)
        button1 = findViewById(R.id.buttonHistorico)

        textView2 = findViewById(R.id.idHistorico)
        textViewIMC = findViewById(R.id.txtIMC)
        textViewIMCPrime = findViewById(R.id.textViewPrime22)
        textViewCategoria = findViewById(R.id.txtCategoria)

        //Esta función hace que guarde los datos en el arrayLista para
        //desplazarlos en el Text View
        fun  botonHISTORICO(imc : Double, imcPrime:Double, date:String, cate: String){
            var impre : String

            impre = date + "   IMC: "+ imc.toString()+ " Prime: "+ imcPrime.toString()+ "     "+"\n"+cate+"\n"
            array.add(impre)

            for(i in array) {
                impre = i + impre
            }
            textView2.text = impre

        }

        //Función que imprime la fecha actual.
        fun aDate():String{
            val sdf = SimpleDateFormat("dd/MM/yy hh:mm")
            return sdf.format(Date())
        }

        //Calcula los IMC para ambas categorias, dependiendo el valor que le mande
        // si envia un 1 calcula el imc Metrico, si es 2 calcula el imc Ingles
        //Regresa el valor de imc
        fun apretarBotonCalcularMI(peso: Double, estatura1: Double, estatura2:Double, valor:Int) : Double{

            if(valor == 1){
                val imc : Double = peso/ ((estatura1/100)*(estatura1/100))
                return imc
            }
            else{
                if(valor == 2){
                    val estaturaR : Double = (estatura2)+(estatura1*12)
                    val imc : Double = (peso/(estaturaR*estaturaR))*703
                    return imc
                }
                else
                {
                    val imc : Double = 0.0
                    return imc
                }
            }
        }

        //Calcula el imc PRIME, regresando ese valor en Double
        fun imcPrime(imc: Double ): Double{
            val prime : Double = imc / 25
            return prime
        }

        //Recibe el inmc y el imc prime y define una categoria
        // categoria                 | BMI PRIME       |   BMI RANGE
        //Muy severo bajo peso       | Menor que 0.60  | Menor que 15.0
        //Severamente bajo peso      |0.60-0.64        |15.0-16-0
        //Bajo peso                  |0.64-0.74        |16.0-18.5
        //Peso saludable             |0.74-1.0         |18.5-25
        //Exceso de peso             |1.0-1.2          |25.0-30.0
        //Moderadamente obeso        |1.2-1.4          |30.0-35.0
        //Severamente Obeso          |1.4-1.6          |35.0-40.0
        //Muy severamente obeso      |mayor 1.6        |mas de 40.0
        //https://captaincalculator.com/health/weight/bmi-prime-calculator/
        fun validarCategoria(prime: Double, imc: Double) : String{
            val categoria :String

            if(prime < 0.60 ){
                categoria = "MUY SEVERO BAJO DE PESO"
                return categoria
            }
            else{
                if(prime == 0.60 || prime < 0.64){
                    categoria = "SEVERO BAJO DE PESO"
                    return categoria
                }
                else{
                    if(prime == 0.64 || prime < 0.74){
                        categoria = "BAJO DE PESO"
                        return categoria
                    }
                    else{
                        if(prime == 0.74 || prime < 1.0){
                            categoria = "PESO SALUDABLE"
                            return categoria
                        }
                        else{
                            if(prime == 1.0 || prime < 1.2){
                                categoria = "EXCESO DE PESO"
                                return categoria
                            }
                            else{
                                if(prime == 1.2 || prime < 1.4){
                                    categoria = "MODERADAMENTE OBESO"
                                    return categoria
                                }
                                else{
                                    if(prime == 1.4 || prime < 1.6){
                                        categoria = "SEVERAMENTE OBESO"
                                        return categoria
                                    }
                                    else{
                                        if(prime >1.6){
                                            categoria = "MUY SEVERAMENTE OBESO"
                                            return categoria
                                        }
                                        else{
                                            categoria=" X X X X XX X X"
                                            return categoria
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }

        }

        //Ejecuta la funcion para sistemas metricos
        fun sistemaMetricoS() {
            //VALIDA SI ES UN DOUBLE O UN NULL
            var kg : Double?= editText.text.toString().toDoubleOrNull()
            var cm : Double?= editText1.text.toString().toDoubleOrNull()


            if (kg != null && cm != null) {
                //valor para la elección del IMC
                var valor: Int = 1
                //Como no usa una edit Text (Pulgadas) se le envio un valor double a la función
                var esta2: Double = 0.0

                var imc: Double = apretarBotonCalcularMI(kg, cm, esta2, valor)
                var imcPrimeD: Double = imcPrime(imc)

                //Los imprime los valor de imc e imc prime con 3 decimales
                var imcR: Double = String.format("%.3f", imc).toDouble()
                var imcPrimeR: Double = String.format("%.3f", imcPrimeD).toDouble()

                //imprime la categoria en la que se encuentra la persona.
                var cate: String = validarCategoria(imcPrimeR, imcR)

                //imprime los resultados en su respectivo textview
                textViewIMC.text = imcR.toString()
                textViewIMCPrime.text = imcPrimeR.toString()
                textViewCategoria.text = cate

                //mientras hace el cálculo desaparecem los cuadros de introducir el número
                peso1.visibility = View.INVISIBLE
                esta3.visibility = View.INVISIBLE

                //imprime los resultados en el historico
                botonHISTORICO(imcR, imcPrimeR, aDate(), cate)

            } else {
                //Enviar un error al usuario de que ingreso mal los datos.
                Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show()
                peso1.visibility = View.INVISIBLE
                esta3.visibility = View.INVISIBLE
            }
        }

        //Ejecuta la funcion para sistemas Ingles
        fun sistemaInglesS(){

            //VALIDA SI ES UN DOUBLE O UN NULL
            var lb : Double ?= editText.text.toString().toDoubleOrNull()
            var es1 : Double ?= editText1.text.toString().toDoubleOrNull()
            var es2 : Double ?= editText2.text.toString().toDoubleOrNull()

            if (lb != null && es1 !=null && es2!=null) {
                var valor : Int = 2

                var imc: Double = apretarBotonCalcularMI(lb,es1,es2,valor)
                var imcPrimeD: Double = imcPrime(imc)
                var imcR : Double = String.format("%.3f",imc).toDouble()
                var imcPrimeR : Double = String.format("%.3f",imcPrimeD).toDouble()

                //Valida la categoria
                var cate: String = validarCategoria(imcPrimeR, imcR)

                //imprime los valores correspondientes en el Text View
                textViewIMC.text = imcR.toString()
                textViewIMCPrime.text = imcPrimeR.toString()
                textViewCategoria.text = cate
                peso1.visibility = View.INVISIBLE
                esta2.visibility = View.INVISIBLE
                esta3.visibility = View.INVISIBLE

                //Guarda los valores en el ArrayList
                botonHISTORICO(imcR,imcPrimeR,aDate(),cate)
            }
            else{
                //Envia un error
                Toast.makeText(this,"ERROR",Toast.LENGTH_LONG).show()
                peso1.visibility = View.INVISIBLE
                esta2.visibility = View.INVISIBLE
                esta3.visibility = View.INVISIBLE
            }
        }

        //Aqui comienza para validar sobre los radiobutton lo que seleccione
        radioGroup1.setOnCheckedChangeListener { _, checkedId -> val radio:RadioButton = findViewById(checkedId)
            if(radio.id == radioButton.id) {
                peso1.visibility = View.VISIBLE
                esta3.visibility = View.VISIBLE
                idHistorico.visibility = View.INVISIBLE
                esta2.visibility = View.INVISIBLE
                peso1.hint = " Kg"
                esta3.hint = " cm"

                //Al selecionar el boton de calcular, realiza los calculos para Métricos
                button.setOnClickListener {
                    sistemaMetricoS()
                }
                //Al seleccionar historico hace visible el text View donde imprime
                //todos los datos del historico
                button1.setOnClickListener {
                    idHistorico.visibility = View.VISIBLE
                }

            } else{
                peso1.visibility = View.VISIBLE
                esta2.visibility = View.VISIBLE
                esta3.visibility = View.VISIBLE
                peso1.hint = " lb"
                esta3.hint = " ft"
                esta2.hint = "in"
                idHistorico.visibility = View.INVISIBLE

                button.setOnClickListener {
                    sistemaInglesS()
                }
                button1.setOnClickListener {
                    idHistorico.visibility = View.VISIBLE
                }

            }

        }
    }
}
