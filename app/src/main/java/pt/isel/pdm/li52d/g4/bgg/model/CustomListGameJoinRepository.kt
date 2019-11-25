package pt.isel.pdm.li52d.g4.bgg.model

import pt.isel.pdm.li52d.g4.bgg.BggApp
import pt.isel.pdm.li52d.g4.bgg.dto.GameDto

class CustomListGameJoinRepository{
    private fun insertGametoCustomList(
        dto: GameDto,
        listName:String
    ){
        val game = CustomListsGameJoin(listName,dto.id!!,dto.name!!, dto.yearPublished, dto.minPlayer,dto.maxPlayer,dto.minAge,dto.description,dto.primaryPublisher)
        BggApp.db.customListGameJoinDao().insert(game)
    }

}