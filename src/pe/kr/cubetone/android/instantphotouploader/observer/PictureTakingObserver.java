package pe.kr.cubetone.android.instantphotouploader.observer;

import java.io.File;

import pe.kr.cubetone.android.instantphotouploader.persistence.Media;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;

public class PictureTakingObserver extends ContentObserver {
	private Context context = null;
	
	public PictureTakingObserver(Context context) {
		super(null);
		this.context = context;
	}
	
	@Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        Media media = readFromMediaStore(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Log.d("INSTANT", "=========================================");
        Log.d("INSTANT", "file=" + media.getFile().getAbsolutePath());
        Log.d("INSTANT", "=========================================");
        
        //saved = "I detected " + media.getFile().getName();
        Log.d("INSTANT", "detected picture");
    }
	
	private Media readFromMediaStore(Context context, Uri uri) {
	    Cursor cursor = context.getContentResolver().query(uri, null, null,
	        null, "date_added DESC");
	    Media media = null;
	    if (cursor.moveToNext()) {
	        int dataColumn = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
	        String filePath = cursor.getString(dataColumn);
	        int mimeTypeColumn = cursor.getColumnIndexOrThrow(MediaColumns.MIME_TYPE);
	        String mimeType = cursor.getString(mimeTypeColumn);
	        media = new Media(new File(filePath), mimeType);
	    }
	    cursor.close();
	    return media;
	}

}
