object Configuration {
    object App {

        val implementation = with(Deps) {
            listOf(
                splashScreen,
                paging,
                coreKtx,
                lifecycleViewModelKtx,
                coil,
                coilTransformations,
                logcat,
                pagingAndroid,
            ) + with(Deps.AndroidUtils) {
                listOf(
                    android,
                    coroutine,
                    compose,
                )
            } + with(Deps.Iconics) {
                listOf(
                    core,
                    material,
                )
            } + Deps.Compose.all + Common.implementation + Deps.Koin.all
        }
        val debugImplementation = listOf(
            Deps.Compose.tooling,
        )
    }

    object Data {

        val implementation = with(Deps) {
            listOf(
                logcat,
                kotlinSerialization,
                Deps.AndroidUtils.preferences,
            ) + Deps.Ktor.all + Deps.Koin.all + Common.implementation
        }
        val api = with(Deps) {
            listOf(
                paging,
            )
        }
    }

    object Common {

        val implementation = with(Deps) {
            listOf(
                stdlib,
                coroutines,
                Deps.AndroidUtils.core,
                arrow,
                flowExt,
                datetime,
            )
        }
    }

    object Core {

        val api = Common.implementation + listOf(
            Deps.kotlinSerialization, Deps.paging
        )
    }
}
