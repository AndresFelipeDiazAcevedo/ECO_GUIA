
package me.andresdiaz.eco_guia.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

data class ScanRecord(
    val id: Int,
    val material: String,
    val confidence: Float,
    val timestamp: Long
)

class ScanDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "ecoguia.db"
        private const val DATABASE_VERSION = 2

        private const val TABLE_SCANS = "scans"
        private const val COLUMN_ID = "id"
        private const val COLUMN_USER_ID = "user_id"
        private const val COLUMN_MATERIAL = "material"
        private const val COLUMN_CONFIDENCE = "confidence"
        private const val COLUMN_TIMESTAMP = "timestamp"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_SCANS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_USER_ID TEXT NOT NULL,
                $COLUMN_MATERIAL TEXT NOT NULL,
                $COLUMN_CONFIDENCE REAL NOT NULL,
                $COLUMN_TIMESTAMP INTEGER NOT NULL
            )
        """.trimIndent()

        db.execSQL(createTable)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        if (oldVersion < 2) {
            db.execSQL(
                "ALTER TABLE $TABLE_SCANS ADD COLUMN $COLUMN_USER_ID TEXT NOT NULL DEFAULT ''"
            )
        }
    }

    fun insertScan(
        userId: String,
        material: String,
        confidence: Float
    ): Long {
        val values = ContentValues().apply {
            put(COLUMN_USER_ID, userId)
            put(COLUMN_MATERIAL, material)
            put(COLUMN_CONFIDENCE, confidence)
            put(COLUMN_TIMESTAMP, System.currentTimeMillis())
        }

        return writableDatabase.insert(TABLE_SCANS, null, values)
    }

    fun getAllScans(userId: String): List<ScanRecord> {
        val scans = mutableListOf<ScanRecord>()

        val cursor = readableDatabase.query(
            TABLE_SCANS,
            null,
            "$COLUMN_USER_ID = ?",
            arrayOf(userId),
            null,
            null,
            "$COLUMN_TIMESTAMP DESC"
        )

        cursor.use {
            while (it.moveToNext()) {
                scans.add(
                    ScanRecord(
                        id = it.getInt(it.getColumnIndexOrThrow(COLUMN_ID)),
                        material = it.getString(
                            it.getColumnIndexOrThrow(COLUMN_MATERIAL)
                        ),
                        confidence = it.getFloat(
                            it.getColumnIndexOrThrow(COLUMN_CONFIDENCE)
                        ),
                        timestamp = it.getLong(
                            it.getColumnIndexOrThrow(COLUMN_TIMESTAMP)
                        )
                    )
                )
            }
        }

        return scans
    }

    fun deleteScan(id: Int, userId: String): Int {
        return writableDatabase.delete(
            TABLE_SCANS,
            "$COLUMN_ID = ? AND $COLUMN_USER_ID = ?",
            arrayOf(id.toString(), userId)
        )
    }

    fun deleteAllScans(userId: String): Int {
        return writableDatabase.delete(
            TABLE_SCANS,
            "$COLUMN_USER_ID = ?",
            arrayOf(userId)
        )
    }
}
