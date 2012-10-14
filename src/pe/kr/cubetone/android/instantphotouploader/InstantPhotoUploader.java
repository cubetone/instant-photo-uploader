package pe.kr.cubetone.android.instantphotouploader;

import java.util.ArrayList;

import pe.kr.cubetone.android.instantphotouploader.observer.PictureTakingObserver;
import android.app.ListActivity;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class InstantPhotoUploader extends ListActivity {
	private PictureTakingObserver observer;
	private static final int MAX_LIST = 30;
	private ArrayList<String> list;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(observer == null) observer = new PictureTakingObserver(getApplicationContext());
        setContentView(R.layout.activity_instant_photo_uploader);
		this.getApplicationContext()
				.getContentResolver()
				.registerContentObserver(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI, false,
						observer);
		
		list = new ArrayList<String>();
		for(int i=0; i<MAX_LIST; i++) {
			list.add("¾ÆÀÌÅÛ" + i);
		}
		
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, list));
		
		getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_instant_photo_uploader, menu);
        return true;
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.getApplicationContext().getContentResolver()
                .unregisterContentObserver(observer);
        Log.d("INSTANT", "unregistered content observer");
    }
}
