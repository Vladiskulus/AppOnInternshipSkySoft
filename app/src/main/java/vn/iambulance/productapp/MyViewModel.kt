package vn.iambulance.productapp

import android.app.Application
import androidx.lifecycle.*
import vn.iambulance.productapp.room.*

class MyViewModel(private val app: Application) : AndroidViewModel(app) {

    val vmStatus = MutableLiveData<String>()

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
                        vmStatus.value = app.getString(R.string.success)
                    } else if (dao.checkEmail(email) !== null) {
                        vmStatus.value = app.getString(R.string.error)
                    }
                } else if (!validEmail(email)) {
                    vmStatus.value = app.getString(R.string.incorrect_email)
                } else if (!validPassword(passwordTop)) {
                    vmStatus.value = app.getString(R.string.incorrect_password)
                }
            } else {
                vmStatus.value = app.getString(R.string.both_password_error)
            }
        } else {
            vmStatus.value = app.getString(R.string.account_error)
        }
    }

    fun singIn(email: String?, password: String?) {
        dao = RoomDB.getData(app).roomDao()
        if (validEmail(email) && validPassword(password)) {
            if (dao.signIn(email, password) !== null) {
                vmStatus.value = app.getString(R.string.success)
            } else if (dao.checkEmail(email) == null) {
                vmStatus.value = app.getString(R.string.unreg_email)
            } else if (dao.checkEmail(email) !== null && dao.signIn(email, password) == null) {
                vmStatus.value = app.getString(R.string.incorrect_password)
            }
        } else if (!validEmail(email)) {
            vmStatus.value = app.getString(R.string.incorrect_email)
        } else if (!validPassword(password)) {
            vmStatus.value = app.getString(R.string.incorrect_password)
        }
    }
}