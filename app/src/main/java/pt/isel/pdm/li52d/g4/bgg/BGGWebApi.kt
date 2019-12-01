package pt.isel.pdm.li52d.g4.bgg


import android.content.Context
import android.os.AsyncTask
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import pt.isel.pdm.li52d.g4.bgg.dto.CategoriesSearchDto
import pt.isel.pdm.li52d.g4.bgg.dto.GameSearchDto
import pt.isel.pdm.li52d.g4.bgg.dto.MechanicsSearchDto

const val BGG_GET_GAMES = "https://www.boardgameatlas.com/api/search?name=%S&limit=%d&skip=%d&pretty=true&client_id=SB1VGnDv7M"
const val BGG_MPP = "https://www.boardgameatlas.com/api/search/?order_by=%s&limit=%d&skip=%d&ascending=false&client_id=SB1VGnDv7M"
const val BGG_PUBLISHER = "https://www.boardgameatlas.com/api/search/?publisher=%s&limit=%d&skip=%d&client_id=SB1VGnDv7M"
const val BGG_DESIGNERS ="https://www.boardgameatlas.com/api/search/?designer=%s&limit=%d&skip=%d&client_id=SB1VGnDv7M"
const val BGG_MECHANICS = "https://www.boardgameatlas.com/api/game/mechanics?pretty=true&client_id=SB1VGnDv7M"
const val BGG_CATEGORIES = "https://www.boardgameatlas.com/api/game/categories?pretty=true&client_id=SB1VGnDv7M"
const val PUBLISHER_SEARCH = "publisher=%s&"
const val DESIGNER_SEARCH = "designer=%s&"
const val MECHANICS_SEARCH = "mechanics=%s&"
const val CATEGORIES_SEARCH = "categories=%s&"
const val END_URL = "pretty=true&client_id=SB1VGnDv7M"
var BGG_OPTIONS = "https://www.boardgameatlas.com/api/search/?"

class BGGWebApi(ctx: Context) {
    val queue = Volley.newRequestQueue(ctx)
    val gson = Gson()

    fun gameSearch (
        name: String?,
        limit: Int?,
        skip: Int?,
        onSuccess: (GameSearchDto) -> Unit,
        onError: (VolleyError) -> Unit,
        url: String)
    {
        val url = String.format(url, name, limit, skip)
        val smt : String
        class MyTask: AsyncTask<String, Int, GameSearchDto>() {
            override fun doInBackground(vararg resp: String): GameSearchDto {
                return gson.fromJson(resp[0], GameSearchDto::class.java)
            }
            override fun onPostExecute(result: GameSearchDto) {
                onSuccess(result)
            }
        }
        val task =  MyTask()

        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            Response.Listener<String> { response ->
                task.execute(response)
            },
            Response.ErrorListener { err -> onError(err)})
        queue.add(stringRequest)
    }

    fun mechanicsSearch (
        name: String?,
        onSuccess: (MechanicsSearchDto) -> Unit,
        onError: (VolleyError) -> Unit,
        url: String)
    {
        val url = String.format(url, name)

        class MyTask: AsyncTask<String, Int, MechanicsSearchDto>() {
            override fun doInBackground(vararg resp: String): MechanicsSearchDto {
                return gson.fromJson(resp[0], MechanicsSearchDto::class.java)
            }
            override fun onPostExecute(result: MechanicsSearchDto) {
                onSuccess(result)
            }
        }
        val task =  MyTask()

        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            Response.Listener<String> { response ->
                task.execute(response)
            },
            Response.ErrorListener { err -> onError(err)})
        queue.add(stringRequest)
    }

    fun categoriesSearch (
        name: String?,
        onSuccess: (CategoriesSearchDto) -> Unit,
        onError: (VolleyError) -> Unit,
        url: String)
    {
        val url = String.format(url, name)

        class MyTask: AsyncTask<String, Int, CategoriesSearchDto>() {
            override fun doInBackground(vararg resp: String): CategoriesSearchDto {
                return gson.fromJson(resp[0], CategoriesSearchDto::class.java)
            }
            override fun onPostExecute(result: CategoriesSearchDto) {
                onSuccess(result)
            }
        }
        val task =  MyTask()

        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            Response.Listener<String> { response ->
                task.execute(response)
            },
            Response.ErrorListener { err -> onError(err)})
        queue.add(stringRequest)
    }

    fun favoriteSearch (
        publisher: String,
        designer: String,
        mechanics: String,
        categories: String,
        onSuccess: (GameSearchDto) -> Unit,
        onError: (VolleyError) -> Unit)
    {
        var url = BGG_OPTIONS
        if(publisher != "")
            url += String.format(PUBLISHER_SEARCH, publisher)
        if(designer != "")
            url += String.format(DESIGNER_SEARCH, designer)
        if(mechanics != "")
            url += String.format(MECHANICS_SEARCH, mechanics)
        if(categories != "")
            url += String.format(CATEGORIES_SEARCH, categories)
        url += END_URL


        class MyTask: AsyncTask<String, Int, GameSearchDto>() {
            override fun doInBackground(vararg resp: String): GameSearchDto {
                return gson.fromJson(resp[0], GameSearchDto::class.java)
            }
            override fun onPostExecute(result: GameSearchDto) {
                onSuccess(result)
            }
        }
        val task =  MyTask()

        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            Response.Listener<String> { response ->
                task.execute(response)
            },
            Response.ErrorListener { err -> onError(err)})
        queue.add(stringRequest)
    }


}
