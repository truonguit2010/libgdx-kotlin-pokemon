package vn.pokemon.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import vn.pokemon.model.HintPath
import java.net.PortUnreachableException
import java.util.*
import vn.pokemon.model.Point
import vn.pokemon.model.MyLine

/**
 * Created by truongps on 12/6/15.
 */
class Algorithm {

    val TAG = "Algorithm"

    val barrier: String = "barrier"
    lateinit var matrix: ArrayList<ArrayList<String>>
    var gameSkinType : String = "a"
    var numberOfImage : Int = 35

    fun createMatrix(row: Int, col: Int) {
        matrix = ArrayList<ArrayList<String>>()
        for (iRow in 0..row) {
            var rowArray = ArrayList<String>()
            for (iCol in 0..col) {
                rowArray.add(barrier)
            }
            matrix.add(rowArray)
        }
        //        matrix = new int[row][col];
        //        for (int i = 0; i < col; i++) {
        //            matrix[0][i] = matrix[row - 1][i] = 0;
        //        }
        //        for (int i = 0; i < row; i++) {
        //            matrix[i][0] = matrix[i][col - 1] = 0;
        //        }

        var rand = Random()
        var imgNumber: Int = numberOfImage
        var maxDouble: Int = imgNumber / 4
        var imgArr: ArrayList<Int> = ArrayList()
        for (i in 0..imgNumber) {
            imgArr.add(0)
        }
        //        int imgArr[] = new int[imgNumber + 1];
        var listPoint = ArrayList<Point>()
        for (i in 1..(row - 1)) {
            // int i = 1; i < row - 1; i++
            for (j in 1..(col - 1)) {
                //  int j = 1; j < col - 1; j++
                listPoint.add(Point(i, j))

            }
        }
        var i = 0
        do {
            var imgIndex = rand.nextInt(imgNumber) + 1
            if (imgArr[imgIndex] < maxDouble) {
                imgArr.set(imgIndex, 2 + imgArr?.get(imgIndex))
                for (j in 0..1) {
//                    try {
                        var size = listPoint.size()
                        if (size > 0) {
                            var pointIndex = rand.nextInt(size)
                            var point = listPoint.get(pointIndex)
                            matrix.get(point.row).set(point.col, "" + imgIndex + gameSkinType)
                            //                        matrix[(int)point.x][listPoint
                            //                                .get(pointIndex).y] = imgIndex;
                            listPoint.remove(pointIndex);
                        }

//                    } catch (Exception e) {
//                    }
                }
                i++;
            }
        } while (i < row * col / 2)
    }

    fun resetPoint(point : Point) {
        Gdx.app.log(TAG, "resetPoint ("+ point.row + ", " + point.col + ")")
        matrix.get(point.row).set(point.col, barrier)
    }

    fun findGeneralPath(p1 : Point, p2 : Point) : ArrayList<MyLine> {
        Gdx.app.log(TAG, "findGeneralPath (${p1.row}, ${p1.col}) to (${p2.row}, ${p2.col})")
        var results = ArrayList<MyLine>()
        var ok = false

        // Do xem 2 diem nay co row nao chung k?
        var min = Math.min(p1.col, p2.col)
        var max = Math.max(p1.col, p2.col)
        if (min < max) {
            if (max - min == 1 && p1.row == p2.row) {
                results.add(MyLine(p1, p2))
            } else {
                for (row in 0..(matrix.size - 1)) {
                    ok = true
                    for (col in min..max) {
                        if (!p1.isEqualTo(row, col) && !p2.isEqualTo(row, col) && !matrix.get(row).get(col).equals(barrier)) {
                            // false - khong tinh hang nay.
                            ok = false
                            break
                        }
                    }
                    if (ok == true) {
                        Gdx.app.log(TAG, "Row ($row, $min) to ($row, $max)")
                        results.add(MyLine(Point(row, min), Point(row, max)))
                    }
                }
            }
        }
        // Do xem 2 diem nay co col nao chung k?
        min = Math.min(p1.row, p2.row)
        max = Math.max(p1.row, p2.row)
        if (min < max) {
            if (max - min == 1 && p1.col == p2.col) {
                results.add(MyLine(p1, p2))
            } else {
                for (col in 0..(matrix.get(0).size - 1)) {
                    ok = true
                    for (row in min..max) {
                        if (!p1.isEqualTo(row, col) && !p2.isEqualTo(row, col) && !matrix.get(row).get(col).equals(barrier)) {
                            // false - khong tinh hang nay.
                            ok = false
                            break
                        }
                    }
                    if (ok == true) {
                        Gdx.app.log(TAG, "Col ($min, $col) to ($max, $col)")
                        results.add(MyLine(Point(min, col), Point(max, col)))
                    }
                }
            }
        }
        return results
    }

    fun isConnectedToRow(p : Point, l : MyLine) : MyLine? {
        var min = Math.min(p.row, l.p1.row)
        var max = Math.max(p.row, l.p1.row)
        if (max - min > 1) {
            min++
            max--
            for (row in min..max) {
                if (!matrix.get(row).get(p.col).equals(barrier)) {
                    // false - khong tinh hang nay.
                    return null
                }
            }
        }
        var connectedPoint = if (p.col == l.p1.col) l.p1 else l.p2
        var line = MyLine(p, connectedPoint)
        return line
    }

    fun isConnectedToCol(p : Point, l : MyLine) : MyLine? {
        var min = Math.min(p.col, l.p1.col)
        var max = Math.max(p.col, l.p1.col)
        if (max - min > 1) {
            min++
            max--
            for (col in min..max) {
                if (!matrix.get(p.row).get(col).equals(barrier)) {
                    // false - khong tinh hang nay.
                    return null
                }
            }
        }
        var connectedPoint = if (p.row == l.p1.row) l.p1 else l.p2
        var line = MyLine(p, connectedPoint)
        return line
    }

    fun checkTwoPointOK(p1 : Point, p2 : Point) : HintPath? {
        if (matrix[p1.row][p1.col] != matrix[p2.row][p2.col]) {
            return null
        }
        if (matrix[p1.row][p1.col] == barrier) {
            return null
        }
        if (p1.isEqualTo(p2)) {
            return null
        }
        var mlines = findGeneralPath(p1, p2)
        for (line in mlines) {
            if (line.isRow()) {
                var isP1 = isConnectedToRow(p1, line)
                var isP2 = isConnectedToRow(p2, line)
                if (isP1 != null && isP2 != null) {
                    return HintPath(isP1, line, isP2)
                }
            } else {
                var isP1 = isConnectedToCol(p1, line)
                var isP2 = isConnectedToCol(p2, line)
                if (isP1 != null && isP2 != null) {
                    return HintPath(isP1, line, isP2)
                }
            }
        }
        return null
    }

    fun shuffle() {
        Gdx.app.log(TAG, "reorderMatrixByRow")
        var cache =  ArrayList<String>()
        for (row in 1 .. matrix.size - 2) {
            for (col in 1 .. matrix[0].size - 2) {
                cache.add(matrix[row][col])
            }
        }
        Collections.shuffle(cache)
        var i = 0;
        for (row in 1 .. matrix.size - 2) {
            for (col in 1 .. matrix[0].size - 2) {
                matrix[row][col] = cache[i]
                i++
            }
        }
        if (getHint() == null) {
            shuffle()
        }
    }

    fun isClear() : Boolean {
        for (row in 0 .. matrix.size - 1) {
            for (col in 0 .. matrix[0].size - 1) {
                if (matrix[row][col] != barrier) {
                    return false
                }
            }
        }
        return true
    }

    fun getHint() : HintPath? {
        Gdx.app.log(TAG, "get Hint")
        for (row1 in 0 .. matrix.size - 1) {
            for (col1 in 0 .. matrix[0].size - 1) {
                for (row2 in 0 .. matrix.size - 1) {
                    for (col2 in 0 .. matrix[0].size - 1) {
                        var p1 = Point(row1, col1)
                        var p2 = Point(row2, col2)
                        var line = checkTwoPointOK(p1, p2)
                        if (line != null) {
                            Gdx.app.log(TAG, "get Hint $p1 tp $p2 OK")
                            return line
                        }
                    }
                }
            }
        }
        Gdx.app.log(TAG, "getHint - Need reorder matrix")
        return null
    }
}