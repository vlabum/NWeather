package ru.vlabum.android.apps.nweather.util;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import com.google.common.collect.FluentIterable;
import com.google.common.primitives.Ints;

import java.util.Collection;
import java.util.List;

public class PermissionsActivity extends AppCompatActivity {

    static final int REQUEST_CODE = 5;

    protected void onAllow() {
    }

    protected void onRefuse() {
    }

    public void askPermissions(Collection<String> permissions) {
        List<String> notAllowedPermissions =
                FluentIterable.from(permissions)
                        .filter(permission -> !isPermissionAllowed(permission))
                        .toList();
        if (notAllowedPermissions.isEmpty()) {
            onAllow();
            return;
        }
        ActivityCompat.requestPermissions(
                this,
                notAllowedPermissions.toArray(new String[notAllowedPermissions.size()]),
                REQUEST_CODE);
    }

    boolean isPermissionAllowed(String permission) {
        return ContextCompat.checkSelfPermission(this, permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != REQUEST_CODE) {
            return;
        }
        if (FluentIterable.from(Ints.asList(grantResults)).allMatch(integer -> integer == PackageManager.PERMISSION_GRANTED)) {
            onAllow();
        } else {
            onRefuse();
        }
    }
}
