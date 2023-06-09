package com.application.lostandfound.ViewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.application.lostandfound.Models.LostFoundDataModel;
import com.application.lostandfound.Repositories.LostAndFoundItemRepository;
import java.util.List;

/**
 * This acts as a bridge between the UI and the repository.
 * Also, we can use this to share information between the fragments if needed.
 * According to the guide, we can use the view model to handle the data aspects of the app and leave activities and fragments to handle the UI.
 */
public class LostFoundViewModel extends AndroidViewModel {

    // Just a note with this, I am pretty sure it will be better to use dependency injection to pass in the reference to the repository.
    // If I use this architecture for higher level tasks, I will look into DI libraries for android.
    // The idea is so that we can swap out different implementations of the repository with ease.
    private LostAndFoundItemRepository lostAndFoundItemRepository;

    // Live data for all of our lost and found items - this will be auto-updated when we insert and delete new items.
    private final LiveData<List<LostFoundDataModel>> allLostAndFoundItems;


    // We also need to keep track of the current item being viewed.
    // At this point, I don't think this needs to be a live data as I will be setting this.
    // However, there is probably a better way to handle this.
    private LostFoundDataModel currentItemToView;

    public LostFoundViewModel(@NonNull Application application) {

        super(application);

        // As mentioned, we are creating a direct dependency between the view model and the repository
        // DI is probably a better way to handle this
        lostAndFoundItemRepository = new LostAndFoundItemRepository(application);
        allLostAndFoundItems = lostAndFoundItemRepository.getAllLostItems();
    }

    // Methods dealing with the current item to view
    public LostFoundDataModel getCurrentItemToView() {

        return currentItemToView;
    }

    public void setCurrentItemToView(LostFoundDataModel currentItemToView) {

        this.currentItemToView = currentItemToView;
    }

    // Methods to fetch data in relation to the repository and by extension, the data base.
    public LiveData<List<LostFoundDataModel>> getAllLostAndFoundItems() {

        return allLostAndFoundItems;
    }

    public void insert(LostFoundDataModel newItem) {

        lostAndFoundItemRepository.insert(newItem);
    }

    public void delete(LostFoundDataModel itemToDelete){
        lostAndFoundItemRepository.delete(itemToDelete);
    }
}
