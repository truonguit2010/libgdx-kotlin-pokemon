package vn.pokemon.screens

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
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

    inner class PokemonGestureListener : ActorGestureListener {
        constructor() : super() {

        }

        override fun tap(event: InputEvent?, x: Float, y: Float, count: Int, button: Int) {
//            if (count > 0) {
//                return
//            }
            if (event!!.target is ImageButton)  {
                when (event!!.target.name) {
                    "back_button" -> {
                        game.showMenuScreen()
                    }
                }
                return
            }

            if (selectedImage == null && event?.target is Image) {
                selectedImage = event?.target as PokemonImage
            } else if (event?.target is Image)  {
                var lastSelectedImage = event?.target as PokemonImage
                var connectionLine : MyLine? = algorithm.checkTwoPointOK(selectedImage!!.point, lastSelectedImage.point)
                if (connectionLine == null) {
                    // Wrong, reset all selections.
                    selectedImage!!.color.a = 1f
                    lastSelectedImage.color.a = 1f
                } else {
                    // Right, score
                    selectedImage!!.color.a = 1f
                    lastSelectedImage.color.a = 1f
                    // Update score
                    score += SCORE_UNIT
                    setScoreLabel(score)
                    Gdx.app.log("GAME SCORE", "Score: $score")

                    algorithm.resetPoint(selectedImage!!.point)
                    algorithm.resetPoint(lastSelectedImage.point)

                    updatePokemons()
                }
                selectedImage = null
            }
            Gdx.app.log(TAG, "tap")
        }

        override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
            event?.target?.getColor()?.a = 0.5f
            Gdx.app.log(TAG, "touch down")
        }

        override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
            Gdx.app.log(TAG, "touch up")
            if (selectedImage == null) {
                event!!.target.color.a = 1f
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

        var labelStyle = Label.LabelStyle()
        labelStyle.font = game.assets.scoreFont
        labelStyle.fontColor = Color.DARK_GRAY
        scoreLabel = Label("Score: 0", labelStyle)
        scoreLabel.y = height - scoreLabel.height
        scoreLabel.x = (width - scoreLabel.width) / 2
        addActor(scoreLabel)

        initGameBoard()
        addGameBoardToScreen()
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
                var region : TextureAtlas.AtlasRegion = game.assets.getPokemon(pId) as TextureAtlas.AtlasRegion
                var img = PokemonImage(pId, Point(row + 1, col + 1), region)
                img.addListener(PokemonGestureListener())
                img.touchable = Touchable.enabled
                rowList.add(img)
            }
            gameBoard.add(rowList)
        }
    }

    fun updatePokemons() {
        for (row in 0..numberOfRow) {
            var rowLog = ""
            for (col in 0..numberOfCol) {
                var pId : String = algorithm.matrix.get(row + 1).get(col + 1)
                var isInvisible = pId.equals(algorithm.barrier)
                if (isInvisible == true) {
                    Gdx.app.log("GameScreen", "Hide")
                }
                gameBoard.get(row).get(col).isVisible = !isInvisible
                rowLog += pId + " "
            }
            Gdx.app.log("Update Pokemon", rowLog)
        }
    }

    fun addGameBoardToScreen() {
        var x = boardPadding.toFloat()
        var y = numberOfRow * itemSize + boardPadding

        for (row in gameBoard) {
            for (item in row) {
                item.setPosition(x.toFloat(), y.toFloat())
                item.setSize(itemSize.toFloat(), itemSize.toFloat())
                item.setScaling(Scaling.stretch)
                addActor(item)
                x += itemSize
            }
            x = boardPadding.toFloat()
            y -= itemSize
        }
    }




}