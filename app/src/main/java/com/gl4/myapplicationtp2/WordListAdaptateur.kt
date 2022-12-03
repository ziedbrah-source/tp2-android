package com.gl4.myapplicationtp2

import android.annotation.SuppressLint
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import java.util.*


class studentAdapter(var StudentAfficher : ArrayList<Student>)
    : RecyclerView.Adapter<studentAdapter.ViewHolder>() ,Filterable{

    var dataFilterList = ArrayList<Student>()
    init {
        dataFilterList = StudentAfficher
    }
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.student_item,parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = StudentAfficher.size

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = StudentAfficher[position].nom+" "+StudentAfficher[position].prenom
        if(StudentAfficher[position].etat=="A"){
            holder.CheckBoxA.setChecked(true);
        }
        else{
            holder.CheckBoxP.setChecked(true);
            holder.CheckBoxA.setChecked(false);
        }
        if (StudentAfficher[position].genre == "h")
            holder.image.setImageResource(R.drawable.h)
        else {
            holder.image.setImageResource(R.drawable.f)
        }

        if(StudentAfficher[position].etat=="A"){
            holder.CheckBoxA.setOnCheckedChangeListener { _, isChecked ->
                StudentAfficher[position].etat="P"
                holder.CheckBoxP.setChecked(true);
                holder.CheckBoxA.setChecked(false);
                Handler().post(Runnable { notifyDataSetChanged() })
            }

        }

        else{
            holder.CheckBoxA.setOnCheckedChangeListener { _, isChecked ->

                StudentAfficher[position].etat="A"
                holder.CheckBoxP.setChecked(false);
                holder.CheckBoxA.setChecked(true);
                Handler().post(Runnable { notifyDataSetChanged() })
            }
        }

        if(StudentAfficher[position].etat=="P"){
            holder.CheckBoxP.setOnCheckedChangeListener { _, isChecked ->
                StudentAfficher[position].etat="A"
                holder.CheckBoxP.setChecked(false);
                holder.CheckBoxA.setChecked(true);
                Handler().post(Runnable { notifyDataSetChanged() })
            }

        }

        else{
            holder.CheckBoxP.setOnCheckedChangeListener { _, isChecked ->

                StudentAfficher[position].etat="P"

                holder.CheckBoxP.setChecked(true);
                holder.CheckBoxA.setChecked(false);
                Handler().post(Runnable { notifyDataSetChanged() })
            }

        }

    }

    class ViewHolder(val elementDeListe : View) : RecyclerView.ViewHolder(elementDeListe)
    {

        val textView: TextView
        init {
            textView=elementDeListe.findViewById(R.id.textView)}
        var image:ImageView

        init {
            image=elementDeListe.findViewById(R.id.imageView)
        }
        var CheckBoxA:CheckBox

        init {
            CheckBoxA=elementDeListe.findViewById(R.id.checkBoxA)
        }
        var CheckBoxP:CheckBox

        init {
            CheckBoxP=elementDeListe.findViewById(R.id.checkBoxP)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    dataFilterList = StudentAfficher
                } else {
                    val resultList = ArrayList<Student>()
                    for (student in StudentAfficher) {
                        if (student.nom.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))
                        ) {
                            resultList.add(student)
                        }
                    }
                    dataFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = dataFilterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                dataFilterList = results?.values as ArrayList<Student>
                notifyDataSetChanged()
            }
        }
    }


}