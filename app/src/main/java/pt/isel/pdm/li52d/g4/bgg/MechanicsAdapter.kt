package pt.isel.pdm.li52d.g4.bgg

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.github.angads25.toggle.LabeledSwitch
import pt.isel.pdm.li52d.g4.bgg.model.Mechanics

class MechanicsAdapter(val model: MechanicsViewModel, val intent: Intent) :
    RecyclerView.Adapter<MechanicsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MechanicsViewHolder {
        val mechanicsView = LayoutInflater.from(parent.context)
            .inflate(R.layout.detailed_option, parent, false) as ConstraintLayout
        return MechanicsViewHolder(mechanicsView, intent)

    }

    override fun getItemCount(): Int = model.mechanics.size

    override fun onBindViewHolder(holder: MechanicsViewHolder, position: Int) {
        holder.bindTo(model.mechanics[position])
    }
}

class MechanicsViewHolder(view: ConstraintLayout, val intent: Intent) : RecyclerView.ViewHolder(view) {
    private lateinit var mechanics: Mechanics
    private val mechanicsName: TextView = view.findViewById(R.id.option_name)
//    private val switchOption: Switch = view.findViewById(R.id.switch1)
    private val switchOption: LabeledSwitch = view.findViewById(R.id.switch_option)

    fun bindTo(mechanics: Mechanics) {
        this.mechanics = mechanics
        mechanicsName.text = mechanics.nameMechanics


        switchOption.setOnToggledListener { switchOption, isOn ->
            if(switchOption.isOn){
                mechanics.checked = "true"
                intent.putExtra(MECHANICSURL, intent.getStringExtra(MECHANICSURL)!! + mechanics.id + "," )
                intent.putExtra(MECHANICSNAMES, intent.getStringExtra(MECHANICSNAMES)!! + mechanics.nameMechanics+",")
            }
            else{
                mechanics.checked = "false"
                intent.putExtra(MECHANICSURL, intent.getStringExtra(MECHANICSURL)!!.replace(mechanics.id + ",", "" ))
                intent.putExtra(MECHANICSNAMES, intent.getStringExtra(MECHANICSNAMES)!!.replace(mechanics.nameMechanics + ",", "" ))
            }
        }

//        switchOption.setOnClickListener{
//            if(switchOption.isChecked)
//                mechanics.checked = "true"
//            else mechanics.checked = "false"
//        }
    }
}