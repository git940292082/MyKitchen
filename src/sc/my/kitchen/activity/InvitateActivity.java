package sc.my.kitchen.activity;

import java.util.List;

import sc.my.kitchen.adapter.ContactAdapter;
import sc.my.kitchen.app.KitchenApplication;
import sc.my.kitchen.entity.Contact;
import sc.my.kitchen.util.AppManager;
import sc.my.mykitchen.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class InvitateActivity extends Activity {

	private ListView lvInvitate;
	private ContactAdapter adapter;
	private List<Contact> contacts;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invitate);
		AppManager.getAppManager().addActivity(this);
		
		contacts = KitchenApplication.getContacts();
		
		lvInvitate = (ListView) findViewById(R.id.lv_invitate_list);
		adapter = new ContactAdapter(this, contacts);
		lvInvitate.setAdapter(adapter);
	}

}
