package pokemon.ios

import com.badlogic.gdx.backends.iosrobovm.IOSApplication
import com.badlogic.gdx.backends.iosrobovm.IOSApplication.Delegate
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration
import org.robovm.apple.foundation.NSAutoreleasePool
import org.robovm.apple.uikit.UIApplication
import vn.pokemon.PokemonGame



class IOSKotlinLauncher : Delegate {

    constructor() : super() {

    }

    protected override fun createApplication(): IOSApplication {
        val config = IOSApplicationConfiguration()
        return IOSApplication(PokemonGame(), config)
    }

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            val pool = NSAutoreleasePool()
            UIApplication.main<UIApplication, IOSKotlinLauncher>(args, null, IOSKotlinLauncher::class.java)
            pool.release()
        }
    }

//    public fun main(argv: Array<String>) : Unit {
////        val pool = NSAutoreleasePool()
////        UIApplication.main<UIApplication, IOSKotlinLauncher>(argv, null, IOSKotlinLauncher::class.java)
////        pool.close()
//    }
}