package pt.isel.pdm.li52d.g4.bgg.model

import pt.isel.pdm.li52d.g4.bgg.BggApp
import pt.isel.pdm.li52d.g4.bgg.dto.GameDto

class CustomListGameJoinRepository{
    private fun insertGametoCustomList(
        dto: GameDto,
        name:String
    ){
       BggApp.db.customListGameJoinDao().insert(dto)
    }
}