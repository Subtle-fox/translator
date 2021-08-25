package com.andyanika.translator.repository.local;

//import androidx.room.Room;
//import android.content.Context;
//import android.content.SharedPreferences;
//
//import com.andyanika.translator.common.interfaces.LocalRepository;
//import com.andyanika.translator.common.scopes.ApplicationScope;
//
//import javax.inject.Named;
//
//import dagger.Module;
//import dagger.Provides;
//import io.reactivex.rxjava3.core.Scheduler;
//
//@Module
//public class LocalRepositoryModule {
//    @Provides
//    @ApplicationScope
//    LocalRepository getDataSource(TranslatorDao dao, SharedPreferences preferences, ModelsAdapter adapter, @Named("io") Scheduler ioScheduler) {
//        return new LocalRepositoryImpl(dao, preferences, adapter, ioScheduler);
//    }
//
//    @Provides
//    @ApplicationScope
//    TranslatorDao provideDatabase(@Named("app") Context context) {
//        return Room.databaseBuilder(context, DatabaseTranslator.class, "words_database").build().translatorDao();
//    }
//
//    @Provides
//    @ApplicationScope
//    SharedPreferences providePreferences(@Named("app") Context ctx) {
//        return ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
//    }
//}
