package sc.my.kitchen.adapter;

import java.util.List;

import sc.my.kitchen.entity.Contact;
import sc.my.mykitchen.R;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class ContactAdapter extends BaseAdapter{

	private Context context;
	private List<Contact> contacts;
	private LayoutInflater inflater;
	
	
	
	public ContactAdapter(Context context, List<Contact> contacts) {
		super();
		this.context = context;
		this.contacts = contacts;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return contacts.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		final Contact contact = contacts.get(position);
		ViewHolder holder = null;
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_invitate_item, null);
			holder = new ViewHolder();
			holder.tvName = (TextView) convertView.findViewById(R.id.tv_invitate_name);
			holder.btnInvitate = (Button) convertView.findViewById(R.id.btn_invitate);
			convertView.setTag(holder);
		}
		Log.i("sc", contact.getName());
		holder = (ViewHolder) convertView.getTag();
		holder.tvName.setText(contact.getName());
		holder.btnInvitate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Uri smsToUri = Uri.parse("smsto:" + contact.getPhone());
				Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
				intent.putExtra("sms_body", "欢迎下载我的厨房APP哟！");
				context.startActivity(intent);
			}
		});
		return convertView;
	}

	class ViewHolder{
		TextView tvName;
		Button btnInvitate;
	}
	
}
