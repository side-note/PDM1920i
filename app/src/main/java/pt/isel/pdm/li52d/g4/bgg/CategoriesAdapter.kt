package pt.isel.pdm.li52d.g4.bgg

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.github.angads25.toggle.LabeledSwitch
import pt.isel.pdm.li52d.g4.bgg.model.Categories

class CategoriesAdapter(val model: CategoriesViewModel, val intent: Intent) :
    RecyclerView.Adapter<CategoriesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val categoriesView = LayoutInflater.from(parent.context)
            .inflate(R.layout.detailed_option, parent, false) as ConstraintLayout
        return CategoriesViewHolder(categoriesView, intent)

    }

    override fun getItemCount(): Int = model.categories.size

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.bindTo(model.categories[position])
    }
}

class CategoriesViewHolder(view: ConstraintLayout, val intent: Intent) : RecyclerView.ViewHolder(view) {
    private lateinit var categories: Categories
    private val categoriesName: TextView = view.findViewById(R.id.option_name)
    //    private val switchOption: Switch = view.findViewById(R.id.switch1)
    private val switchOption: LabeledSwitch = view.findViewById(R.id.switch_option)

    fun bindTo(categories: Categories) {
        this.categories = categories
        categoriesName.text = categories.nameCategories

        switchOption.setOnToggledListener { switchOption, isOn ->
            if(switchOption.isOn){
                categories.checked = "true"
                intent.putExtra(CATEGORIESURL, intent.getStringExtra(CATEGORIESURL)!! + categories.id + "," )
                intent.putExtra(CATEGORIESNAMES, intent.getStringExtra(CATEGORIESNAMES)!! + categories.nameCategories + "," )
            }
            else{
                categories.checked = "false"
                intent.putExtra(CATEGORIESURL, intent.getStringExtra(CATEGORIESURL)!!.replace(categories.id + ",", "" ))
                intent.putExtra(CATEGORIESNAMES, intent.getStringExtra(CATEGORIESNAMES)!!.replace(categories.nameCategories + ",", "" ))
            }
        }

//        switchOption.setOnClickListener{
//            if(switchOption.isChecked)
//                mechanics.checked = "true"
//            else mechanics.checked = "false"
//        }
    }
}