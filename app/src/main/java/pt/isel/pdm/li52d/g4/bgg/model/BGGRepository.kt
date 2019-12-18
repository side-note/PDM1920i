package pt.isel.pdm.li52d.g4.bgg.model

import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.work.Data
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.android.volley.VolleyError
import pt.isel.pdm.li52d.g4.bgg.*
import pt.isel.pdm.li52d.g4.bgg.dto.*
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

const val FAV_LIST_NAME: String = "Favorite List Name"
const val PUBLISHER_NAME: String = "Publisher Name"
const val DESIGNER_NAME: String = "Designer Name"
const val MECHANICS_URL: String = "Mechanics Url"
const val CATEGORIES_URL: String = "Categories Url"
const val MECHANICS_NAME: String = "Mechanics Name"
const val CATEGORIES_NAME: String = "Categories Name"

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

    private class insertDesignerInListTask : AsyncTask<String, Unit, Unit>() {
        override fun doInBackground(vararg params: String) {
            BggApp.db.customListAndGamesDao()
                .insertDesigner(Designer(params[0], params[1], params[2]))
        }
    }

    private class insertFavoritesTask : AsyncTask<String, Unit, Array<String>>() {
        override fun doInBackground(vararg params: String): Array<String> {
            BggApp.db.FavoritesDao().insertFavorites(Favorites(params[0], params[1], params[2],null))
            return arrayOf(
                params[0],
                params[1],
                params[2],
                params[3],
                params[4],
                params[5],
                params[6]
            )
        }

        override fun onPostExecute(result: Array<String>) {
            scheduleBackgroundWork(
                result[0],
                result[1],
                result[2],
                result[3],
                result[4],
                result[5],
                result[6]
            )
        }

        private fun scheduleBackgroundWork(vararg params: String) {
            val request = PeriodicWorkRequestBuilder<WorkerFavorites>(TIME, TimeUnit.MINUTES)
                .setInputData(
                    Data.Builder()
                        .putString(FAV_LIST_NAME, params[0])
                        .putString(PUBLISHER_NAME, params[1])
                        .putString(DESIGNER_NAME, params[2])
                        .putString(MECHANICS_URL, params[3])
                        .putString(CATEGORIES_URL, params[4])
                        .putString(MECHANICS_NAME, params[5])
                        .putString(CATEGORIES_NAME, params[6])
                        .build()
                )
                .setInitialDelay(1, TimeUnit.MINUTES)
                .build()
            updateWorkerId().execute("Fav ${params[0]}", request.id.toString())
            WorkManager.getInstance(BggApp.bgg.ctx).enqueue(request)
        }
    }
    private class updateWorkerId : AsyncTask<String, Unit, Unit>(){
        override fun doInBackground(vararg params: String) {
            BggApp.db.FavoritesDao().updateWorkerId(params[0], params[1])
        }
    }

    private class insertDesignerInFavoritesTask : AsyncTask<String, Unit, Unit>() {
        override fun doInBackground(vararg params: String) {
            BggApp.db.FavoritesDao().insertDesigner(Designer(params[0], params[1], params[2]))
        }
    }

    private class insertGamesinFavoritesTask : AsyncTask<Game, Unit, Unit>() {
        override fun doInBackground(vararg game: Game) {
            game.forEach { BggApp.db.FavoritesDao().insertGame(it) }
        }
    }

    private class insertMechanicsinFavoritesTask : AsyncTask<Mechanics, Unit, Unit>() {
        override fun doInBackground(vararg mechanics: Mechanics) {
            mechanics.forEach { BggApp.db.FavoritesDao().insertMechanics(it) }
        }
    }

    private class insertCategoriesinFavoritesTask : AsyncTask<Categories, Unit, Unit>() {
        override fun doInBackground(vararg categories: Categories) {
            categories.forEach { BggApp.db.FavoritesDao().insertCategories(it) }
        }
    }

    private class getListTask : AsyncTask<String, Unit, CustomList>() {
        override fun doInBackground(vararg name: String): CustomList {
            return BggApp.db.customListAndGamesDao().getCustomList(name[0])
        }
    }

    private class getAllListTask : AsyncTask<Unit, Unit, List<CustomList>>() {
        override fun doInBackground(vararg dummy: Unit): List<CustomList> {
            return BggApp.db.customListAndGamesDao().getAllCustomList()
        }
    }

    private class getMechanicsTask : AsyncTask<String, Unit, List<Mechanics>>() {
        override fun doInBackground(vararg namefav: String): List<Mechanics> {
            return BggApp.db.FavoritesDao().getMechanicsForFavorites(namefav[0])
        }
    }

    private class getCategoriesTask : AsyncTask<String, Unit, List<Categories>>() {
        override fun doInBackground(vararg namefav: String): List<Categories> {
            return BggApp.db.FavoritesDao().getCategoriesForFavorites(namefav[0])
        }
    }

    private class getAllFavListTask : AsyncTask<Unit, Unit, List<Favorites>>() {
        override fun doInBackground(vararg dummy: Unit): List<Favorites> {
            return BggApp.db.FavoritesDao().getAllFavs()
        }
    }

    private class getFavListTask : AsyncTask<String, Unit, Favorites>() {
        override fun doInBackground(vararg name: String): Favorites {
            return BggApp.db.FavoritesDao().getFavList(name[0])
        }
    }

    private class deleteGamesinListTask : AsyncTask<Game, Unit, Unit>() {
        override fun doInBackground(vararg games: Game) {
            games.forEach {
                BggApp.db.customListAndGamesDao().delete(it)
            }
        }
    }

    private class deleteListTask : AsyncTask<CustomList, Unit, Unit>() {
        override fun doInBackground(vararg lists: CustomList) {
            BggApp.db.customListAndGamesDao().deleteList(lists[0])
        }
    }

    private class deleteDesignersTask : AsyncTask<Designer, Unit, Unit>() {
        override fun doInBackground(vararg designers: Designer) {
            designers.forEach {
                BggApp.db.customListAndGamesDao().deleteDesigners(it)
            }
        }
    }

    private class deleteFavGamesinListTask : AsyncTask<String, Unit, Unit>() {
        override fun doInBackground(vararg favListName: String) {
            BggApp.db.FavoritesDao().deleteFavGame(favListName[0])
        }
    }

    private class deleteFavListTask : AsyncTask<String, Unit, Unit>() {
        override fun doInBackground(vararg favListName: String) {
            val id = BggApp.db.FavoritesDao().getFavList(favListName[0]).id
            BggApp.db.FavoritesDao().deleteFavList(favListName[0])
            if(id != null)
                WorkManager.getInstance(BggApp.bgg.ctx).cancelWorkById(UUID.fromString(id))
        }
    }

    private class deleteFavDesignersTask : AsyncTask<String, Unit, Unit>() {
        override fun doInBackground(vararg names: String) {
            BggApp.db.FavoritesDao().deleteFavDesigners(names[0], names[1])
        }
    }

    private class deleteMechanicsTask : AsyncTask<String, Unit, Unit>() {
        override fun doInBackground(vararg favListName: String) {

            BggApp.db.FavoritesDao().deleteMechanics(favListName[0])

        }

    }

    private class deleteCategoriesTask : AsyncTask<String, Unit, Unit>() {
        override fun doInBackground(vararg favListName: String) {

            BggApp.db.FavoritesDao().deleteCategories(favListName[0])

        }

    }


    fun getAllList(): List<CustomList> {
        val task = getAllListTask()
        task.execute()
        return task.get()
    }

    fun getAllFavs(): List<Favorites> {
        val task = getAllFavListTask()
        task.execute()
        return task.get()
    }

    fun getFavList(favListName: String): Favorites {
        val task = getFavListTask()
        task.execute(favListName)
        return task.get()

    }

    fun getGamesAndDesignersList(name: String): LiveData<Array<DesignersAndGames>> {
       return BggApp.db.customListAndGamesDao().getGamesForCustomList(name)
    }

    fun getGamesAndDesignersFavList(name: String): LiveData<Array<DesignersAndGames>> {
        return BggApp.db.FavoritesDao().getGamesForFavorites(name)
    }

    fun insertList(nameList: String) = insertListTask().execute(nameList)
    fun insertGameinList(game: Game) = insertGameinListTask().execute(game)
    fun insertDesignerInList(listName: String, gameName: String, designerName: String) =
        insertDesignerInListTask().execute(listName, gameName, designerName)

    fun insertFavorite(
        nameFavList: String,
        publisher: String,
        designer: String,
        mechanicsUrl: String,
        categoriesUrl: String,
        mechanicsName: String,
        categoriesName: String
    ) = insertFavoritesTask().execute(
        nameFavList,
        publisher,
        designer,
        mechanicsUrl,
        categoriesUrl,
        mechanicsName,
        categoriesName
    )

    fun insertGamesinFavorites(games: Game) = insertGamesinFavoritesTask().execute(games)
    fun insertMechanics(mechanics: Mechanics) = insertMechanicsinFavoritesTask().execute(mechanics)
    fun insertCategories(categories: Categories) =
        insertCategoriesinFavoritesTask().execute(categories)

    fun insertDesignerInFavorites(favListName: String, gameName: String, designerName: String) =
        insertDesignerInFavoritesTask().execute(favListName, gameName, designerName)


    fun deleteGamesinList(vararg games: Game) = deleteGamesinListTask().execute(*games)

    fun deleteList(list: CustomList) = deleteListTask().execute(list)

    fun deleteDesigner(vararg designer: Designer) = deleteDesignersTask().execute(*designer)

    fun deleteGamesinFav(favListName: String) = deleteFavGamesinListTask().execute(favListName)

    fun deleteFavList(favListName: String) = deleteFavListTask().execute(favListName)

    fun deleteFavDesigner(gameName: String, favListName: String) = deleteFavDesignersTask().execute(gameName, favListName)

    fun deleteMechanics(favListName: String) = deleteMechanicsTask().execute(favListName)

    fun deleteCategories(favListName: String) = deleteCategoriesTask().execute(favListName)


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

    fun favoriteSearch(
        publisher: String,
        designer: String,
        mechanics: String,
        categories: String,
        onSuccess: (GameSearchDto) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        Log.v(TAG, "**** FETCHING Game called by festure from BoardGameAtlas.com...")
        BggApp.bgg.favoriteSearch(
            publisher,
            designer,
            mechanics,
            categories,
            onSuccess,
            onError
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
            designers.add(Designer("", gameName, it))
        }
        return DesignersAndGames(game, designers)
    }

    fun fromMechanicsDto(dto: MechanicsDto): Mechanics {
        return Mechanics("", dto.id, dto.name, dto.checked)
    }

    fun fromCategoriesDto(dto: CategoriesDto): Categories {
        return Categories("", dto.id, dto.name, dto.checked)
    }

    fun getList(listName: String): CustomList {
        val task = getListTask()
        task.execute(listName)
        return task.get()
    }

    fun getMechanics(id: String): List<Mechanics> {
        val task = getMechanicsTask()
        task.execute(id)
        return task.get()
    }

    fun getCategories(id: String): List<Categories> {
        val task = getCategoriesTask()
        task.execute(id)
        return task.get()
    }

}




