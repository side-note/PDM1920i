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

const val BGG_GET_GAMES = "https://www.boardgameatlas.com/api/gameSearch?name=%S&limit=%d&skip=%d&pretty=true&client_id=SB1VGnDv7M"
const val BGG_MPP = "https://www.boardgameatlas.com/api/gameSearch/?order_by=%S&limit=%d&skip=%d&ascending=false&client_id=SB1VGnDv7M"
const val BGG_PUBLISHER = "https://www.boardgameatlas.com/api/gameSearch/?publisher=%s&limit=%d&skip=%d&client_id=SB1VGnDv7M"
const val BGG_DESIGNERS ="https://www.boardgameatlas.com/api/gameSearch/?designer=%s&limit=%d&skip=%d&client_id=SB1VGnDv7M"
const val BGG_MECHANICS = "https://www.boardgameatlas.com/api/game/mechanics?pretty=true&client_id=SB1VGnDv7M"
const val BGG_CATEGORIES = "https://www.boardgameatlas.com/api/game/categories?pretty=true&client_id=SB1VGnDv7M"
const val BGG_OPTIONS = "https://www.boardgameatlas.com/api/gameSearch/?publisher=%s&mechanics=%s&categories=%s&pretty=true&client_id=SB1VGnDv7M"

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


}
