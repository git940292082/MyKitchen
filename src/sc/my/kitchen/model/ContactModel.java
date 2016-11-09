package sc.my.kitchen.model;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;
import sc.my.kitchen.app.KitchenApplication;
import sc.my.kitchen.entity.Contact;

public class ContactModel {
	public void getAllContacts(final ContactCallBack callback){
		AsyncTask<String, String, List<Contact>> task = new AsyncTask<String, String, List<Contact>>(){

			@Override
			protected List<Contact> doInBackground(String... params) {
				List<Contact> cs = loadContacts();
				
				return cs;
			}
			
			@Override
			protected void onPostExecute(List<Contact> result) {
				callback.onDataLoaded(result);
			}
			
		};
		task.execute();
	}
	
	private List<Contact> loadContacts(){
		List<Contact> contacts = new ArrayList<Contact>();
		ContentResolver r = KitchenApplication.getApp().getContentResolver();
		
		Uri contactUri = Contacts.CONTENT_URI;
		Uri dataUri = Data.CONTENT_URI;
		
		String[] projection = {Contacts._ID};
		String[] columns = {Data.MIMETYPE, Data.DATA1};
		Cursor c1 = r.query(contactUri, projection, null, null, null);
		
		while(c1.moveToNext()){
			Contact contact = new Contact();
			int id = c1.getInt(0);
			contact.setId(id);
			
			
			Cursor c2 = r.query(dataUri, columns, Data.RAW_CONTACT_ID+"=?", new String[]{id+""}, null);
			while(c2.moveToNext()){
				String mm = c2.getString(0);
				String data1 = c2.getString(1);
				if (mm.equals(Phone.CONTENT_ITEM_TYPE)) {
					contact.setPhone(data1);
				}
				else if (mm.equals("vnd.android.cursor.item/name")) {
					contact.setName(data1);
				}
			}
			c2.close();
			contacts.add(contact);
		}
		c1.close();
		return contacts;
	}
	
}
