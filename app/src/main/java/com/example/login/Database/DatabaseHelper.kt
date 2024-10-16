
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.login.Model.Model_izin
import com.example.login.Model.Model_presensi
import com.example.login.Model.Model_registrasi

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 3
        private const val DATABASE_NAME = "presensidb"

        private const val TABLE_CONTACTS = "user"

        private const val KEY_ID = "id"
        private const val KEY_NOHP = "nohp"
        private const val KEY_PASSWORD = "password"
        private const val KEY_NAME = "name"
        private const val KEY_EMAIL = "email"

        private const val TABLE_PRESENSI = "presensi"
        private const val KEY_PRESENSI_ID = "id"
        private const val KEY_PRESENSI_USER_name = "user_name"
        private const val KEY_PRESENSI_TIME = "presensi_time"
        private const val KEY_PRESENSI_LOCATION = "presensi_lokasi"
        private const val KEY_PRESENSI_NOTE = "presensi_keterangan"

        private const val TABLE_IZIN = "izin"
        private const val KEY_IZIN_ID = "id"
        private const val KEY_IZIN_NAMA = "izin_nama"
        private const val KEY_IZIN_WAKTU = "izin_waktu"
        private const val KEY_IZIN_KETERANGAN = "izin_keterangan"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE $TABLE_CONTACTS ("
                + "$KEY_ID INTEGER PRIMARY KEY,"
                + "$KEY_NAME TEXT,"
                + "$KEY_EMAIL TEXT,"
                + "$KEY_NOHP TEXT,"
                + "$KEY_PASSWORD TEXT)")
        db.execSQL(CREATE_CONTACTS_TABLE)

        val createPresensiTable = ("CREATE TABLE $TABLE_PRESENSI ("
                + "$KEY_PRESENSI_ID INTEGER PRIMARY KEY,"
                + "$KEY_PRESENSI_USER_name TEXT,"
                + "$KEY_PRESENSI_TIME TEXT,"
                + "$KEY_PRESENSI_LOCATION TEXT,"
                + "$KEY_PRESENSI_NOTE TEXT,"
                + "FOREIGN KEY($KEY_PRESENSI_USER_name) REFERENCES $TABLE_CONTACTS($KEY_ID))")
        db.execSQL(createPresensiTable)

        val createIzinTable = ("CREATE TABLE $TABLE_IZIN ("
                + "$KEY_IZIN_ID INTEGER PRIMARY KEY,"
                + "$KEY_IZIN_NAMA TEXT,"
                + "$KEY_IZIN_WAKTU TEXT,"
                + "$KEY_IZIN_KETERANGAN TEXT,"
                + "FOREIGN KEY($KEY_IZIN_ID) REFERENCES $TABLE_CONTACTS($KEY_ID))")
        db.execSQL(createIzinTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PRESENSI")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_IZIN")
        onCreate(db)
    }

    fun GetUserName(): Set<String> {

        val userNames = LinkedHashSet<String>()


        val selectQuery = "SELECT $KEY_NAME FROM $TABLE_CONTACTS LIMIT 1"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        cursor.use {
            if (it.moveToFirst()) {
                do {
                    val columnIndex = it.getColumnIndex(KEY_NAME)
                    if (columnIndex != -1) {
                        val userName = it.getString(columnIndex)
                        if (!userName.isNullOrEmpty()) {
                            userNames.add(userName)
                        }
                    } else {
                        Log.d("GetUserName", "Column not found")
                    }
                } while (it.moveToNext())
            }
        }

        cursor.close()


        return userNames
    }

    fun getLoggedInUserName(email: String): String? {
        val db = this.readableDatabase
        var userName: String? = null
        val selectQuery = "SELECT $KEY_NAME FROM $TABLE_CONTACTS WHERE $KEY_EMAIL = ?"

        db.rawQuery(selectQuery, arrayOf(email)).use { cursor ->
            cursor?.apply {
                if (moveToFirst()) {
                    val columnIndex = getColumnIndex(KEY_NAME)
                    if (columnIndex != -1) {
                        userName = getString(columnIndex)
                    } else {
                        Log.e("DatabaseHelper", "Column not found: $KEY_NAME")
                    }
                }
            }
        }

        db.close()

        return userName
    }

    fun addEmployee(emp: Model_registrasi): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues().apply {
            put(KEY_NAME, emp.name)
            put(KEY_EMAIL, emp.email)
            put(KEY_NOHP, emp.nohp)
            put(KEY_PASSWORD, emp.password)
        }

        val success = db.insert(TABLE_CONTACTS, null, contentValues)
        db.close()
        return success
    }

    fun userLogin(email: String, password: String): Boolean {
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_CONTACTS WHERE $KEY_EMAIL = ? AND $KEY_PASSWORD = ?"
        val cursor = db.rawQuery(selectQuery, arrayOf(email, password))
        val count = cursor.count
        cursor.close()
        return count > 0
    }




    fun insertPresensiData(nama: String, waktu: String, keterangan: String, lokasi: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(KEY_PRESENSI_USER_name, nama)
            put(KEY_PRESENSI_TIME, waktu)
            put(KEY_PRESENSI_NOTE, keterangan)
            put(KEY_PRESENSI_LOCATION, lokasi)

        }

        val success = db.insert(TABLE_PRESENSI, null, values)
        db.close()
        return success
    }


    fun updateIzin(izin: Model_izin): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(KEY_IZIN_NAMA, izin.nama)
            put(KEY_IZIN_KETERANGAN, izin.keterangan)
        }

        val success = db.update(TABLE_IZIN, contentValues, "$KEY_IZIN_ID=?", arrayOf(izin.id.toString()))
        db.close()
        return success > 0
    }

    fun showPresensi(): ArrayList<Model_presensi> {
        val listPresensi = ArrayList<Model_presensi>()
        val db = this.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery("SELECT * FROM $TABLE_PRESENSI", null)
            cursor?.use {
                val columnIndexUserID = it.getColumnIndex(KEY_PRESENSI_ID)
                val columnIndexUserName = it.getColumnIndex(KEY_PRESENSI_USER_name)
                val columnIndexTime = it.getColumnIndex(KEY_PRESENSI_TIME)
                val columnIndexLocation = it.getColumnIndex(KEY_PRESENSI_LOCATION)
                val columnIndexNote = it.getColumnIndex(KEY_PRESENSI_NOTE)

                while (it.moveToNext()) {
                    val id = it.getString(columnIndexUserID)
                    val name = it.getString(columnIndexUserName)
                    val time = it.getString(columnIndexTime)
                    val location = it.getString(columnIndexLocation)
                    val note = it.getString(columnIndexNote)

                    val presensi = Model_presensi(id = id?.toIntOrNull() ?: 0, nama = name ?: "", waktuAbsen = time ?: "", lokasi = location ?: "", keterangan = note ?: "")
                    listPresensi.add(presensi)

                }
            }
        } catch (se: SQLiteException) {

            se.printStackTrace()
        } finally {
            cursor?.close()
        }

        return listPresensi
    }

    fun deleteIzin(izin: Model_izin): Boolean {
        val db = this.writableDatabase
        val args = arrayOf(izin.id.toString())
        val result = db.delete(TABLE_IZIN, "$KEY_IZIN_ID=?", args)
        db.close()
        return result != -1
    }

    fun changePassword(email: String, oldPassword: String, newPassword: String): Boolean {
        val db = this.writableDatabase
        var isPasswordChanged = false

        val selectQuery = "SELECT $KEY_PASSWORD FROM $TABLE_CONTACTS WHERE $KEY_EMAIL = ?"
        val cursor = db.rawQuery(selectQuery, arrayOf(email))

        cursor.use { cursor ->
            if (cursor != null && cursor.moveToFirst()) {
                val storedPasswordIndex = cursor.getColumnIndex(KEY_PASSWORD)

                if (storedPasswordIndex != -1) {
                    val storedPassword = cursor.getString(storedPasswordIndex)


                    if (storedPassword == oldPassword) {
                        val contentValues = ContentValues().apply {
                            put(KEY_PASSWORD, newPassword)
                        }

                        val updated = db.update(
                            TABLE_CONTACTS,
                            contentValues,
                            "$KEY_EMAIL = ?",
                            arrayOf(email)
                        )

                        isPasswordChanged = updated > 0
                    }
                } else {
                    
                    Log.e("changePassword", "Column $KEY_PASSWORD not found")
                }
            }
        }

        return isPasswordChanged
    }


    fun insertIzin(nama: String, waktu: String, keterangan: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(KEY_IZIN_NAMA, nama)
            put(KEY_IZIN_WAKTU, waktu)
            put(KEY_IZIN_KETERANGAN, keterangan)
        }

        val success = db.insert(TABLE_IZIN, null, values)

        return success
    }

    fun showIzin(): ArrayList<Model_izin> {
        val listIzin = ArrayList<Model_izin>()
        val db = this.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery("SELECT * FROM $TABLE_IZIN", null)
            cursor?.use {
                val columnIndexIzinID = it.getColumnIndex(KEY_IZIN_ID)
                val columnIndexIzinNama = it.getColumnIndex(KEY_IZIN_NAMA)
                val columnIndexIzinWaktu = it.getColumnIndex(KEY_IZIN_WAKTU)
                val columnIndexIzinKeterangan = it.getColumnIndex(KEY_IZIN_KETERANGAN)

                while (it.moveToNext()) {
                    val izinID = it.getString(columnIndexIzinID)
                    val izinNama = it.getString(columnIndexIzinNama)
                    val izinWaktu = it.getString(columnIndexIzinWaktu)
                    val izinKeterangan = it.getString(columnIndexIzinKeterangan)

                    val izin = Model_izin(id = izinID?.toIntOrNull() ?: 0, nama = izinNama ?: "", waktuAbsen = izinWaktu ?: "", keterangan = izinKeterangan ?: "")
                    listIzin.add(izin)
                }
            }
        } catch (se: SQLiteException) {
            se.printStackTrace()
        } finally {
            cursor?.close()
        }

        return listIzin
    }





}



