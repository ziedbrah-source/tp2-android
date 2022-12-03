package com.gl4.myapplicationtp2

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gl4.myapplicationtp2.databinding.ActivityMainBinding
import java.util.*


class MainActivity : AppCompatActivity()  {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    var matieres = listOf<String>("Cours","TP")
    var etats = listOf<String>("All","Absent","Present")
    lateinit var mEdit : EditText;
    lateinit var button1 :Button
    var students = ArrayList<Student>()
    lateinit var position:String
    fun CreerMesLigneCours() :ArrayList<Student>
    {
        val students = ArrayList<Student>()
        students.add( Student("Brahmi","Mohamed Zied","h","P"))
        students.add( Student("Said","Kais","h","P"))
        students.add( Student("Biden","Joe","h","A"))
        students.add( Student("Jefferson","Thomas","h","P"))
        students.add( Student("Clinton","Bill","h","A"))
        students.add( Student("Bush","George","h","P"))
        return students
    }


    lateinit var monRecycler: RecyclerView
    private lateinit var Adapter: studentAdapter

    val spinner : Spinner by lazy { findViewById(R.id.spinner) }
    val spinner2 : Spinner by lazy { findViewById(R.id.spinner2) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        spinner.adapter = ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,matieres)
        spinner2.adapter = ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,etats)
        students=CreerMesLigneCours();
        monRecycler = findViewById(R.id.recyclerView)
        monRecycler.layoutManager = LinearLayoutManager(this)
        mEdit = findViewById<View>(R.id.Search) as EditText

        Adapter=studentAdapter(students)
        var studientscours = CreerMesLigneCours();
        var studientstp= CreerMesLigneCours();
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                this@MainActivity.position =matieres[position]
                if(matieres[position] =="Cours"){
                    students=studientscours;

                }
                else{
                    students=studientstp;
                }
                monRecycler.adapter =studentAdapter(students)
            }
            override fun onNothingSelected(adapterView: AdapterView<*>?) {
            }
        }

        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {

                if(this@MainActivity.position=="Cours"){
                    students=studientscours
                }else students=studientstp;


               if(etats[position]=="Absent")
                    students=getFilter2("A");

                if(etats[position]=="Present")
                   students=getFilter2("P");
                else
                    students=getFilter2("");
                monRecycler.adapter =studentAdapter(students)
            }
            override fun onNothingSelected(adapterView: AdapterView<*>?) {
            }
        }

        mEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                monRecycler.adapter =studentAdapter(getFilter1(mEdit.text.toString()))
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }


    fun getFilter1(toString: String): ArrayList<Student> {
        var dataFilterList = students
        if (toString.isEmpty()) {
           var dataFilterList = students
        } else {
            val resultList = ArrayList<Student>()
            println(students)
            for (student in students) {
                if (student.nom.lowercase(Locale.ROOT)
                        .contains(toString.lowercase(Locale.ROOT))
                ) {
                    resultList.add(student)
                }
            }
            dataFilterList = resultList
            println(dataFilterList)
        }

        return dataFilterList;


    }

    fun getFilter2(toString: String): ArrayList<Student> {
        var dataFilterList = students
        if (toString.isEmpty()) {
            var dataFilterList = students
        } else {
            val resultList = ArrayList<Student>()
            println(students)
            for (student in students) {
                if (student.etat.lowercase(Locale.ROOT)
                        .contains(toString.lowercase(Locale.ROOT))
                ) {
                    resultList.add(student)
                }
            }
            dataFilterList = resultList
            println(dataFilterList)
        }

        return dataFilterList;


    }

}