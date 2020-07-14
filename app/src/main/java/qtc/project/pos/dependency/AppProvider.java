package qtc.project.pos.dependency;

import b.laixuantam.myaarlibrary.api.ApiManagement;
import b.laixuantam.myaarlibrary.helper.AppCleanerHelper;
import b.laixuantam.myaarlibrary.helper.AuthHelper;
import b.laixuantam.myaarlibrary.helper.ConnectivityHelper;
import b.laixuantam.myaarlibrary.helper.FileHelper;
import b.laixuantam.myaarlibrary.helper.ImageHelper;
import b.laixuantam.myaarlibrary.helper.InstallationHelper;
import b.laixuantam.myaarlibrary.helper.LanguageHelper;
import qtc.project.pos.database.DatabaseHelper;
import qtc.project.pos.helper.SharePrefs;

/**
 * Holds a singleton instance of the current object provider and provides a quick access to the
 * instances of some objects.
 */
public class AppProvider {
    private static ObjectProviderInterface instance;

    public static void init(ObjectProviderInterface objectProviderInterface) {
        instance = objectProviderInterface;
    }

    public static DatabaseHelper getDatabaseHelper() {
        return instance.getDatabaseHelper();
    }

    public static ImageHelper getImageHelper() {
        return instance.getImageHelper();
    }

    public static SharePrefs getPreferences() {
        return instance.getPreferences();
    }

    public static InstallationHelper getInstallationHelper() {
        return instance.getInstallationHelper();
    }

    public static AppCleanerHelper getAppCleanerHelper() {
        return instance.getAppCleanerHelper();
    }

    public static FileHelper getFileHelper() {
        return instance.getFileHelper();
    }

    public static ConnectivityHelper getConnectivityHelper() {
        return instance.getConnectivityHelper();
    }

    public static ApiManagement getApiManagement() {
        return instance.getApiManagement();
    }

    public static LanguageHelper getLanguageHelper() {
        return instance.getLanguageHelper();
    }

    public static AuthHelper getAuthHelper(){
        return instance.getAuthHelper();
    }

}