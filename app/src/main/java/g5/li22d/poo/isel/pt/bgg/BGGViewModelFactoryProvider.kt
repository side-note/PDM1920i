package g5.li22d.poo.isel.pt.bgg

class BGGViewModelFactoryProvider {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            AlbumsViewModel::class.java -> AlbumsViewModel(app.lastfm) as T
            ArtistsViewModel::class.java -> ArtistsViewModel(app.lastfm) as T
            else -> throw IllegalArgumentException("There is no ViewModel for class $modelClass")
        }
    }
}