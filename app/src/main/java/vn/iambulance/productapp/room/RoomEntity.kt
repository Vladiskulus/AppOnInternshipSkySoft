package vn.iambulance.productapp.room

import androidx.room.*
import vn.iambulance.productapp.*

@Entity(tableName = dbName)
class RoomEntity {

    @PrimaryKey(autoGenerate = true)
    var id:Int? = null

    @ColumnInfo(name = eAccount)
    var account:String? = null

    @ColumnInfo(name = eMail)
    var email:String? = null

    @ColumnInfo(name = ePassword)
    var password:String? = null
}