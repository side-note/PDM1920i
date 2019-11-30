package pt.isel.pdm.li52d.g4.bgg.model

import android.os.AsyncTask
import android.util.Log
import com.android.volley.VolleyError
import pt.isel.pdm.li52d.g4.bgg.BggApp
import pt.isel.pdm.li52d.g4.bgg.TAG
import pt.isel.pdm.li52d.g4.bgg.dto.*

class BGGRepository {
    private class insertListTask : AsyncTask<String, Unit, Unit>() {
        override fun doInBackground(vararg nameList: String) {
            BggApp.db.customListAndGamesDao().insertList(CustomList(nameList[0]))
        }
    }

    private class insertGameinListTask : AsyncTask<Game, Unit, Unit>() {
        override fun doInBackground(vararg game: Game) {
            BggApp.db.customListAndGamesDao().insertGame(game[0])
        }
    }

    private class insertDesignerTask : AsyncTask<String, Unit, Unit>() {
        override fun doInBackground(vararg params: String) {
            BggApp.db.customListAndGamesDao().insertDesigner(Designer(params[0], params[1], params[2]))
        }
    }

    private class insertFavoritesTask : AsyncTask<String, Unit, Unit>() {
        override fun doInBackground(vararg params: String) {
            BggApp.db.FavoritesDao().insertFavorites(Favorites(params[0], params[1], params[2]))
        }
    }

    private class insertGamesinFavoritesTask : AsyncTask<Array<out Game>, Unit, Unit>() {
        override fun doInBackground(vararg game: Array<out Game>) {
            game.forEach { BggApp.db.FavoritesDao().insertGame(it) }
        }
    }

    private class insertMechanicsinFavoritesTask : AsyncTask<Array<out Mechanics>, Unit, Unit>() {
        override fun doInBackground(vararg mechanics: Array<out Mechanics>) {
            mechanics.forEach { BggApp.db.FavoritesDao().insertMechanics(it) }
        }
    }

    private class insertCategoriesinFavoritesTask : AsyncTask<Array<out Categories>, Unit, Unit>() {
        override fun doInBackground(vararg categories: Array<out Categories>) {
            categories.forEach { BggApp.db.FavoritesDao().insertCategories(it) }
        }
    }


    private class getGamesListTask : AsyncTask<String, Unit, List<DesignersAndGames>>() {
        override fun doInBackground(vararg name: String): List<DesignersAndGames> {
            return BggApp.db.customListAndGamesDao().getGamesForCustomList(name[0])
        }
    }
    private class getListTask : AsyncTask<String, Unit, CustomList>() {
        override fun doInBackground(vararg name: String): CustomList {
            return BggApp.db.customListAndGamesDao().getCustomList(name[0])
        }
    }
    private class getAllListTask : AsyncTask<Unit, Unit, List<CustomList>>() {
        override fun doInBackground(vararg dummy : Unit): List<CustomList> {
            return BggApp.db.customListAndGamesDao().getAllCustomList()
        }
    }
    private class deleteGamesinListTask : AsyncTask<Game, Unit, Unit>(){
        override fun doInBackground(vararg games: Game) {
            games.forEach {
                BggApp.db.customListAndGamesDao().delete(it)
            }
        }
    }
    private class deleteListTask : AsyncTask<CustomList, Unit, Unit>(){
        override fun doInBackground(vararg lists: CustomList) {
            BggApp.db.customListAndGamesDao().deleteList(lists[0])
        }
    }

    private class deleteDesignersTask : AsyncTask<Designer, Unit, Unit>(){
        override fun doInBackground(vararg designers: Designer) {
            designers.forEach {
                BggApp.db.customListAndGamesDao().deleteDesigners(it)
            }
        }
    }


    fun getAllList(): List<CustomList>{
        val task = getAllListTask()
        task.execute()
        return task.get()
    }

    fun getGamesAndDesignersList(name: String): List<DesignersAndGames> {
        val task = getGamesListTask()
        task.execute(name)
        return task.get()
    }

    fun insertList(nameList: String) = insertListTask().execute(nameList)
    fun insertGameinList(game: Game) = insertGameinListTask().execute(game)
    fun insertDesigner(listName: String, gameName: String, artistName: String) = insertDesignerTask().execute(listName, gameName, artistName)
    fun insertFavorite(nameFavList: String, publisher: String, designer: String) = insertFavoritesTask().execute(nameFavList,publisher,designer)
    fun insertGamesinFavorites(vararg games: Game) = insertGamesinFavoritesTask().execute(games)
    fun insertMechanics(vararg mechanics: Mechanics) = insertMechanicsinFavoritesTask().execute(mechanics)
    fun insertCategories(vararg categories: Categories) = insertCategoriesinFavoritesTask().execute(categories)

    fun deleteGamesinList(vararg games: Game) = deleteGamesinListTask().execute(*games)

    fun deleteList(list: CustomList) = deleteListTask().execute(list)

    fun deleteDesigner(vararg designer: Designer) = deleteDesignersTask().execute(*designer)

    fun gameSearch(
        name: String,
        limit: Int,
        skip: Int,
        onSuccess: (GameSearchDto) -> Unit,
        onError: (VolleyError) -> Unit,
        url: String
    ) {
        Log.v(TAG, "**** FETCHING Game called by $name from BoardGameAtlas.com...")
        BggApp.bgg.gameSearch(
            name,
            limit,
            skip,
            onSuccess,
            onError,
            url
        )
    }

    fun mechanicsSearch(
        name: String,
        onSuccess: (MechanicsSearchDto) -> Unit,
        onError: (VolleyError) -> Unit,
        url: String
    ) {
        Log.v(TAG, "**** FETCHING Game called by $name from BoardGameAtlas.com...")
        BggApp.bgg.mechanicsSearch(
            name,
            onSuccess,
            onError,
            url
        )
    }
    fun categoriesSearch(
        name: String,
        onSuccess: (CategoriesSearchDto) -> Unit,
        onError: (VolleyError) -> Unit,
        url: String
    ) {
        Log.v(TAG, "**** FETCHING Game called by $name from BoardGameAtlas.com...")
        BggApp.bgg.categoriesSearch(
            name,
            onSuccess,
            onError,
            url
        )
    }

    fun fromGameDto(dto: GameDto): DesignersAndGames {
        val gameName = dto.name!!
        val game = Game(
            dto.id!!,
            "",
            "",
            gameName,
            dto.description!!,
            dto.avgUserRating,
            dto.yearPublished,
            dto.minPlayer,
            dto.maxPlayer,
            dto.minAge,
            dto.primaryPublisher,
            dto.rulesUrl,
            dto.url,
            dto.images?.small
        )
        val designers: ArrayList<Designer> = arrayListOf()
        dto.designers?.forEach {
            designers.add(Designer("",gameName, it))
        }
        return DesignersAndGames(game, designers)
    }

    fun fromMechanicsDto(dto: MechanicsDto): Mechanics{
        return Mechanics("", dto.id, dto.name)
    }

    fun fromCategoriesDto(dto: CategoriesDto): Categories{
        return Categories("", dto.id, dto.name)
    }

    fun getList(listName : String): CustomList{
        val task = getListTask()
        task.execute(listName)
        return task.get()
    }

}




