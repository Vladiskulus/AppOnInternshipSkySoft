package vn.iambulance.productapp.room

import android.content.Context
import androidx.room.*
import vn.iambulance.productapp.dbName

@Database(entities = [RoomEntity::class], version = 1)

abstract class RoomDB : RoomDatabase() {

    companion object {
        private var db: RoomDB? = null
        @Synchronized
        fun getData(context: Context): RoomDB? {
            if (db == null) {
                db = Room.databaseBuilder(context.applicationContext, RoomDB::class.java, dbName)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration().build()
            }
            return db
        }
    }

    abstract fun roomDao(): RoomDAO?
}