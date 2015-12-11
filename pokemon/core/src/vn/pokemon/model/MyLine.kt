package vn.pokemon.model

/**
 * Created by truongps on 12/7/15.
 */
class MyLine {
    var p1 : Point
    var p2 : Point

    constructor(p1 : Point, p2 : Point) {
        this.p1 = p1;
        this.p2 = p2;
    }

    fun isRow() : Boolean {
        return this.p1.row == this.p2.row
    }

    fun isCol() : Boolean {
        return this.p1.col == this.p2.col
    }

    fun isPoint() : Boolean {
        return isRow() && isCol()
    }

    override fun toString(): String {
        return p1.toString() + " to " + p2.toString()
    }

    //        public String toString() {
    //            String string = "(" + p1.x + "," + p1.y + ") and (" + p2.x + "," + p2.y
    //            + ")";
    //            return string;
    //        }

}