//import com.cjmobileapps.quidditch_players_kmm_2024.room.DatabaseFactory
//import com.cjmobileapps.quidditch_players_kmm_2024.room.QuidditchPlayersDatabase
import com.cjmobileapps.quidditch_players_kmm_2024.room.DatabaseFactory
import com.cjmobileapps.quidditch_players_kmm_2024.room.QuidditchPlayersDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual val databaseModule: Module = module {
    single<QuidditchPlayersDatabase> { DatabaseFactory.getDB(context = get()) }
}
