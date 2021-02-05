package com.example.uproject.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.uproject.data.local.db.entity.CategoryEntity
import com.example.uproject.data.local.db.entity.OrderByProductEntity
import com.example.uproject.data.local.db.entity.OrderEntity
import com.example.uproject.data.local.db.entity.ProductEntity

@Database(
    entities = [CategoryEntity::class, ProductEntity::class, OrderByProductEntity::class, OrderEntity::class],
    version = 1
)
abstract class DulcekatDataBase: RoomDatabase() {

    abstract fun dulcekatDao(): DulcekatDao

    companion object{
        private var INSTANCE: DulcekatDataBase? = null
        private const val DBNAME = "Dulcekat.db"

        fun getInstance(ctx: Context): DulcekatDataBase {
            val tempInstance = INSTANCE
            if(tempInstance != null) return tempInstance

            synchronized(this){
                val instance = Room.databaseBuilder(
                    ctx.applicationContext,
                    DulcekatDataBase::class.java,
                    DBNAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }

}