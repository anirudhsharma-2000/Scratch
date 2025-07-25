package com.smitcoderx.scratch.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import com.smitcoderx.scratch.data.category.Category
import com.smitcoderx.scratch.data.category.CategoryDao
import com.smitcoderx.scratch.data.note.Note
import com.smitcoderx.scratch.data.note.NoteDao


@Database(entities = [Category::class, Note::class], version = 2, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun noteDao(): NoteDao

    companion object {
        val MIGRATION_1_2 = Migration(1, 2) {
            it.execSQL(
                """
            CREATE TABLE IF NOT EXISTS `Note` (
                `id` INTEGER PRIMARY KEY NOT NULL,
                `title` TEXT NOT NULL,
                `content` TEXT NOT NULL,
                `type` TEXT NOT NULL,
                `createdAt` INTEGER NOT NULL,
                `categoryId` INTEGER
            )
        """.trimIndent()
            )
        }

        @Volatile
        private var Instance: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "database")
                    .addMigrations(MIGRATION_1_2)
                    .fallbackToDestructiveMigration(false)
                    .build().also { Instance = it }
            }
        }
    }
}
