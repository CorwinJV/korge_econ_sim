import com.soywiz.korge.Korge
import com.soywiz.korge.view.graphics
import com.soywiz.korim.color.Colors
import com.soywiz.korim.color.RGBA
import com.soywiz.korma.geom.Point
import com.soywiz.korma.geom.vector.polygon
import kotlin.math.sqrt

const val SCREEN_WIDTH = 1920
const val SCREEN_HEIGHT = 1080

const val hexSize = 10
const val hexWidth = 2 * hexSize
val hexHeight = sqrt(3.0) * hexSize
const val colWidth = hexWidth * (3.0/4.0 * 2.0)
val rowHeight = hexHeight * 2.0

suspend fun main() = Korge(width = SCREEN_WIDTH, height = SCREEN_HEIGHT, bgcolor = Colors["#044764"]) {
	val map = generateMap()

	graphics {
		fill(RGBA(255, 0, 0, 255)) {
			map.forEach { pos ->
				polygon(
					getHexPolygon()
						.translate(Point(hexWidth.toDouble(), hexHeight)) // Shift everything down/right a little bit so it's on screen
						.translate(Point(
							pos.x * colWidth,
							getOffsetForColumn(pos.x.toInt()) + (pos.y * rowHeight)
						)
					)
				)
			}
		}
	}
}

fun generateMap(): List<Point> {
	return listOf(
		Point(0,0),
		Point(0,1),
		Point(0,2),
		Point(1,0),
		Point(1,1),
		Point(1,2),
		Point(4,0),
		Point(4,2),
		Point(6,2),
		Point(5,5),
		Point(0,8)
	)
}

private fun getOffsetForColumn(col: Int) = (if (col % 2.0 == 0.0) hexHeight else 0.0)

fun List<Point>.translate(dist: Point): List<Point> {
	return this.map {
		Point(it.x + dist.x, it.y + dist.y)
	}
}

fun List<Point>.scale(factor: Point): List<Point> {
	return this.map {
		Point(it.x * factor.x, it.y * factor.y)
	}
}

fun getHexPolygon(): List<Point> {
	return listOf(
		Point(-1, 0),
		Point(-0.5, -1.0),
		Point(0.5, -1.0),
		Point(1, 0),
		Point(0.5, 1.0),
		Point(-0.5, 1.0)
	).map {
		Point(
			it.x * hexWidth,
			it.y * hexHeight
		)
	}
}