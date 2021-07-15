package vn.iambulance.productapp

import android.app.Application
import androidx.lifecycle.*
import vn.iambulance.productapp.room.RoomDAO
import vn.iambulance.productapp.room.RoomDB
import vn.iambulance.productapp.room.RoomEntity

class MyViewModel(app: Application) : AndroidViewModel(app) {

    private val vmEmail = MutableLiveData<String>()
    private val vmPassword = MutableLiveData<String>()
    private lateinit var entity: RoomEntity
    private lateinit var dao: RoomDAO

    fun singIn(email: String?, password: String?) {
        entity = RoomEntity()
        entity.email = email
        entity.password = password
        dao = RoomDB.getData((getApplication()))?.roomDao()!!
        dao.signIn(entity.email, entity.password)
    }

    fun singUp(entity: RoomEntity) {
        vmEmail.value = entity.email
        vmPassword.value = entity.password
        dao = RoomDB.getData((getApplication()))?.roomDao()!!
        dao.signUp(entity)
    }
}