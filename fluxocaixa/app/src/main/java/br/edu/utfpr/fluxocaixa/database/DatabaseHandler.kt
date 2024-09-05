package br.edu.utfpr.fluxocaixa.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.edu.utfpr.fluxocaixa.entity.Lancamento

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS $TABLE_NAME (_id INTEGER PRIMARY KEY AUTOINCREMENT, tipo TEXT, detalhe TEXT, valor DOUBLE, data_lancamento text) ")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insert(lancamento: Lancamento) {
        val values = ContentValues()
        values.put("tipo", lancamento.tipo)
        values.put("detalhe", lancamento.detalhe)
        values.put("valor", lancamento.valor)
        values.put("data_lancamento", lancamento.data_lancamento)

        this.writableDatabase.insert(TABLE_NAME, null, values)
    }

    fun getAll(): List<Lancamento> {
        val list = mutableListOf<Lancamento>()
        val cursor = this.readableDatabase.query(TABLE_NAME, null, null, null, null, null, null)
        while (cursor.moveToNext()) {
            val lancamento = Lancamento(
                cursor.getInt(ID),
                cursor.getString(TIPO),
                cursor.getString(DETALHE),
                cursor.getDouble(VALOR),
                cursor.getString(DATA_LANCAMENTO)
            )
            list.add(lancamento)
        }
        cursor.close()
        return list
    }

    companion object {
        private const val DATABASE_NAME = "dbfile.sqlite"
        private const val DATABASE_VERSION = 3
        private const val TABLE_NAME = "lancamento"
        const val ID = 0
        const val TIPO = 1
        const val DETALHE = 2
        const val VALOR = 3
        const val DATA_LANCAMENTO = 4
    }
}