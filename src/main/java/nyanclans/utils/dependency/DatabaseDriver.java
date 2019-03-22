/*
 * This file is part of NyanClans Bukkit plug-in.
 *
 * NyanClans is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NyanClans is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NyanClans. If not, see <https://www.gnu.org/licenses/>.
 */
package nyanclans.utils.dependency;

import java.net.MalformedURLException;
import java.net.URL;

/** @author NyanGuyMF - Vasiliy Bely */
public enum DatabaseDriver {
    H2(
        "ccd0a3d6bfab69d668a3461867ec4017",
        "h2",
        "h2-driver.jar",
        "http://185.159.130.118/driver/h2"
    ),
    MYSQL(
        "80e4321ee33dd6670e3643d85bf5c7c0",
        "mysql",
        "mysql-driver.jar",
        "http://185.159.130.118/driver/mysql"
    ),
    POSTGRESQL(
        "47d66d83f9894ebbb18df20d9ad2e5d3",
        "postgresql",
        "postgresql-driver.jar",
        "http://185.159.130.118/driver/postgresql"
    );

    private final String md5sum;

    private final String driverName;

    private final String fileName;

    private URL downloadUrl;

    private DatabaseDriver(
        final String md5sum, final String driverName,
        final String fileName, final String downloadUrl
    ) {
        this.md5sum      = md5sum;
        this.driverName  = driverName;
        this.fileName    = fileName;
        try {
            this.downloadUrl = new URL(downloadUrl);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            this.downloadUrl = null;
        }
    }

    @Override public String toString() {
        return getDriverName();
    }

    /**
     * Gets MD5 hash sum of driver file.
     * <p>
     * To get it manually you can run </tt>$ md5sum {file}<tt>
     * command on Linux.
     *
     * @return MD5 hash sum of driver file.
     */
    public String getMd5sum() {
        return md5sum;
    }

    /**
     * Gets driver's name.
     * <p>
     * Should equal enum variable.
     *
     * @return Name of driver.
     */
    public String getDriverName() {
        return driverName;
    }

    /**
     * Gets driver's file name.
     * <p>
     * Usually it's {driver-name}-driver.jar.
     *
     * @return Name of driver's file.
     */
    public String getFileName() {
        return fileName;
    }

    /** @return the downloadUrl */
    public URL getDownloadUrl() {
        return downloadUrl;
    }
}
