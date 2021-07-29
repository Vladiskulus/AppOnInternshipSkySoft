package vn.iambulance.productapp.view_model

import android.app.Application
import android.location.Location
import android.util.Log
import androidx.lifecycle.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import vn.iambulance.productapp.StatusEnum
import vn.iambulance.productapp.room.*
import vn.iambulance.productapp.validEmail
import vn.iambulance.productapp.validPassword

class MyViewModel(private val app: Application) : AndroidViewModel(app) {

    private val vmStatus = MutableLiveData<String>()
    var status = vmStatus as LiveData<String>
    private lateinit var dao: RoomDAO

    fun singUp(account: String?, email: String?, passwordTop: String?, passwordBottom: String?) {
        val entity = RoomEntity()
        entity.email = email
        entity.password = passwordTop
        dao = RoomDB.getData(app).roomDao()
        if (account !== null) {
            if (passwordBottom == passwordTop) {
                if (validEmail(email) && validPassword(passwordTop)) {
                    if (dao.signIn(entity.email, entity.password) == null) {
                        dao.signUp(entity)
                        vmStatus.value = StatusEnum.SUCCESS.status
                    } else if (dao.checkEmail(email) !== null) {
                        vmStatus.value = StatusEnum.EMAIL_REGISTERED.status
                    }
                } else if (!validEmail(email)) {
                    vmStatus.value = StatusEnum.EMAIL_WRONG.status
                } else if (!validPassword(passwordTop)) {
                    vmStatus.value = StatusEnum.PASSWORD_WRONG.status
                }
            } else {
                vmStatus.value = StatusEnum.PASSWORD_DO_NOT_MATCH.status
            }
        } else {
            vmStatus.value = StatusEnum.ACCOUNT_ERROR.status
        }
    }

    fun singIn(email: String?, password: String?) {
        dao = RoomDB.getData(app).roomDao()
        if (validEmail(email) && validPassword(password)) {
            if (dao.signIn(email, password) !== null) {
                vmStatus.value = StatusEnum.SUCCESS.status
            } else if (dao.checkEmail(email) == null) {
                vmStatus.value = StatusEnum.EMAIL_NOT_REGISTERED.status
            } else if (dao.checkEmail(email) !== null && dao.signIn(email, password) == null) {
                vmStatus.value = StatusEnum.PASSWORD_WRONG.status
            }
        } else if (!validEmail(email)) {
            vmStatus.value = StatusEnum.EMAIL_WRONG.status
        } else if (!validPassword(password)) {
            vmStatus.value = StatusEnum.PASSWORD_WRONG.status
        }
    }
}