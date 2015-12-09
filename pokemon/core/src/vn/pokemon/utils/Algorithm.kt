package vn.pokemon.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
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
        var results = ArrayList<MyLine>()
        var ok = false

        // Do xem 2 diem nay co row nao chung k?
        var min = Math.min(p1.col, p2.col)
        var max = Math.max(p1.col, p2.col)
        if (min < max) {
            if (max - min == 1) {
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
                        results.add(MyLine(Point(row, min), Point(row, max)))
                    }
                }
            }
        }
        // Do xem 2 diem nay co col nao chung k?
        min = Math.min(p1.row, p2.row)
        max = Math.max(p1.row, p2.row)
        if (min < max) {
            if (max - min == 1) {
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
                        results.add(MyLine(Point(min, col), Point(max, col)))
                    }
                }
            }
        }
        return results
    }

    fun isConnectedToRow(p : Point, l : MyLine) : Boolean {
        var min = Math.min(p.row, l.p1.row)
        var max = Math.max(p.row, l.p1.row)
        min++
        max--
        for (row in min .. max) {
            if (!matrix.get(row).get(p.col).equals(barrier)) {
                // false - khong tinh hang nay.
                return false
            }
        }
        return true
    }

    fun isConnectedToCol(p : Point, l : MyLine) : Boolean {
        var min = Math.min(p.col, l.p1.col)
        var max = Math.max(p.col, l.p1.col)
        min++
        max--
        for (col in min .. max) {
            if (!matrix.get(p.row).get(col).equals(barrier)) {
                // false - khong tinh hang nay.
                return false
            }
        }
        return true
    }

    fun checkTwoPointOK(p1 : Point, p2 : Point) : MyLine? {
        var mlines = findGeneralPath(p1, p2)
        for (line in mlines) {
            if (line.isRow()) {
                var isP1 = isConnectedToRow(p1, line)
                var isP2 = isConnectedToRow(p2, line)
                if (isP1 == true && isP2 == true) {
                    return line
                }
            } else {
                var isP1 = isConnectedToCol(p1, line)
                var isP2 = isConnectedToCol(p2, line)
                if (isP1 == true && isP2 == true) {
                    return line
                }
            }
        }
        return null
    }
}