package com.example.codetest

import java.io.BufferedReader

// Introduction: Find the longest increasing subsequence in the array in nlogn time
// Input: The array
// Output: Longest increasing subsequence in the array
// Learning place: https://www.youtube.com/watch?v=S9oUiVYEq7E&t=285s
fun lis(array: IntArray): IntArray {

    // lis: longest increasing subsequence

    // Get size of the array
    val n = array.size

    // Index of the surface of the specific length
    val surface = IntArray(n) { -1 }

    // Index of the parent of some value, follow the parent, can print increasing value
    val parent = IntArray(n) { -1 }

    // Length of the lis have found but minus one
    var len = 0

    // Init surface with first value
    surface[0] = 0

    for (i in 1 until n) {
        when {

            // Find value which can put later in the lis have found,
            // if change '>' to '>=', then return longest non decreasing sequence
            array[i] > array[surface[len]] -> {
                parent[i] = surface[len++]
                surface[len] = i
            }

            // Find smallest value in the array
            array[i] <= array[surface[0]] -> {
                surface[0] = i
            }

            // The i'th value is in between of surface
            else -> {

                // Find the ceil of i'th value use binary search and replace it with i
                var left = 1
                var right = len
                var candidateIndex = -1
                while (left <= right) {
                    val mid = left + ((right - left) shr 1)
                    when {
                        array[i] <= array[surface[mid]] -> {
                            candidateIndex = mid
                            right = mid - 1
                        }
                        else -> {
                            left = mid + 1
                        }
                    }
                }
                if (candidateIndex == -1) {
                    println("i is $i, value ${array[i]}, ${array[surface[len]]}")
                }
                surface[candidateIndex] = i
                parent[i] = surface[candidateIndex - 1]
            }
        }
    }

    // Get lis use parent array
    val lis = IntArray(len + 1) { -1 }
    var i = len
    var j = surface[len]
    while (i >= 0) {
        lis[i--] = array[j]
        j = parent[j]
    }
    return lis
}

fun main() {
    val r = System.`in`.bufferedReader()
    val sb = StringBuilder()
    val a = intArrayOf(1, 2, 3, 4, 2, 3, 4, 5, 6, 9)
    println(lis(a).joinToString(" "))
}


// Hakiobo's code for faster reading input
private const val SPACE_INT = ' '.toInt()
private const val ZERO_INT = '0'.toInt()
private const val NL_INT = '\n'.toInt()

private fun BufferedReader.readInt(): Int {
    var ret = read()
    while (ret <= SPACE_INT) {
        ret = read()
    }
    val neg = ret == '-'.toInt()
    if (neg) {
        ret = read()
    }
    ret -= ZERO_INT
    var read = read()
    while (read >= ZERO_INT) {
        ret *= 10
        ret += read - ZERO_INT
        read = read()
    }

    while (read <= SPACE_INT && read != -1 && read != NL_INT) {
        mark(1)
        read = read()
    }
    if (read > SPACE_INT) {
        reset()
    }
    return if (neg) -ret else ret
}