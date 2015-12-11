package vn.pokemon.screens

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.utils.Scaling
import vn.pokemon.PokemonGame
import vn.pokemon.utils.Algorithm
import vn.pokemon.asset.Assets
import vn.pokemon.ui.PokemonImage
import vn.pokemon.model.*
import java.util.*

/**
 * Created by truongps on 12/6/15.
 */
class GameScreen : BaseScreen {
    val TAG : String = "GameScreen"
    lateinit var gameBoard : ArrayList<ArrayList<PokemonImage>>


    inner class EatEffectDoneAction : Action {

        constructor() : super() {

        }

        override fun act(delta: Float): Boolean {
            reOrderMatrix()
            return true
        }
    }

    inner class ShowEffectDoneAction : Action {
        constructor() : super() {

        }

        override fun act(delta: Float): Boolean {
            closePokemon(actor as PokemonImage)
            return true
        }
    }

    inner class SetImageDrawableAction : Action {
        var drawable : Drawable
        constructor(drawable: Drawable) : super() {
            this.drawable = drawable
        }

        override fun act(delta: Float): Boolean {
            if (actor is PokemonImage) {
                (actor as PokemonImage).drawable = drawable
            }
            return true
        }
    }

    inner class PokemonGestureListener : ActorGestureListener {
        constructor() : super() {

        }

        override fun tap(event: InputEvent?, x: Float, y: Float, count: Int, button: Int) {
            if (event!!.target is ImageButton)  {
                when (event!!.target.name) {
                    "back_button" -> {
                        game.showMenuScreen()
                    }
                }
                return
            }
            if (selectedImage == null && event?.target is PokemonImage) {
                selectedImage = event?.target as PokemonImage
                if (gameType == GAME_TYPE_HIDDEN) {
                    openPokemon(selectedImage)
                }
            } else if (event?.target is Image)  {
                var lastSelectedImage = event?.target as PokemonImage
                var connectionPath : HintPath? = algorithm.checkTwoPointOK(selectedImage!!.point, lastSelectedImage.point)
                if (connectionPath == null) {
                    // Wrong, reset all selections.
                    selectedImage!!.color.a = 1f
                    lastSelectedImage.color.a = 1f
                    if (gameType == GAME_TYPE_HIDDEN) {
                        openPokemon(lastSelectedImage)
                        var action = Actions.sequence(Actions.delay(SHOW_DELAY), ShowEffectDoneAction())
                        selectedImage!!.addAction(action)
                        action = Actions.sequence(Actions.delay(SHOW_DELAY), ShowEffectDoneAction())
                        lastSelectedImage.addAction(action)
                    }
                } else {
                    // Right, score
                    selectedImage!!.color.a = 1f
                    lastSelectedImage.color.a = 1f
                    if (gameType == GAME_TYPE_HIDDEN) {
                        openPokemon(lastSelectedImage)
                    }
                    // Update score
                    score += SCORE_UNIT
                    setScoreLabel(score)
                    Gdx.app.log("GAME SCORE", "Score: $score")

                    algorithm.resetPoint(selectedImage!!.point)
                    algorithm.resetPoint(lastSelectedImage.point)

                    updatePokemons(true)
                    setHintPath(connectionPath)
                }
                selectedImage = null
            }
        }

        override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
            if (gameType == GAME_TYPE_HIDDEN) {

            } else {
                event?.target?.getColor()?.a = 0.5f
            }
        }

        override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
            if (gameType == GAME_TYPE_HIDDEN) {

            } else {
                if (selectedImage == null) {
                    event!!.target.color.a = 1f
                }
            }
        }
    }

    var numberOfRow : Int = 0 // Actual 10
    var numberOfCol : Int = 0 // Actual 7
    var topHeight : Int = 0
    var boardHeight : Int = 0
    var itemSize : Int = 0
    var boardPadding : Int = 0

    var algorithm : Algorithm = Algorithm()
    var selectedImage : PokemonImage? = null

    val SCORE_UNIT : Int = 10
    var score : Int = 0

    var scoreLabel : Label

    var line1 : Image
    var line2 : Image
    var line3 : Image

    val HINT_TIME : Float = 1.4f
    val LINE_A : Float = 0.5f

    var isHandleCannotContinue : Boolean = false

    val SHOW_DELAY = 1f
    val GAME_TYPE_NORMAL = 0
    val GAME_TYPE_HIDDEN = 1
    var gameType: Int = GAME_TYPE_NORMAL

    constructor(game: PokemonGame) : super(game) {
        var background = Image(game.assets.secondBackground)
        background.setScaling(Scaling.stretch)
        background.setSize(width, height)
        addActor(background)

        var backButton = game.uiUtils.createImageButton(null, game.assets.backButton)
//        backButton.setSize(backButton.width * 4, backButton.height * 4)
        backButton.setPosition(10f, height - backButton.height - 10)
        backButton.addListener(PokemonGestureListener())
        backButton.image.setScaling(Scaling.stretch)
        backButton.name = "back_button"
        addActor(backButton)

        algorithm.gameSkinType = "a"

        // Calculate number of column and number of row for device.
        topHeight = (height / 6).toInt()
        boardHeight = (height - topHeight).toInt()
        numberOfCol = 7
        itemSize = (width / numberOfCol).toInt()

        while ((numberOfRow + 1) * itemSize < boardHeight) {
            numberOfRow++
        }
        numberOfCol--//Bo 1 o de lam padding
        boardPadding = ((width - numberOfCol * itemSize) / 2).toInt()

        // Set lai number of col
        numberOfCol = 5
        // End of calculation

        var labelStyle = Label.LabelStyle()
        labelStyle.font = game.assets.scoreFont
        labelStyle.fontColor = Color.DARK_GRAY
        scoreLabel = Label("Score: 0", labelStyle)
        scoreLabel.y = height - scoreLabel.height
        scoreLabel.x = (width - scoreLabel.width) / 2
        addActor(scoreLabel)

        initGameBoard()
        addGameBoardToScreen()

        line1 = Image(game.assets.greenLine)
        line2 = Image(game.assets.greenLine)
        line3 = Image(game.assets.greenLine)

//        setHintLine(MyLine(Point(0,0), Point(0, numberOfCol + 2)), line1)
//        setHintLine(MyLine(Point(numberOfRow,0), Point(numberOfRow, numberOfCol + 1)), line1)
    }

    override fun show() {
        super.show()

    }

    fun changeGameType(gameType : Int) {
        if (gameType == GAME_TYPE_NORMAL) {
            this.gameType = gameType
        } else if (gameType == GAME_TYPE_HIDDEN) {
            this.gameType = gameType
            for (row in 0..numberOfRow) {
                for (col in 0..numberOfCol) {
                    closePokemon(gameBoard[row][col])
                }
            }
        }
    }

    fun openPokemon(image : PokemonImage?) {
        if (image != null) {
            Gdx.app.log(TAG, "openPokemon " + image.pId)
            if (image.pId == algorithm.barrier) {
                return
            }
            var drawble = game.assets.getPokemon(image.pId)
            if (image.drawable != drawble) {
//                image.drawable = drawble
                var action = Actions.sequence(Actions.scaleTo(0f, 1f, 0.1f), SetImageDrawableAction(drawble as Drawable), Actions.scaleTo(1f, 1f, 0.1f))
                image.addAction(action)
            }
        }
    }

    fun closePokemon(image : PokemonImage?) {
        if (image != null) {
            if (image.drawable != game.assets.pokemonBackground) {
                var action = Actions.sequence(Actions.scaleTo(0f, 1f, 0.1f), SetImageDrawableAction(game.assets.pokemonBackground as Drawable), Actions.scaleTo(1f, 1f, 0.1f))
                image.addAction(action)
            }
        }
    }

    fun setScoreLabel(score : Int) = scoreLabel.setText("Score: " + score)

    fun initGameBoard() {
        algorithm.numberOfImage = game.assets.pokemons.size - 1
        algorithm.createMatrix(numberOfRow + 2, numberOfCol + 2)
        gameBoard = ArrayList()
        for (row in 0..numberOfRow) {
            var rowList = ArrayList<PokemonImage>()
            for (col in 0..numberOfCol) {
                var pId : String = algorithm.matrix.get(row + 1).get(col + 1)
                var region = game.assets.getPokemon(pId)
                if (region == null) {
                    Gdx.app.log(TAG, "Pokemon region == null")
                }
                var img = PokemonImage(pId, Point(row + 1, col + 1), region)
                img.addListener(PokemonGestureListener())
                img.touchable = Touchable.enabled
                rowList.add(img)
            }
            gameBoard.add(rowList)
        }
    }

    fun setHintPath(path : HintPath) {
        if (path.line1 != null && !path.line1!!.isPoint()) {
            setHintLine(path.line1!!, line1)
        } else {
            line1.remove()
        }
        if (path.line2 != null && !path.line2!!.isPoint()) {
            setHintLine(path.line2!!, line2)
        } else {
            line2.remove()
        }
        if (path.line3 != null && !path.line3!!.isPoint()) {
            setHintLine(path.line3!!, line3)
        } else {
            line3.remove()
        }
    }

    fun setHintLine(line : MyLine, lineImage : Image) {
        Gdx.app.log(TAG, "setHintLine " + line.toString())
        var min = line.p1
        var max = line.p2
        var extra = 20
        var cell : PokemonImage
        if (line.isRow()) {
            lineImage.drawable = NinePatchDrawable(game.assets.greenLine)
            lineImage.height = lineImage.drawable.minHeight
            if (line.p1.col > line.p2.col) {
                min = line.p2
                max = line.p1
            }

            if (min.col == 0 && min.row == 0) {
                // Left - Bottom
                cell = gameBoard[0][0]
                lineImage.setPosition(cell.x - extra, cell.y - extra)
            } else if (min.col == 0 && min.row == numberOfRow + 2) {
                // Left - Top
                cell = gameBoard[numberOfRow][0]
                lineImage.setPosition(cell.x - extra, cell.y + itemSize + extra)
            } else if (min.col == 0) {
                // Left - Center
                cell = gameBoard[min.row - 1][0]
                lineImage.setPosition(cell.x - extra, cell.y + itemSize / 2)
            } else if (min.row == 0) {
                // Bottom
                cell = gameBoard[0][min.col - 1]
                lineImage.setPosition(cell.x + itemSize / 2, cell.y - extra)
            } else if (min.row == numberOfRow + 2) {
                // Top
                cell = gameBoard[numberOfRow][min.col - 1]
                lineImage.setPosition(cell.x + itemSize / 2, cell.y + itemSize + extra)
            } else {
                // In board
                cell = gameBoard[min.row - 1][min.col - 1]
                lineImage.setPosition(cell.x + itemSize / 2, cell.y + itemSize / 2)
            }

            if (max.col == numberOfCol + 2) {
                // To Right Edge
                cell = gameBoard[0][numberOfCol]
                lineImage.width = cell.x + itemSize + extra - lineImage.x
            }
//            else if (max.col == 0) {
//                // Left
//                cell = gameBoard[0][0]
//                lineImage.width = cell.x + extra - lineImage.x
//            }
            else {
                cell = gameBoard[0][max.col - 1]
                lineImage.width = cell.x + itemSize / 2 - lineImage.x
            }
        } else {
            lineImage.drawable = NinePatchDrawable(game.assets.greenLineVer)
            lineImage.width = lineImage.drawable.minWidth
            if (line.p1.row > line.p2.row) {
                min = line.p2
                max = line.p1
            }

            if (min.row == 0 && min.col == 0) {
                // Bottom - Left
                cell = gameBoard[0][0]
                lineImage.setPosition(cell.x - extra, cell.y - extra)
            } else if (min.row == 0 && min.col == numberOfCol + 2) {
                // Bottom - Right
                cell = gameBoard[0][numberOfCol]
                lineImage.setPosition(cell.x + itemSize + extra, cell.y - extra)
            } else if (min.row == 0) {
                // Bottom - Center
                cell = gameBoard[0][min.col - 1]
                lineImage.setPosition(cell.x + itemSize / 2, cell.y - extra)
            } else if(min.col == 0) {
                // Left
                cell = gameBoard[min.row - 1][0]
                lineImage.setPosition(cell.x - extra, cell.y + itemSize / 2)
            } else if (min.col == numberOfCol + 2) {
                // Right
                cell = gameBoard[min.row - 1][numberOfCol]
                lineImage.setPosition(cell.x + itemSize + extra, cell.y + itemSize / 2)
            } else {
                // In board
                cell = gameBoard[min.row - 1][min.col - 1]
                lineImage.setPosition(cell.x + itemSize / 2, cell.y + itemSize / 2)
            }

            if (max.row == numberOfRow + 2) {
                // Top
                cell = gameBoard[numberOfRow][0]
                lineImage.height = cell.y + itemSize + extra - lineImage.y
            }
//            else if (max.row == 0) {
//                // Bottom
//                cell = gameBoard[0][0]
//                lineImage.height = cell.y + extra - lineImage.y
//            }
            else {
                // In board
                cell = gameBoard[max.row - 1][0]
                lineImage.height = cell.y + itemSize / 2 - lineImage.y
            }
        }
        lineImage.color.a = LINE_A
        lineImage.clearActions()
        var action = Actions.fadeOut(HINT_TIME / 2)
        lineImage.addAction(action)
        addActor(lineImage)
        addActor(scoreLabel)
    }

    fun updatePokemons(effect : Boolean) {
        for (row in 0..numberOfRow) {
//            var rowLog = ""
            for (col in 0..numberOfCol) {
                var pId : String = algorithm.matrix[row + 1][col + 1]
                gameBoard[row][col].pId = pId
                var isInvisible = pId.equals(algorithm.barrier)
                if (isInvisible == true && gameBoard[row][col].isVisible == true) {
//                    Gdx.app.log("GameScreen", "Hide")
                    // Do effect
                    gameBoard[row][col].touchable = Touchable.disabled
                    if (!isHandleCannotContinue && !algorithm.isClear() && algorithm.getHint() == null) {
                        isHandleCannotContinue = true
                        var action = Actions.sequence(Actions.scaleTo(1.2f, 1.2f, HINT_TIME / 4), Actions.parallel(Actions.scaleTo(0f, 0f, HINT_TIME / 2), Actions.fadeOut(HINT_TIME / 2)), EatEffectDoneAction())
                        if (gameType == GAME_TYPE_HIDDEN) {
                            action = Actions.sequence(Actions.delay(0.3f), action)
                        }
                        gameBoard[row][col].addAction(action)
                    } else {
                        var action = Actions.sequence(Actions.scaleTo(1.2f, 1.2f, HINT_TIME / 4), Actions.parallel(Actions.scaleTo(0f, 0f, HINT_TIME / 2), Actions.fadeOut(HINT_TIME / 2)))
                        if (gameType == GAME_TYPE_HIDDEN) {
                            action = Actions.sequence(Actions.delay(0.3f), action)
                        }
                        gameBoard[row][col].addAction(action)
                    }
                    addActor(gameBoard[row][col])
                }
//                rowLog += pId + " "
            }
//            Gdx.app.log("Update Pokemon", rowLog)
        }
    }

    fun addGameBoardToScreen() {
        var x = boardPadding.toFloat()
        var y = boardPadding

        for (row in gameBoard) {
            for (item in row) {
                item.setPosition(x.toFloat(), y.toFloat())
                item.setSize(itemSize.toFloat(), itemSize.toFloat())
                item.setOrigin(item.width / 2, item.height / 2)
                item.setScaling(Scaling.stretch)
                addActor(item)
                x += itemSize
            }
            x = boardPadding.toFloat()
            y += itemSize
        }
        isHandleCannotContinue = false
    }

    fun reOrderMatrix() {
        Gdx.app.log(TAG, "reOrderMatrix")
        algorithm.shuffle()
        for (row in 0..numberOfRow) {
            for (col in 0..numberOfCol) {
                var pId : String = algorithm.matrix[row + 1][col + 1]
                if (!pId.equals(algorithm.barrier)) {
                    var region: TextureAtlas.AtlasRegion = game.assets.getPokemon(pId) as TextureAtlas.AtlasRegion
                    var img = gameBoard[row][col]
                    img.clearActions()
                    img.setScale(1f)
                    img.color.a = 1f
                    img.isVisible = true
                    img.drawable = TextureRegionDrawable(region)
                    img.pId = pId
                    img.touchable = Touchable.enabled
                    addActor(img)
                } else {
                    var img = gameBoard[row][col]
                    img.touchable = Touchable.disabled
                    img.isVisible = false
                    img.pId = pId
                    addActor(img)
                }
            }
        }
    }

}