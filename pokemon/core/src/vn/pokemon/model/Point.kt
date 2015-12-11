package vn.pokemon.model

/**
 * Created by truongps on 12/7/15.
 */

class Point {
    var row: Int
    var col: Int

    constructor(row: Int, col: Int) {
        this.row = row
        this.col = col
    }

    override fun toString(): String {
        return "($row, $col)"
    }

    fun isEqualTo(p : Point) : Boolean {
        return row == p.row && col == p.col
    }

    fun isEqualTo(row: Int, col: Int) : Boolean {
        return row == this.row && col == this.col
    }
}