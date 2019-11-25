package pt.isel.pdm.li52d.g4.bgg.model

import androidx.lifecycle.LiveData
import pt.isel.pdm.li52d.g4.bgg.BggApp
import pt.isel.pdm.li52d.g4.bgg.dto.GameDto

class CustomListGameJoinRepository{
    fun getList(name : String) : LiveData<List<CustomListsGameJoin>>{
        val res = BggApp.db.customListGameJoinDao().getGamesForCustomList(name)
        return res
    }
    fun insertGametoCustomList(
        dto: GameDto,
        listName:String
    ){
        val game = CustomListsGameJoin(listName,dto.id!!,dto.name!!, dto.yearPublished, dto.minPlayer,dto.maxPlayer,dto.minAge,dto.description,dto.primaryPublisher)
        BggApp.db.customListGameJoinDao().insert(game)
    }

}