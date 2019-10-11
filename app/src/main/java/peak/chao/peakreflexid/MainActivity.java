package peak.chao.peakreflexid;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import peak.chao.reflexid.BindId;
import peak.chao.reflexid.Id;
import peak.chao.reflexid.OnClick;

public class MainActivity extends AppCompatActivity {
    @Id(R.id.tv)
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BindId.bind(this);

        tv.setText("通过注解找到ID");
    }

    @OnClick(value = R.id.tv, type = BindId.OnClick)
    public void Click() {
        Toast.makeText(this, tv.getText().toString(), Toast.LENGTH_SHORT).show();
    }

    @OnClick(value = R.id.tv, type = BindId.OnLongClick)
    public void LongClick() {
        Toast.makeText(this, tv.getText().toString(), Toast.LENGTH_SHORT).show();
    }

}
