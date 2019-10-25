package g5.li22d.poo.isel.pt.bgg

import androidx.lifecycle.ViewModel
import g5.li22d.poo.isel.pt.bgg.dto.GameDto

class GameViewModel(var games : Array<GameDto> = emptyArray()) : ViewModel()