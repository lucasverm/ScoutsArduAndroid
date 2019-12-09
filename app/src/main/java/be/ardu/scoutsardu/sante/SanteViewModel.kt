package be.ardu.scoutsardu.sante

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.ardu.scoutsardu.models.Winkelwagen

class SanteViewModel : ViewModel() {
    val winkelwagen = MutableLiveData<Winkelwagen>()



}
