package vn.iambulance.productapp.room

import androidx.room.*

@Dao
interface RoomDAO {

    @Insert
    fun signUp(roomEntity: RoomEntity)

    @Query("SELECT * FROM users WHERE email=(:email) AND password=(:password)")
    fun signIn(email: String?, password: String?): RoomEntity?

}